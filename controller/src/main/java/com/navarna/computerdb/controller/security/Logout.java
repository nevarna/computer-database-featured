package com.navarna.computerdb.controller.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Logout {
    private static final Logger LOGGER = LoggerFactory.getLogger(Logout.class);
    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping
    @RequestMapping("/logout")
    public String getRequest(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("-------->getRequest(request,response)");
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.deconnectionManuel(user.getUsername());
        return "redirect:/";
    }

    @GetMapping
    @RequestMapping("/")
    public String getRequest() {
        LOGGER.info("-------->getRequest()");
        return "redirect:/dashboard";
    }

}
