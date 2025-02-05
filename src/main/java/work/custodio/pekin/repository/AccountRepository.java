package work.custodio.pekin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.custodio.pekin.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {}
