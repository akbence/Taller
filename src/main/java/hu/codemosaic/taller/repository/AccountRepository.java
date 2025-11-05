package hu.codemosaic.taller.repository;

import hu.codemosaic.taller.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    List<AccountEntity> findByAccountContainerId(UUID containerId);

    @Query("""
    SELECT a FROM account a
    WHERE a.accountContainer.id = :containerId
    AND a.accountContainer.owner.id = :ownerId
""")
    List<AccountEntity> findByContainerIdAndOwnerId(UUID containerId, UUID ownerId);
}
