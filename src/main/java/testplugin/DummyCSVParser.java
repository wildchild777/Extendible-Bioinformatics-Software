package testplugin;

import java.io.File;
import java.util.List;
import java.util.Collections;

import model.ParsedData;
import model.GeneExpressionParsedData;
import plugin.ParserPlugin;

public class DummyCSVParser implements ParserPlugin {
    @Override
    public String getName() {
        return "Dummy CSV Parser";
    }

    @Override
    public String getDescription() {
        return "Parses dummy CSV files";
    }

    @Override
    public List<String> getSupportedFileExtension() {
        return List.of("csv");
    }

    @Override
    public ParsedData parse(File file) {
        System.out.println("Dummy CSV parsing called on: " + file.getName());
        return new GeneExpressionParsedData(); // return empty dummy data
    }
}
