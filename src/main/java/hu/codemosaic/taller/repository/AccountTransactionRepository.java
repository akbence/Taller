package hu.codemosaic.taller.repository;

import hu.codemosaic.taller.entity.AccountTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, UUID> {

}
