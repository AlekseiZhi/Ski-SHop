package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.SkiDto;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.mapper.SkiMapper;
import ru.skishop.repository.SkiRepository;
import ru.skishop.service.SkiService;
import utils.HttpUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SkiControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    //private static final String TEST_HEADER = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SkiRepository skiRepository;

    @Autowired
    private SkiService skiService;

    @Autowired
    private SkiMapper skiMapper;

    @Test
    @Transactional
    @Sql("/db/insertTestRows.sql")
    public void findAllSkis() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/ski"))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(skiRepository.findAll());

        List<SkiDto> skiDtoListExpected = skiRepository.findAll().stream().map(skiMapper::toSkiDto).toList();
        List<SkiDto> skiDtoListActual = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        SkiDto expected = skiDtoListExpected.get(0);
        SkiDto actual = skiDtoListActual.get(0);

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Transactional
    public void create() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("marksmanTestCreate");
        expected.setCategory("freeride");
        expected.setCompany("k2");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));

        MvcResult mvcResult = mockMvc.perform(post("/ski")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andReturn();

        SkiDto actual = HttpUtils.convertMvcResult(mvcResult, SkiDto.class);

        Assertions.assertEquals(expected.getTitle(), actual.getTitle());
        Assertions.assertNotNull(actual.getId());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Transactional
    public void negativeCredentialsCreate() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("");
        expected.setCategory("");
        expected.setCompany("");
        expected.setLength(10);
        expected.setPrice(BigDecimal.valueOf(35));

        mockMvc.perform(post("/ski")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestRows.sql")
    @Transactional
    public void edit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("marksmanTestEdit");
        expected.setCategory("freeride");
        expected.setCompany("k2");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(1L);

        MvcResult mvcResult = mockMvc.perform(put("/ski")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andReturn();

        Assertions.assertEquals(expected.getTitle(), HttpUtils.convertMvcResult(mvcResult, SkiDto.class).getTitle());

        //Assertions.assertEquals(expected.getId(), OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), SkiDto.class).getId());
        Assertions.assertEquals(expected.getId(), HttpUtils.convertMvcResult(mvcResult, SkiDto.class).getId());

    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestRows.sql")
    @Transactional
    public void negativeIdEdit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("marksmanTestEdit");
        expected.setCategory("freeride");
        expected.setCompany("k2");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(2L);

        mockMvc.perform(put("/ski")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestRows.sql")
    @Transactional
    public void negativeCredentialsEdit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("");
        expected.setCategory("");
        expected.setCompany("");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(1L);

       mockMvc.perform(put("/ski")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "admin")
    @Sql("/db/insertTestRows.sql")
    @Transactional
    public void delete() throws Exception {
        Long testId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/ski/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertNull(skiService.find(testId));
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    public void negativeDelete() throws Exception {
        Long testId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/ski/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}