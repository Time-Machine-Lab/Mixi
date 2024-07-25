package com.mixi.webroom.controller;

import com.mixi.common.annotation.auth.ApiAuth;
import com.mixi.common.annotation.auth.AuthType;
import com.mixi.webroom.pojo.dto.CreateRoomDTO;
import com.mixi.webroom.service.WebRoomService;
import org.springframework.web.bind.annotation.*;
import io.github.common.web.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @ApiAuth(value = AuthType.NEED)
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
    @ApiAuth(value = AuthType.NEED)
    public Result<?> linkShare(@RequestHeader
                                   @Valid
                                   @NotBlank
                                   String uid) {
        return webRoomService.linkShare(uid);
    }

    @PostMapping("/pull")
    @ApiAuth(value = AuthType.NEED, before = "TouristBeforeHandler")
    public Result<?> pull(@RequestHeader
                              @Valid
                              @NotBlank
                              String uid,
                              @RequestBody
                              @Valid
                              @Size(max = 20, message = "The maximum limit for email list is 20")
                              List<String> emails) {
        return webRoomService.pull(uid, emails);
    }

    @GetMapping("/linkJoin")
    @ApiAuth(value = AuthType.NEED)
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
    @ApiAuth(value = AuthType.NEED)
    public Result<?> quitRoom(@RequestHeader
                                  @Valid
                                  @NotBlank
                                  String uid,
                                  @RequestPart
                                  @Valid
                                  @NotBlank
                                  String roomId) {
        return webRoomService.quitRoom(uid, roomId);
    }

    @PostMapping("/transferOwner")
    @ApiAuth(value = AuthType.NEED)
    public Result<?> transferOwner(@RequestHeader
                                   @Valid
                                   @NotBlank
                                   String uid,
                                   @RequestPart
                                   @Valid
                                   @NotBlank
                                   String owner){
        return null;
    }
}
