package hu.codemosaic.taller.unit.service;

import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.entity.CategoryEntity;
import hu.codemosaic.taller.repository.CategoryRepository;
import hu.codemosaic.taller.security.UserContext;
import hu.codemosaic.taller.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void getAllCategoriesForCurrentUser_returnsMappedDtos() {
        // Arrange
        UUID userId = UUID.randomUUID();

        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName("Groceries");

        List<CategoryEntity> categoryEntities = List.of(categoryEntity);

        try (MockedStatic<UserContext> mockedUserContext = Mockito.mockStatic(UserContext.class)) {
            mockedUserContext.when(UserContext::getUserId).thenReturn(userId);
            when(categoryRepository.findByOwner_Id(userId)).thenReturn(categoryEntities);

            // Act
            List<CategoryDto> result = categoryService.getAllCategoriesForCurrentUser();

            // Assert
            assertEquals(1, result.size());
            assertEquals("Groceries", result.getFirst().getName());
        }
    }
}
