package uk.jakebaum.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import uk.jakebaum.recipe.ITSetup;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.repositories.RecipeRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeController_getRecipeIT extends ITSetup {

  @Autowired
  private RecipeRepository recipeRepository;

  @BeforeEach
  public void setUp() {
    recipeRepository.save(new Recipe("1", "Spaghetti Bolognese"));
  }

  @Test
  public void getRecipe_success() {
    ResponseEntity<RecipeDto> response =
            restTemplate.getForEntity(RecipeController.BASE_ENDPOINT, RecipeDto.class, "1");

    RecipeDto expectedResponse = new RecipeDto("1", "Spaghetti Bolognese");

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
  }

}
