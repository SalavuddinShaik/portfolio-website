package com.salavuddinshaik.service;

import com.salavuddinshaik.model.Portfolio;
import com.salavuddinshaik.repository.PortfolioRepository;
import com.salavuddinshaik.service.impl.PortfolioServiceImpl;  // Assuming this is your implementation class
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PortfolioServiceTest {

    @Mock
    private PortfolioRepository portfolioRepository;

    @InjectMocks
    private PortfolioServiceImpl portfolioService;  // Use the actual implementation class

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createPortfolio_ShouldReturnSavedPortfolio() {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.setName("Test Portfolio");
        portfolio.setDescription("Test Description");

        when(portfolioRepository.save(any(Portfolio.class))).thenReturn(portfolio);

        // When
        Portfolio savedPortfolio = portfolioService.createPortfolio(portfolio);

        // Then
        assertNotNull(savedPortfolio);
        assertEquals("Test Portfolio", savedPortfolio.getName());
    }

    @Test
    public void getPortfolioById_ShouldReturnNull_WhenIdDoesNotExist() {
        // Given
        Long nonExistentId = 999L;

        when(portfolioRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // When
        Portfolio portfolio = portfolioService.getPortfolioById(nonExistentId);

        // Then
        assertNull(portfolio);
    }

    @Test
    public void getPortfolioById_ShouldReturnPortfolio_WhenIdExists() {
        // Given
        Portfolio portfolio = new Portfolio();
        portfolio.setName("Existing Portfolio");
        portfolio.setDescription("Existing Description");

        when(portfolioRepository.findById(any(Long.class))).thenReturn(Optional.of(portfolio));

        // When
        Portfolio foundPortfolio = portfolioService.getPortfolioById(1L);

        // Then
        assertNotNull(foundPortfolio);
        assertEquals("Existing Portfolio", foundPortfolio.getName());
    }
}
