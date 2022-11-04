package br.com.alura.dayscode;

import br.com.alura.dayscode.controller.ImdbController;
import br.com.alura.dayscode.domain.ListOfMovies;
import br.com.alura.dayscode.exception.FilmIdNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	void shouldReturnTop250Films() {

		ResponseEntity<ListOfMovies> response = this.restTemplate.getForEntity("http://localhost:" + port + "/imdb/top250", ListOfMovies.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(250, response.getBody().items().size());
	}

	@Test
	void shouldSaveAFavoriteFilm(){

		String filmId = "tt0167260";

		ResponseEntity<String> response =
				this.restTemplate.postForEntity("http://localhost:" + port + "/imdb/favorite/" + filmId,null, String.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(ImdbController.FAVORITE_ADD_POST, response.getBody());

	}

}


