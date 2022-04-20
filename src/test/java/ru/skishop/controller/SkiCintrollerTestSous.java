package ru.skishop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.skishop.entity.Ski;
import ru.skishop.repository.SkiRepository;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SkiCintrollerTestSous {


        @Autowired
        private ObjectMapper objectMapper;
        @Autowired
        private SkiRepository repository;
        @Autowired
        private MockMvc mockMvc;

//        @AfterEach
//        public void resetDb() {
//            repository.deleteAll();
//        }

        @Test
        public void givenPerson_whenAdd_thenStatus200ndPersonReturned() throws Exception {
            Ski ski = new Ski();
            ski.setTitle("marksman");
            ski.setCategory("freeride");
            ski.setCompany("k2");
            ski.setLength(190);
            ski.setPrice(BigDecimal.valueOf(35000));

            mockMvc.perform(
                            post("/ski")
                                    .content(objectMapper.writeValueAsString(ski))
                                    .contentType(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.title").value("marksman"));
        }
        //другие тесты

        private Ski createTestPerson(String name) {
            Ski emp = new Ski();
            return repository.save(emp);
        }
    }