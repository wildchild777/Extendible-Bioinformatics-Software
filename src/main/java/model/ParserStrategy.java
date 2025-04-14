package model;
import java.io.File;
import java.util.List;

/** This is an interface that applies the Strategy design pattern for our parsing needs.
 */
public interface ParserStrategy {
/**
 * @param filename the file to be parsed
 * @return a type of parsedData 
 */
	public ParsedData parse(File filename);

}
