package br.com.alura.dayscode.service;

import br.com.alura.dayscode.clients.ImdbClient;
import br.com.alura.dayscode.domain.ListOfMovies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Service
public class ImdbService {

    @Value("${imdb.api.key}")
    private String apiKey;

    @Autowired
    private ImdbClient imdbClient;

    private ListOfMovies movies = new ListOfMovies(new ArrayList<>());

    private ListOfMovies favoriteFilmsList = new ListOfMovies(new ArrayList<>());

    @GetMapping("/top250")
    public ListOfMovies getTop250()  {

        return imdbClient.getTop250(apiKey);
    }
}
