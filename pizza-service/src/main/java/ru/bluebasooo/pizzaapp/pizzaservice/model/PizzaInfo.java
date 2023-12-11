package ru.bluebasooo.pizzaapp.pizzaservice.model;

import io.r2dbc.spi.Blob;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PizzaInfo {
    @Id
    Long id;
    String name;
    Double price;
    String ingridients;
    String discription;
    Blob image;
}
