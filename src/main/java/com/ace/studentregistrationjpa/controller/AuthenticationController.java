package com.ace.studentregistrationjpa.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.service.UserService;

@Controller
public class AuthenticationController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public ModelAndView setUpLogin() {
        return new ModelAndView("LGN001", "data", new User());
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("data") User userBean, HttpSession session, ModelMap model) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        if (userService.checkLogin(userBean.getEmail(), userBean.getPassword())) {
            User resUser = userService.selectUserByEmail(userBean.getEmail());
            session.setAttribute("date", currentDate);
            session.setAttribute("userInfo", resUser);
            return "redirect:/welcome";
        }
        model.addAttribute("data", userBean);
        model.addAttribute("error", "Email and Password do not match !!");
        return "LGN001";
    }

    @GetMapping("/logout")
    public String logout(ModelMap model, HttpSession session) {
        session.removeAttribute("userInfo");
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/welcome")
    public String showWelcome() {
        return "MNU001";
    }

}
