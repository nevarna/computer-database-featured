package com.navarna.computerdb.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Logout {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping
    @RequestMapping("/logout")
    public String getRequest(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.deconnectionManuel(user.getUsername());
        return "redirect:/";
    }

    @GetMapping
    @RequestMapping("/")
    public String getRequest() {
        return "redirect:/dashboard";
    }

}
