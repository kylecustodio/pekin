package work.custodio.pekin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String description;
    private LocalDate date;

    public TransactionDTO(Long id, Long accountId, BigDecimal amount, String description, LocalDate date) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
