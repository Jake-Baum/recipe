package uk.jakebaum.recipe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.jakebaum.recipe.controllers.dto.RecipeDto;

@RestController
@RequestMapping(RecipeController.BASE_ENDPOINT)
public class RecipeController {

  public static final String BASE_ENDPOINT = "/recipe";

  @GetMapping
  public RecipeDto getRecipe() {
    return new RecipeDto(1, "Spaghetti Bolognese");
  }

}
