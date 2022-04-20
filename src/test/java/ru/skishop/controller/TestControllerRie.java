//package ru.skishop.controller;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.web.context.WebApplicationContext;
//import ru.skishop.dto.SkiDto;
//import ru.skishop.entity.Ski;
//import ru.skishop.service.SkiService;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////@ExtendWith(SpringExtension.class)
////@ContextConfiguration(classes = { ApplicationConfig.class })
////@WebAppConfiguration
//@WebMvcTest(SkiController.class)
//public class TestControllerRie {
//
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    @Autowired
//    private MockMvc mockMvc;
//
////    private MockMvc mockMvc;
////    @BeforeEach
////    public void setup() throws Exception {
////        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
////    }
//
//    @MockBean
//    private SkiService skiService;
//
//    @Test
//    public void shouldReturnAllUsersForUnauthenticatedUsers() throws Exception {
//
//        Ski ski = new Ski();
//        ski.setTitle("marksman");
//        ski.setCategory("freeride");
//        ski.setCompany("k2");
//        ski.setLength(190);
//        ski.setPrice(BigDecimal.valueOf(35000));
//
//        SkiDto skiDto = new SkiDto();
//        ski.setTitle("marksman");
//        ski.setCategory("freeride");
//        ski.setCompany("k2");
//        ski.setLength(190);
//        ski.setPrice(BigDecimal.valueOf(35000));
//
//        when(skiService.findAllSkis())
//                .thenReturn(List.of(skiDto));
//
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/ski"))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("marksman"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].company").value("k2"));
//    }

//    @Test
//    public void shouldAllowCreationForUnauthenticatedUsers() throws Exception {
//        this.mockMvc
//                .perform(
//                        post("/ski")
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content("{\"\": \"duke\", \"email\":\"duke@spring.io\"}")
//                )
//                .andExpect(status().isCreated())
//                .andExpect(header().exists("Location"))
//                .andExpect(header().string("Location", Matchers.containsString("duke")));
//
//        verify(userService).storeNewUser(any(User.class));
//    }
//}
