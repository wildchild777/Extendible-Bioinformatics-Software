package model;

import java.util.List;
/**
 * Supporting class to store data for hierarchal clustered data - it is a recursive data type used to store tree-like
 * structure used by clustering algorithms like Hierarchal clustering
 */
public class ClusterNode {
    private ClusterNode left;
    private ClusterNode right;
    private double distance;//Numeric distance to get the difference or similarity of node based on this.
    private Entry entry; // Leaf node if this is not null

    public ClusterNode(Entry entry) {
        this.entry = entry;
    }

    public ClusterNode(ClusterNode left, ClusterNode right, double distance) {
        this.left = left;
        this.right = right;
        this.distance = distance;
    }

    public boolean isLeaf() {
        return entry != null;
    }

    public Entry getEntry() {
        return entry;
    }

    public ClusterNode getLeft() {
        return left;
    }

    public ClusterNode getRight() {
        return right;
    }

    public double getDistance() {
        return distance;
    }
}
