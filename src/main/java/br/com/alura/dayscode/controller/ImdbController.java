package br.com.alura.dayscode.controller;

import br.com.alura.dayscode.clients.ImdbClient;
import br.com.alura.dayscode.domain.response.ImdbResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imdb")
public class ImdbController {

    @Value("${imdb.api.key}")
    private String apiKey;

    @Autowired
    private ImdbClient imdbClient;

    @GetMapping("/top250")
    public ResponseEntity<ImdbResponse> getTop250() {

        ImdbResponse top250 = imdbClient.getTop250(apiKey);

        return ResponseEntity.ok(top250);
    }

}
