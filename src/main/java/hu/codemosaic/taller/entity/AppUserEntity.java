package hu.codemosaic.taller.entity;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "app_user")
@Data
public class AppUserEntity extends BaseEntity{

    private String username;

}
