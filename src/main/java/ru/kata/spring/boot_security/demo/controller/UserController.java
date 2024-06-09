package ru.kata.spring.boot_security.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;


@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String getUser(Model model, Principal principal) {
        logger.info("Attempting to retrieve user with username: {}", principal.getName());
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            logger.warn("User not found for username: {}", principal.getName());
            return "error";
        }
        model.addAttribute("users", user);
        logger.info("User retrieved successfully: {}", user);
        return "user";
    }
}
