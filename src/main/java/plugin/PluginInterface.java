package plugin;

public interface PluginInterface {
	/**
     * @return The name of the plugin.
     */
    String getName();

    /**
     * @return a description of what the plugin-does
     */
    String getDescription();

    /**
     * @return The type of plugin -> either a parser, clustering, viz technique
     */
    PluginType getPluginType();
}
