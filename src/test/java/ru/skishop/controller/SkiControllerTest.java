package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
import ru.skishop.dto.PaginationWrapper;
import ru.skishop.dto.SkiDto;
import ru.skishop.service.SkiService;
import utils.HttpUtils;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SkiControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SkiService skiService;

    @Test
    @Transactional
    @Sql("/db/insertTestSki.sql")
    public void getSkisWithCriteria() throws Exception {

        Integer page = 0;
        Integer size = 2;

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}", page, size))
                .andExpect(status().isOk())
                .andReturn();

        PaginationWrapper<SkiDto> paginationWrapper = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<SkiDto>>() {
        });

        Long totalElements = paginationWrapper.getTotalElements();

        int skisOnPage = paginationWrapper.getData().size();

        Integer prevPage = paginationWrapper.getPrevPage();

        Integer nextPage = paginationWrapper.getNextPage();

        Assertions.assertEquals(totalElements, 3);
        Assertions.assertEquals(skisOnPage, 2);
        Assertions.assertNull(prevPage);
        Assertions.assertEquals(nextPage, 1);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestSki.sql")
    public void negativeGetSkisWithCriteria() throws Exception {
        Integer page = null;
        Integer size = 0;

        mockMvc.perform(get("/skis?page={page}&size={size}", page, size))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql("/db/insertTestSki.sql")
    public void skiPageableFilter() throws Exception {

        Integer page = 0;
        Integer size = 2;
        String title = "marksmanTest1";

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}&title={title}", page, size, title))
                .andExpect(status().isOk())
                .andReturn();

        SkiDto expected = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<SkiDto>>() {
        }).getData().get(0);

        Assertions.assertEquals(expected.getTitle(), title);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestSki.sql")
    public void skiPageableFilterEmptyTitle() throws Exception {

        Integer page = 0;
        Integer size = 2;
        String title = "mar";

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}&title={title}", page, size, title))
                .andExpect(status().isOk())
                .andReturn();

        boolean expected = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<SkiDto>>() {
        }).getData().isEmpty();

        Assertions.assertTrue(expected);
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

        MvcResult mvcResult = mockMvc.perform(post("/skis")
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
    public void negativeSkiCreate() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("");
        expected.setCategory("");
        expected.setCompany("");
        expected.setLength(10);
        expected.setPrice(BigDecimal.valueOf(35));

        mockMvc.perform(post("/skis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isBadRequest());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestSki.sql")
    @Transactional
    public void edit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("marksmanTestEdit");
        expected.setCategory("freeride");
        expected.setCompany("k2");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(1L);

        MvcResult mvcResult = mockMvc.perform(put("/skis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andReturn();
        SkiDto actualSkiDto = HttpUtils.convertMvcResult(mvcResult, SkiDto.class);

        Assertions.assertEquals(expected.getTitle(), actualSkiDto.getTitle());
        Assertions.assertEquals(expected.getId(), actualSkiDto.getId());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestSki.sql")
    @Transactional
    public void negativeIdEdit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("marksmanTestEdit");
        expected.setCategory("freeride");
        expected.setCompany("k2");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(4L);

        mockMvc.perform(put("/skis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(roles = "admin")
    @Test
    @Sql("/db/insertTestSki.sql")
    @Transactional
    public void negativeFieldsEdit() throws Exception {

        SkiDto expected = new SkiDto();
        expected.setTitle("");
        expected.setCategory("");
        expected.setCompany("");
        expected.setLength(190);
        expected.setPrice(BigDecimal.valueOf(35000));
        expected.setId(1L);

        mockMvc.perform(put("/skis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expected)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "admin")
    @Sql("/db/insertTestSki.sql")
    @Transactional
    public void delete() throws Exception {
        Long testId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/skis/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertNull(skiService.find(testId));
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    public void negativeDelete() throws Exception {
        Long testId = 4L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/skis/{testId}", testId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}