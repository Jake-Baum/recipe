package uk.jakebaum.recipe.controllers.dto;

import java.util.List;

public record RecipeDto(String id, String title, List<String> ingredients) {}
