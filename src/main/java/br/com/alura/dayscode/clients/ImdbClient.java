package br.com.alura.dayscode.clients;

import br.com.alura.dayscode.domain.ListOfMovies;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient( name = "imdb", url = "https://imdb-api.com/en/API/Top250Movies")
public interface ImdbClient {

    @GetMapping(value = "/{apiKey}")
    ListOfMovies getTop250(@PathVariable(name = "apiKey") String apiKey);




}
