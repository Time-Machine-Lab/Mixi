package com.mixi.server.core.concurrent;

import com.mixi.server.core.ack.AckMessageHandler;
import com.mixi.server.netty.channel.MixiNettyChannel;
import com.mixi.server.netty.channel.RoomChannelManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/18 17:55
 */
@Component
public class RoomConsumerPool {
    private ConcurrentHashMap<String,WorkThread> pool = new ConcurrentHashMap<>();
    @Resource
    AckMessageHandler ackHandler;

    public void consume(String roomId,String uid){
        MixiNettyChannel nettyChannel = MixiNettyChannel.getChannelById(uid);
        nettyChannel.setSleep(false);
        pool.get(roomId).startWork();
    }

    public void addConsumer(String roomId){
        pool.put(roomId,new WorkThread(roomId));
        pool.get(roomId).start();
    }

    public void removeConsumer(String roomId){
        pool.remove(roomId);
    }
    private final class WorkThread extends Thread{
        private final String roomId;
        private volatile boolean work = false;
        private WorkThread(String roomId) {
            this.roomId = roomId;
        }
        @Override
        public void run() {
            while (work) {
                RoomChannelManager.RoomInfo roomInfo = RoomChannelManager.getRoomInfo(roomId);
                for (MixiNettyChannel channel : roomInfo.getChannels()) {
                    //sleep这个状态在被处理一次后就会为true 防止重复消费
                    if(!channel.isSleep()){
                        dispatchConsumer(channel);
                    }
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public void startWork(){
            this.work=true;
        }

        private void dispatchConsumer(MixiNettyChannel channel) {
            if(!channel.isSleep()){
                channel.setSleep(true);
                ackHandler.consumerAck(channel);
            }
        }
    }
}
