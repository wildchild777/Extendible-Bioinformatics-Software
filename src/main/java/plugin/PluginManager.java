package plugin;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * PluginManager handles loading plugin JARs from a directory
 * and gives access to all supported plugin types: Parsers, Clustering Algos, Visualizers.
 */
public class PluginManager {

    private final List<ParserPlugin> parserPlugins = new ArrayList<>();
    private final List<ClusteringPlugin> clusteringPlugins = new ArrayList<>();
    private final List<VisualizationPlugin> visualizationPlugins = new ArrayList<>();

    /**
     * Load all plugins from a given folder path.
     * 
     * @param pluginDirectoryPath path to folder containing .jar plugin files
     * @throws URISyntaxException 
     */
    public void loadPlugins(String pluginDirectoryPath) throws URISyntaxException {
    	//this is where our directory for the plug-ins are stored
        File directory = new File(pluginDirectoryPath);
        //error testing to see if directory exists or not 
        if (!directory.exists() || !directory.isDirectory()) {
            System.err.println("Plugin directory not found: " + pluginDirectoryPath);
            return;
        }
        //gets the name of every jar file found
        File[] jarFiles = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        //loads them into needed lists
        for (File jar : jarFiles) {
            loadPluginsFromJar(jar);
        }
    }

    /**
     * Basically we go into every file and look into it to check if it implements our interfaces
     * if yes then we store it
     * 
     * Looks into a jar file and extracts the class basically
     *
     * @param jarFile plugin .jar file
     * @throws URISyntaxException 
     */
    private void loadPluginsFromJar(File jarFile) throws URISyntaxException {
        try (JarFile jar = new JarFile(jarFile)) {
        	//this gives us everything that is inside the JAR
            Enumeration<JarEntry> entries = jar.entries();
            //gets the uri of the file and then converts it into a url
            URI uri = new URI("jar", jarFile.toURI().toString() + "!/", null);
            URL[] urls = { uri.toURL() };
            //our temp class holder
            try (URLClassLoader loader = new URLClassLoader(urls, getClass().getClassLoader())) {
            	//gets all the things in the jar
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    if (entry.getName().endsWith(".class")) {//only care about the class
                        String className = entry.getName().replace('/', '.').replace(".class", "");//convert to actual class name

                        try {//load the class from the .jar fil;e
                            Class<?> cls = Class.forName(className, true, loader);
                            loadIfPlugin(cls);
                        } catch (Throwable t) {
                            System.err.println("Skipped loading class: " + className + " = " + t.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Could not read JAR file: " + jarFile.getName());
        }
    }

    /**
     * Check if the class implements a known plugin interface and register it.
     */
    private void loadIfPlugin(Class<?> cls) {
        if (PluginInterface.class.isAssignableFrom(cls)) {//only continue if class is a valid plugin type
            try {
                Object pluginInstance = cls.getDeclaredConstructor().newInstance();

                if (pluginInstance instanceof ParserPlugin) {
                    parserPlugins.add((ParserPlugin) pluginInstance);
                    System.out.println("Loaded Parser Plugin: " + ((ParserPlugin) pluginInstance).getName());
                } else if (pluginInstance instanceof ClusteringPlugin) {
                    clusteringPlugins.add((ClusteringPlugin) pluginInstance);
                    System.out.println("Loaded Clustering Plugin: " + ((ClusteringPlugin) pluginInstance).getName());
                } else if (pluginInstance instanceof VisualizationPlugin) {
                    visualizationPlugins.add((VisualizationPlugin) pluginInstance);
                    System.out.println("Loaded Visualization Plugin: " + ((VisualizationPlugin) pluginInstance).getName());
                }
            } catch (Exception e) {
                System.err.println("Failed to instantiate plugin class: " + cls.getName());
            }
        }
    }

   //getters

    public List<ParserPlugin> getParsers() {
        return parserPlugins;
    }

    public List<ClusteringPlugin> getClustering() {
        return clusteringPlugins;
    }

    public List<VisualizationPlugin> getVisualizations() {
        return visualizationPlugins;
    }
}
