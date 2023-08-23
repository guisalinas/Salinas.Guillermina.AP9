package com.homebankingAP.homebankingAP.Repositories;

import com.homebankingAP.homebankingAP.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {
}
