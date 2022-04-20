//package ru.skishop.controller;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import ru.skishop.dto.SkiDto;
//import ru.skishop.entity.Ski;
//import ru.skishop.mapper.SkiMapper;
//import ru.skishop.repository.SkiRepository;
//import ru.skishop.service.SkiService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//
//@ExtendWith(MockitoExtension.class)
//public class SkiControllerTestHowToDo {
//
//    @InjectMocks
//    SkiController skiController;
//
//    @Mock
//    SkiMapper skiMapper;
//
//    @Mock
//    SkiService skiService;
//
//    @Mock
//    SkiRepository skiRepository;
//
//    @Test
//    public void testFindAll() {
//
//        // given
//        SkiDto ski1 = new SkiDto();
//        ski1.setTitle("k2");
//        ski1.setLength(180);
//        SkiDto ski2 = new SkiDto();
//        ski2.setTitle("k2");
//        ski2.setLength(190);
//        List<SkiDto> skis = Arrays.asList(ski1, ski2);
//
//        when(skiService.findAllSkis()).thenReturn(skis);
//
//        // when
//        ResponseEntity<List<SkiDto>> result = skiController.findAllSkis();
//
//        // then
//        assertThat(Objects.requireNonNull(result.getBody()).size()).isEqualTo(2);
//
//        assertThat(result.getBody().get(0).getTitle())
//                .isEqualTo(ski1.getTitle());
//
//        assertThat(result.getBody().get(1).getTitle())
//                .isEqualTo(ski2.getTitle());
//    }
//
//    @Test
//    public void testAddEmployee() {
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//        Ski ski = new Ski();
//        ski.setTitle("k2");
//        ski.setLength(180);
//
//        when(skiRepository.save(ArgumentMatchers.any(Ski.class))).thenReturn(ski);
//        ResponseEntity<SkiDto> responseEntity = skiController.create(skiMapper.toSkiDto(ski));
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
//        assertThat(Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath()).isEqualTo("/1");
//    }
//}