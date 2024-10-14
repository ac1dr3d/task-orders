package com.task.orders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.task.orders.controllers.OrderController;
import com.task.orders.model.Order;
import com.task.orders.model.User;
import com.task.orders.producers.KafkaProducer;
import com.task.orders.repository.OrderRepository;
import com.task.orders.request.OrderRequest;
import com.task.orders.security.services.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private KafkaProducer kafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;
    private OrderRequest orderRequest;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L); // Assuming the User class has an id setter
        user.setUsername("john_doe");

        order = new Order();
        order.setId(1L);
        order.setUser(user); // Set the User object instead of userId
        order.setProduct("Laptop");
        order.setQuantity(2);
        order.setPrice(new BigDecimal("1500.00"));
        order.setStatus("PENDING");

        orderRequest = new OrderRequest();
        orderRequest.setUser_id(1L); // Assuming the request still uses user ID
        orderRequest.setProduct("Laptop");
        orderRequest.setQuantity(2);
        orderRequest.setPrice(new BigDecimal("1500.00"));
        orderRequest.setStatus("PENDING");
    }

    @Test
    @WithMockUser(roles = { "USER" })
    void testCreateOrder() throws Exception {
        when(orderService.createOrder(any(Long.class), any(String.class), any(Integer.class), any(BigDecimal.class),
                any(String.class)))
                .thenReturn(order);

        // log.info("Creating order for user with ID: {}", orderRequest);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(order)));
    }

    @Test
    @WithMockUser(roles = { "USER" })
    void testGetAllOrders() throws Exception {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order));

        mockMvc.perform(get("/api/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(Arrays.asList(order))));
    }
}