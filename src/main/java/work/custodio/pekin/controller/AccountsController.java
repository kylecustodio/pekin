package work.custodio.pekin.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.custodio.pekin.model.Account;
import work.custodio.pekin.repository.AccountRepository;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {

    private final AccountRepository accountRepository;

    public AccountsController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(
        @PathVariable Long id,
        @RequestBody Account account
    ) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        account.setId(id);
        Account updatedAccount = accountRepository.save(account);
        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
