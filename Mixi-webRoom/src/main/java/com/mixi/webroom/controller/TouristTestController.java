package com.mixi.webroom.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.utils.R;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 描述: 游客模式测试
 * @author suifeng
 * 日期: 2024/7/20
 */
@RequestMapping("/webRoom/test")
@RequiredArgsConstructor
@RestController
public class TouristTestController {

    private final WebRoomService webRoomService;

    @ApiAuth(value = AuthType.NEED, before = "TouristBeforeHandler")
    @PostMapping("/create")
    public R<Map<String, Object>> createRoom(@RequestBody @Valid CreateRoomDTO createRoomDTO) {
        return R.success("welsir666创建房间成功");
//        return webRoomService.createRoom(createRoomDTO, UserThread.getUserId());
    }

    @ApiAuth(value = AuthType.NEED, before = "TouristBeforeHandler")
    @PostMapping("/linkJoin")
    public R<Map<String, Object>> linkJoin(@RequestParam @Valid @NotBlank String key) {
        return R.success("welsir666加入房间成功");
//        return webRoomService.linkJoin(UserThread.getUserId(), key);
    }
}
