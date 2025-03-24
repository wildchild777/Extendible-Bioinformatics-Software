import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	@Test
	void read_and_store() {
		//each parser run should use a new instance of the parser
		SoftParser soft = new SoftParser();
		SoftParser soft2 = new SoftParser();
		List<Entry> temp = new ArrayList();
		temp = (List<Entry>) soft.parse("src/main/resources/ParseTestFile.soft");
		assertNotNull(temp);
		assertEquals(6,temp.size());
		List<Entry> temp2= new ArrayList();
		temp2 = (List<Entry>) soft2.parse("src/main/resources/ParseTestFile.soft");
		assertEquals(temp.size(),temp2.size(),"their sizes should be the same");
		for(int i =0;i<temp.size();i++) {
			Entry tempEntry = temp.get(i);
			Entry tempEntry2= temp2.get(i);
			String tempEntryName = tempEntry.getName();
			String tempEntryName2 = tempEntry.getName();
			Map<String,Double> tempEntryGene =tempEntry.getGene();
			Map<String,Double> tempEntryGene2 =tempEntry2.getGene();
			
			
			assertEquals(tempEntryName,tempEntryName2);
			assertEquals(tempEntryGene,tempEntryGene2);
			
		}
	}
}

