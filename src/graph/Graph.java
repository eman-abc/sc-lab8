package graph;

import java.util.Map;
import java.util.Set;

public interface Graph<T> {
    // Adds a vertex to the graph if not already present
    boolean add(T vertex);

    // Sets the weight of an edge between source and target
    int set(T source, T target, int weight);

    // Removes a vertex and its edges from the graph
    boolean remove(T vertex);

    // Returns a set of all vertex labels in the graph
    Set<T> vertices();

    // Returns a map of source vertices and edge weights for a target vertex
    Map<T, Integer> sources(T target);

    // Returns a map of target vertices and edge weights for a source vertex
    Map<T, Integer> targets(T source);

    // Returns a human-readable string representation of the graph
    String toString();

    // Returns true if the graph is empty
    boolean empty();
}
