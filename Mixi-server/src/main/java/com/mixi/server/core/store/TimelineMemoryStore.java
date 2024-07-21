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
    private ConcurrentHashMap<String, Deque<TimelineMessage>> roomStore = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Deque<TimelineMessage>> userStore = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();
    @Override
    public void storeRoomMessage(TimelineMessage timelineMessage) {
        Deque<TimelineMessage> list = roomStore.computeIfAbsent(timelineMessage.getRoomId(), (key) -> new ConcurrentLinkedDeque<>());
        list.add(timelineMessage);
        //这里需要考虑定期删除？如果消息无限膨胀占用内存
    }

    @Override
    public void storeUserMessage(String channelId,TimelineMessage timelineMessage) {
        Deque<TimelineMessage> list = userStore.computeIfAbsent(channelId, (key) -> new ConcurrentLinkedDeque<>());
        list.add(timelineMessage);
    }

    @Override
    public List<TimelineMessage> queryRoomHistoryMsg(String roomId) {
        Deque<TimelineMessage> deque = roomStore.get(roomId);
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
        if(userStore.get(channelId).getLast().getId()<roomStore.get(roomId).getLast().getId()){
            return null;
        }
        Deque<TimelineMessage> userMessages = userStore.get(channelId);
        Deque<TimelineMessage> roomMessages = roomStore.get(roomId);
        List<TimelineMessage> res = new ArrayList<>();
        try {
            lock.lock();
            if (userMessages != null && roomMessages != null) {
                TimelineMessage lastUserMessage = userMessages.peekLast();
                TimelineMessage lastRoomMessage = roomMessages.peekLast();

                if (lastUserMessage != null && lastRoomMessage != null) {
                    // 找出用户最后一条消息的下一个消息在房间中的位置
                    Iterator<TimelineMessage> roomIterator = roomMessages.iterator();
                    while (roomIterator.hasNext()) {
                        TimelineMessage msg = roomIterator.next();
                        if (msg.equals(lastUserMessage)) {
                            // 房间中从下一个消息开始的集合
                            while (roomIterator.hasNext()) {
                                res.add(roomIterator.next());
                            }
                            // messagesFromNext 就是从 room 中从下一个消息开始的集合
                            break;
                        }
                    }
                }
            }
        }finally {
            lock.unlock();
        }
        return res;
    }

}
