package com.example.reactive;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SpringBootTest
class ReactiveApplicationTests {

	@Test
	void contextLoads() {
		Flux<String> fruitFlux = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
		fruitFlux.subscribe(
				f -> System.out.println("Here is some fruit : " + f)
		);

		StepVerifier.create(fruitFlux)
				.expectNext("Apple")
				.expectNext("Orange")
				.expectNext("Grape")
				.expectNext("Banana")
				.expectNext("Strawberry");
	}

	@Test
	void flux_rangeTest() {
		Flux<Integer> intervalFlux = Flux.range(1, 5);

		StepVerifier.create(intervalFlux)
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();
	}

	@Test
	void flux_IntervalTest() {
		Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1)).take(5);

		StepVerifier.create(intervalFlux)
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.expectNext(3L)
				.expectNext(4L)
				.verifyComplete();
	}

	@Test
	void mergeFluxes() {
		Flux<String> charFlux = Flux.just("Garfield", "Kojak", "Barbossa")
				.delayElements(Duration.ofMillis(500));
		Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
				.delaySubscription(Duration.ofMillis(250))
				.delayElements(Duration.ofMillis(500));

		Flux<String> mergedFlux = charFlux.mergeWith(foodFlux);

		StepVerifier.create(mergedFlux)
				.expectNext("Garfield")
				.expectNext("Lasagna")
				.expectNext("Kojak")
				.expectNext("Lollipops")
				.expectNext("Barbossa")
				.expectNext("Apples")
				.verifyComplete();
	}

	@Test
	void zipFluxes() {
		Flux<String> charFlux = Flux.just("Garfield", "Kojak", "Barbossa");
		Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples");
		Flux<Tuple2<String, String>> zippedFlux = Flux.zip(charFlux, foodFlux);

		StepVerifier.create(zippedFlux)
				.expectNextMatches(p ->
						p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
				.expectNextMatches(p->
						p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
				.expectNextMatches(p ->
						p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
				.verifyComplete();

	}

	@Test
	void firstFlux(){
		Flux<String> slowFlux = Flux.just("tortoise", "snail", "sloth").delaySubscription(Duration.ofMillis(100));
		Flux<String> fastFlux = Flux.just("hair", "cheetah", "squirrel");
		Flux<String> firstFlux = Flux.firstWithSignal(slowFlux, fastFlux);

		StepVerifier.create(firstFlux)
				.expectNext("hair")
				.expectNext("cheetah")
				.expectNext("squirrel")
				.verifyComplete();
	}

	@Test
	void skipAFew() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		).skip(3);

		StepVerifier.create(skipFlux)
				.expectNext("ninety nine")
				.expectNext("one hundred")
				.verifyComplete();
	}

	@Test
	void skipAFewSeconds() {
		Flux<String> skipFlux = Flux.just(
				"one", "two", "skip a few", "ninety nine", "one hundred"
		).delayElements(Duration.ofSeconds(1))
				.skip(Duration.ofSeconds(4));

		StepVerifier.create(skipFlux)
				.expectNext("ninety nine", "one hundred")
				.verifyComplete();
	}

	@Test
	void takeAFew() {
		Flux<Integer> nums = Flux.just(
				1, 2, 3, 4, 5, 6, 7, 8, 9
		).take(3);

		StepVerifier.create(nums)
				.expectNext(1,2,3)
				.verifyComplete();
	}

	@Test
	void takeAFewDuration() {
		Flux<Integer> nums = Flux.just(
				1, 2, 3, 4, 5, 6, 7, 8, 9
		).delayElements(Duration.ofSeconds(1))
				.take(Duration.ofMillis(3500));

		StepVerifier.create(nums)
				.expectNext(1,2,3)
				.verifyComplete();
	}

	@Test
	void filter() {
		Flux<String> nationalPark = Flux.just(
				"Yellowstone", "Yosemite", "Grand Canyon", "Zion", "Grand Teton"
		).filter(park -> !park.contains(" "));

		StepVerifier.create(nationalPark)
				.expectNext("Yellowstone", "Yosemite", "Zion")
				.verifyComplete();
	}

	@Test
	void distinct(){
		Flux<String> animalFlux = Flux.just(
				"dog", "cat", "bird", "dog", "bird", "anteater"
		).distinct();

		StepVerifier.create(animalFlux)
				.expectNext("dog", "cat", "bird", "anteater")
				.verifyComplete();
	}

	class Player{
		String firstName;
		String lastName;

		public Player(String firstName, String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		@Override
		public String toString() {
			return "Player{" +
					"firstName='" + firstName + '\'' +
					", lastName='" + lastName + '\'' +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Player player = (Player) o;
			return Objects.equals(firstName, player.firstName) && Objects.equals(lastName, player.lastName);
		}

		@Override
		public int hashCode() {
			return Objects.hash(firstName, lastName);
		}
	}

	@Test
	void map() {
		Flux<Player> playerFlux = Flux.just(
				"Michael Jordan", "Scottie Pippen", "Steve Kerr"
		).map(p ->{
			String[] split = p.split("\\s");
			return new Player(split[0], split[1]);
		});

		StepVerifier.create(playerFlux)
				.expectNext(new Player("Michael", "Jordan"))
				.expectNext(new Player("Scottie", "Pippen"))
				.expectNext(new Player("Steve", "Kerr"))
				.verifyComplete();
	}

	@Test
	void flatMap() {
		Flux<Player> playerFlux = Flux.just(
				"Michael Jordan", "Scottie Pippen", "Steve Kerr"
		).flatMap(n -> Mono.just(n).map(p ->{
			String[] split = p.split("\\s");
			return new Player(split[0], split[1]);
		})
		).subscribeOn(Schedulers.parallel());

		List<Player> players = Arrays.asList(
				new Player("Michael", "Jordan"),
				new Player("Scottie", "Pippen"),
				new Player("Steve", "Kerr")
		);

		StepVerifier.create(playerFlux)
				.expectNextMatches(players::contains)
				.expectNextMatches(players::contains)
				.expectNextMatches(players::contains)
				.verifyComplete();
	}
}
