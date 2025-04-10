package model;
import java.util.List;

/** This is an interface that applies the Strategy design pattern for our parsing needs.
 */
public interface ParserStrategy {
/**
 * @param filename the file to be parsed
 * @return a type of parsedData 
 */
	//need to change this to return a concrete subclass of the the interface ParsedData
	
	public List<Entry> parse(String filename);

}
