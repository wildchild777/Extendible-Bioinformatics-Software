package plugin;

import java.io.File;
import java.util.List;

import model.ParsedData;
import model.ParserStrategy;

public interface ParserPlugin extends PluginInterface, ParserStrategy {
	
	/**
     * @return A short label describing the file type this parser handles eg -> "csv" "fasta" in lowecase.
     */
    List<String> getSupportedFileExtension();

    /**
     * Parse the given file and return the data in the internal ParsedData format.
     *
     * @param file The file to parse.
     * @return ParsedData object containing the structured data.
     * @throws Exception if parsing fails.
     */
    ParsedData parse(File file) throws Exception;
    
    
    /**
     * Optionally indicate what type of ParsedData this parser returns (e.g., GeneExpressionParsedData).
     * Useful for clustering plugin compatibility checks.
     *
     * @return The concrete class of the ParsedData this parser produces.
     */
    default Class<? extends ParsedData> getParsedDataType() {
        return ParsedData.class;
    }
    
    /**
     * @return The plugin type for identification purposes. Should return PluginType.PARSER.
     */
    @Override
    default PluginType getPluginType() {
        return PluginType.PARSER;
    }
    
    /**
     * Whether this plugin requires extra configuration before parsing.
     *
     * @return true if additional configuration is required, false otherwise.
     */
    default boolean requiresConfiguration() {
        return false;
    }

}
