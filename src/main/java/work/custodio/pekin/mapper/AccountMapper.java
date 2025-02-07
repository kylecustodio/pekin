package work.custodio.pekin.mapper;

import org.mapstruct.Mapper;
import work.custodio.pekin.dto.AccountDTO;
import work.custodio.pekin.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO accountDTO);
}
