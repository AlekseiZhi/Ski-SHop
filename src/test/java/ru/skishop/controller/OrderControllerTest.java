package ru.skishop.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.skishop.dto.OrderDto;
import ru.skishop.entity.CurrentUser;
import ru.skishop.service.OrderService;
import ru.skishop.service.SecurityService;
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
public class OrderControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());

    private static final String BEARER_TOKEN = "Bearer mock_token";
    private final MockMvc mockMvc;
    private final OrderService orderService;

    @MockBean
    private CurrentUser currentUser;

    @MockBean
    private SecurityService securityService;

    @Test
    @Transactional
    @Sql("/db/insertTestOrders.sql")
    public void getOrderForCurrentUser() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(get("/orders")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        List<OrderDto> actualOrderDtos = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int expectedOrderSize = 2;
        int expectedSkiAmount = 3;
        int actualSkiAmount = actualOrderDtos.get(0).getItems().get(0).getAmount();

        Assertions.assertEquals(expectedOrderSize, actualOrderDtos.size());
        Assertions.assertEquals(expectedSkiAmount, actualSkiAmount);
    }

    @Test
    @Transactional
    @Sql("/db/insertTestOrdersForCreate.sql")
    public void create() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(post("/orders/buy")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto actualOrderDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int expectedSkiAmount = 5;

        Assertions.assertEquals(expectedSkiAmount, actualOrderDto.getItems().get(0).getAmount());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestInfoForCreateUserBasketItem.sql")
    public void negativeCreate() throws Exception {
        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(post("/orders/buy")
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestOrders.sql")
    public void edit() throws Exception {
        Long orderId = 1L;
        Long skiId = 1L;
        int skiAmount = 11;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        MvcResult mvcResult = mockMvc.perform(put("/orders?orderId={orderId}&skiId={skiId}&skiAmount={skiAmount}", orderId, skiId, skiAmount)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isOk())
                .andReturn();

        OrderDto actualOrderDto = OBJECT_MAPPER.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        int expectedSkiAmount = 11;

        Assertions.assertEquals(expectedSkiAmount, actualOrderDto.getItems().get(1).getAmount());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestOrders.sql")
    public void negativeEditWithBadOrderId() throws Exception {
        Long orderId = 0L;
        Long skiId = 1L;
        int skiAmount = 11;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(put("/orders?orderId={orderId}&skiId={skiId}&skiAmount={skiAmount}", orderId, skiId, skiAmount)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    @Sql("/db/insertTestOrders.sql")
    public void negativeEditWithBadSkiId() throws Exception {
        Long orderId = 1L;
        Long skiId = 0L;
        int skiAmount = 11;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(put("/orders?orderId={orderId}&skiId={skiId}&skiAmount={skiAmount}", orderId, skiId, skiAmount)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/db/insertTestOrders.sql")
    @Transactional
    public void delete() throws Exception {
        Long orderId = 1L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders?orderId={orderId}", orderId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNoContent());

        Assertions.assertNull(orderService.findOrderById(orderId));
    }

    @Test
    @Sql("/db/insertTestOrders.sql")
    @WithMockUser(roles = "admin")
    @Transactional
    public void negativeDelete() throws Exception {
        Long orderId = 0L;

        doAnswer(SecurityMockUtils.replaceTokenProcess()).when(securityService).addToSecurityContext(any(String.class));
        SecurityMockUtils.mockCurrentUser(currentUser);

        mockMvc.perform(MockMvcRequestBuilders.delete("/orders?orderId={orderId}", orderId)
                        .header("Authorization", BEARER_TOKEN))
                .andExpect(status().isNotFound());
    }
}