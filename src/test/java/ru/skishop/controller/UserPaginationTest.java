package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.PaginationWrapper;
import ru.skishop.dto.UserDto;
import ru.skishop.service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserPaginationTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    @Sql("/db/insertTestUser.sql")
    public void getUsersWithCriteria() throws Exception {

        Integer page = 0;
        Integer size = 2;

        MvcResult mvcResult = mockMvc.perform(get("/users?page={page}&size={size}", page, size))
                .andExpect(status().isOk())
                .andReturn();

        PaginationWrapper<UserDto> paginationWrapper = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        Long totalElements = paginationWrapper.getTotalElements();

        int usersOnPage = paginationWrapper.getData().size();

        Integer prevPage = paginationWrapper.getPrevPage();

        Integer nextPage = paginationWrapper.getNextPage();

        Assertions.assertEquals(totalElements, 3);
        Assertions.assertEquals(usersOnPage, 2);
        Assertions.assertNull(prevPage);
        Assertions.assertEquals(nextPage, 1);
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    @Sql("/db/insertTestUser.sql")
    public void negativeGetUsersWithCriteria() throws Exception {
        Integer page = null;
        Integer size = 0;

        mockMvc.perform(get("/users?page={page}&size={size}", page, size))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    @Sql("/db/insertTestUser.sql")
    public void userPageableFilter() throws Exception {

        Integer page = 0;
        Integer size = 2;
        String fullName = "user1";

        MvcResult mvcResult = mockMvc.perform(get("/users?page={page}&size={size}&fullName={fullName}", page, size, fullName))
                .andExpect(status().isOk())
                .andReturn();

        UserDto expected = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<UserDto>>() {
        }).getData().get(0);

        Assertions.assertEquals(expected.getFullName(), fullName);
    }

    @Test
    @WithMockUser(roles = "admin")
    @Transactional
    @Sql("/db/insertTestUser.sql")
    public void userPageableFilterEmptyFullName() throws Exception {

        Integer page = 0;
        Integer size = 2;
        String fullName = "us";

        MvcResult mvcResult = mockMvc.perform(get("/users?page={page}&size={size}&fullName={fullName}", page, size, fullName))
                .andExpect(status().isOk())
                .andReturn();

        boolean resultIsEmpty = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<PaginationWrapper<UserDto>>() {
        }).getData().isEmpty();

        Assertions.assertTrue(resultIsEmpty);
    }
}