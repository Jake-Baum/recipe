package uk.jakebaum.recipe.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import uk.jakebaum.recipe.ITSetup;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;
import uk.jakebaum.recipe.model.Recipe;
import uk.jakebaum.recipe.repositories.RecipeRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class RecipeControllerIT extends ITSetup {

  @Autowired
  private RecipeRepository recipeRepository;

  @BeforeEach
  public void setUp() {
    recipeRepository.save(new Recipe("1", "Spaghetti Bolognese", List.of("Spaghetti", "Tomato", "Beef Mince")));
  }

  @Nested
  class getRecipe {

    @Test
    public void success() {
      ResponseEntity<RecipeDto> response =
              restTemplate.getForEntity(RecipeController.BASE_ENDPOINT, RecipeDto.class, "1");

      RecipeDto expectedResponse =
              new RecipeDto("1", "Spaghetti Bolognese", List.of("Spaghetti", "Tomato", "Beef Mince"));

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }

    @Test
    public void doesNotExist() {
      ResponseEntity<String> response = restTemplate.getForEntity(RecipeController.BASE_ENDPOINT, String.class, "999");

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

  }

  @Nested
  public class updateRecipe {

    @Test
    public void success() {
      RecipeDto updatedRecipe = new RecipeDto("1", "Spaghetti Carbonara", List.of("Bacon", "Egg", "Spaghetti"));

      ResponseEntity<RecipeDto> response = restTemplate.exchange(RecipeController.BASE_ENDPOINT,
                                                                 HttpMethod.PUT,
                                                                 new HttpEntity<>(updatedRecipe),
                                                                 RecipeDto.class,
                                                                 "1");

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(updatedRecipe);

      Optional<Recipe> persistedRecipe = recipeRepository.findById("1");
      Recipe expectedPersistedRecipe =
              new Recipe(updatedRecipe.id(), updatedRecipe.title(), updatedRecipe.ingredients());

      assertThat(persistedRecipe.isPresent()).isTrue();
      assertThat(persistedRecipe.get()).isEqualTo(expectedPersistedRecipe);
    }

  }

}
