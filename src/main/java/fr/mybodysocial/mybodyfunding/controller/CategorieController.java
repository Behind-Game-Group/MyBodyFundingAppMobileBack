package fr.mybodysocial.mybodyfunding.controller;

import fr.mybodysocial.mybodyfunding.dto.in.CategorieDtoIn;
import fr.mybodysocial.mybodyfunding.dto.out.CategorieDtoOut;
import fr.mybodysocial.mybodyfunding.service.interfaces.ICategorieService;
import fr.mybodysocial.mybodyfunding.util.Constante;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(Constante.BASE_URL + "/categorie")
@SecurityRequirement(name= "Bearer Authentification")
@Tag(name = "Categorie management API", description = "Categorie management API")
public class CategorieController {

    private final ICategorieService categorieService;

    /*
    @Operation(tags = {"Project API"},
            summary = "get number of backers ", description = "method allow to get all contributors from a project by his id")
    @GetMapping("title")
    ResponseEntity<Integer> getNombreBackers(@RequestParam("title") String categorieProject) throws Exception {
        return ResponseEntity.ok(categorieService.getNombreBackers(categorieProject));
    }
    */

    @Operation(tags = {"Project API"},
            summary = "get all categories", description = "method allow to get all categories from db")
    @GetMapping("")
    ResponseEntity<List<CategorieDtoOut>> getCategories() throws Exception {
        return ResponseEntity.ok(this.categorieService.getAllCategories());
    }

    @Operation(tags = {"Project API"},
            summary = "create category", description = "method allow to create a category and save it in the db")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    ResponseEntity<CategorieDtoOut> createCategory(@RequestBody CategorieDtoIn newCategory) throws Exception {
        return ResponseEntity.ok(this.categorieService.addCategorie(newCategory));
    }

    @Operation(tags = {"Project API"},
            summary = "update category", description = "method allow to update a category and save it in the db")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.PATCH})
    ResponseEntity<CategorieDtoOut> updateCategory(@RequestParam(name = "id") long id, @RequestBody CategorieDtoIn categoryUpdated) throws Exception {
        return ResponseEntity.ok(this.categorieService.updateCategorie(id, categoryUpdated));
    }

    @Operation(tags = {"Project API"}, summary = "delete a category", description = "method allow to delete a category in the db")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    ResponseEntity<CategorieDtoOut> deleteCategory(@RequestParam(name = "id") long id) throws Exception {
        return ResponseEntity.ok(this.categorieService.deleteCategorie(id));
    }
}
