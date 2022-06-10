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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.RoleDto;
import ru.skishop.dto.UserDto;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.service.SecurityService;
import ru.skishop.service.UserService;
import utils.HttpUtils;
import utils.SecurityMockUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BEARER_TOKEN = "Bearer mock_token";
    private final MockMvc mockMvc;
    private final UserService userService;

    @MockBean
    private SecurityService securityService;

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void create() throws Exception {
        RoleDto role = new RoleDto();
        role.setName("admin");
        role.setId(1L);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setFullName("testName");
        expectedUserDto.setEmail("test@email");
        expectedUserDto.setPassword("password");
        expectedUserDto.setRoles(List.of(role));

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expectedUserDto)))
                .andExpect(status().isOk())
                .andReturn();

        UserDto actualUser = HttpUtils.convertMvcResult(mvcResult, UserDto.class);

        Assertions.assertEquals(expectedUserDto.getFullName(), actualUser.getFullName());
        Assertions.assertNotNull(userService.findById(actualUser.getId()));
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void negativeCreateEmptyPassword() throws Exception {
        RoleDto role = new RoleDto();
        role.setName("admin");
        role.setId(1L);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setFullName("testName");
        expectedUserDto.setEmail("test@email");
        expectedUserDto.setPassword("");
        expectedUserDto.setRoles(List.of(role));

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(post("/users")
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expectedUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void negativeCreateEmptyName() throws Exception {
        RoleDto role = new RoleDto();
        role.setName("admin");
        role.setId(1L);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setFullName("");
        expectedUserDto.setEmail("test@email");
        expectedUserDto.setPassword("password");
        expectedUserDto.setRoles(List.of(role));

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(post("/users")
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expectedUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void findAll() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(get("/users")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        List<UserDto> actualUsers = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, actualUsers.size());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestOrdersForCreate.sql")
    public void findById() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        Long userId = 1L;

        MvcResult mvcResult = mockMvc.perform(get("/users/{userId}", userId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        String expectedFullName = "admin";

        UserDto actualUser = HttpUtils.convertMvcResult(mvcResult, UserDto.class);
        Assertions.assertEquals(expectedFullName, actualUser.getFullName());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void negativeFindById() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        Long userId = 5L;

        mockMvc.perform(get("/users/{userId}", userId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void edit() throws Exception {
        RoleDto role = new RoleDto();
        role.setName("admin");
        role.setId(1L);
        UserDto expectedUser = new UserDto();
        expectedUser.setId(2L);
        expectedUser.setFullName("testName");
        expectedUser.setEmail("user@mail");
        expectedUser.setPassword("password");
        expectedUser.setRoles(List.of(role));

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(put("/users")
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expectedUser)))
                .andExpect(status().isOk())
                .andReturn();

        UserDto actualUser = HttpUtils.convertMvcResult(mvcResult, UserDto.class);

        Assertions.assertEquals(expectedUser.getFullName(), actualUser.getFullName());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestRoleAndUser.sql")
    public void negativeEdit() throws Exception {
        RoleDto role = new RoleDto();
        role.setName("admin");
        role.setId(1L);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(2L);
        expectedUserDto.setFullName("");
        expectedUserDto.setEmail("test@email");
        expectedUserDto.setPassword("password");
        expectedUserDto.setRoles(List.of(role));

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(put("/users")
                        .header("Authorization", BEARER_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(expectedUserDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/insertTestRoleAndUser.sql")
    @Transactional
    public void delete() throws Exception {
        Long userId = 2L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());

        Assertions.assertThrows(NotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    @Sql("/db/insertTestRoleAndUser.sql")
    @Transactional
    public void negativeDelete() throws Exception {
        Long userId = 6L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{userId}", userId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }
}
