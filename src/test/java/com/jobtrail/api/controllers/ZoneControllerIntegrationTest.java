package com.jobtrail.api.controllers;

import com.jobtrail.api.controllers.base.BaseControllerIntegrationTest;
import com.jobtrail.api.models.entities.ZoneEntity;
import com.jobtrail.api.repositories.ZoneRepository;
import com.jobtrail.api.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private UUID parentZoneId = UUID.fromString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e");
    private UUID childZoneId = UUID.fromString("2da9c33a-5eeb-43e6-b3e2-7c9af4da2fbd");

    @Before
    public void setUp() throws Exception {
        init();

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

        Mockito.when(zoneRepository.getByName(zone.getName())).thenReturn(zone);
        Mockito.when(zoneRepository.getById(zone.getId())).thenReturn(zone);
        Mockito.when(zoneRepository.getById(zoneChild.getId())).thenReturn(zoneChild);
    }

    @Test
    public void getUserZones() throws Exception {
        mockMvc.perform(get("/api/zone?userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }

    @Test
    public void getZoneById() throws Exception {
        mockMvc.perform(get("/api/zone/" + parentZoneId).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }

    @Test
    public void getZoneByName() throws Exception {
        mockMvc.perform(get("/api/zone/get?name=test").header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }

    @Test
    public void getZoneComplex() throws Exception {
        mockMvc.perform(get("/api/zone/" + childZoneId).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("207a488e-b9b0-4a05-8b17-7b38b5ccad9e")));
    }
}
