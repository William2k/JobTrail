package com.jobtrail.api.controllers;

import com.jobtrail.api.config.TestConfig;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.dto.UserResponseWithTokenDTO;
import com.jobtrail.api.models.Role;
import com.jobtrail.api.models.SignIn;
import com.jobtrail.api.models.entities.UserEntity;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.repositories.UserRepository;
import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.services.UserService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
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
public class ZoneControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZoneRepository zoneRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    private UserEntity currentUser;

    private UUID parentZoneId = UUID.fromString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e");
    private UUID childZoneId = UUID.fromString("2da9c33a-5eeb-43e6-b3e2-7c9af4da2fbd");

    @Before
    public void setUp() {
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

        setUserZones(currentUser.getId());
    }

    private void setUserZones(UUID userId) {
        List<ZoneEntity> zones = new ArrayList<>();

        ZoneEntity zone = new ZoneEntity();
        zone.setId(parentZoneId);
        zone.setName("test");
        zone.setDescription("test");
        zone.setActive(true);

        zones.add(zone);

        ZoneEntity zoneChild = new ZoneEntity();
        zoneChild.setId(childZoneId);
        zoneChild.setName("testComp");
        zoneChild.setDescription("testComp");
        zoneChild.setActive(true);
        zoneChild.setParentZoneId(parentZoneId);

        Mockito.when(zoneRepository.getAll(userId)).thenReturn(zones);

        Mockito.when(zoneRepository.getById(zone.getId())).thenReturn(zone);
        Mockito.when(zoneRepository.getById(zoneChild.getId())).thenReturn(zoneChild);
    }

    private String getToken() throws Exception {
        SignIn signIn = new SignIn();
        signIn.setUsername("test");
        signIn.setPassword("test");

        String json = ConversionHelper.toJson(signIn);

        MvcResult mvcResult = mockMvc.perform(post("/api/account/signin").contentType(MediaType.APPLICATION_JSON).content(json)).andReturn();

        json = mvcResult.getResponse().getContentAsString();

        UserResponseWithTokenDTO user = ConversionHelper.jsonToObject(json, UserResponseWithTokenDTO.class);

        return user.getToken();
    }

    @Test
    public void getUserZones() throws Exception {
        String token = getToken();

        mockMvc.perform(get("/api/zone?userId=" + currentUser.getId()).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }

    @Test
    public void getZonesSimple() throws Exception {
        String token = getToken();

        mockMvc.perform(get("/api/zone/" + parentZoneId).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }

    @Test
    public void getZonesComplex() throws Exception {
        String token = getToken();

        mockMvc.perform(get("/api/zone/" + childZoneId).header("Authorization", "Bearer " + token))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }
}
