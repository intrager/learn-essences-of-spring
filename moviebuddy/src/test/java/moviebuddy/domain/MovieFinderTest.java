package moviebuddy.domain;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import moviebuddy.MovieBuddyApplication;
import moviebuddy.MovieBuddyFactory;

/**
 * @author springrunner.kr@gmail.com
 */

@SpringJUnitConfig(MovieBuddyFactory.class)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = MovieBuddyFactory.class)
public class MovieFinderTest {
	
	@Autowired MovieFinder movieFinder;
	
//	@Autowired
//	MovieFinderTest(MovieFinder movieFinder) {
//		this.movieFinder = movieFinder;
//	}

	
//	@Autowired
//	void setMovieFinder(MovieFinder movieFinder) {
//		this.movieFinder = movieFinder;
//	}
//	
	@Test
	void NotEmpty_directedBy() {
		List<Movie> movies = movieFinder.directedBy("Michael Bay");
		Assertions.assertEquals(3, movies.size());
	}
	
	@Test
	void NotEmpty_ReleasedYearBy() {
		List<Movie> movies = movieFinder.releasedYearBy(2015);
		Assertions.assertEquals(225,movies.size());
	}	
}
