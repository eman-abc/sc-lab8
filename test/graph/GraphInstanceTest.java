package graph;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class GraphInstanceTest {

    // This method will be provided in the subclasses to return an empty graph instance.
    protected abstract Graph<String> emptyInstance();

    // Test that a new graph is initially empty
    @Test
    public void testInitialVerticesEmpty() {
        Graph<String> graph = emptyInstance();
        assertTrue("Graph should be empty initially.", graph.vertices().isEmpty());
    }

    // Test adding a vertex and verify it is added
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Adding a new vertex should return true.", graph.add("A"));
        assertTrue("Graph should contain the vertex after adding it.", graph.vertices().contains("A"));
    }

    // Test adding a duplicate vertex, which should not create a second instance
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue("Adding new vertex A should return true.", graph.add("A"));
        assertFalse("Adding duplicate vertex A should return false.", graph.add("A"));
    }

    // Test setting edges and verify that they exist with correct weights
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        
        // Test setting a new edge with weight 5
        assertEquals("New edge should return weight 0.", 0, graph.set("A", "B", 5));
        
        // Test updating the edge weight to 3
        assertEquals("Updating edge should return previous weight 5.", 5, graph.set("A", "B", 3));
        
        // Test removing the edge by setting weight to 0
        assertEquals("Removing edge should return previous weight 3.", 3, graph.set("A", "B", 0));
        
        // Check that the target "B" is now empty
        assertTrue("Target should be empty after removing edge.", graph.targets("A").isEmpty());
    }

    // Test removing a vertex and verify it no longer exists
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 1);
        
        // Test removing vertex A
        assertTrue("Removing existing vertex A should return true.", graph.remove("A"));
        
        // Verify that vertex A is no longer in the graph
        assertFalse("Vertex A should not exist after removal.", graph.vertices().contains("A"));
        
        // Ensure that there are no incoming edges to B after A is removed
        assertTrue("No incoming edges should remain to B after A is removed.", graph.sources("B").isEmpty());
    }

    // Test the sources and targets methods for directed edges
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        
        // Set edges with different weights
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);
        
        // Check targets of A
        Map<String, Integer> targetsA = graph.targets("A");
        assertEquals("A should have 1 target.", 1, targetsA.size());
        assertEquals("Target B should have weight 5 from A.", Integer.valueOf(5), targetsA.get("B"));
        
        // Check sources of B
        Map<String, Integer> sourcesB = graph.sources("B");
        assertEquals("B should have 2 sources.", 2, sourcesB.size());
        assertEquals("Source A should have weight 5 to B.", Integer.valueOf(5), sourcesB.get("A"));
        assertEquals("Source C should have weight 3 to B.", Integer.valueOf(3), sourcesB.get("C"));
    }
}
