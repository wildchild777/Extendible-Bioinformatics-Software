package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.linear.*;
/**
 * This class is very important to reduce the dimension of our data, uses PCA(principal component analysis)
 */
public class PcaReducer implements DimensionalityReducer {
	private static final int MAX_GENES = 500; // Limit to top 500 most variable genes
	
    @Override
    public List<Entry> reduce(List<Entry> entries, int targetDimensions) {
        if (entries == null || entries.isEmpty()) return new ArrayList<>();//sanity check

        // get all the the geneNames
        List<String> geneNames = new ArrayList<>(entries.get(0).getGene().keySet());
        int nSamples = entries.size();//gets the sizes of the samples

        //data matrix where rows = samples columns = genes
        Map<String, Double> geneToVariance = new HashMap<>();
        for (String gene : geneNames) {//this is used to get the mean and variance of each gene which is used below.
            double mean = 0, sqSum = 0;
            int validCount = 0;
            for (Entry e : entries) {
                Double val = e.getGene().get(gene);
                if (val != null) {
                    mean += val;//mean
                    sqSum += val * val;//square sum
                    validCount++;//valid samples
                }
            }
            if (validCount > 0) {
                mean /= validCount;
                double variance = (sqSum / validCount) - (mean * mean);//gets basic variance   
                geneToVariance.put(gene, variance);//puts the variance into our list
            } else {
                // If all values were null for a gene, skip it
                System.err.println("Skipping gene due to all nulls: " + gene);
            }
        }
        //sorts 
        geneNames.sort((g1, g2) -> Double.compare(geneToVariance.get(g2), geneToVariance.get(g1)));
        //min of max_genes or the size of the gene list
        geneNames = geneNames.subList(0, Math.min(MAX_GENES, geneNames.size()));
        //gets the actual reduced dimension of the list
        int reducedDim = geneNames.size();
        
        double[][] dataMatrix = new double[nSamples][reducedDim];//we dont need the og dim now we work with reducedim
        for (int i = 0; i < nSamples; i++) {
            Map<String, Double> geneMap = entries.get(i).getGene();
            for (int j = 0; j < reducedDim; j++) {//changed here as well
            	 Double val = geneMap.get(geneNames.get(j));
                 dataMatrix[i][j] = val != null ? val : 0.0;//sanity check for null values
            }
        }

        RealMatrix matrix = MatrixUtils.createRealMatrix(dataMatrix);

        // here we centre the data
        RealMatrix centered = matrix.copy();
        double[] means = new double[reducedDim];
        for (int j = 0; j < reducedDim; j++) {
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
        RealMatrix projectionMatrix = eigenvectors.getSubMatrix(0, reducedDim - 1, 0, targetDimensions - 1);

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
