package com.example.tacocloud.api;

import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Ingredient.Type;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;



public class IngredientResource extends RepresentationModel<IngredientResource> {
    @Getter
    private final String name;
    @Getter
    private final Type type;

    public IngredientResource(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }
}
