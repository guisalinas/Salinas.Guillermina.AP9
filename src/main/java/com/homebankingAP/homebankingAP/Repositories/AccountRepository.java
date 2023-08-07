package com.homebankingAP.homebankingAP.Repositories;

import com.homebankingAP.homebankingAP.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {

}
