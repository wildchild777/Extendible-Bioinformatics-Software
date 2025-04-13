import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import model.*;

class SoftParserTest {

	@Test
	void test() {
		SoftParser soft = new SoftParser();
		 assertNotNull(soft);
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
			//List<Entry> temp = new ArrayList();
			ParsedData temp = soft.parse("src/main/resources/ParseTestFile.soft");
			ParsedData temp2 = soft2.parse("src/main/resources/ParseTestFile.soft");
			assertTrue(temp instanceof EntryBasedData && temp2 instanceof EntryBasedData);
			assertNotNull(temp);
			List<Entry> temp_entries= ((EntryBasedData) temp).getEntries();
			assertEquals(6,temp_entries.size());
			//List<Entry> temp2= new ArrayList();
			 
			List<Entry> temp2_entries= ((EntryBasedData) temp2).getEntries();
			assertEquals(temp_entries.size(),temp2_entries.size(),"their sizes should be the same");
			for(int i =0;i<temp_entries.size();i++) {
				Entry tempEntry = temp_entries.get(i);
				Entry tempEntry2= temp2_entries.get(i);
				String tempEntryName = tempEntry.getName();
				String tempEntryName2 = tempEntry.getName();
				Map<String,Double> tempEntryGene =tempEntry.getGene();
				Map<String,Double> tempEntryGene2 =tempEntry2.getGene();
				
				
				assertEquals(tempEntryName,tempEntryName2);
				assertEquals(tempEntryGene,tempEntryGene2);
				
			}
	}
}

