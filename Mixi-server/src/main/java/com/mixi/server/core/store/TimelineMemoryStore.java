package com.mixi.server.core.store;

import com.mixi.server.config.ServerProperties;
import com.mixi.server.pojo.VO.TimelineMessage;
import com.mixi.server.util.ApplicationContextUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/16 19:22
 */
@Component
public class TimelineMemoryStore implements MixiTimelineStore{
    private ConcurrentHashMap<String, List<TimelineMessage>> store = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,List<Integer>> consumerStore = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void storeRoomMessage(TimelineMessage timelineMessage) {
        List<TimelineMessage> list = store.computeIfAbsent(timelineMessage.getRoomId(), (key) -> new CopyOnWriteArrayList<>());
        list.add(timelineMessage);
    }

    @Override
    public void registerUserCursor(String channelId) {
        consumerStore.computeIfAbsent(channelId,(key)-> new CopyOnWriteArrayList<>()).add(1);
    }

    @Override
    public List<TimelineMessage> queryRoomHistoryMsg(String roomId) {
        List<TimelineMessage> deque = store.get(roomId);
        if (deque == null || deque.isEmpty()) {
            return Collections.emptyList();
        }
        List<TimelineMessage> list = new ArrayList<>(deque);
        ServerProperties properties = ApplicationContextUtils.getBean(ServerProperties.class);
        int start = Math.max(list.size() - properties.getMaxHistoryNum(), 0);
        return list.subList(start, list.size());
    }
    @Override
    public List<TimelineMessage> consumeMsgIfNeed(String channelId, String roomId) {
        Integer roomLastId = store.get(roomId).get(store.get(roomId).size()-1).getId();
        Integer userLastId = consumerStore.get(channelId).get(consumerStore.get(channelId).size()-1);
        if(Objects.equals(userLastId, roomLastId)){
            return null;
        }
        List<TimelineMessage> roomMessages = store.get(roomId);
        List<TimelineMessage> res = new ArrayList<>();
        try {
            lock.lock();
            int pullNums = roomLastId-userLastId;
            for(int i=pullNums;i<roomMessages.size();i++){
                res.add(roomMessages.get(i));
            }
        }finally {
            lock.unlock();
        }
        return res;
    }

    @Override
    public void registerRoomStore(String roomId) {
        List<TimelineMessage> list = new CopyOnWriteArrayList<>();
        list.add(new TimelineMessage(1));
        store.put(roomId,list);
    }

    @Override
    public void removeUserStore(String channelId) {
        consumerStore.remove(channelId);
    }

    @Override
    public void removeRoomStore(String roomId) {
        store.remove(roomId);
    }
}
