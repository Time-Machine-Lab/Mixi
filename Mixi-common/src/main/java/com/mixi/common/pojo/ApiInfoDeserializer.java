package com.mixi.common.pojo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ApiInfoDeserializer extends JsonDeserializer<ApiInfo> {

    @Override
    public ApiInfo deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) p.getCodec();
        JsonNode node = mapper.readTree(p);

        String authType = node.get("authType").asText();
        String url = node.get("url").asText();
        String module = node.get("module").asText();
        String method = node.get("method").asText();
        int[] roles = mapper.convertValue(node.get("roles"), int[].class);
        String beforeHandler = node.get("beforeHandler").asText();
        String afterHandler = node.get("afterHandler").asText();

        return new ApiInfo(authType, url, module, method, roles, beforeHandler, afterHandler);
    }
}