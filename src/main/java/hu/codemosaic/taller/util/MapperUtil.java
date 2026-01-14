package hu.codemosaic.taller.util;

import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.dto.AccountTransactionDto;
import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.entity.AccountEntity;
import hu.codemosaic.taller.entity.AccountTransactionEntity;
import hu.codemosaic.taller.entity.CategoryEntity;


public class MapperUtil {

    public static AccountDto mapUserAccountEntityToAccountDto(AccountEntity entity) {
        if (entity == null) return null;
        return AccountDto.builder()
                .accountType(entity.getAccountType())
                .id(entity.getId())
                .name(entity.getName())
                .balance(entity.getBalance())
                .currency(entity.getCurrency())
                .accountContainer(entity.getAccountContainer().getId())
                .build();
    }

    public static CategoryDto mapCategoryEntityToCategoryDto(CategoryEntity entity) {
        if (entity == null) return null;
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    public static AccountTransactionDto mapAccountTransactionEntityToAccountTransactionDto(AccountTransactionEntity e) {
        return AccountTransactionDto.builder()
                .description(e.getDescription())
                .amount(e.getAmount())
                .latitude(e.getLatitude())
                .longitude(e.getLongitude())
                .transactionTime(e.getTransactionTime())
                .transactionType(e.getTransactionType())
                .currency(e.getCurrency())
                .account(mapUserAccountEntityToAccountDto(e.getAccount()))
                .category(mapCategoryEntityToCategoryDto(e.getCategory()))
                .build();
    }
}
