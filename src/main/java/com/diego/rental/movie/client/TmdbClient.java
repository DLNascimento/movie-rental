package com.diego.rental.movie.client;
import com.diego.rental.movie.dto.TmdbResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TmdbClient {

    private final WebClient webClient;
    private final String apiKey;

    public TmdbClient(WebClient.Builder builder,
                      @Value("${tmdb.api.key}") String apiKey) {

        this.webClient = builder
                .baseUrl("https://api.themoviedb.org/3")
                .build();

        this.apiKey = apiKey;
    }

    public TmdbResponse searchMovie(String title) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .build())
                .header("Authorization", "Bearer " + apiKey) // 👈 usa token aqui
                .header("accept", "application/json")
                .retrieve()
                .bodyToMono(TmdbResponse.class)
                .block();
    }
}
