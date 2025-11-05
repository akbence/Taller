package hu.codemosaic.taller.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "accountContainer")
public class AccountContainerEntity extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private AppUserEntity owner;

    @OneToMany(mappedBy = "accountContainer", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountEntity> accounts = new ArrayList<>();

}
