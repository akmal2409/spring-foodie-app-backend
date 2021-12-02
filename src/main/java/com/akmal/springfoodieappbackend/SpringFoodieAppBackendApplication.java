package com.akmal.springfoodieappbackend;

import com.akmal.springfoodieappbackend.model.*;
import com.akmal.springfoodieappbackend.repository.CategoryRepository;
import com.akmal.springfoodieappbackend.repository.MenuRepository;
import com.akmal.springfoodieappbackend.repository.OpeningTimeRepository;
import com.akmal.springfoodieappbackend.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

@SpringBootApplication
public class SpringFoodieAppBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringFoodieAppBackendApplication.class, args);
  }

  @Bean
  @Profile("!test && !integration-test")
  public CommandLineRunner commandLineRunner(RestaurantRepository restaurantRepository,
                                             CategoryRepository categoryRepository,
                                             MenuRepository menuRepository,
                                             OpeningTimeRepository openingTimeRepository) {
    return args -> {
      if (restaurantRepository.count() == 0) {

        var categories = List.of(
                Category.builder().name("Italian").description("Italian Food").build(),
                Category.builder().name("Pizza").description("Pizza").build()
        );

        var savedCategories = categoryRepository.saveAll(categories);

        var italianCategory = categoryRepository.findAllById(List.of(1L, 2L));

        var options = List.of(
                Option.builder().name("Mozzarella").price(BigDecimal.valueOf(2.0)).build(),
                Option.builder().name("Gorgonzola").price(BigDecimal.valueOf(2.0)).build()
        );

        var optionSets = List.of(
                OptionSet.builder().optionSetType(OptionSet.OptionSetType.TOPPING).name("Extra cheese")
                        .exclusive(false).required(false).maximumOptionsSelected(2).orderPosition(1)
                        .options(options).build()
        );


        var address = new Address("Netherlands", "Hengelo", "7665", "Hengelostraat 21",
                "A", null, new Address.Location(40.3, 44.1));


        Restaurant restaurant = Restaurant.builder().name("Semo de Roma").phone("+316342434")
                .address(address).minimumOrderValue(20.0).deliveryCost(BigDecimal.valueOf(20.4))
                .categories(italianCategory).build();
        restaurant = restaurantRepository.save(restaurant);

        var menu = Menu.builder().name("Pizza").category(italianCategory.get(1)).restaurant(restaurant).build();

        var openingTimes = List.of(
                OpeningTime.builder().day(1).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build(),
                OpeningTime.builder().day(2).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build(),
                OpeningTime.builder().day(3).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build(),
                OpeningTime.builder().day(4).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build(),
                OpeningTime.builder().day(6).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build(),
                OpeningTime.builder().day(7).openFrom(LocalTime.parse("12:00:00")).restaurant(restaurant).openTill(LocalTime.parse("23:00:00")).build()
        );

        menuRepository.save(menu);

        openingTimeRepository.saveAll(openingTimes);

        menu = menu.withMenuItems(List.of(
                MenuItem.builder().menu(menu).basePrice(BigDecimal.valueOf(20.4)).name("Pizza").optionSets(optionSets).build()
        ));

        menuRepository.save(menu);
      }
    };
  }
}
