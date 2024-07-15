package com.mixi.webroom.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.N;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：XiaoChun
 * @Date：2024/7/15 下午4:53
 */
@Data
public class PullVO {
    List<String> success;

    List<String> fail;

    public PullVO() {
        this.success = new ArrayList<>();
        this.fail = new ArrayList<>();
    }

    public PullVO(List<String> success, List<String> fail) {
        this.success = success;
        this.fail = fail;
    }
}
