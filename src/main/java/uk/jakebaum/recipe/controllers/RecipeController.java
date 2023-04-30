package uk.jakebaum.recipe.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.controllers.dto.converters.RecipeDtoConverter;
import uk.jakebaum.recipe.exception.EntityDoesNotExistException;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.services.RecipeService;

@RestController
@RequestMapping(value = RecipeController.BASE_ENDPOINT)
public class RecipeController {

  public static final String BASE_ENDPOINT = RecipesController.BASE_ENDPOINT + "/{recipeId}";

  private final RecipeService recipeService;

  private final RecipeDtoConverter recipeDtoConverter;

  @Autowired
  public RecipeController(RecipeService recipeService, RecipeDtoConverter recipeDtoConverter) {
    this.recipeService = recipeService;
    this.recipeDtoConverter = recipeDtoConverter;
  }

  @GetMapping
  public RecipeDto getRecipe(@PathVariable("recipeId") String recipeId) {
    Recipe recipe = recipeService.getRecipeById(recipeId)
                                 .orElseThrow(() -> new EntityDoesNotExistException(Recipe.class, recipeId));

    return recipeDtoConverter.toDto(recipe);
  }

  @PutMapping
  public RecipeDto updateRecipe(@PathVariable("recipeId") String recipeId, @RequestBody RecipeDto recipe) {
    recipeService.getRecipeById(recipeId).orElseThrow(() -> new EntityDoesNotExistException(Recipe.class, recipeId));

    Recipe requestRecipe = recipeDtoConverter.toEntity(recipe);
    Recipe persistedRecipe = recipeService.save(requestRecipe);

    return recipeDtoConverter.toDto(persistedRecipe);
  }

  @DeleteMapping
  public void deleteRecipe(@PathVariable("recipeId") String recipeId) {
    recipeService.getRecipeById(recipeId).orElseThrow(() -> new EntityDoesNotExistException(Recipe.class, recipeId));
    recipeService.deleteRecipe(recipeId);
  }

}
