package model;
import java.util.List;

/**
 * This class is used for requests and to pass the request onto concrete subclasses
 */
public class ParserContext {
	//privately held strategy
	private ParserStrategy strategy;
	
	/**
	 * This is used to set the Parser We want to use
	 * @param strategy - concrete Parsing algorithm we want to use
	 */
	public void setParser(ParserStrategy strategy) {
        this.strategy = strategy;
    }
	
	/**
	 * This is used to execute the parse
	 * @param filename - the file we want to parse
	 * @return - returns ParsedData which can be further used
	 */
	public List<Entry> executeParse(String filename) {
        if (strategy == null) {
            throw new IllegalStateException("ParserStrategy is not set.");
        }
        return strategy.parse(filename);
    }
}
