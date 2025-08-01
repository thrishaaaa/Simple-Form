package com.Simple_form.Controller;

import com.Simple_form.Model.UserModel;
import com.Simple_form.Repository.UserRepository;
import com.Simple_form.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerRequest",new UserModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginRequest", new UserModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel){
        System.out.println("User registered: " + userModel);
        if(userRepository.findByUsername(userModel.getUsername()).isPresent()){
            return "user_exists_error_page";
        }
        UserModel registeredUser = userService.registerUser(userModel.getUsername(),
                                                            userModel.getPassword(),
                                                            userModel.getEmail());
        if (registeredUser == null) {
            return "error_page";
        }
        return "redirect:/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel,Model model){
        System.out.println("login request: " + userModel);
        UserModel authenticatedUser = userService.authentication(userModel.getUsername(), userModel.getPassword());
        if (authenticatedUser == null) {
            return "error_page";
        }
        model.addAttribute("userLogin", authenticatedUser.getUsername());
        return "personal_page";

    }
}
