package work.custodio.pekin.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.custodio.pekin.dto.AccountDTO;
import work.custodio.pekin.mapper.AccountMapper;
import work.custodio.pekin.model.Account;
import work.custodio.pekin.repository.AccountRepository;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountsController(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(accountMapper::toDTO).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(accountMapper::toDTO).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
        return accountRepository.findById(id).map(Account::getBalance).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody Account account) {
        return accountMapper.toDTO(accountRepository.save(account));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> updateAccount(
            @PathVariable Long id,
            @RequestBody Account account
    ) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        account.setId(id);
        Account updatedAccount = accountRepository.save(account);
        return ResponseEntity.ok(accountMapper.toDTO(updatedAccount));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AccountDTO> deleteAccount(@PathVariable Long id) {
        if (!accountRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        accountRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
