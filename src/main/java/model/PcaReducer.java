package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.*;

public class PcaReducer implements DimensionalityReducer {

    @Override
    public List<Entry> reduce(List<Entry> entries, int targetDimensions) {
        if (entries == null || entries.isEmpty()) return new ArrayList<>();

        // get all the the geneNames
        List<String> geneNames = new ArrayList<>(entries.get(0).getGene().keySet());
        int originalDim = geneNames.size();
        int nSamples = entries.size();

        //data matrix where rows = samples columns = genes
        double[][] dataMatrix = new double[nSamples][originalDim];
        for (int i = 0; i < nSamples; i++) {
            Map<String, Double> geneMap = entries.get(i).getGene();
            for (int j = 0; j < originalDim; j++) {
                dataMatrix[i][j] = geneMap.get(geneNames.get(j));
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(dataMatrix);

        // here we centre the data
        RealMatrix centered = matrix.copy();
        double[] means = new double[originalDim];
        for (int j = 0; j < originalDim; j++) {
            double mean = 0;
            for (int i = 0; i < nSamples; i++) {
                mean += matrix.getEntry(i, j);
            }
            mean /= nSamples;
            means[j] = mean;
            for (int i = 0; i < nSamples; i++) {
                centered.addToEntry(i, j, -mean);
            }
        }
        
        
     // here we create a co-variance matrix
        RealMatrix transposed = centered.transpose();             // first transpose the matrix
        RealMatrix product = transposed.multiply(centered);       // multiply transposed * centered
        RealMatrix cov = product.scalarMultiply(1.0 / (nSamples - 1));  // scale for covariance

        //we decompose for the eigenvectors
        EigenDecomposition eig = new EigenDecomposition(cov);
        RealMatrix eigenvectors = eig.getV();

        // select for the top dimensions in our matrix
        RealMatrix projectionMatrix = eigenvectors.getSubMatrix(0, originalDim - 1, 0, targetDimensions - 1);

        //project
        RealMatrix reducedMatrix = centered.multiply(projectionMatrix);

        // converted back to entries
        List<Entry> reducedEntries = new ArrayList<>();
        for (int i = 0; i < nSamples; i++) {
            Map<String, Double> reducedMap = new HashMap<>();
            for (int j = 0; j < targetDimensions; j++) {
                reducedMap.put("PC" + (j + 1), reducedMatrix.getEntry(i, j));
            }
            reducedEntries.add(new Entry(entries.get(i).getName(), reducedMap));
        }

        return reducedEntries;
    }
}
