package com.example.tacocloud.bootstrap;

import com.example.tacocloud.Ingredient;
import com.example.tacocloud.User;
import com.example.tacocloud.data.IngredientRepository;
import com.example.tacocloud.data.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Arrays;

@Configuration
@Profile("!prod")
public class DevelopmentConfig {
    @Bean
    public CommandLineRunner DataLoader(IngredientRepository ingredientRepository, UserRepository userRepository, PasswordEncoder encoder){
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Ingredient[] ingredients = {new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                        new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                        new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                        new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                        new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                        new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                        new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                        new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                        new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                        new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE),
                };
                Arrays.stream(ingredients).forEach(ingredientRepository::save);
                userRepository.save(new User("imbtmn",
                        encoder.encode("123456"),
                        "123456",
                        "123456",
                        "123456",
                        "as",
                        "123",
                        "123456789"
                        ));
            }
        };
    }

}
