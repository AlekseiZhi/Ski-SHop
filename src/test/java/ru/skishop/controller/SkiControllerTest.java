package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import ru.skishop.service.SecurityService;
import ru.skishop.service.SkiService;
import utils.HttpUtils;
import utils.SecurityMockUtils;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SkiControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final MockMvc mockMvc;
    private final SkiService skiService;
    private final String BEARER_TOKEN = "Bearer mock_token";

    @MockBean
    private SecurityService securityService;

    @Test
    @Transactional
    @Sql("/db/insertTestSki.sql")
    public void getSkisWithCriteria() throws Exception {
        Integer page = 0;
        Integer size = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}", page, size)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        PaginationWrapper<SkiDto> paginationWrapper = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(get("/skis?page={page}&size={size}", page, size)
                        .header("Authorization", BEARER_TOKEN))
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}&title={title}", page, size, title)
                        .header("Authorization", BEARER_TOKEN))
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(get("/skis?page={page}&size={size}&title={title}", page, size, title)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        boolean resultIsEmpty = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<SkiDto>>() {
        }).getData().isEmpty();

        Assertions.assertTrue(resultIsEmpty);
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(post("/skis")
                        .header("Authorization", BEARER_TOKEN)
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(post("/skis")
                        .header("Authorization", BEARER_TOKEN)
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(put("/skis")
                        .header("Authorization", BEARER_TOKEN)
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(put("/skis")
                        .header("Authorization", BEARER_TOKEN)
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(put("/skis")
                        .header("Authorization", BEARER_TOKEN)
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

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/skis/{testId}", testId)
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertNull(skiService.find(testId));
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    public void negativeDelete() throws Exception {
        Long testId = 4L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/skis/{testId}", testId)
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}