package com.Simple_form.Controller;

import com.Simple_form.Model.UserModel;
import com.Simple_form.Repository.UserRepository;
import com.Simple_form.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;


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
    public String login(@ModelAttribute UserModel userModel, Model model, HttpSession session){
        System.out.println("login request: " + userModel);
        UserModel authenticatedUser = userService.authentication(userModel.getUsername(), userModel.getPassword());
        if (authenticatedUser == null) {
            return "error_page";
        }
        session.setAttribute("loggedInUser", authenticatedUser);
        model.addAttribute("userLogin", authenticatedUser.getUsername());
        return "personal_page";

    }
    @GetMapping("/blog")
    public String blog(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("userLogin", user.getUsername());
        model.addAttribute("blogRequest", new UserModel());
        return "blog_page";
    }

    @PostMapping("/blog")
    public String submitBlog(@ModelAttribute("blogRequest") UserModel blogRequest, HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        user.setBlog(blogRequest.getBlog());
        userRepository.save(user);
        model.addAttribute("userLogin", user.getUsername());
        session.setAttribute("loggedInUser", user);
        return "redirect:/blog-success";
    }
    @GetMapping("/blog-success")
    public String blogSuccess(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("userLogin", user.getUsername());
        model.addAttribute("blogContent", user.getBlog());
        return "blog_success_page";
    }

    @GetMapping("/journal")
    public String journal(Model model , HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("userLogin", user.getUsername());
        model.addAttribute("journalRequest", new UserModel());
        return "journal_page";
    }

    @PostMapping("/journal")
    public String submitJournal(@ModelAttribute("journalRequest") UserModel journalRequest, HttpSession session, Model model) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        user.setJournal(journalRequest.getJournal());
        userRepository.save(user);
        model.addAttribute("userLogin", user.getUsername());
        session.setAttribute("loggedInUser", user);
        return "redirect:/journal-success";
    }


    @GetMapping("/journal-success")
    public String journalSuccess(Model model, HttpSession session) {
        UserModel user = (UserModel) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";

        model.addAttribute("userLogin", user.getUsername());
        model.addAttribute("journalContent", user.getJournal());
        return "journal_success_page";
    }

}
