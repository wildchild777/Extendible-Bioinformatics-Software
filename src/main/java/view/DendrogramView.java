package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import model.ClusteredData;
import model.HierarchicalClusteredData;
import model.ClusterNode;

import java.util.HashMap;
import java.util.Map;
/**
 * This outputs out DendogramView which is used by Hierarchal clustering
 */
public class DendrogramView extends ScrollPane  implements VisualizationView, Observer {
	
    private Canvas canvas;
    private double width = 800;
    private double height = 600;
    private final Pane contentPane = new Pane(); // holds the canvas
    private Map<ClusterNode, Double> yPositions = new HashMap<>();
    private int leafCounter = 0;
    private double leafSpacing = 30;
    //creates our new view for dendograms
    public DendrogramView() {
    	canvas = new Canvas(width, height);
        contentPane.getChildren().add(canvas);
        this.setContent(contentPane); // set scrollable content
    }
    
    private int countLeaves(ClusterNode node) {
        if (node.isLeaf()) return 1;
        return countLeaves(node.getLeft()) + countLeaves(node.getRight());
    }
    private int computeDepth(ClusterNode node) {
        if (node.isLeaf()) return 1;
        return 1 + Math.max(computeDepth(node.getLeft()), computeDepth(node.getRight()));
    }
    
    @Override
    public void displayClusters(ClusteredData data) {
        if (!(data instanceof HierarchicalClusteredData)) {
            throw new IllegalArgumentException("DendrogramView only supports HierarchicalClusteredData");
        }

        HierarchicalClusteredData hData = (HierarchicalClusteredData) data;
        ClusterNode root = hData.getRoot();
        
        int totalLeaves = countLeaves(root);
        height = Math.max(leafSpacing * totalLeaves + 100, 600);
        canvas.setHeight(height);
        
        int treeDepth = computeDepth(root);
        width = Math.max(100 + treeDepth * 80, 800); // 80 is horizontal spacing per level
        canvas.setWidth(width);
        contentPane.setPrefWidth(width);
        contentPane.setPrefSize(width, height);
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, width, height);
        yPositions.clear();
        leafCounter = 0;

        // First pass: compute Y positions for all nodes
        computeYPositions(root);

        // Second pass: draw the tree
        drawDendrogram(gc, root, 50, width - 100);
    }

    private void computeYPositions(ClusterNode node) {
        if (node.isLeaf()) {
            double y = leafSpacing * leafCounter + 50;
            yPositions.put(node, y);
            leafCounter++;
        } else {
            computeYPositions(node.getLeft());
            computeYPositions(node.getRight());

            double yLeft = yPositions.get(node.getLeft());
            double yRight = yPositions.get(node.getRight());
            double midY = (yLeft + yRight) / 2;
            yPositions.put(node, midY);
        }
    }

    private void drawDendrogram(GraphicsContext gc, ClusterNode node, double x, double maxX) {
        double y = yPositions.get(node);

        if (node.isLeaf()) {
            gc.fillText(node.getEntry().getName(), x + 20, y+4);
        } else {
            ClusterNode left = node.getLeft();
            ClusterNode right = node.getRight();
            double yLeft = yPositions.get(left);
            double yRight = yPositions.get(right);

            double childX = x + 50;

            // Draw lines
            gc.strokeLine(x, yLeft, childX, yLeft); // horizontal to left
            gc.strokeLine(x, yRight, childX, yRight); // horizontal to right
            gc.strokeLine(x, yLeft, x, yRight); // vertical connecting both
            gc.strokeLine(x, y, childX, y); // connect parent to midpoint

            // Recurse
            drawDendrogram(gc, left, childX, maxX);
            drawDendrogram(gc, right, childX, maxX);
        }
    }

    @Override
    public void update(ClusteredData data) {
        displayClusters(data);
    }
}
