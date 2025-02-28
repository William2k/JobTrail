package com.jobtrail.api.controllers;

import com.jobtrail.api.controllers.base.BaseControllerIntegrationTest;
import com.jobtrail.api.core.enums.Priority;
import com.jobtrail.api.core.helpers.ConversionHelper;
import com.jobtrail.api.dto.JobResponseDTO;
import com.jobtrail.api.dto.full.FullJobResponseDTO;
import com.jobtrail.api.models.AddJob;
import com.jobtrail.api.models.entities.JobEntity;
import com.jobtrail.api.repositories.JobRepository;
import com.jobtrail.api.services.ZoneService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class JobControllerIntegrationTest extends BaseControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobRepository jobRepository;

    @MockBean
    private ZoneService zoneService;

    private final JobEntity parent = new JobEntity();
    private final JobEntity child = new JobEntity();
    private final UUID zoneId = UUID.randomUUID();

    @Before
    public void setUp() throws Exception {
        init();

        jobSetUp();
    }

    private void jobSetUp() {
        parent.setId(UUID.randomUUID());
        parent.setName("parentTest");
        parent.setDescription("test");
        parent.setDueDate(LocalDateTime.now());
        parent.setManagerId(currentUser.getId());
        parent.setZoneId(zoneId);
        parent.setActive(true);

        child.setId(UUID.randomUUID());
        child.setName("childTest");
        child.setDescription("test");
        child.setDueDate(LocalDateTime.now());
        child.setManagerId(currentUser.getId());
        child.setZoneId(parent.getZoneId());
        child.setParentJobId(parent.getId());
        child.setActive(true);

        List<JobEntity> jobs = Arrays.asList(parent, child);

        Mockito.when(jobRepository.getById(parent.getId())).thenReturn(parent);
        Mockito.when(jobRepository.getById(child.getId())).thenReturn(child);
        Mockito.when(jobRepository.getJobsForUser(currentUser.getId())).thenReturn(jobs);
        Mockito.when(jobRepository.getJobByName(parent.getName(), parent.getZoneId())).thenReturn(parent);
        Mockito.when(jobRepository.getJobsForZone(zoneId)).thenReturn(jobs);
        Mockito.when(jobRepository.exists(parent.getName(), parent.getZoneId())).thenReturn(true);
        Mockito.when(jobRepository.getJobs(null, currentUser.getId(), null, null)).thenReturn(Collections.singletonList(parent));
        Mockito.when(jobRepository.getJobs(zoneId, null, null, null)).thenReturn(Collections.singletonList(parent));
    }

    @Test
    public void unauthorisedTest() throws Exception {
        authenticationTest("/api/jobs/" + parent.getId().toString());
    }

    @Test
    public void getJobs() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs?zoneId=" + zoneId).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getName()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<JobResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getJob() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs/" + parent.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getName()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        JobResponseDTO response = ConversionHelper.jsonToObject(json, JobResponseDTO.class);
    }

    @Test
    public void getFullJob() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs/" + child.getId() + "?full=true").header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getName()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        FullJobResponseDTO response = ConversionHelper.jsonToObject(json, FullJobResponseDTO.class);
    }

    @Test
    public void getJobsForUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs?userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getId().toString()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<JobResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void getFullJobsForUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/jobs?full=true&userId=" + currentUser.getId()).header("Authorization", "Bearer " + authToken))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString(parent.getId().toString()))).andReturn();

        String json = mvcResult.getResponse().getContentAsString();

        List<FullJobResponseDTO> response = ConversionHelper.jsonToListObject(json);
    }

    @Test
    public void addJobSuccess() throws Exception {
        AddJob job = new AddJob();
        job.setName("test");
        job.setDescription("This is for test purposes");
        job.setPriority(Priority.NORMAL.name());
        job.setDueDate(LocalDateTime.now().plusDays(1));
        job.setZoneId(UUID.randomUUID());
        job.setManagerId(UUID.randomUUID());

        String json = ConversionHelper.toJson(job);

        mockMvc.perform(post("/api/jobs").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void addJobInvalidModelFail() throws Exception {
        AddJob job = new AddJob();
        job.setName(parent.getName());
        job.setDescription("This is for test purposes");
        job.setDueDate(LocalDateTime.now());

        String json = ConversionHelper.toJson(job);

        mockMvc.perform(post("/api/jobs").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity()).andExpect(status().reason(containsString("Job is not valid")));
    }

    @Test
    public void addJobNameConflictFail() throws Exception {
        AddJob job = new AddJob();
        job.setName(parent.getName());
        job.setDescription("This is for test purposes");
        job.setDueDate(LocalDateTime.now().plusDays(1));
        job.setPriority(Priority.NORMAL.name());
        job.setZoneId(parent.getZoneId());
        job.setManagerId(currentUser.getId());

        String json = ConversionHelper.toJson(job);

        mockMvc.perform(post("/api/jobs").header("Authorization", "Bearer " + authToken)
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void takeJobSuccess() throws Exception {
        mockMvc.perform(put("/api/jobs/" + parent.getId() + "/takeJob").header("Authorization", "Bearer " + authToken))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void takeJobFakeJobIdFail() throws Exception {
        mockMvc.perform(put("/api/jobs/" + UUID.randomUUID() + "/takeJob").header("Authorization", "Bearer " + authToken))
                .andDo(print())
                .andExpect(status().isNotFound()).andExpect(status().reason(containsString("Job not found")));
    }
}
