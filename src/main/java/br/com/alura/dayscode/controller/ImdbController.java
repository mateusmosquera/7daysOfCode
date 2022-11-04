package br.com.alura.dayscode.controller;

import br.com.alura.dayscode.domain.HTMLGenerator;
import br.com.alura.dayscode.domain.ListOfMovies;
import br.com.alura.dayscode.domain.Movie;
import br.com.alura.dayscode.exception.FilmIdNotFoundException;
import br.com.alura.dayscode.service.ImdbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

    @Autowired
    private ImdbService imdbService;

    private ListOfMovies movies = new ListOfMovies(new ArrayList<>());

    private ListOfMovies favoriteFilmsList = new ListOfMovies(new ArrayList<>());

    public static final String FAVORITE_ADD_POST = "FILM ADD TO YOU FAVORITE LIST";
    public static final String NOT_FOUND_FILM = "FILM ID NOT FOUND";

    @GetMapping("/top250")
        public ResponseEntity<ListOfMovies> getTop250(@RequestParam(required = false, name = "title") String title) throws FileNotFoundException {

        movies.items().clear();
        nm    
        ListOfMovies response = imdbService.getTop250();

        if (Objects.nonNull(title)){
            movies.items().addAll( response.items().stream().filter(movie -> movie.title().contains(title)).toList());
        }else{
            movies = response;
        }

        PrintWriter writer = new PrintWriter("src/main/resources/content.html");
        new HTMLGenerator(writer).generate(Objects.requireNonNull(movies));
        writer.close();

        return ResponseEntity.ok().body(movies);
    }

    @PostMapping("/favorite/{filmId}")
    public ResponseEntity<String> addFavoriteFilm(@PathVariable(name = "filmId") String filmId) throws FilmIdNotFoundException, FileNotFoundException {


        if (this.movies.items().isEmpty()) {
            getTop250(null);
        }

        Optional<Movie> favoriteMovie = movies.items().stream().filter(movie -> movie.id().equals(filmId)).findFirst();

        if (favoriteMovie.isEmpty()){
           throw new FilmIdNotFoundException(NOT_FOUND_FILM);
        } else {
            favoriteFilmsList.items().add(favoriteMovie.get());
            return ResponseEntity.ok().body(FAVORITE_ADD_POST);
        }

    }

}
