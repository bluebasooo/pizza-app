package ru.bluebasooo.pizzaapp.pizzaservice.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.bluebasooo.pizzaapp.pizzaservice.model.PizzaInfo;

@Repository
public interface PizzaRepository extends R2dbcRepository<PizzaInfo, Long> {

    @Query("select id, name, price, ingridients, discription, image " +
            "from pizza_info " +
            "where name = :name;")
    Mono<PizzaInfo> getPizzaInfoByName(@Param("name") String name);

    @Modifying
    @Query("insert into pizza_info(name, price, ingridients, discription, image) " +
            "values(:info.name, :info.price, :info.ingridients, :info.discription, :info.image) " +
            "returning name;")
    Mono<String> insertPizzaInfo(@Param("info") PizzaInfo info);

    @Modifying
    @Query("delete from pizza_info " +
            "where name = :name " +
            "returning id, name, price, ingridients, disctiption, image; ")
    Mono<PizzaInfo> deletePizzaInfo(@Param("name") String pizzaName);
}
