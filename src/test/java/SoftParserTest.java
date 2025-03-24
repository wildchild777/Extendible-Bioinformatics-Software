import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class SoftParserTest {

	@Test
	void test() {
		SoftParser soft = new SoftParser();
	}
	@Test
	void can_read() {
		SoftParser soft = new SoftParser();
		soft.parse("TestFile.soft");
	}
	@Test
	void filename_wrong() {
		SoftParser soft = new SoftParser();
		assertThrows(IllegalArgumentException.class,() -> soft.parse("TestFile.gg"));		
	}
	@Test
	void filename_right() {
		SoftParser soft = new SoftParser();
		soft.parse("TestFile.soft");		
	}
	@Test
	void filename_not_present() {
		try {
		SoftParser soft = new SoftParser();
		soft.parse("heg.soft");
		}catch(Exception e) {
			assertNotNull(e);	
		}
	}
	
}
