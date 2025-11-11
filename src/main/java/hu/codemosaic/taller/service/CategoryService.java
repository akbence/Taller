package hu.codemosaic.taller.service;

import hu.codemosaic.taller.db.CategoryDb;
import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDb categoryDb;

    public List<CategoryDto> getAllCategoriesForCurrentUser() {

        var categories =  categoryDb.findAllByOwnerId(UserContext.getUserId());
        return categories.stream().map(categoryEntity -> CategoryDto.builder()
                .name(categoryEntity.getName())
                .id(categoryEntity.getId())
                .description(categoryEntity.getDescription())
                .build())
            .toList();
    }

}
