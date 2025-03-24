import java.util.List;

/**
 * This interface defines datatype that can be returned from a parse.
 */
public interface ParsedData {
	/**
	 * This returns the stored data from a parse - can be an concrete class.
	 * @return a list containing a sample and it's gene - expression pairs.
	 */
	public List<Entry> getEntries();
}
