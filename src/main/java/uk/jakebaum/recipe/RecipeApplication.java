package uk.jakebaum.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import uk.jakebaum.recipe.controllers.RecipeController;

@SpringBootApplication
@ComponentScan(basePackageClasses = RecipeController.class)
public class RecipeApplication {

  public static void main(String[] args) {
    SpringApplication.run(RecipeApplication.class, args);
  }

}
