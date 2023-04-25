package uk.jakebaum.recipe.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import uk.jakebaum.recipe.ITSetup;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeController_getRecipeIT extends ITSetup {

  @Test
  public void getRecipe_success() {
    ResponseEntity<RecipeDto> response = restTemplate.getForEntity(RecipeController.BASE_ENDPOINT, RecipeDto.class);

    RecipeDto expectedResponse = new RecipeDto(1, "Spaghetti Bolognese");

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);

  }

}
