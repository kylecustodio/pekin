package work.custodio.pekin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.custodio.pekin.dto.TransactionDTO;
import work.custodio.pekin.mapper.TransactionMapper;
import work.custodio.pekin.model.Account;
import work.custodio.pekin.model.Transaction;
import work.custodio.pekin.repository.AccountRepository;
import work.custodio.pekin.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    public TransactionsController(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream().map(transactionMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransaction(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(transactionMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountId}")
    public List<TransactionDTO> getTransactionsByAccountId(@PathVariable Long accountId) {
        return transactionRepository.findByAccountId(accountId).stream().map(transactionMapper::toDTO).toList();
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transaction) {
        Transaction newTransaction = transactionMapper.toEntity(transaction);

        Account account = accountRepository.findById(transaction.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(newTransaction.getAmount()));

        return transactionMapper.toDTO(transactionRepository.save(newTransaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transaction) {
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);

        if (existingTransaction == null) {
            return ResponseEntity.notFound().build();
        }

        Account account = existingTransaction.getAccount();

        account.setBalance(account.getBalance().subtract(existingTransaction.getAmount()));
        account.setBalance(account.getBalance().add(transaction.getAmount()));

        Transaction updatedTransaction = transactionMapper.toEntity(transaction);
        updatedTransaction.setId(id);
        updatedTransaction.setAccount(account);

        updatedTransaction = transactionRepository.save(updatedTransaction);
        return ResponseEntity.ok(transactionMapper.toDTO(updatedTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionDTO> deleteTransaction(@PathVariable Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        transaction.getAccount().setBalance(transaction.getAccount().getBalance().subtract(transaction.getAmount()));

        transactionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
