package graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;

/**
 * An implementation of Graph for vertices with String labels.
 * 
 * This class represents a graph where vertices are mutable.
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();
    private final Map<String, Map<String, Integer>> edges = new HashMap<>();

    // Abstraction Function:
    // The graph is represented by a list of vertices, each with a label, and a map of edges where each entry contains 
    // the source vertex label as a key and a map of target vertex labels with their edge weights as the value.
    //
    // Representation Invariant:
    // - Each vertex has a unique label.
    // - Vertices are mutable.
    // - The edges map tracks directed edges with their weights between vertices.
    //
    // Safety from Rep Exposure:
    // - The vertices list and edges map are private and not exposed directly.
    // - Clients can interact with vertices but not modify the internal structure.

    /**
     * Adds a vertex to the graph if it is not already present.
     * 
     * @param vertex the label of the vertex to add
     * @return true if the vertex was added, false if it was already present
     */
    @Override
    public boolean add(String vertex) {
        checkRep();
        for (Vertex v : vertices) {
            if (v.label.equals(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex(vertex));
        edges.put(vertex, new HashMap<>());  // Initialize an empty edge map for the new vertex
        checkRep();
        return true;
    }

    /**
     * Sets the weight of an edge between the given source and target vertices.
     * 
     * @param source the label of the source vertex
     * @param target the label of the target vertex
     * @param weight the weight of the edge
     * @return the previous weight of the edge, or zero if there was no such edge
     */
    @Override
    public int set(String source, String target, int weight) {
        checkRep();
        
        // Validate the edge weight
        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative");
        }

        if (!edges.containsKey(source)) {
            add(source);  // Ensure the source vertex exists
        }
        if (!edges.containsKey(target)) {
            add(target);  // Ensure the target vertex exists
        }

        Map<String, Integer> sourceEdges = edges.get(source);
        int previousWeight = sourceEdges.getOrDefault(target, 0);
        
        if (weight == 0) {
            sourceEdges.remove(target);  // Remove the edge if the weight is zero
        } else {
            sourceEdges.put(target, weight);  // Add or update the edge
        }

        checkRep();
        return previousWeight;
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
        if (!edges.containsKey(vertex)) {
            return false;
        }

        // Remove all edges to and from this vertex
        edges.remove(vertex);
        for (Map<String, Integer> targetEdges : edges.values()) {
            targetEdges.remove(vertex);
        }

        // Remove the vertex itself
        vertices.removeIf(v -> v.label.equals(vertex));
        checkRep();
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
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices) {
            result.add(v.label);
        }
        return Collections.unmodifiableSet(result);
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
        for (Map.Entry<String, Map<String, Integer>> entry : edges.entrySet()) {
            if (entry.getValue().containsKey(target)) {
                result.put(entry.getKey(), entry.getValue().get(target));
            }
        }
        return Collections.unmodifiableMap(result);
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
        return Collections.unmodifiableMap(edges.getOrDefault(source, new HashMap<>())); // Return a copy of the source's edge map
    }

    /**
     * Returns a string representation of the graph.
     * 
     * @return a human-readable string of the graph
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices.size()).append(" vertices\n");
        sb.append("Edges:\n");
        for (Map.Entry<String, Map<String, Integer>> entry : edges.entrySet()) {
            sb.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Checks the representation invariant.
     */
    private void checkRep() {
        // Ensure that all vertices are unique
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i + 1; j < vertices.size(); j++) {
                if (vertices.get(i).label.equals(vertices.get(j).label)) {
                    throw new IllegalStateException("Duplicate vertices found!");
                }
            }
        }

        // Ensure that the edges map is consistent
        for (Map.Entry<String, Map<String, Integer>> entry : edges.entrySet()) {
            String vertex = entry.getKey();
            for (String target : entry.getValue().keySet()) {
                if (vertices.stream().noneMatch(v -> v.label.equals(target))) {
                    throw new IllegalStateException("Edge target " + target + " is not a vertex!");
                }
            }
        }
    }

    /**
     * Checks if the graph is empty (has no vertices).
     * 
     * @return true if the graph has no vertices, false otherwise
     */
    @Override
    public boolean empty() {
        return vertices.isEmpty();
    }

    /**
     * A vertex with a label.
     */
    private static class Vertex {
        private final String label;

        /**
         * Constructs a vertex with the given label.
         * 
         * @param label the label of the vertex
         */
        public Vertex(String label) {
            this.label = label;
        }

        /**
         * Returns a string representation of the vertex.
         * 
         * @return a human-readable string of the vertex
         */
        @Override
        public String toString() {
            return label;
        }
    }
}
