package com.salavuddinshaik.service;

import com.salavuddinshaik.model.Portfolio;
import java.util.List;

public interface PortfolioService {
    List<Portfolio> getAllPortfolios();
    Portfolio getPortfolioById(Long id);
    Portfolio createPortfolio(Portfolio portfolio);
    void deletePortfolio(Long id);
}
