package hu.codemosaic.taller.controller;

import hu.codemosaic.taller.dto.CategoryDto;
import hu.codemosaic.taller.security.Auth;
import hu.codemosaic.taller.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BaseApiController.BASE_PATH + "/category")
@RequiredArgsConstructor
public class CategoryController extends BaseApiController{

    private final CategoryService categoryService;


    @Auth
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategoriesForUser() {
        return ResponseEntity.ok(categoryService.getAllCategoriesForCurrentUser());
    }
}
