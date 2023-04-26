package uk.jakebaum.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.controllers.dto.converters.RecipeDtoConverter;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.services.RecipeService;

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

  @PostMapping
  public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
    Recipe recipe = recipeDtoConverter.toEntity(recipeDto);

    Recipe persistedRecipe = recipeService.createRecipe(recipe);
    return recipeDtoConverter.toDto(persistedRecipe);
  }

}
