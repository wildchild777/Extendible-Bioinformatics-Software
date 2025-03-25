import java.util.ArrayList;
import java.util.List;

/**
 * This class is a type of data that holds gene expression data - like the ones you get if you parse SOFT files.
 * 
 */
public class GeneExpressionParsedData implements ParsedData {
	
	private List<Entry> entry = new ArrayList<Entry>();//where we store all the entries
	
	public List<Entry>getEntries(){
		return entry;
	}
	
	public void add(Entry temp) {
		entry.add(temp);
	}
	
//	public Entry getEntry() {
		
	//}
}
