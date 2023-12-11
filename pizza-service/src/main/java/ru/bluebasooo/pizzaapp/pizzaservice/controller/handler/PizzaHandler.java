package ru.bluebasooo.pizzaapp.pizzaservice.controller.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.Part;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.bluebasooo.pizzaapp.pizzaservice.dto.CreatingPizzaDto;
import ru.bluebasooo.pizzaapp.pizzaservice.service.PizzaService;

import java.io.IOException;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
@AllArgsConstructor
public class PizzaHandler {

    private PizzaService pizzaService;

    public Mono<ServerResponse> getPizzaInfoByName(ServerRequest request) {
        var pizzaName = Mono.justOrEmpty(request.pathVariable("pizza-name"));
        var pizzaInfo = pizzaName.map(
                (name) -> pizzaService.getPizzaInfoByName(name));

        return pizzaInfo.flatMap(
                info -> info.flatMap((inner) ->
                                ServerResponse.ok().body(fromValue(inner))).switchIfEmpty(
                                ServerResponse.badRequest().body(fromValue("None pizza with current name"))))
                .switchIfEmpty(ServerResponse.badRequest().body(fromValue("bad param")));

    }

    public Mono<ServerResponse> createPizza(ServerRequest request) {
        var dto = request.body(BodyExtractors.toParts())
                .filter(part -> part.name().equals("data"))
                .flatMap(Part::content)
                .map(buff -> {
                    byte[] bytes = new byte[buff.readableByteCount()];
                    buff.read(bytes);
                    DataBufferUtils.release(buff);

                    try {
                        return new ObjectMapper().readValue(bytes, CreatingPizzaDto.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).onErrorStop();

        var img = request.body(BodyExtractors.toParts())
                .filter(part -> part.name().equals("file"))
                .flatMap(Part::content);

//        var blob = Blob.from(img.map((buff) -> {
//            var buffer = new  ByteBuffer();
//            buff.toByteBuffer(buffer);
//            return buffer;
//        }).publish());

        //var pizzaInfo = request.body(BodyExtractors.toMono(CreatingPizzaDto.class));
        //var img = request.multipartData();
        //var pizzaId = pizzaInfo.map((info) -> pizzaService.createPizza(info));

//        return pizzaId.flatMap(
//                id -> id.flatMap((inner) ->
//                        ServerResponse.ok().body(fromValue(id)).switchIfEmpty(
//                        ServerResponse.badRequest().body(fromValue("???"))))
//                .switchIfEmpty(ServerResponse.badRequest().body(fromValue("Invalid info"))));
        return null;
    }

    public Mono<ServerResponse> deletePizza(ServerRequest request) {
        var pizzaName = Mono.justOrEmpty(request.queryParam("pizza-name"));
        var pizzaInfo = pizzaName.map((name) -> pizzaService.deletePizza(name));

        return pizzaInfo.flatMap(
                name ->  name.flatMap((info) ->
                        ServerResponse.ok().body(fromValue(info)).switchIfEmpty(
                        ServerResponse.badRequest().body(fromValue("Can not delete")))).switchIfEmpty(
                                ServerResponse.badRequest().body(fromValue("Pizza-name is required"))));
    }

}
