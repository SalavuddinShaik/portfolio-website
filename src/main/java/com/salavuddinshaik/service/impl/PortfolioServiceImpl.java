package com.salavuddinshaik.service.impl;

import com.salavuddinshaik.model.Portfolio;
import com.salavuddinshaik.repository.PortfolioRepository;
import com.salavuddinshaik.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PortfolioServiceImpl implements PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

    @Override
    public void deletePortfolio(Long id) {
        portfolioRepository.deleteById(id);
    }
}
