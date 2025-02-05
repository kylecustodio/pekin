package work.custodio.pekin.controller;

import java.util.List;
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
    public Account getAccount(@PathVariable Long id) {
        return accountRepository
            .findById(id)
            .orElseThrow(RuntimeException::new);
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
        Account existingAccount = accountRepository.findById(id).orElseThrow(RuntimeException::new);
        existingAccount.setName(account.getName());
        existingAccount = accountRepository.save(existingAccount);

        return ResponseEntity.ok(existingAccount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
