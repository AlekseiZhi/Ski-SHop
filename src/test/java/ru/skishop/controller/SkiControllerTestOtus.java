package ru.skishop.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skishop.entity.Ski;
import ru.skishop.repository.SkiRepository;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@WebMvcTest(SkiController.class)
//@AutoConfigureMockMvc
@WebAppConfiguration
@SpringBootTest
public class SkiControllerTestOtus {


        @MockBean
        private SkiRepository repository;

@Autowired
        private MockMvc mockMvc;

    @Test
        public void testReturn200() throws Exception {
        Ski ski = new Ski();
        ski.setTitle("marksman");
        ski.setCategory("freeride");
        ski.setCompany("k2");
        ski.setLength(190);
        ski.setPrice(BigDecimal.valueOf(35000));

            given(repository.getById(any())).willReturn(ski);
            this.mockMvc.perform(MockMvcRequestBuilders.get("/ski"))
                    .andExpect(status().isOk())
                    .andExpect(content()
                            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        }
    }