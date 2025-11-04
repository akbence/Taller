package hu.codemosaic.taller.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static jakarta.persistence.FetchType.LAZY;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "category")
@Data
public class CategoryEntity extends BaseEntity{

    private String name;
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner", nullable = false)
    private AppUserEntity owner;

}
