package uk.jakebaum.recipe.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("recipes")
public class Recipe {

  @Id
  private String id;

  @NotBlank
  private String title;

  private List<String> ingredients;

  public Recipe(String id, String title, List<String> ingredients) {
    this.id = id;
    this.title = title;
    this.ingredients = ingredients;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getIngredients() {
    return ingredients;
  }

  public void setIngredients(List<String> ingredients) {
    this.ingredients = ingredients;
  }

}
