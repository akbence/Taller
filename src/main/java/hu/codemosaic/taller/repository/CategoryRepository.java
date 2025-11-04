package hu.codemosaic.taller.repository;

import hu.codemosaic.taller.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, UUID> {

    List<CategoryEntity> findByOwner_Id(UUID appUserId);

    Optional<CategoryEntity> findByIdAndOwner_Id(UUID categoryId, UUID userId);
}
