package com.example.tacocloud.web;

import com.example.tacocloud.*;
import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private final UserRepository userRepository;

    public DesignTacoController(IngredientRepository ingredientRepository, UserRepository userRepository) {
        this.ingredientRepository = ingredientRepository;
        this.userRepository = userRepository;
    }


    @ModelAttribute(name = "order")
    public Order order(){
        return new Order();
    }

    @ModelAttribute(name = "design")
    public Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm(Model model, Principal principal){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Ingredient.Type[] types = Ingredient.Type.values();

        for (Type type : types){
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients,type));
        }
        System.out.println(principal.getName());
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
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
