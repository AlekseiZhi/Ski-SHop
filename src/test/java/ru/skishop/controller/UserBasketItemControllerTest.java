package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.service.SecurityService;
import ru.skishop.service.UserBasketItemService;
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
public class UserBasketItemControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final MockMvc mockMvc;
    private final UserBasketItemService userBasketItemService;

    @SpyBean
    CurrentUser currentUser;

    @MockBean
    private SecurityService mockSecurityService;

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    public void getBasketForCurrentUser() throws Exception {

//        CurrentUser currentUser = Mockito.spy(CurrentUser.class);
//        currentUser.setId(1L);
//        currentUser.setEmail("test@email");
//        currentUser.setRoles(List.of("admin"));

//        Mockito.when(currentUser.getId()).thenReturn(1L);
//        Mockito.when(currentUser.getEmail()).thenReturn("asd");
//        Mockito.when(currentUser.getRoles()).thenReturn(List.of("admin"));

        doAnswer(SecurityMockUtils.replaceTokenProcess(currentUser)).when(mockSecurityService).addToSecurityContext(any(String.class));

        int page = 0;
        int size = 2;

        MvcResult mvcResult = mockMvc.perform(get("/baskets/current?page={page}&size={size}", page, size)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s"))
                .andExpect(status().isOk())
                .andReturn();

        List<UserBasketItemDto> userBasketItemDtoList = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });
        int actual = userBasketItemDtoList.get(0).getAmount();

        Assertions.assertEquals(size, userBasketItemDtoList.size());
        Assertions.assertEquals(1, actual);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    public void negativeGetBasketForCurrentUser() throws Exception {

        Integer page = null;
        Integer size = 0;

       // doAnswer(SecurityMockUtils.replaceTokenProcess(currentUser)).when(mockSecurityService).addToSecurityContext(any(String.class));

        mockMvc.perform(get("/baskets/current?page={page}&size={size}", page, size)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForCreateUserBasketItem.sql")
    public void create() throws Exception {

        //doAnswer(SecurityMockUtils.replaceTokenProcess(currentUser)).when(mockSecurityService).addToSecurityContext(any(String.class));

        MvcResult mvcResult = mockMvc.perform(post("/baskets?skiId=1")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s"))
                .andExpect(status().isOk())
                .andReturn();

        UserBasketItemDto userBasketItemDtoList = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int actual = userBasketItemDtoList.getAmount();

        Assertions.assertEquals(1, actual);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForCreateUserBasketItem.sql")
    public void negativeCreate() throws Exception {

      // doAnswer(SecurityMockUtils.replaceTokenProcess(currentUser)).when(mockSecurityService).addToSecurityContext(any(String.class));

        mockMvc.perform(post("/baskets")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void editAmount() throws Exception {

        Long skiId = 1L;
        int skiAmountExpected = 2;

       // CurrentUser currentUser = Mockito.spy(CurrentUser.class);
        //doAnswer(SecurityMockUtils.replaceTokenProcess(currentUser)).when(mockSecurityService).addToSecurityContext(any(String.class));

       mockMvc.perform(put("/baskets?skiId={skiId}&skiAmount={skiAmount}",skiId, skiAmountExpected)
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJhZG1pbkBtYWlsIiwicm9sZXMiOlsiYWRtaW4iXX0.Q86rc5CfwturyaJLEDDJCEkYTas7K2uV6AWnAEmnp-s"))
                .andExpect(status().isOk())
                .andReturn();

        List<UserBasketItemDto> userBasketItemDtoList = userBasketItemService.getBasketForCurrentUser(0,2);
        int skiAmountActual = userBasketItemDtoList.get(0).getAmount();

        Assertions.assertEquals(skiAmountExpected,skiAmountActual);
    }
}