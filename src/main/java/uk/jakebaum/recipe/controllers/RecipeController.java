package uk.jakebaum.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.controllers.dto.converters.RecipeDtoConverter;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.services.RecipeService;

@RestController
@RequestMapping(value = RecipeController.BASE_ENDPOINT)
public class RecipeController {

  public static final String BASE_ENDPOINT = "/recipe/{recipeId}";

  private final RecipeService recipeService;

  private final RecipeDtoConverter recipeDtoConverter;

  @Autowired
  public RecipeController(RecipeService recipeService, RecipeDtoConverter recipeDtoConverter) {
    this.recipeService = recipeService;
    this.recipeDtoConverter = recipeDtoConverter;
  }

  @GetMapping
  public RecipeDto getRecipe(@PathVariable("recipeId") String recipeId) {
    Recipe recipe = recipeService.getRecipeById(recipeId).orElseThrow(IllegalArgumentException::new);

    return recipeDtoConverter.toDto(recipe);
  }

}
