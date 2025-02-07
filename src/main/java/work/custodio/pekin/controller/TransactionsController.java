package work.custodio.pekin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import work.custodio.pekin.dto.TransactionDTO;
import work.custodio.pekin.mapper.TransactionMapper;
import work.custodio.pekin.model.Transaction;
import work.custodio.pekin.repository.TransactionRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionsController {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionsController(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
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
    public TransactionDTO createTransaction(@RequestBody Transaction transaction) {
        return transactionMapper.toDTO(transactionRepository.save(transaction));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transaction) {
        // TODO: enable partial updates
        if (!transactionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transaction.setId(id);
        Transaction updatedTransaction = transactionRepository.save(transactionMapper.toEntity(transaction));
        return ResponseEntity.ok(transactionMapper.toDTO(updatedTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionDTO> deleteTransaction(@PathVariable Long id) {
        if (!transactionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transactionRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
