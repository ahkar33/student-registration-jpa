package com.ace.studentregistrationjpa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.studentregistrationjpa.entity.User;
import com.ace.studentregistrationjpa.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userManagement")
    public String showUserManagement(ModelMap model) {
        List<User> userList = userService.selectAllUsers();
        model.addAttribute("userList", userList);
        return "USR003";
    }

    @GetMapping("/addUser")
    public ModelAndView setupAdduser() {
        return new ModelAndView("USR001", "data", new User());
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("data") User userBean, ModelMap model) {
        if (userBean.getEmail().isBlank() || userBean.getName().isBlank() || userBean.getPassword().isBlank()
                || userBean.getConfirmPassword().isBlank() || userBean.getUserRole().isBlank()) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", userBean);
            return "USR001";
        } else if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match !!");
            model.addAttribute("data", userBean);
            return "USR001";
        }
        List<User> userList = userService.selectAllUsers();
        if (userService.checkEmailExists(userBean.getEmail())) {
            model.addAttribute("error", "Email already exists !!");
            model.addAttribute("data", userBean);
            return "USR001";
        }
        if (userList.size() == 0) {
            userBean.setId("USR001");
        } else {
            int tempId = Integer.parseInt(userList.get(userList.size() - 1).getId().substring(3)) + 1;
            String userId = String.format("USR%03d", tempId);
            userBean.setId(userId);
        }
        userService.insertUser(userBean);
        model.addAttribute("message", "Registered Succesfully !!");
        // clear the bean
        model.addAttribute("data", new User());
        return "USR001";
    }

    @GetMapping("/updateUser/{id}")
    public ModelAndView setupUpdateUser(@PathVariable("id") String id) {
        User resUser = userService.selectUserById(id);
        User userBean = new User();
        userBean.setId(id);
        userBean.setEmail(resUser.getEmail());
        userBean.setName(resUser.getName());
        userBean.setPassword(resUser.getPassword());
        userBean.setConfirmPassword(resUser.getPassword());
        userBean.setUserRole(resUser.getUserRole());
        return new ModelAndView("USR002", "data", userBean);
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("data") User userBean, ModelMap model, HttpSession session,
            HttpServletRequest req) {
        // for testing only
        session.setAttribute("userInfo", userBean);
        User sessionDto = (User) session.getAttribute("userInfo");
        if (userBean.getEmail().isBlank() || userBean.getName().isBlank() || userBean.getPassword().isBlank()
                || userBean.getConfirmPassword().isBlank() || userBean.getUserRole().isBlank()) {
            model.addAttribute("error", "Fill the blank !!");
            return "USR002";
        }
        if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match !!");
            return "USR002";
        }
        User tempUser = userService.selectUserById(userBean.getId());
        if (!tempUser.getEmail().equals(userBean.getEmail())) {
            if (userService.checkEmailExists(userBean.getEmail())) {
                model.addAttribute("error", "Email already exists !!");
                return "USR002";
            }
            userService.updateUser(userBean);
            if (userBean.getEmail().equals(sessionDto.getEmail())) {
                session.setAttribute("userInfo", userBean);
            }
            List<User> userList = userService.selectAllUsers();
            req.getServletContext().setAttribute("userList", userList);
            return "redirect:/user/userManagement";
        }
        userService.updateUser(userBean);
        if (userBean.getEmail().equals(sessionDto.getEmail())) {
            session.setAttribute("userInfo", userBean);
        }
        List<User> userList = userService.selectAllUsers();
        req.getServletContext().setAttribute("userList", userList);
        return "redirect:/user/userManagement";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "redirect:/user/userManagement";
    }

    @GetMapping("/searchUser")
    public String searchUser(@RequestParam("id") String searchId, @RequestParam("name") String searchName,
            ModelMap model) {
        // ")#<>(}" <- this is just random bullshit to avoid sql wildcard, not REGEX
        String id = searchId.isBlank() ? ")#<>(}" : searchId;
        String name = searchName.isBlank() ? ")#<>(}" : searchName;
        List<User> searchUserList = null;
        searchUserList = userService.selectUserListByIdOrName(id, name);
        if (searchUserList.size() == 0) {
            searchUserList = userService.selectAllUsers();
        }
        model.addAttribute("userList", searchUserList);
        return "USR003";
    }
}
