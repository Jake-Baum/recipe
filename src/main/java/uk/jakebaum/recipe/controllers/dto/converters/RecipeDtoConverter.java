package uk.jakebaum.recipe.controllers.dto.converters;

import org.springframework.stereotype.Component;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.model.Recipe;

@Component
public class RecipeDtoConverter implements DtoConverter<Recipe, RecipeDto> {

  @Override
  public Recipe toEntity(RecipeDto recipeDto) {
    return recipeDto != null ? new Recipe(recipeDto.id(), recipeDto.title(), recipeDto.ingredients()) : null;
  }

  @Override
  public RecipeDto toDto(Recipe recipe) {
    return recipe != null ? new RecipeDto(recipe.getId(), recipe.getTitle(), recipe.getIngredients()) : null;
  }

}
