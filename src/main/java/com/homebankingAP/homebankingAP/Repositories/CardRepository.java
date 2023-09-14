package com.homebankingAP.homebankingAP.repositories;

import com.homebankingAP.homebankingAP.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
    boolean existsByNumber(String cardNumber);
    boolean existsByCvv(String cvv);

}
