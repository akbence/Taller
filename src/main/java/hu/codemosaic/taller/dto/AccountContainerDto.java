package hu.codemosaic.taller.dto;

import hu.codemosaic.taller.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountContainerDto {

    private String name;
    private AccountType accountType;
    private List<AccountDto> subaccounts;


}
