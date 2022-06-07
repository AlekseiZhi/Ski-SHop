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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.OrderDto;
import ru.skishop.dto.UserBasketItemDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.service.OrderService;
import ru.skishop.service.SecurityService;
import ru.skishop.service.UserBasketItemService;
import utils.SecurityMockUtils;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@ActiveProfiles(profiles = "testcontainers")
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String BEARER_TOKEN = "Bearer mock_token";
    private final MockMvc mockMvc;

    @MockBean
    private CurrentUser currentUser;

    @MockBean
    private SecurityService securityService;

    @Test
    @Transactional
    @Sql("/db/insertOrder.sql")
    public void getOrderForCurrentUser() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(get("/orders")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        List<OrderDto> orderDtos = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int expectedOrderSize = 2;
        int expectedSkiAmount = 7;
        int actualSkiAmount = orderDtos.get(0).getItems().get(0).getAmount();

        Assertions.assertEquals(expectedOrderSize, orderDtos.size());
        Assertions.assertEquals(expectedSkiAmount, actualSkiAmount);
    }
}
