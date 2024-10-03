package com.github.angel.scm.controller.auth;

import com.github.angel.scm.dto.request.Login;
import com.github.angel.scm.dto.request.Register;
import com.github.angel.scm.exception.ResourceAlreadyExistsException;
import com.github.angel.scm.service.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authService;
    @Test
    public void testLogin_Get() throws Exception {
        mockMvc.perform(get("/authentication/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"))
                .andExpect(model().attributeExists("login"));
    }

    @Test
    void testRegister_Get() throws Exception {
        mockMvc.perform(get("/authentication/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("register"));
    }


    @Test
    void testLogin_Post_Success() throws Exception {
        Login login = new Login("test@example.com", "password");

        mockMvc.perform(post("/authentication/login")
                        .flashAttr("login", login))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/dashboard?message=Login+Successful"));
    }

    @Test
     void testLogin_Post_ValidationErrors() throws Exception {
        Login login = new Login("", "");

        mockMvc.perform(post("/authentication/login")
                        .flashAttr("login", login))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testRegister_Post_Success() throws Exception {
        Register register = new Register("exmaple@gmail.com", "12345", "8092023020", "test ");

        mockMvc.perform(post("/authentication/register")
                        .flashAttr("register", register))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authentication/login?message=Registration+Successful"));

    }
    @Test
    public void testRegister_Post_ValidationErrors() throws Exception {
        Register register = new Register();

        mockMvc.perform(post("/authentication/register")
                        .flashAttr("register", register))
                .andExpect(status().isOk())
                .andExpect(view().name("register"));
    }

    @Test
     void testRegister_Post_IllegalArgumentException() throws Exception {
        Register register = new Register();

        mockMvc.perform(post("/authentication/register")
                        .flashAttr("register", register))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("passwordError", "Passwords do not match"));
    }

    @Test
     void testRegister_Post_ResourceAlreadyExistsException() throws Exception {
        Register register = new Register();
        doThrow(new ResourceAlreadyExistsException("Email already exists"))
                .when(authService).register(any(Register.class));
        mockMvc.perform(post("/authentication/register")
                        .flashAttr("register", register))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attribute("emailError", "Email already exists"));
    }

    @Test
    public void testLogout() throws Exception {
        mockMvc.perform(get("/authentication/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authentication/login?logout=true"));
    }
}