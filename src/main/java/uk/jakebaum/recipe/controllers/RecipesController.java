package uk.jakebaum.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.controllers.dto.converters.RecipeDtoConverter;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.services.RecipeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = RecipesController.BASE_ENDPOINT)
public class RecipesController {

  public static final String BASE_ENDPOINT = "/recipe";

  private final RecipeService recipeService;

  private final RecipeDtoConverter recipeDtoConverter;

  @Autowired
  public RecipesController(RecipeService recipeService, RecipeDtoConverter recipeDtoConverter) {
    this.recipeService = recipeService;
    this.recipeDtoConverter = recipeDtoConverter;
  }

  @GetMapping
  public List<RecipeDto> getRecipes() {
    List<Recipe> recipes = recipeService.getAllRecipes();

    return recipes.stream().map(recipeDtoConverter::toDto).collect(Collectors.toList());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
    Recipe recipe = recipeDtoConverter.toEntity(recipeDto);

    Recipe persistedRecipe = recipeService.save(recipe);
    return recipeDtoConverter.toDto(persistedRecipe);
  }

}
