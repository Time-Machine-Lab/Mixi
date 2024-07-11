package com.mixi.user.designpattern.chain;

import com.google.common.collect.Maps;
import com.mixi.user.utils.BeanUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @NAME: ApproveChainBuilder
 * @USER: yuech
 * @Description:
 * @DATE: 2024/7/9
 */
@Component
@Scope("prototype")
public class ApproveChainBuilder {

    public static Builder buildInstance(){
        return new ApproveChainBuilder.Builder();
    }

    @Component
    @Scope("prototype")
    public static class Builder {

        @Resource
        private BeanUtil beanUtil;

        ApproveChain head;

        public ApproveChain Build(){
            return head;
        }

        public Builder set(String key, String... value) {
            ApproveChain node = getapproveChain(key);
            node.setParams(value);
            if (Objects.isNull(head)){
                head = node;
                return this;
            }
            ApproveChain current = head;
            while (current.getNextChain() != null) {
                current = current.getNextChain(); // 找到链表的最后一个节点
            }
            current.setNext(node);// 将新节点连接到链表的末尾
            return this;
        }

        public ApproveChain buildMap() {
            return this.head;
        }

        public ApproveChain getapproveChain(String key){
            return (ApproveChain) beanUtil.getBeanByName(key);
        }
    }

}