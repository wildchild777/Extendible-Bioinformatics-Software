package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is a type of data that holds gene expression data - like the ones you get if you parse SOFT files.
 * 
 */
public class GeneExpressionParsedData implements EntryBasedData  {
	
	private List<Entry> entry = new ArrayList<Entry>();//where we store all the entries
	private List<String> name= new ArrayList<String>();//where we store all the entries
	private List<Map<String,Double>> gene = new ArrayList<Map<String,Double>>();//where we store all the entries
	
	/**This is used to get a list of all the entires held in entry
	 * @return This returns a list 
	 *   
	 */
	public List<Entry> getEntries(){
		return entry;
	}
	/**
	 * This will allow you to add an entry in the list
	 * @param temp - the entry you want to add
	 */
	public void add(Entry temp) {
		entry.add(temp);
	}
	/**
	 * This will give you a list of string (i.e samples) 
	 * @return list of string/samples in the held entry list
	 */
	public List<String> getName(){
		for ( Entry temp : entry) {
			name.add(temp.getName());
		}
		return name;
	}
	/**
	 * Gives you a list of all the gene expression held in the entry list
	 * @return a list of all the gene to its mapping
	 */
	public List<Map<String,Double>> getGene(){
		for ( Entry temp : entry) {
			gene.add(temp.getGene());
		}
		return gene;
	}
	/**
	 * Gives you a mapping from all the genes to it's expression for a specific sample
	 * @param sampleName - the sample whose gene expression you want
	 * @return returns the gene expression
	 */
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
