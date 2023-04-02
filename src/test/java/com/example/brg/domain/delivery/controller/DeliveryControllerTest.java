package com.example.brg.domain.delivery.controller;

import com.example.brg.config.SecurityConfig;
import com.example.brg.domain.delivery.controller.request.DeliveryRequest;
import com.example.brg.domain.delivery.service.DeliveryService;
import com.example.brg.domain.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeliveryController.class)
class DeliveryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    DeliveryService deliveryService;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SecurityConfig securityConfig;

    @Test
    @DisplayName("배달 조회 성공")
    @WithMockUser(username = "test1")
    void getDeliveries() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 3);
        DeliveryRequest deliveryRequest = new DeliveryRequest(startDate, endDate);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(deliveryRequest)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/v1/deliveriy fail - 시작일이 종료일보다 나중인 경우")
    @WithMockUser(username = "test1")
    void getDelivereis_fail_date_error() throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 3);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        DeliveryRequest deliveryRequest = new DeliveryRequest(startDate, endDate);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(deliveryRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/v1/deliveriy fail - Authentication 미포함으로 인한 실패")
    // @WithMockUser를 사용하지 않아 Authentication 값이 빈 값이 된다.
    void getDelivereis_fail_date_error3()  throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 3);
        LocalDate endDate = LocalDate.of(2023, 1, 1);
        DeliveryRequest deliveryRequest = new DeliveryRequest(startDate, endDate);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(deliveryRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("PATCH /api/v1/deliveriy/{id} 성공")
    @WithMockUser(username = "test1")
    void update_address_success()  throws Exception {
        Long id = 1L;
        String destination = "a street";
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/delivery/" + id)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(destination))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PATCH /api/v1/deliveriy/{id} 실패 - 찾을 수 없는 계정인 경우")
    @WithMockUser(username = "test1")
    void update_address_fail2()  throws Exception {
        Long id = 1L;
        String destination = "a street";

        doNothing().when(deliveryService).updateAddress(eq(id), anyString(), eq(destination));

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/api/v1/delivery")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(destination);

        mockMvc.perform(builder)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/v1/deliveriy/{id} 실패 - 배달 정보가 없는데 수정하려는 경우")
    @WithMockUser(username = "test1")
    void update_address_fail3()  throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 3);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new DeliveryRequest(startDate, endDate))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/v1/deliveriy/{id} 실패 - Authentication 미포함")
    @WithMockUser(username = "test1")
    void update_address_fail4()  throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 3);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new DeliveryRequest(startDate, endDate))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PATCH /api/v1/deliveriy/{id} 실패 - 변경하려는 주소값이 미포함")
    @WithMockUser(username = "test1")
    void update_address_fail5()  throws Exception {
        LocalDate startDate = LocalDate.of(2023, 1, 3);
        LocalDate endDate = LocalDate.of(2023, 1, 1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/delivery")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new DeliveryRequest(startDate, endDate))))
                .andExpect(status().isBadRequest());
    }
}