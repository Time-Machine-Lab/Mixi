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
        pool.get(roomId).sleep=false;
    }

    public void addConsumer(String roomId){
        WorkThread workThread = pool.computeIfAbsent(roomId, key -> {
            WorkThread newThread = new WorkThread(key);
            newThread.start();
            return newThread;
        });
    }

    public void removeConsumer(String roomId){
        pool.remove(roomId);
    }
    private final class WorkThread extends Thread{
        private final String roomId;
        private volatile boolean work = true;
        private volatile boolean sleep = true;
        private WorkThread(String roomId) {
            this.roomId = roomId;
        }
        @Override
        public void run() {
            try {
                while (work) {
                    RoomChannelManager.RoomInfo roomInfo = RoomChannelManager.getRoomInfo(roomId);
                    if(roomInfo==null)
                        continue;
                    for (MixiNettyChannel channel : roomInfo.getChannels()) {
                        //sleep这个状态在被处理一次后就会为true 防止重复消费
                        if(!sleep){
                            dispatchConsumer(channel);
                        }
                    }
                    sleep=true;
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public void startWork(){
            this.work=true;
        }

        private void dispatchConsumer(MixiNettyChannel channel) {
            if(!sleep){
                ackHandler.consumerAck(channel);
            }
        }
    }
}
