package hu.codemosaic.taller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class AccountContainerDto {

    private UUID id;
    private String name;
    private List<AccountDto> subaccounts;
}
