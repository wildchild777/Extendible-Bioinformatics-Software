package plugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.ParsedData;
import model.ParserStrategy;

public interface ParserPlugin extends PluginInterface, ParserStrategy {
	
	/**
     * @return A short label describing the file type this parser handles eg -> "csv" "fasta" in lowecase.
     */
    List<String> getSupportedFileExtension();


    
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
    
    default Map<String, Object> getParameters() {
        return new HashMap<>();
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
