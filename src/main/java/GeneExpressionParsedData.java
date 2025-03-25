import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a type of data that holds gene expression data - like the ones you get if you parse SOFT files.
 * 
 */
public class GeneExpressionParsedData implements ParsedData {
	
	private List<Entry> entry = new ArrayList<Entry>();//where we store all the entries
	private List<String> name= new ArrayList<String>();//where we store all the entries
	private List<Map<String,Double>> gene = new ArrayList<Map<String,Double>>();//where we store all the entries
	
	public List<Entry>getEntries(){
		return entry;
	}
	
	public void add(Entry temp) {
		entry.add(temp);
	}
	
	public List<String> getName(){
		for ( Entry temp : entry) {
			name.add(temp.getName());
		}
		return name;
	}
	
	public List<Map<String,Double>> getGene(){
		for ( Entry temp : entry) {
			gene.add(temp.getGene());
		}
		return gene;
	}
	
	public Map<String,Double> getSpecificGene(String sampleName) {
		if(entry!=null) {
			for(Entry t : entry) {
				if (t.getName().equals(sampleName)) {
					return t.getGene();
				}
			}
		}
		return null;
	}

}
