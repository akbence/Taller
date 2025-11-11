package hu.codemosaic.taller.db;

import hu.codemosaic.taller.entity.CategoryEntity;
import hu.codemosaic.taller.exception.CategoryNotFoundException;
import hu.codemosaic.taller.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryDb {
    private final CategoryRepository repository;


    public CategoryEntity save(CategoryEntity categoryEntity) {
        return repository.save(categoryEntity);
    }

    public List<CategoryEntity> findAllByOwnerId(UUID ownerId) {
        return repository.findByOwner_Id(ownerId);
    }

    public CategoryEntity findByIdAndOwnerId(UUID id, UUID currentUserId) {
        return repository.findByIdAndOwnerId(id, currentUserId)
                .orElseThrow(()-> new CategoryNotFoundException("Category not found with id: " + id));
    }
}
