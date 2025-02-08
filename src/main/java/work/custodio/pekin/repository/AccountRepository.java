package work.custodio.pekin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import work.custodio.pekin.model.Account;

import java.math.BigDecimal;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
