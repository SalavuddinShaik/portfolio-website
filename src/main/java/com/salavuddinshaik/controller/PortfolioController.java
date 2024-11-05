package com.salavuddinshaik.controller;

import com.salavuddinshaik.exception.ResourceNotFoundException;
import com.salavuddinshaik.model.Portfolio;
import com.salavuddinshaik.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

    @Autowired
private PortfolioService portfolioService;

// Create a new portfolio with validation
@PostMapping
public ResponseEntity<?> createPortfolio(@Valid @RequestBody Portfolio portfolio, BindingResult result) {
    if (result.hasErrors()) {
        // Log or print errors for debugging
        result.getAllErrors().forEach(error -> {
            System.out.println("Validation error: " + error.getDefaultMessage());
        });
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    Portfolio savedPortfolio = portfolioService.createPortfolio(portfolio);
    return new ResponseEntity<>(savedPortfolio, HttpStatus.CREATED);
}

// Get all portfolios
@GetMapping
public ResponseEntity<List<Portfolio>> getAllPortfolios() {
    List<Portfolio> portfolios = portfolioService.getAllPortfolios();
    return new ResponseEntity<>(portfolios, HttpStatus.OK);
}

// Get a portfolio by ID
@GetMapping("/{id}")
public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
    Portfolio portfolio = portfolioService.getPortfolioById(id);
    if (portfolio == null) {
        throw new ResourceNotFoundException("Portfolio not found with id: " + id);
    }
    return new ResponseEntity<>(portfolio, HttpStatus.OK);
}

// Update a portfolio by ID with validation
@PutMapping("/{id}")
public ResponseEntity<?> updatePortfolio(@PathVariable Long id, @Valid @RequestBody Portfolio updatedPortfolio, BindingResult result) {
    if (result.hasErrors()) {
        return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
    }

    Portfolio portfolio = portfolioService.getPortfolioById(id);
    if (portfolio == null) {
        throw new ResourceNotFoundException("Portfolio not found with id: " + id);
    }

    portfolio.setName(updatedPortfolio.getName());
    portfolio.setDescription(updatedPortfolio.getDescription());
    Portfolio updated = portfolioService.createPortfolio(portfolio);
    return new ResponseEntity<>(updated, HttpStatus.OK);
}

// Delete a portfolio by ID
@DeleteMapping("/{id}")
public ResponseEntity<String> deletePortfolio(@PathVariable Long id) {
    Portfolio portfolio = portfolioService.getPortfolioById(id);
    if (portfolio == null) {
        throw new ResourceNotFoundException("Portfolio not found with id: " + id);
    }

    portfolioService.deletePortfolio(id);
    return new ResponseEntity<>("Portfolio deleted successfully", HttpStatus.OK);
}
}
