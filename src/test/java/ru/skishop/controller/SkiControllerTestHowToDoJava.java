//package ru.skishop.controller;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//public class SkiControllerTestHowToDoJava {
//
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void getAllEmployeesAPI() throws Exception
//    {
//        mvc.perform( MockMvcRequestBuilders
//                        .get("/employees")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees").exists())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employees[*].employeeId").isNotEmpty());
//    }
//
//    @Test
//    public void getEmployeeByIdAPI() throws Exception
//    {
//        mvc.perform( MockMvcRequestBuilders
//                        .get("/employees/{id}", 1)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").value(1));
//    }
//}
