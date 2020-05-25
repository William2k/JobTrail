package com.jobtrail.api.controllers.base;

import com.jobtrail.api.config.TestConfig;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.dto.full.FullUserResponseWithTokenDTO;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.repositories.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestConfig.class)
public class BaseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    protected UserEntity currentUser;
    protected String authToken;

    protected void init() throws Exception {
        currentUser = new UserEntity();
        currentUser.setActive(true);
        currentUser.setUsername("test");
        currentUser.setId(UUID.fromString("259b773c-4c89-444e-a1b3-12a647837033"));
        currentUser.setFirstName("test");
        currentUser.setLastName("test");
        currentUser.setEmailAddress("test@test.com");
        currentUser.setPassword("test-pass");
        currentUser.setStringRoles( new String[] {Role.ROLE_USER.toString()});

        Mockito.when(userRepository.getByUsername("test")).thenReturn(currentUser);
        Mockito.when(userRepository.getById(currentUser.getId())).thenReturn(currentUser);

        authToken = getToken();
    }

    private String getToken() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("test");
        signIn.setPassword("test");

        String json = ConversionHelper.toJson(signIn);

        MvcResult mvcResult = mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        json = mvcResult.getResponse().getContentAsString();

        FullUserResponseWithTokenDTO user = ConversionHelper.jsonToObject(json, FullUserResponseWithTokenDTO.class);

        return user.getToken();
    }

    protected void authenticationTest(String url) throws Exception {
        mockMvc.perform(get(url)).andDo(print()).andExpect(status().isUnauthorized());
    }
}
