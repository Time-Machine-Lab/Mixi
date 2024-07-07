package com.mixi.user.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @NAME: MapUtils
 * @USER: yuech
 * @Description:
 * @DATE: 2024/6/27
 */
public class MapUtils implements Serializable {


    private Map<String, String> map;

    private MapUtils() {
        this.map = new HashMap<>();
    }

    public static Builder build(){
        return new MapUtils.Builder();
    }

    public static class Builder {
        private Map<String, String> map;

        private Builder() {
            this.map = new HashMap<>();
        }

        public Builder set(String key, String value) {
            this.map.put(key, value);
            return this;
        }

        public Map<String, String> buildMap() {
            return this.map;
        }
    }
}