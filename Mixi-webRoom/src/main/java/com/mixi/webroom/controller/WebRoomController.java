package com.mixi.webroom.controller;

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
                              @Valid
                              @Size(max = 20, message = "The maximum limit for email list is 20")
                              List<String> emails) {
        return webRoomService.pull(uid, emails);
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
