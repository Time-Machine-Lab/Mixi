package com.mixi.webroom.controller;

import com.mixi.common.constant.enums.UserStateEnum;
import com.mixi.webroom.core.annotation.UserState;
import com.mixi.webroom.domain.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import org.springframework.web.bind.annotation.*;
import io.github.common.web.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
*@Author：XiaoChun
*@Date：2024/6/25  17:18
*/
@RestController
@RequestMapping("/webRoom")
public class WebRoomController {

    @Resource
    WebRoomService webRoomService;

    @PostMapping("/create")
    @UserState(value = UserStateEnum.NORMAL)
    public Result<?> createRoom(@RequestBody
                                    @Valid
                                    CreateRoomDTO createRoomDTO,
                                    @RequestHeader
                                    @Valid
                                    @NotBlank
                                    String uid) {
        return webRoomService.createRoom(createRoomDTO, uid);
    }

    @GetMapping("/linkShare")
    public Result<?> linkShare(@RequestHeader
                                   @Valid
                                   @NotBlank
                                   String uid) {
        return webRoomService.linkShare(uid);
    }

    @PostMapping("/pull")
    public Result<?> pull(@RequestHeader
                               @Valid
                               @NotBlank
                               String uid,
                          @RequestBody
                          List<String> ids) {
        return webRoomService.pull(uid, ids);
    }

    @GetMapping("/linkJoin")
    public Result<?> linkJoin(@RequestHeader
                                  @Valid
                                  @NotBlank
                                  String uid,
                                  @RequestParam
                                  @Valid
                                  @NotBlank
                                  String key) {
        return webRoomService.linkJoin(uid, key);
    }

    @PostMapping("/quit")
    public Result<?> quitRoom() {
        return webRoomService.quitRoom();
    }
}
