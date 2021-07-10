package com.example.tacocloud.web;

import com.example.tacocloud.Design;
import com.example.tacocloud.Ingredient;
import com.example.tacocloud.Order;
import com.example.tacocloud.Taco;
import com.example.tacocloud.data.IngredientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tacocloud.Ingredient.Type;

import javax.validation.Valid;

@Slf4j
@Controller
@SessionAttributes("order")
@RequestMapping("/design")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    public DesignTacoController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Ingredient.Type[] types = Ingredient.Type.values();

        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        model.addAttribute("taco", new Taco());
        System.out.println("Model = " + model.toString() + "\n\n\n\n");
        return "design";
    }

    private Object filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    @PostMapping
    private String processDesign(@Valid Taco taco, BindingResult bindingResult, @ModelAttribute Order order){
        if(bindingResult.hasErrors()){
            log.error("Taco error : " + bindingResult.getAllErrors() + "\n\n\n\n");
            return "/design";
        }
        order.getTacos().add(taco);
        log.info("Processing taco : " + taco);
        log.info("Check Order : " + order);
        return "redirect:/orders/current";
    }
}
