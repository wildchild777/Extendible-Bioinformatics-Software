import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
public class ParsedDataTest {
	@Test
	void setup() {
		GeneExpressionParsedData temp = new GeneExpressionParsedData();
		assertNotNull(temp.getEntries());
		
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
	@Test
	void get_all_sample_name() {
		Map<String,Double> hello = new HashMap<String,Double>();//samples
		hello.put("karan",5.0);
		hello.put("arayn",7.0);
		hello.put("mok",9.0);
		hello.put("san",11.0);
		GeneExpressionParsedData temp = new GeneExpressionParsedData();
		for(int i =0; i<hello.size();i++) {
			temp.add(new Entry("mewo"+i,hello));
		}
		assertNotNull(temp.getName());
		assertNotNull(temp.getGene());
		
		Map<String,Double> gg= new HashMap<String,Double>();//samples
		
		
		gg = temp.getSpecificGene("mewo2");//this will give me arayn -> 7.0
		System.out.println(gg);
		
	}
	@Test 
	void get_null_for_exmpty_entrylist(){
		GeneExpressionParsedData temp = new GeneExpressionParsedData();
		assertNull(temp.getSpecificGene("gg"));
	}
}
