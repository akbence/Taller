package hu.codemosaic.taller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

    private String name;
    private String description;

}