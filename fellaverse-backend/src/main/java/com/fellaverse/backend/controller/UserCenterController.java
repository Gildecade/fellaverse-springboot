package com.fellaverse.backend.controller;

import com.fellaverse.backend.dto.FlashSaleOrderUserDTO;
import com.fellaverse.backend.dto.UserProfileDTO;
import com.fellaverse.backend.mapper.FlashSaleOrderUserMapper;
import com.fellaverse.backend.mapper.UserProfileMapper;
import com.fellaverse.backend.service.FlashSaleOrderUserService;
import com.fellaverse.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for user center, including displaying user info, order info, etc.
 */
@RestController
@RequestMapping("/user")
public class UserCenterController {
    @Qualifier("userServiceImpl")
    @Autowired
    private UserService userService;

    @Autowired
    private FlashSaleOrderUserService flashSaleOrderUserService;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private FlashSaleOrderUserMapper flashSaleOrderUserMapper;

    @GetMapping("/{id}")
    public UserProfileDTO findAllInfo(@PathVariable("id") Long id) {
        return userProfileMapper.toDto(userService.findUserById(id));
    }

    @GetMapping("/{id}/flashSaleOrder")
    public List<FlashSaleOrderUserDTO> findAllFlashSaleOrder(@PathVariable("id") Long id) {
        return flashSaleOrderUserService.findAll(id).stream().map(flashSaleOrderUserMapper::toDto).collect(Collectors.toList());
    }
}
