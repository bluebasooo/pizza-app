package ru.bluebasooo.pizzaapp.pizzaservice.dto;

import io.r2dbc.spi.Blob;
import lombok.Value;

import java.nio.ByteBuffer;

@Value
public class CreatingPizzaDto {
    String name;
    Double price;
    String ingridients;
    String discription;
    Blob image;
}
