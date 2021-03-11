package io.javabrains.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.javabrains.model.Movie;
import io.javabrains.model.MovieSummary;

@RestController
@RequestMapping("/movies")
public class MovieResource {
	
	@Value("${api.key}")
	private String apiKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping("/{movieId}")
	public Movie getMovie(@PathVariable("movieId") String movieId) {
		MovieSummary movieSummary = restTemplate.getForObject
				("https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey, MovieSummary.class);
		
		return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());
	}
	//https://api.themoviedb.org/3/movie/550?api_key=55189a64dfc393d4c1e3e9c25d87495e
}
