package com.ace.studentregistrationjpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student {
    @Id
    private String id;
    private String name;
    private String dob;
    private String gender;
    private String phone;
    private String education;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "student_course", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> attendCourses;

    @Transient
    private List<String> attendCoursesString;

    public void addAttendCourses(Course course) {
        if (attendCourses == null)
            attendCourses = new ArrayList<>();
        attendCourses.add(course);
    }

    public void addAttendCoursesString(Course course) {
        if (attendCoursesString == null)
            attendCoursesString = new ArrayList<>();
        attendCoursesString.add(course.getId());
    }

}
