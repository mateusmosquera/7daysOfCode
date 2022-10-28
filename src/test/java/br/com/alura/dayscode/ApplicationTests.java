package br.com.alura.dayscode;

import br.com.alura.dayscode.clients.ImdbClient;
import br.com.alura.dayscode.domain.ListOfMovies;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = ApplicationTests.FeignConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {

	@Autowired
	private ImdbClient imdbClient;

	@Value("${imdb.api.key}")
	private String apiKey;

	@Test
	void shouldReturnTop250Films() {

		ResponseEntity<ListOfMovies> response = this.imdbClient.getTop250(apiKey);

		assertNotNull(response);
	}


	@EnableFeignClients(clients = ImdbClient.class)
	@RestController
	@Configuration
	@EnableAutoConfiguration
	@RibbonClient(name = "imdb", configuration = ApplicationTests.RibbonConfig.class)
	static class FeignConfig {
	}

	@Configuration
	static class RibbonConfig {

		@LocalServerPort
		int port;

		@Bean
		public ServerList<Server> serverList() {
			return new StaticServerList<>(new Server("127.0.0.1", port), new Server("127.0.0.1", port));
		}
	}

}


