package io.javabrains.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.javabrains.model.Movie;
import io.javabrains.model.MovieCatalog;
import io.javabrains.model.Rating;
import io.javabrains.model.UserRating;
import io.javabrains.service.MovieInfo;
import io.javabrains.service.UserRatingInfo;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	MovieInfo movieInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
	@RequestMapping("/{userId}")
	@HystrixCommand(fallbackMethod = "getFallbackMovieCatalog")
	public List<MovieCatalog> getMovieCatalog(@PathVariable("userId") String userId){
		
		UserRating ratings =restTemplate.getForObject("http://rating-data-service/ratingdata/users/"+userId, UserRating.class); 
				//userRatingInfo.getUserRating(userId);
		
		List<MovieCatalog> list = ratings.getRatings().stream()
				.map(rating -> {Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
				return new MovieCatalog(movie.getName(), movie.getDescription(), rating.getRating());
				}).collect(Collectors.toList());
		
		return list;
	}
	
	public MovieCatalog getFallbackMovieCatalog(Rating rating) {
		return new MovieCatalog("no movie found", " ", 0);
	}
}
