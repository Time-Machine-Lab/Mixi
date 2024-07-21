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
    void storeUserMessage(String channelId,TimelineMessage timelineMessage);
    List<TimelineMessage> queryRoomHistoryMsg(String roomId);
    List<TimelineMessage> consumeMsgIfNeed(String channelId, String roomId);
}
