package com.Simple_form.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class UserController {

    @GetMapping("/register")
    public String register() {
        return "register_page";
    }

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }
}
