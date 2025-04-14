package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;


public class CSVParser implements ParserStrategy{

    /**
     * Parses the given CSV file into a ParsedData object.
     */
    @Override
    public ParsedData parse(File file) {
        GeneExpressionParsedData data = new GeneExpressionParsedData();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                System.err.println("CSV file is empty.");
                return data;
            }
            // Split header line by comma to get column names.
            String[] headers = headerLine.split(",");
            // Start reading data rows.
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length != headers.length) {
                    // Skip the row if column count doesn't match header count.
                    System.err.println("Skipping line due to column count mismatch: " + line);
                    continue;
                }
                // The first column is the sample name.
                String sampleName = fields[0].trim();
                Map<String, Double> geneExpressions = new HashMap<>();
                // For each remaining field, attempt to parse as a double.
                for (int i = 1; i < fields.length; i++) {
                    String geneName = headers[i].trim();
                    try {
                        Double value = Double.parseDouble(fields[i].trim());
                        geneExpressions.put(geneName, value);
                    } catch (NumberFormatException nfe) {
                        System.err.println("Invalid number format for " + geneName + " in sample " + sampleName);
                    }
                }
                // Create an Entry for this sample and add it to the parsed data.
                Entry entry = new Entry(sampleName, geneExpressions);
                data.add(entry);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return data;
    }
}
