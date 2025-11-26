package hu.codemosaic.taller.util;

import hu.codemosaic.taller.dto.AccountDto;
import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.entity.AccountEntity;
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
}
