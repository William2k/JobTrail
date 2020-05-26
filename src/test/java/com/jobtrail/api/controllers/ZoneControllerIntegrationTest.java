package com.jobtrail.api.controllers;

import com.jobtrail.api.controllers.base.BaseControllerIntegrationTest;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.dto.ZoneResponseDTO;
import com.jobtrail.api.dto.full.FullZoneResponseDTO;
import com.jobtrail.api.models.AddZone;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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

public class ZoneControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZoneRepository zoneRepository;

    @MockBean
    private UserService userService;

    private final UUID parentZoneId = UUID.fromString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e");
    private final UUID childZoneId = UUID.fromString("2da9c33a-5eeb-43e6-b3e2-7c9af4da2fbd");

    @Before
    public void setUp() throws Exception {
        init();

        setUserZones(currentUser.getId());
    }

    private void setUserZones(UUID userId) {
        List<ZoneEntity> zones = new ArrayList<>();

        ZoneEntity zone = new ZoneEntity();
        zone.setId(parentZoneId);
        zone.setName("parentTest");
        zone.setDescription("test");
        zone.setActive(true);

        zones.add(zone);

        ZoneEntity zoneChild = new ZoneEntity();
        zoneChild.setId(childZoneId);
        zoneChild.setName("childTest");
        zoneChild.setDescription("testComp");
        zoneChild.setActive(true);
        zoneChild.setParentZoneId(parentZoneId);

        Mockito.when(zoneRepository.getAll(userId)).thenReturn(zones);

        Mockito.when(zoneRepository.getById(zone.getId())).thenReturn(zone);
        Mockito.when(zoneRepository.getById(zoneChild.getId())).thenReturn(zoneChild);
    }

    @Test
    public void unauthorisedTest() throws Exception {
        authenticationTest("/api/zones/" + parentZoneId);
    }

    @Test
    public void getUserZones() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones?userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e"))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<ZoneResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getUserFullZones() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones?full=true&userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e"))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<FullZoneResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getZoneById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones/" + parentZoneId).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e"))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        ZoneResponseDTO response = ConversionHelper.jsonToObject(json, ZoneResponseDTO.class);
    }

    @Test
    public void getFullZoneById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones/" + childZoneId + "?full=true").header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("parentTest"))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        FullZoneResponseDTO response = ConversionHelper.jsonToObject(json, FullZoneResponseDTO.class);
    }

    @Test
    public void addZone() throws Exception {
        AddZone addZone = new AddZone();
        addZone.setName("testZone");
        addZone.setDescription("This for testing purposes");
        addZone.setParentZoneId(parentZoneId);
        addZone.setManagerId(currentUser.getId());

        String json = ConversionHelper.toJson(addZone);

        mockMvc.perform(post("/api/zones").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
