package io.javabrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.model.Movie;
import io.javabrains.model.MovieCatalog;
import io.javabrains.model.Rating;

@Service
public class MovieInfo {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackMovieCatalog")
	public MovieCatalog getMovieCatalog(Rating rating) {
		 Movie movie = restTemplate.getForObject 
					("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
		 return new MovieCatalog(movie.getName(), movie.getDescription(), rating.getRating());
	}
	
	public MovieCatalog getFallbackMovieCatalog(Rating rating) {
		return new MovieCatalog("no movie found", " ", 0);
	}
}
