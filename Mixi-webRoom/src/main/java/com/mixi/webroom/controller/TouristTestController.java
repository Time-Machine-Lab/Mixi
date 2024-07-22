package com.mixi.webroom.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.common.utils.R;
import com.mixi.common.utils.UserThread;
import com.mixi.webroom.core.annotation.TouristCheck;
import com.mixi.webroom.domain.dto.CreateRoomDTO;
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

    @TouristCheck
    @ApiAuth(AuthType.OPTIONAL)
    @PostMapping("/create")
    public R<Map<String, Object>> createRoom(@RequestParam(required = false) String code,
                                             @RequestParam(required = false) String fingerprint,
                                             @RequestBody @Valid CreateRoomDTO createRoomDTO) {
        Object token = UserThread.getField("token");
        return R.success(Map.of("token", token == null ? "" : token.toString(),
                                "other", "welsir666创建房间成功"));
//        return webRoomService.createRoom(createRoomDTO, UserThread.getUserId());
    }

    @TouristCheck
    @ApiAuth(AuthType.OPTIONAL)
    @PostMapping("/linkJoin")
    public R<Map<String, Object>> linkJoin(@RequestParam(required = false) String code,
                                           @RequestParam(required = false) String fingerprint,
                                           @RequestParam @Valid @NotBlank String key) {
        Object token = UserThread.getField("token");
        return R.success(Map.of("token", token == null ? "" : token.toString(),
                                "other", "welsir666加入房间成功"));
//        return webRoomService.linkJoin(UserThread.getUserId(), key);
    }
}
