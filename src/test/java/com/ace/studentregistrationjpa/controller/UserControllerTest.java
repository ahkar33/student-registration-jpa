package com.ace.studentregistrationjpa.controller;

import com.ace.studentregistrationjpa.entity.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.ace.studentregistrationjpa.repository.UserRepository;
import com.ace.studentregistrationjpa.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        UserService userService;

        @MockBean
        UserRepository repo;

        @Test
        public void setUpUserManagementTest() throws Exception {
                this.mockMvc.perform(get("/user/userManagement"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("USR003"))
                                .andExpect(model().attributeExists("userList"));
        }

        @Test
        public void setUpAddUserTest() throws Exception {
                this.mockMvc.perform(get("/user/addUser"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void deleteUserTest() throws Exception {
                this.mockMvc.perform(get("/user/deleteUser/{id}", "USR001"))
                                .andExpect(status().is(302))
                                .andExpect(redirectedUrl("/user/userManagement"));
        }

        @Test
        public void setUpUpdateUserTest() throws Exception {
                User user = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                given(userService.selectUserById("1")).willReturn(user);
                this.mockMvc.perform(get("/user/updateUser/{id}", "1"))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR002"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void emptyRoleAddUserTest() throws Exception {
                User userBean = User.builder()
                                .id("")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("").build();
                this.mockMvc.perform(post("/user/addUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("error"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void passwordsDoNotMatchAddUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("qwer")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                this.mockMvc.perform(post("/user/addUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("error"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void emailExistsAddUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                given(userService.checkEmailExists(userBean.getEmail())).willReturn(true);
                this.mockMvc.perform(post("/user/addUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("error"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void userListIsZeroAddUserTest() throws Exception {
                User userBean = User.builder()
                                .id("USR001")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                given(userService.selectAllUsers()).willReturn(userList);
                this.mockMvc.perform(post("/user/addUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("message"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void generateUserIdAddUserTest() throws Exception {
                User userBean = User.builder()
                                .id("USR001")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                userList.add(userBean);
                given(userService.selectAllUsers()).willReturn(userList);
                this.mockMvc.perform(post("/user/addUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR001"))
                                .andExpect(model().attributeExists("message"))
                                .andExpect(model().attributeExists("data"));
        }

        @Test
        public void emptyRoleUpdateUserTest() throws Exception {
                User userBean = User.builder()
                                .id("")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("").build();
                this.mockMvc.perform(post("/user/updateUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR002"))
                                .andExpect(model().attributeExists("error"));
        }

        @Test
        public void passwordsDoNotMatchUpdateUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("qwer")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                this.mockMvc.perform(post("/user/updateUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR002"))
                                .andExpect(model().attributeExists("error"));
        }

        @Test
        public void emailExistsUpdateUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                User tempUser = User.builder()
                                .id("1")
                                .email("maw@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                given(userService.selectUserById(userBean.getId())).willReturn(tempUser);
                given(userService.checkEmailExists(userBean.getEmail())).willReturn(true);
                this.mockMvc.perform(post("/user/updateUser").flashAttr("data", userBean))
                                .andExpect(status().is(200))
                                .andExpect(view().name("USR002"))
                                .andExpect(model().attributeExists("error"));
        }

        @Test
        public void emailDoesNotExistUpdateUserTest() throws Exception {
                User tempUser = User.builder()
                                .id("1")
                                .email("maw@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                userList.add(userBean);
                HashMap<String, Object> sessionAttribute = new HashMap<String, Object>();
                sessionAttribute.put("userInfo", userBean);
                given(userService.selectAllUsers()).willReturn(userList);
                given(userService.selectUserById(userBean.getId())).willReturn(tempUser);
                given(userService.checkEmailExists(userBean.getEmail())).willReturn(false);
                this.mockMvc.perform(post("/user/updateUser").flashAttr("data", userBean).sessionAttrs(sessionAttribute))
                                .andExpect(status().is(302))
                                .andExpect(redirectedUrl("/user/userManagement"));
        }

        @Test
        public void userEmailEqualsTempEmailUpdateUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                userList.add(userBean);
                HashMap<String, Object> sessionAttribute = new HashMap<String, Object>();
                sessionAttribute.put("userInfo", userBean);
                given(userService.selectAllUsers()).willReturn(userList);
                given(userService.selectUserById(userBean.getId())).willReturn(userBean);
                this.mockMvc.perform(post("/user/updateUser").flashAttr("data", userBean).sessionAttrs(sessionAttribute))
                                .andExpect(status().is(302))
                                .andExpect(redirectedUrl("/user/userManagement"));
        }

        @Test
        public void searchUserTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                userList.add(userBean);
                given(userService.selectUserListByIdOrName(userBean.getId(), userBean.getName())).willReturn(userList);
                given(userService.selectAllUsers()).willReturn(userList);
                this.mockMvc.perform(get("/user/searchUser").param("id", "1").param("name", "Admin"))
                                .andExpect(view().name("USR003"))
                                .andExpect(model().attributeExists("userList"));
        }

        @Test
        public void searchUserListIsZeroTest() throws Exception {
                User userBean = User.builder()
                                .id("1")
                                .email("admin@gmail.com")
                                .name("Admin")
                                .password("admin")
                                .confirmPassword("admin")
                                .userRole("Admin").build();
                List<User> userList = new ArrayList<>();
                userList.add(userBean);
                List<User> searchList = new ArrayList<>();
                given(userService.selectUserListByIdOrName(userBean.getId(), userBean.getName()))
                                .willReturn(searchList);
                given(userService.selectAllUsers()).willReturn(userList);
                this.mockMvc.perform(get("/user/searchUser").param("id", "1").param("name", "Admin"))
                                .andExpect(view().name("USR003"))
                                .andExpect(model().attributeExists("userList"));
        }

}
