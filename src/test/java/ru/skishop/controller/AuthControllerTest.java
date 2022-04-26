package ru.skishop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.TokenWrapperDto;
import ru.skishop.dto.UserForAuthDto;
import utils.HttpUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void login() throws Exception {

        UserForAuthDto userForAuthDto = new UserForAuthDto();
        userForAuthDto.setEmail("admin@mail");
        userForAuthDto.setPassword("123");
        userForAuthDto.setFullName("admin");

        MvcResult mvcResult = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userForAuthDto)))
                .andExpect(status().isOk())
                .andReturn();

        TokenWrapperDto actualObject = HttpUtils.convertMvcResult(mvcResult, TokenWrapperDto.class);
        String actualToken = actualObject != null ? actualObject.getAccessToken() : null;

        Assertions.assertNotNull(actualToken);
        Assertions.assertNotEquals("", actualToken);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void negativeLogin() throws Exception {

        UserForAuthDto userForAuthDto = new UserForAuthDto();
        userForAuthDto.setEmail("asd");
        userForAuthDto.setPassword("asd");
        userForAuthDto.setFullName("asd");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userForAuthDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    void createNewUser() throws Exception {

        UserForAuthDto userForAuthDto = new UserForAuthDto();
        userForAuthDto.setEmail("test1@mail");
        userForAuthDto.setPassword("asd123");
        userForAuthDto.setFullName("asd");

        MvcResult mvcResult = mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userForAuthDto)))
                .andExpect(status().isOk())
                .andReturn();

        TokenWrapperDto actualObject = HttpUtils.convertMvcResult(mvcResult, TokenWrapperDto.class);
        String actualToken = actualObject.getAccessToken();

        Assertions.assertNotNull(actualObject);
        Assertions.assertNotNull(actualToken);
        Assertions.assertNotEquals("", actualToken);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    void negativeCreateNewUser() throws Exception {

        UserForAuthDto userForAuthDto = new UserForAuthDto();
        userForAuthDto.setEmail("test1@mail");
        userForAuthDto.setPassword("");
        userForAuthDto.setFullName("asd");

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(userForAuthDto)))
                .andExpect(status().isBadRequest());
    }
}