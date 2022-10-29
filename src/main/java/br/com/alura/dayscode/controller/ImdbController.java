package br.com.alura.dayscode.controller;

import br.com.alura.dayscode.domain.HTMLGenerator;
import br.com.alura.dayscode.domain.ListOfMovies;
import br.com.alura.dayscode.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

    @Autowired
    private ImdbService imdbService;

    @GetMapping("/top250")
    public ResponseEntity<ListOfMovies> getTop250() throws FileNotFoundException {

        ResponseEntity<ListOfMovies> response = imdbService.getTop250();

        PrintWriter writer = new PrintWriter("src/main/resources/content.html");
        new HTMLGenerator(writer).generate(Objects.requireNonNull(response.getBody()));
        writer.close();

        return response;
    }


}
