package uk.jakebaum.recipe.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.repositories.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

  private final RecipeRepository recipeRepository;

  @Autowired
  public RecipeService(RecipeRepository recipeRepository) {
    this.recipeRepository = recipeRepository;
  }

  public Optional<Recipe> getRecipeById(String id) {
    return recipeRepository.findById(id);
  }

  public Recipe createRecipe(Recipe recipe) {
    return recipeRepository.save(recipe);
  }

}
