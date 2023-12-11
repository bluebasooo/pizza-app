package ru.bluebasooo.pizzaapp.pizzaservice.controller.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.bluebasooo.pizzaapp.pizzaservice.controller.handler.PizzaHandler;
import ru.bluebasooo.pizzaapp.pizzaservice.model.PizzaInfo;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class PizzaRouter {

    @Bean
    public RouterFunction<ServerResponse> pizzaRouting(PizzaHandler pizzaHandler) {
        return RouterFunctions
                .route(GET("/pizza/info/{pizza-name}").and(accept(APPLICATION_JSON)),
                        pizzaHandler::getPizzaInfoByName)
                .andRoute(POST("/pizza/create").and(accept(APPLICATION_JSON)),
                        pizzaHandler::createPizza)
                .andRoute(POST("/pizza/delete/{pizza-name}").and(accept(APPLICATION_JSON)),
                        pizzaHandler::deletePizza);
    }
}
