package uk.jakebaum.recipe.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.jakebaum.recipe.model.Recipe;

import java.util.Optional;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {

  Optional<Recipe> findByTitle(String title);

}
