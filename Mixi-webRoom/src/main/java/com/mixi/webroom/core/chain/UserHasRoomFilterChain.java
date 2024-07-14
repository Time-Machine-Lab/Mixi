package com.mixi.webroom.core.chain;

import com.mixi.webroom.domain.entity.WebRoom;
import io.github.servicechain.annotation.Chain;
import io.github.servicechain.chain.AbstractFilterChain;

import java.util.List;

/**
 * @Date 2024/7/9
 * @Author xiaochun
 */
@Chain("UserHasRoomFilterChain")
public class UserHasRoomFilterChain extends AbstractFilterChain<WebRoom> {
    @Override
    public List<ServicePoint> servicePoints() {
        return null;
    }

    @Override
    public boolean filter(WebRoom value) {
        return false;
    }
}
