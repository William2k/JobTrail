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

    private final ZoneEntity parent = new ZoneEntity();
    private final ZoneEntity child = new ZoneEntity();

    @Before
    public void setUp() throws Exception {
        init();

        setUserZones(currentUser.getId());
    }

    private void setUserZones(UUID userId) {
        List<ZoneEntity> zones = new ArrayList<>();

        parent.setId(UUID.randomUUID());
        parent.setName("parentTest");
        parent.setDescription("test");
        parent.setActive(true);

        zones.add(parent);

        child.setId(UUID.randomUUID());
        child.setName("childTest");
        child.setDescription("testComp");
        child.setActive(true);
        child.setParentZoneId(parent.getId());

        Mockito.when(zoneRepository.getAll(userId)).thenReturn(zones);

        Mockito.when(zoneRepository.getById(parent.getId())).thenReturn(parent);
        Mockito.when(zoneRepository.getById(child.getId())).thenReturn(child);
        Mockito.when(zoneRepository.exists(child.getName(), child.getParentZoneId())).thenReturn(true);
        Mockito.when(zoneRepository.getByName(child.getName(), child.getParentZoneId())).thenReturn(child);
    }

    @Test
    public void unauthorisedTest() throws Exception {
        authenticationTest("/api/zones/" + parent.getId());
    }

    @Test
    public void getUserZones() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones?userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getId().toString()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<ZoneResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getUserFullZones() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones?full=true&userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getId().toString()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<FullZoneResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getZoneById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones/" + parent.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getId().toString()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        ZoneResponseDTO response = ConversionHelper.jsonToObject(json, ZoneResponseDTO.class);
    }

    @Test
    public void getFullZoneById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/zones/" + child.getId() + "?full=true").header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getName()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        FullZoneResponseDTO response = ConversionHelper.jsonToObject(json, FullZoneResponseDTO.class);
    }

    @Test
    public void addZoneSuccess() throws Exception {
        AddZone addZone = new AddZone();
        addZone.setName("testZone");
        addZone.setDescription("This for testing purposes");
        addZone.setParentZoneId(parent.getId());
        addZone.setManagerId(currentUser.getId());

        String json = ConversionHelper.toJson(addZone);

        mockMvc.perform(post("/api/zones").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addZoneInvalidModelFail() throws Exception {
        AddZone addZone = new AddZone();
        addZone.setDescription("This for testing purposes");
        addZone.setParentZoneId(parent.getId());
        addZone.setManagerId(currentUser.getId());

        String json = ConversionHelper.toJson(addZone);

        mockMvc.perform(post("/api/zones").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()).andExpect(status().reason(containsString("Zone is not valid")));
    }

    @Test
    public void addZoneNameConflictFail() throws Exception {
        AddZone addZone = new AddZone();
        addZone.setName(child.getName());
        addZone.setDescription("This for testing purposes");
        addZone.setParentZoneId(parent.getId());
        addZone.setManagerId(currentUser.getId());

        String json = ConversionHelper.toJson(addZone);

        mockMvc.perform(post("/api/zones").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isConflict());
    }
}
