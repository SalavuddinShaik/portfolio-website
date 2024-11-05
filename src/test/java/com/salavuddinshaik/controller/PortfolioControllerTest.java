package com.salavuddinshaik.controller;

import com.salavuddinshaik.model.Portfolio;
import com.salavuddinshaik.service.PortfolioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PortfolioController.class)
public class PortfolioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PortfolioService portfolioService;

    @BeforeEach
    void setUp() {
        Portfolio portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setName("Test Portfolio");
        portfolio.setDescription("Test Description");

        when(portfolioService.getPortfolioById(1L)).thenReturn(portfolio);
    }

    @Test
    void getPortfolioById_ShouldReturnPortfolio() throws Exception {
        mockMvc.perform(get("/api/portfolio/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Portfolio"))
                .andExpect(jsonPath("$.description").value("Test Description"));
    }

    @Test
    void createPortfolio_ShouldReturnCreatedPortfolio() throws Exception {
        Portfolio portfolio = new Portfolio();
        portfolio.setName("New Portfolio");
        portfolio.setDescription("New Description");

        when(portfolioService.createPortfolio(any(Portfolio.class))).thenReturn(portfolio);

        mockMvc.perform(post("/api/portfolio")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"New Portfolio\", \"description\": \"New Description\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Portfolio"))
                .andExpect(jsonPath("$.description").value("New Description"));
    }
}
