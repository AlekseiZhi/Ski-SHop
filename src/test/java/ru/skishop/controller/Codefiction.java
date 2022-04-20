package ru.skishop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.config.SecurityConfig;
import ru.skishop.dto.SkiDto;
import ru.skishop.service.SkiService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:12:///springboot?TC_INITSCRIPT=somepath/init_mysql.sql"
})
@Testcontainers
public class Codefiction {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String TEST_HEADER = "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s";

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SkiService skiService;

    @Test
    public void findAllSkis() {

        SkiDto skiDto = new SkiDto();
        skiDto.setTitle("marksman");
        skiDto.setCategory("freeride");
        skiDto.setCompany("k2");
        skiDto.setLength(190);
        skiDto.setPrice(BigDecimal.valueOf(35000));
        List<SkiDto> skiDtoList = List.of(skiDto);

        given(skiService.findAllSkis()).willReturn(skiDtoList);

        List<SkiDto> expected = skiService.findAllSkis();

        Assertions.assertEquals(expected, skiDtoList);
        verify(skiService).findAllSkis();
    }

    @WithMockUser(roles = "admin")
    @Test
    public void create() throws Exception {

        SkiDto skiDto = new SkiDto();
        skiDto.setTitle("marksman");
        skiDto.setCategory("freeride");
        skiDto.setCompany("k2");
        skiDto.setLength(190);
        skiDto.setPrice(BigDecimal.valueOf(35000));

        given(skiService.create(skiDto)).willReturn(skiDto);

        mockMvc.perform(post("/ski")
                        .header("Authorization", TEST_HEADER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(OBJECT_MAPPER.writeValueAsString(skiDto)))
                .andExpect(status().isOk());

//        MvcResult mvcResult = mockMvc.perform(post("/ski")
//                        .contentType("application/json")
//                        .content(OBJECT_MAPPER.writeValueAsString(skiDto)))
//                .andExpect(status().isCreated())
//                .andReturn();
//        SkiDto expected = skiService.find(skiDto.getTitle());
//        Assertions.assertEquals(expected, skiDto);

//        SkiDto expected = skiDto;
//        SkiDto actual = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), SkiDto.class);
//        Assertions.assertEquals(actual.getTitle(),expected.getTitle());


//        assertThat(expected.getTitle()).isEqualTo("marksman");

//        MvcResult mvcResult = mockMvc.perform(post("/forums/{forumId}/register", 42L)
//                        .contentType("application/json")
//                        .param("sendWelcomeMail", "true")
//                        .content(objectMapper.writeValueAsString(user)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        UserResource expected = user;
//        UserResource actualResponseBody = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserResource.class);
//        assertThat(actualResponseBody.getName()).isEqualTo(expected.getName());
//        assertThat(actualResponseBody.getEmail()).isEqualTo(expected.getEmail());
    }

    @WithMockUser(roles = "admin")
    @Sql({"/insertTestRows.sql"})
    @Test
    public void delete() throws Exception {

        Long testId = 2L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/ski/{testId}", testId)
                        .header("Authorization", TEST_HEADER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertNull(skiService.find(testId));
    }
//        @Test
//        public void should_throw_exception_when_user_doesnt_exist() throws Exception {
//            User user = new User();
//            user.setId(89L);
//            user.setName("Test Name");
//
//            Mockito.doThrow(new UserNotFoundException(user.getId())).when(deleteUserService).deleteUser(user.getId());
//
//            mvc.perform(delete("/users/" + user.getId().toString())
//                            .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isNotFound());
//        }
//    }
}