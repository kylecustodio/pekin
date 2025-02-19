package work.custodio.pekin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import work.custodio.pekin.model.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}
