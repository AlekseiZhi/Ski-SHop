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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.exceptionHandler.NotFoundException;
import ru.skishop.service.SecurityService;
import ru.skishop.service.UserBasketItemService;
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
public class UserBasketItemControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BEARER_TOKEN = "Bearer mock_token";
    private final MockMvc mockMvc;
    private final UserBasketItemService userBasketItemService;

    @MockBean
    private CurrentUser currentUser;

    @MockBean
    private SecurityService securityService;

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    public void getBasketForCurrentUser() throws Exception {
        int page = 0;
        int size = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(get("/baskets/current?page={page}&size={size}", page, size)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        List<UserBasketItemDto> actualUserBasketItems = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int actualSkiAmount = actualUserBasketItems.get(0).getAmount();
        int expectedSkiAmount = 1;

        Assertions.assertEquals(size, actualUserBasketItems.size());
        Assertions.assertEquals(expectedSkiAmount, actualSkiAmount);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    public void negativeGetBasketForCurrentUser() throws Exception {
        Integer page = null;
        Integer size = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(get("/baskets/current?page={page}&size={size}", page, size)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForCreateUserBasketItem.sql")
    public void create() throws Exception {
        Long skiId = 1L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(post("/baskets?skiId={skiId}", skiId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        UserBasketItemDto userBasketItemDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int actualSkiAmount = userBasketItemDto.getAmount();
        Long actualUserBasketItemId = userBasketItemDto.getId();
        int expectedSkiAmount = 1;

        Assertions.assertEquals(expectedSkiAmount, actualSkiAmount);
        Assertions.assertNotNull(actualUserBasketItemId);
        Assertions.assertNotNull(userBasketItemService.findById(actualUserBasketItemId));
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForCreateUserBasketItem.sql")
    public void negativeCreate() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(post("/baskets")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void editAmount() throws Exception {
        Long skiId = 1L;
        int expectedSkiAmount = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(put("/baskets?skiId={skiId}&skiAmount={skiAmount}", skiId, expectedSkiAmount)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        UserBasketItemDto userBasketItem = HttpUtils.convertMvcResult(mvcResult, UserBasketItemDto.class);
        List<UserBasketItemDto> actualUserBasketItems = userBasketItemService.getBasketForCurrentUser(0, 2);
        int actualSkiAmount = actualUserBasketItems.get(0).getAmount();

        Assertions.assertEquals(expectedSkiAmount, actualSkiAmount);
        Assertions.assertEquals(expectedSkiAmount, userBasketItem.getAmount());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void negativeEditAmount() throws Exception {
        Long skiId = 0L;
        int skiAmountExpected = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(put("/baskets?skiId={skiId}&skiAmount={skiAmount}", skiId, skiAmountExpected)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void negativeEditWithNegativeAmount() throws Exception {
        Long skiId = 1L;
        int skiAmountExpected = -2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(put("/baskets?skiId={skiId}&skiAmount={skiAmount}", skiId, skiAmountExpected)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void delete() throws Exception {
        Long skiId = 1L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/baskets/{skiId}", skiId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNoContent());

        Assertions.assertThrows(NotFoundException.class, () -> userBasketItemService.getBasketItem(currentUser.getId(), skiId));
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void negativeDelete() throws Exception {
        Long skiId = 55L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/baskets/{skiId}", skiId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/db/insertTestInfoForUserBasketItem.sql")
    @Transactional
    public void clearBasketForCurrentUser() throws Exception {
        int page = 0;
        int size = 2;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/baskets/clear")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(userBasketItemService.getBasketForCurrentUser(page, size).isEmpty());
    }
}