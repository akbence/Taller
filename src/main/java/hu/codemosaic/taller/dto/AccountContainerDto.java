package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AccountContainerDto {

    private UUID id;
    private String name;
    private AccountType accountType;
    private List<AccountDto> subaccounts;


}
