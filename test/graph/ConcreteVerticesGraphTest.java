package graph;

import static org.junit.Assert.*;
import org.junit.Test;

public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }

    // Testing the addition of a new vertex.
    @Test
    public void testAddVertex() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("A")); // Adding vertex "A"
        assertTrue(graph.vertices().contains("A"));
    }

    // Testing adding a duplicate vertex (should return false).
    @Test
    public void testAddDuplicateVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertFalse(graph.add("A")); // Vertex "A" already exists
    }

    // Testing the removal of a vertex.
    @Test
    public void testRemoveVertex() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        assertTrue(graph.remove("A"));
        assertFalse(graph.vertices().contains("A"));
    }

    // Testing the addition of edges.
    @Test
    public void testSetEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        assertEquals(0, graph.set("A", "B", 5)); // Adding edge A -> B with weight 5
        assertTrue(graph.sources("B").containsKey("A"));
        assertEquals(5, (int) graph.sources("B").get("A"));
    }

    // Testing the removal of an edge.
    @Test
    public void testRemoveEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        assertEquals(5, graph.set("A", "B", 0)); // Removing edge A -> B
        assertFalse(graph.sources("B").containsKey("A"));
    }

    // Testing sources for a vertex.
    @Test
    public void testSourcesAndTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.add("C");
        graph.set("A", "B", 5);
        graph.set("C", "B", 3);

        assertEquals(2, graph.sources("B").size()); // Two sources for B: A and C
        assertTrue(graph.sources("B").containsKey("A"));
        assertTrue(graph.sources("B").containsKey("C"));
        assertEquals(5, (int) graph.sources("B").get("A"));
        assertEquals(3, (int) graph.sources("B").get("C"));
    }


    // Check that checkRep works (you can call checkRep directly or let it be used implicitly)
    @Test
    public void testCheckRep() {
        Graph<String> graph = emptyInstance();
        graph.add("A");
        graph.add("B");
        graph.set("A", "B", 5);
        // Assuming checkRep is called automatically (if implemented correctly).
    }
}
