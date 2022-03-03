package moviebuddy.data;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CsvMovieReaderTest {

	@Test
	void Valid_Metadata() throws Exception {
		AbstractFileSystemMovieReader movieReader = new CsvMovieReader();
		movieReader.setMetadata("movie_metadata.csv");
	
		movieReader.afterPropertiesSet();
	}
	
	@Test
	void Invalid_Metadata() {
		AbstractFileSystemMovieReader movieReader = new CsvMovieReader();
		
		Assertions.assertThrows(FileNotFoundException.class, () -> {
			movieReader.setMetadata("invalid");
			movieReader.afterPropertiesSet();
		});
	}
}
