import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import model.Entry;
import model.ParsedData;
import model.ParserContext;
import model.SoftParser;

public class ParserContextTest {

	@Test
	public void Setup() {
		ParserContext context = new ParserContext();
		context.setParser(new SoftParser());
		ParsedData holder = context.executeParse("src/main/resources/ParseTestFile.soft");
		assertNotNull(holder);
		System.out.println(holder);
	}
	@Test
	public void throw_exception() {
		ParserContext context = new ParserContext();
		assertThrows(IllegalStateException.class, () -> context.executeParse(null));
		
	}
	
	
}
