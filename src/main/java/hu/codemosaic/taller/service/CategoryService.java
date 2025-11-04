package hu.codemosaic.taller.service;

import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.repository.CategoryRepository;
import hu.codemosaic.taller.security.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getAllCategoriesForCurrentUser() {

        var categories =  categoryRepository.findByOwner_Id(UserContext.getUserId());
        return categories.stream().map(categoryEntity -> CategoryDto.builder()
                .name(categoryEntity.getName())
                .build())
            .toList();
    }

}
