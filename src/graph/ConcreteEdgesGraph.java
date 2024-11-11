package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * This class represents a weighted directed graph where vertices are labeled with strings, 
 * and edges have a positive weight.
 */
public class ConcreteEdgesGraph implements Graph<String> {

    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    // Abstraction Function:
    // The graph is represented by a set of vertices and a list of edges between them.
    // Each edge is directed and has a positive weight.
    //
    // Representation Invariant:
    // - The vertices set contains only unique vertex labels.
    // - The edges list contains directed edges with non-null source and target vertices.
    // - The weight of each edge is positive.
    //
    // Safety from Rep Exposure:
    // - The vertices and edges are private and never exposed directly.
    // - Any public methods return defensive copies where applicable.

    /**
     * Adds a vertex to the graph if it is not already present.
     * 
     * @param vertex the label of the vertex to add
     * @return true if the vertex was added, false if it was already present
     */
    @Override
    public boolean add(String vertex) {
        checkRep();
        return vertices.add(vertex);
    }

    /**
     * Sets the weight of an edge between the given source and target vertices.
     * If the weight is zero, the edge is removed.
     * If the vertices do not exist, they are added.
     * 
     * @param source the label of the source vertex
     * @param target the label of the target vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge, or zero if there was no such edge
     */
    @Override
    public int set(String source, String target, int weight) {
        checkRep();
        if (weight == 0) {
            return removeEdge(source, target);
        }

        add(source);
        add(target);

        for (Edge edge : edges) {
            if (edge.source.equals(source) && edge.target.equals(target)) {
                int oldWeight = edge.weight;
                edge.weight = weight;
                return oldWeight;
            }
        }

        edges.add(new Edge(source, target, weight));
        return 0;
    }

    /**
     * Removes a vertex from the graph and its associated edges.
     * 
     * @param vertex the label of the vertex to remove
     * @return true if the vertex was removed, false if it did not exist
     */
    @Override
    public boolean remove(String vertex) {
        checkRep();
        if (!vertices.remove(vertex)) {
            return false;
        }

        edges.removeIf(edge -> edge.source.equals(vertex) || edge.target.equals(vertex));
        return true;
    }

    /**
     * Returns a set of all vertex labels in the graph.
     * 
     * @return a set of vertex labels
     */
    @Override
    public Set<String> vertices() {
        checkRep();
        return new HashSet<>(vertices);
    }

    /**
     * Returns a map of source vertices and edge weights for the given target vertex.
     * 
     * @param target the label of the target vertex
     * @return a map of source vertices and edge weights
     */
    @Override
    public Map<String, Integer> sources(String target) {
        checkRep();
        Map<String, Integer> result = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.target.equals(target)) {
                result.put(edge.source, edge.weight);
            }
        }
        return result;
    }

    /**
     * Returns a map of target vertices and edge weights for the given source vertex.
     * 
     * @param source the label of the source vertex
     * @return a map of target vertices and edge weights
     */
    @Override
    public Map<String, Integer> targets(String source) {
        checkRep();
        Map<String, Integer> result = new HashMap<>();
        for (Edge edge : edges) {
            if (edge.source.equals(source)) {
                result.put(edge.target, edge.weight);
            }
        }
        return result;
    }

    /**
     * Returns a string representation of the graph.
     * 
     * @return a human-readable string of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n");
        sb.append("Edges:\n");
        for (Edge edge : edges) {
            sb.append(edge).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks the representation invariant.
     */
    private void checkRep() {
        // Ensure that all vertices are unique
        assert vertices.size() == new HashSet<>(vertices).size() : "Duplicate vertices found!";
        
        // Ensure that each edge has valid source, target, and positive weight
        for (Edge edge : edges) {
            assert edge.source != null : "Edge source is null!";
            assert edge.target != null : "Edge target is null!";
            assert edge.weight > 0 : "Edge weight is non-positive!";
        }
    }

    /**
     * Removes an edge between the source and target vertices.
     * 
     * @param source the source vertex label
     * @param target the target vertex label
     * @return the previous weight of the edge, or zero if no edge existed
     */
    private int removeEdge(String source, String target) {
        for (Edge edge : edges) {
            if (edge.source.equals(source) && edge.target.equals(target)) {
                edges.remove(edge);
                return edge.weight;
            }
        }
        return 0;
    }

    /**
     * Returns true if the graph is empty (no vertices or edges).
     */
    @Override
    public boolean empty() {
        return vertices.isEmpty() && edges.isEmpty();
    }

    /**
     * An immutable representation of a directed edge with a weight.
     */
    private static class Edge {
        private final String source;
        private final String target;
        private int weight;

        /**
         * Constructs an edge with the given source, target, and weight.
         * 
         * @param source the label of the source vertex
         * @param target the label of the target vertex
         * @param weight the weight of the edge
         */
        public Edge(String source, String target, int weight) {
            this.source = source;
            this.target = target;
            this.weight = weight;
        }

        /**
         * Returns a string representation of the edge.
         * 
         * @return a human-readable string of the edge
         */
        @Override
        public String toString() {
            return source + " -> " + target + ": " + weight;
        }
    }
}
