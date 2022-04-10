package com.strongduanmu.controller;

import com.strongduanmu.domain.Response;
import com.strongduanmu.domain.User;
import com.strongduanmu.service.BookService;
import com.strongduanmu.service.UserService;
import com.strongduanmu.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BookService bookService;

    @ResponseBody
    @GetMapping(value = "/users")
    public Response selectAll() throws InterruptedException {
        List<User> userList = userService.selectAll();
        return ResponseUtils.success(userList);
    }
}
