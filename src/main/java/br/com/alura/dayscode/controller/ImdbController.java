package br.com.alura.dayscode.controller;

import br.com.alura.dayscode.clients.ImdbClient;
import br.com.alura.dayscode.domain.HTMLGenerator;
import br.com.alura.dayscode.domain.ListOfMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

    @Value("${imdb.api.key}")
    private String apiKey;

    @Autowired
    private ImdbClient imdbClient;

    @GetMapping("/top250")
    public ResponseEntity<ListOfMovies> getTop250() throws FileNotFoundException {

        ResponseEntity<ListOfMovies> response = imdbClient.getTop250(apiKey);

        PrintWriter writer = new PrintWriter("src/main/resources/content.html");
        new HTMLGenerator(writer).generate(response.getBody());
        writer.close();

        return response;
    }


}
