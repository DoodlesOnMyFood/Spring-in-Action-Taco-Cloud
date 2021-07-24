package com.example.tacocloud.data;

import com.example.tacocloud.Taco;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
    Mono<Taco> save(Taco taco);
}
