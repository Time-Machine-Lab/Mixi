package com.mixi.server.core.chatroom;

import com.mixi.server.core.concurrent.RoomConsumerPool;
import com.mixi.server.core.store.MixiTimelineStore;
import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.RoomChannelManager;
import com.mixi.server.pojo.VO.TimelineMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** concurrent
 * @Description
 * @Author welsir
 * @Date 2024/7/16 19:21
 */
@Component
public class TimelineMessageManager implements MixiTimeline{

    private MixiTimelineStore timelineStore;
    private RoomConsumerPool pool;
    @Autowired
    public TimelineMessageManager(MixiTimelineStore timelineStore,RoomConsumerPool roomConsumerPool){
        this.timelineStore = timelineStore;
        this.pool = roomConsumerPool;
    }

    @Override
    public void push(TimelineMessage message) {
        //这里也同理
        timelineStore.storeRoomMessage(message);
        for (MixiNettyChannel channel : RoomChannelManager.getRoomInfo(message.getRoomId()).getChannels()) {
            //如果并发量大，这里很可能会乱序添加。目前先不处理，留个坑
            timelineStore.storeUserMessage(channel.getChannelId(),message);
        }
        pool.consume(message.getRoomId(),message.getFromId());
    }

    @Override
    public void registerConsumer(String roomId) {
        pool.addConsumer(roomId);
    }

    @Override
    public void removeConsumer(String roomId) {
        pool.removeConsumer(roomId);
    }
}
