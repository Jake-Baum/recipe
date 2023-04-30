package uk.jakebaum.recipe.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.repositories.RecipeRepository;

import java.util.List;
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

  public List<Recipe> getAllRecipes() {
    return recipeRepository.findAll();
  }

  public Recipe save(@Valid Recipe recipe) {
    return recipeRepository.save(recipe);
  }

  public void deleteRecipe(String recipeId) {
    recipeRepository.deleteById(recipeId);
  }

}
