package com.example.tacocloud.api;

import com.example.tacocloud.Taco;
import com.example.tacocloud.data.TacoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class DesignTacoControllerTest {

    @Test
    void shouldReturnRecentTacos() {
        Taco[] tacos = {
                testTaco(1L), testTaco(2L), testTaco(3L), testTaco(4L),
                testTaco(5L), testTaco(6L), testTaco(7L), testTaco(8L),
                testTaco(9L), testTaco(10L), testTaco(11L), testTaco(12L),
                testTaco(13L), testTaco(14L), testTaco(15L), testTaco(16L)
        };
        Flux<Taco> tacoFlux = Flux.just(tacos);

        TacoRepository tacoRepo = Mockito.mock(TacoRepository.class);
        when(tacoRepo.findAll()).thenReturn(tacoFlux);


        WebTestClient testClient = WebTestClient.bindToController(
                new DesignTacoController(tacoRepo)
        ).build();

        testClient.get().uri("/design/recent")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(new ParameterizedTypeReference<Taco>(){})
                    .contains(Arrays.copyOf(tacos,12));

    }

    Taco testTaco(Long id){
        Taco returnTaco =  new Taco();
        returnTaco.setTacoName("Taco " + id);
        returnTaco.setId(id);
        return returnTaco;
    }

    @Test
    void shouldSaveATaco() {
        TacoRepository tacoRepository = Mockito.mock(TacoRepository.class);

        Mono<Taco> unsavedTacoMono = Mono.just(testTaco(null));
        Taco savedTaco = testTaco(1L);
        Mono<Taco> savedTacoMono = Mono.just(savedTaco);
        when(tacoRepository.save(any())).thenReturn(savedTacoMono);

        WebTestClient testClient = WebTestClient.bindToController(new DesignTacoController(tacoRepository))
                .build();

        testClient.post()
                .uri("/design")
                .contentType(MediaType.APPLICATION_JSON)
                .body(unsavedTacoMono, Taco.class)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Taco.class).isEqualTo(savedTaco);
    }


}