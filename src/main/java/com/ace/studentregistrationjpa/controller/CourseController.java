package com.ace.studentregistrationjpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.studentregistrationjpa.entity.Course;
import com.ace.studentregistrationjpa.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/addCourse")
    public ModelAndView setupAddCourse() {
        return new ModelAndView("BUD003", "data", new Course());
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("data") Course courseBean, ModelMap model) {
        if (courseBean.getName().isBlank()) {
            model.addAttribute("error", "Fill the blank !!");
            model.addAttribute("data", courseBean);
            return "BUD003";
        } else if (courseService.checkCourseName(courseBean.getName())) {
            model.addAttribute("error", "Course name already exists !!");
            model.addAttribute("data", courseBean);
            return "BUD003";
        } else {
            List<Course> courseList = courseService.selectAllCourses();
            if (courseList.size() == 0) {
                courseBean.setId("COU001");
            } else {
                int tempId = Integer.parseInt(courseList.get(courseList.size() -
                        1).getId().substring(3)) + 1;
                String userId = String.format("COU%03d", tempId);
                courseBean.setId(userId);
            }
            courseService.insertCourse(courseBean);
            model.addAttribute("message", "Registered Succesfully !!");
            model.addAttribute("data", new Course());
            return "BUD003";
        }
    }

}
