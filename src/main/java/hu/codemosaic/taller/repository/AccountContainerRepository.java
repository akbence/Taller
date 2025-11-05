package hu.codemosaic.taller.repository;

import hu.codemosaic.taller.entity.AccountContainerEntity;
import hu.codemosaic.taller.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public interface AccountContainerRepository extends JpaRepository<AccountContainerEntity, UUID> {
    List<AccountContainerEntity> findByOwnerId(UUID ownerId);

    Arrays findByIdAndOwnerId(UUID containerId, UUID currentUserId);

    List<AccountContainerEntity> findAllByOwnerId(UUID ownerId);
}
