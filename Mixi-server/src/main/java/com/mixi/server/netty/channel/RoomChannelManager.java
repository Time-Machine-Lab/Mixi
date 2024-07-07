package com.mixi.server.netty.channel;

import cn.hutool.core.collection.ConcurrentHashSet;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author welsir
 * @Date 2024/7/7 18:57
 */
public class RoomChannelManager {

    private final Map<String, RoomInfo> roomInfoMap = new ConcurrentHashMap<>();
    @Data
    public static class RoomInfo {
        private final String name;
        private final Set<MixiNettyChannel> channels;
        private final Map<String, Set<MixiNettyChannel>> memberChannelsMap;

        public RoomInfo(String name) {
            this.name = name;
            this.channels = new ConcurrentHashSet<>();
            this.memberChannelsMap = new ConcurrentHashMap<>();
        }

        public boolean registerUid(String uid, MixiNettyChannel channel) {
            return memberChannelsMap.computeIfAbsent(uid, k -> new ConcurrentHashSet<>(1)).add(channel);
        }

        public boolean deregisterUid(MixiNettyChannel channel) {
            String oldUid = channel.getAttrs().getUid();
            if (StringUtils.isBlank(oldUid)) {
                return false;
            }
            Set<MixiNettyChannel> channels = memberChannelsMap.get(oldUid);
            if (channels != null && channels.remove(channel)) {
                if (channels.isEmpty()) {
                    // destroy if empty
                    memberChannelsMap.computeIfPresent(oldUid, (k, v) -> v.isEmpty() ? null : v);
                }
                return true;
            }
            return false;
        }

        public Set<MixiNettyChannel> getChannelsByMember(String member) {
            return member == null ? Collections.emptySet() : memberChannelsMap.getOrDefault(member, Collections.emptySet());
        }
    }

    public boolean addChannel(String roomName, MixiNettyChannel channel, String uid) {
        RoomInfo roomInfo = roomInfoMap.computeIfAbsent(roomName, RoomInfo::new);
        if (StringUtils.isNotBlank(uid)) {
            roomInfo.registerUid(uid, channel);
        }
        boolean isNewAdded = roomInfo.getChannels().add(channel);
        channel.getAttrs().setEnter(true);
        return isNewAdded;
    }

    public void removeChannel(String roomName, MixiNettyChannel channel) {
        RoomInfo roomInfo = roomInfoMap.get(roomName);
        if (roomInfo != null) {
            Set<MixiNettyChannel> channels = roomInfo.getChannels();
            channels.remove(channel);
            if (channels.isEmpty()) {
                // destroy room if empty
                roomInfoMap.computeIfPresent(roomName, (k, v) -> v.getChannels().isEmpty() ? null : v);
            }
            roomInfo.deregisterUid(channel);
        }
        channel.getAttrs().setEnter(false);
    }

    public void destroyRoom(String roomName) {
        RoomInfo roomInfo = roomInfoMap.remove(roomName);
        if (roomInfo != null) {
            for (MixiNettyChannel channel : roomInfo.getChannels()) {
                channel.getAttrs().setEnter(false);
            }
        }
    }

    public RoomInfo getRoomInfo(String roomName) {
        return roomInfoMap.get(roomName);
    }

    public Collection<RoomInfo> getAllRoomInfos() {
        return roomInfoMap.values();
    }
}
