package dev.spring.petclinic.controller;

import dev.spring.petclinic.model.Owner;
import dev.spring.petclinic.service.OwnerService;
import org.hibernate.jpa.boot.spi.EntityManagerFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {
    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    //@DisplayName으로 테스트 이름 자체를 직관적으로 작성하는 것이 좋음
    void findOwners() throws Exception { //전체 Owner 목록이 잘 조회되는지 테스트
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).
                andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    @DisplayName("2명의 Owner가 주어질 경우 2명의 Owner 목록이 출력(반환)된다.")
    void processFindFomrReturnMany() throws Exception {
        //when() 사용해서 2명의 Owner를 더미로 제공
        List<Owner> list = Arrays.asList(Owner.builder().id(1L).lastName("james").build(), Owner.builder().id(2L).lastName("james").build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(list);

        // mockMvc.perform() 코드 작성
        mockMvc.perform(get("/owners")).andExpect(status().isOk()).
                andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("listOwners", hasSize(2)));
    }

    @Test
    @DisplayName("1명의 Owner가 주어질 경우 1명의 Owner 목록이 출력(반환)된다.")
    void processFindFormReturnOne() throws Exception {
        //Status Code가 리다이렉션으로 응답하였는지도 확인
        List<Owner> list = Arrays.asList(Owner.builder().id(1L).build());
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(list);

        mockMvc.perform(get("/owners")).andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/"+list.get(0).getId()));
    }

    @Test
    @DisplayName("공백 값이 주어질 경우 전체 Owner 목록이 조회된다.")
    void processFindFormEmptyReturnMany() throws Exception {
        List<Owner> list = new ArrayList<>();
        list.add(Owner.builder().id(1L).build());
        list.add(Owner.builder().id(2L).build());

        when(ownerService.findAllByLastNameLike(anyString()))
                .thenReturn(list);

        mockMvc.perform(get("/owners").param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("listOwners", hasSize(2)));
    }

    @Test
    @DisplayName("1명의 Owner 객체를 전달받을 경우 등록 처리 수행 후 Detail 페이지로 리다이렉션 된다.")
    void processCreationForm() throws Exception {
        Owner owner = Owner.builder().build();
        when(ownerService.save(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/new")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

    }


    @Test
    void initCreationForm() {
    }

//    @Test
//    void processCreationForm() {
//    }

    @Test
    void initUpdateForm() {
    }

    @Test
    void initAddPetForm() {
    }
}