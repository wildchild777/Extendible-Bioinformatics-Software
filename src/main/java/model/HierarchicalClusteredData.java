package model;
/**
 * This will store clustered Data from Hierarchical clustering algorithm
 */
public class HierarchicalClusteredData implements ClusteredData {
    private final ClusterNode root;

    public HierarchicalClusteredData(ClusterNode root) {
        this.root = root;
    }

    public ClusterNode getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return toString(root, 0);
    }

    private String toString(ClusterNode node, int level) {
        if (node == null) return "";

        StringBuilder sb = new StringBuilder();
        sb.append("  ".repeat(level));
        if (node.isLeaf()) {
            sb.append("Leaf: ").append(node.getEntry().getName()).append("\n");
        } else {
            sb.append("Node (distance=").append(node.getDistance()).append(")\n");
            sb.append(toString(node.getLeft(), level + 1));
            sb.append(toString(node.getRight(), level + 1));
        }
        return sb.toString();
    }
}
