package com.jobtrail.api.controllers;

import com.jobtrail.api.config.TestConfig;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.dto.full.FullUserResponseWithTokenDTO;
import com.jobtrail.api.models.RegisterUser;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
public class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private final UserEntity currentUser = new UserEntity();

    @Before
    public void setUp() {
        currentUser.setActive(true);
        currentUser.setUsername("test");
        currentUser.setId(UUID.randomUUID());
        currentUser.setFirstName("test");
        currentUser.setLastName("test");
        currentUser.setEmailAddress("test@test.com");
        currentUser.setPassword("test-pass");
        currentUser.setStringRoles( new String[] {Role.ROLE_USER.toString()});

        Mockito.when(userRepository.getByUsername(currentUser.getUsername())).thenReturn(currentUser);
        Mockito.when(userRepository.getById(currentUser.getId())).thenReturn(currentUser);
        Mockito.when(userRepository.existsByUsername(currentUser.getUsername())).thenReturn(true);
        Mockito.when(userRepository.existsByEmail(currentUser.getEmailAddress())).thenReturn(true);
    }

    @Test
    public void signInSuccess() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("test");
        signIn.setPassword("test");

        String json = ConversionHelper.toJson(signIn);

        mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("test@test.com")));
    }

    @Test
    public void signInWrongPassword() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("test");
        signIn.setPassword("tests");

        String json = ConversionHelper.toJson(signIn);

        mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void signInWrongUsername() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("tests");
        signIn.setPassword("test");

        String json = ConversionHelper.toJson(signIn);

        mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    public void signInAndAuthenticationSuccess() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("test");
        signIn.setPassword("test");

        String json = ConversionHelper.toJson(signIn);

        MvcResult mvcResult = mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        json = mvcResult.getResponse().getContentAsString();

        FullUserResponseWithTokenDTO user = ConversionHelper.jsonToObject(json, FullUserResponseWithTokenDTO.class);

        mockMvc.perform(get("/api/account/authenticate").header("Authorization", "Bearer " + user.getToken())).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void signInAndAuthenticationWrongToken() throws Exception {
        mockMvc.perform(get("/api/account/authenticate").header("Authorization", "Bearer Fake")).andDo(print()).andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString("Authentication failed")));
    }

    @Test
    public void signUpSuccess() throws Exception {
        RegisterUser user = new RegisterUser();
        user.setUsername("testValid");
        user.setPassword("test");
        user.setEmailAddress("testValid@test.com");
        user.setFirstName("test");
        user.setLastName("test");

        String json = ConversionHelper.toJson(user);

        mockMvc.perform(post("/api/account/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void signUpInvalidModelFail() throws Exception {
        RegisterUser user = new RegisterUser();
        user.setUsername("test");
        user.setPassword("test");
        user.setFirstName("test");
        user.setLastName("test");

        String json = ConversionHelper.toJson(user);

        mockMvc.perform(post("/api/account/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void signUpUsernameConflict() throws Exception {
        RegisterUser user = new RegisterUser();
        user.setUsername(currentUser.getUsername());
        user.setPassword(currentUser.getPassword());
        user.setEmailAddress(currentUser.getEmailAddress());
        user.setFirstName(currentUser.getFirstName());
        user.setLastName(currentUser.getLastName());

        String json = ConversionHelper.toJson(user);

        mockMvc.perform(post("/api/account/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isConflict());
    }

    @Test
    public void signUpEmailConflict() throws Exception {
        RegisterUser user = new RegisterUser();
        user.setUsername("test2");
        user.setPassword(currentUser.getPassword());
        user.setEmailAddress(currentUser.getEmailAddress());
        user.setFirstName(currentUser.getFirstName());
        user.setLastName(currentUser.getLastName());

        String json = ConversionHelper.toJson(user);

        mockMvc.perform(post("/api/account/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print()).andExpect(status().isConflict());
    }
}
