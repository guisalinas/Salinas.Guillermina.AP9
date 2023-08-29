package com.homebankingAP.homebankingAP.repositories;

import com.homebankingAP.homebankingAP.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByNumber(String number);

    Account findByNumber(String number);
}
