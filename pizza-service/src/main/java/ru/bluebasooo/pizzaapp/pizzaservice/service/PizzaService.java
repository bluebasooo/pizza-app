package ru.bluebasooo.pizzaapp.pizzaservice.service;

import io.r2dbc.spi.Blob;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.bluebasooo.pizzaapp.pizzaservice.dto.CreatingPizzaDto;
import ru.bluebasooo.pizzaapp.pizzaservice.model.PizzaInfo;
import ru.bluebasooo.pizzaapp.pizzaservice.repository.PizzaRepository;

import java.nio.ByteBuffer;

@Service
@AllArgsConstructor
public class PizzaService {

    private PizzaRepository repository;

    public Mono<PizzaInfo> getPizzaInfoByName(String name) {
        return repository.getPizzaInfoByName(name);
    }

    public Mono<String> createPizza(CreatingPizzaDto dto) {

        var pizzaInfo = PizzaInfo.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .ingridients(dto.getIngridients())
                .discription(dto.getDiscription())
                .image(dto.getImage())
                .build();

        return repository.insertPizzaInfo(pizzaInfo);
    }

    public Mono<PizzaInfo> deletePizza(String pizzaName) {
        return repository.deletePizzaInfo(pizzaName);
    }
}
