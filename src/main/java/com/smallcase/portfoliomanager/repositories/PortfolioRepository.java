package com.smallcase.portfoliomanager.repositories;

import com.smallcase.portfoliomanager.models.dao.Portfolio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author Shantanu
 * Interface for Portfolio
 */
@Repository
public interface PortfolioRepository extends MongoRepository<Portfolio, String> {
}
