package com.cabreu.candidatesmanagementservice.adapter.rest;

import com.cabreu.candidatesmanagementservice.application.port.in.CreateClientUseCase;
import com.cabreu.candidatesmanagementservice.application.port.in.GetAllClientsUseCase;
import com.cabreu.candidatesmanagementservice.application.port.in.GetClientMetricsUseCase;
import com.cabreu.candidatesmanagementservice.domain.model.Client;
import com.cabreu.candidatesmanagementservice.domain.model.ClientMetrics;
import com.cabreu.candidatesmanagementservice.domain.model.ClientWithEstimation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateClientUseCase createClientUseCase;

    @MockBean
    private GetAllClientsUseCase getAllClientsUseCase;

    @MockBean
    private GetClientMetricsUseCase getClientMetricsUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void createClient_shouldReturnCreatedClient() throws Exception {
        Client requestClient = new Client(null, "John", "Doe", 30, LocalDate.of(1995, 8, 4));
        Client createdClient = new Client(1L, "John", "Doe", 30, LocalDate.of(1995, 8, 4));

        when(createClientUseCase.create(any(Client.class))).thenReturn(createdClient);

        mockMvc.perform(post("/clients")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestClient)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.birthDate").value("1995-08-04"));
    }


    @Test
    @WithMockUser(roles = "USER")
    void getAllClients_shouldReturnClientList() throws Exception {
        ClientWithEstimation client1 = new ClientWithEstimation(
                1L, "John", "Doe", 30, LocalDate.of(1994, 1, 1), LocalDate.of(2074, 1, 1));
        ClientWithEstimation client2 = new ClientWithEstimation(
                2L, "Jane", "Smith", 25, LocalDate.of(1999, 1, 1), LocalDate.of(2079, 1, 1));

        when(getAllClientsUseCase.getAll()).thenReturn(List.of(client1, client2));


        mockMvc.perform(get("/clients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getMetrics_shouldReturnClientMetrics() throws Exception {
        ClientMetrics metrics = new ClientMetrics(28.5, 4.2);

        when(getClientMetricsUseCase.getMetrics()).thenReturn(metrics);

        mockMvc.perform(get("/clients/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge").value(28.5))
                .andExpect(jsonPath("$.standardDeviation").value(4.2));
    }
}
