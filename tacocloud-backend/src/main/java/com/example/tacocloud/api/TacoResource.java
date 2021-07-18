package com.example.tacocloud.api;

import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Taco;
import lombok.Getter;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Date;


@Relation(value = "taco", collectionRelation = "tacos")
public class TacoResource extends RepresentationModel<TacoResource> {

    private static final IngredientResourceAssembler ingredientAssembler = new IngredientResourceAssembler();

    @Getter
    private final Date createdAt;

    @Getter
    private final String tacoName;

    @Getter
    private CollectionModel<IngredientResource> ingredients;

    public TacoResource(Taco taco) {
        this.createdAt = taco.getCreatedAt();
        this.tacoName = taco.getTacoName();
        this.ingredients = ingredientAssembler.toCollectionModel(taco.getIngredients());
    }
}
