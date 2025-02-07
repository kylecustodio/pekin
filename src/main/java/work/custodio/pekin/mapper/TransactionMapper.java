package work.custodio.pekin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import work.custodio.pekin.dto.TransactionDTO;
import work.custodio.pekin.model.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "account.id", target = "accountId")
    TransactionDTO toDTO(Transaction transaction);

    @Mapping(source = "accountId", target = "account.id")
    Transaction toEntity(TransactionDTO dto);
}
