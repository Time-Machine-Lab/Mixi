package com.mixi.server.core.store;

import com.mixi.server.pojo.VO.TimelineMessage;

import java.util.List;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/16 19:21
 */
public interface MixiTimelineStore {

    void storeRoomMessage(TimelineMessage timelineMessage);
    void registerUserCursor(String channelId);
    List<TimelineMessage> queryRoomHistoryMsg(String roomId);
    List<TimelineMessage> consumeMsgIfNeed(String channelId, String roomId);

    void registerRoomStore(String roomId);
    void removeUserStore(String channelId);
    void removeRoomStore(String roomId);
}
