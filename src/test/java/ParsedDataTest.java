import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class ParsedDataTest {
	@Test
	void setup() {
		GeneExpressionParsedData temp = new GeneExpressionParsedData();
		assertNull(temp.getEntries());
	} 
	@Test
	void addEntry(){
		Map<String,Double> hello = new HashMap<String,Double>();
		hello.put("karan",5.0);
		Entry hehe = new Entry("Hello",hello);
		GeneExpressionParsedData temp = new GeneExpressionParsedData();
		temp.add(hehe);//the entry we make here is null
		assertNotNull(temp.getEntries());
		
	}
}
