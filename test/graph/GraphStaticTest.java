package graph;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GraphStaticTest {
    private Graph<String> graph;

    @Before
    public void setUp() {
        graph = new ConcreteEdgesGraph();  // Ensure you create an instance of ConcreteEdgesGraph
    }

    @Test
    public void testEmptyVerticesEmpty() {
        // Assert that the graph is empty at the start
        assertTrue("Graph should be empty initially", graph.empty());

        // Assert that the vertices set is empty
        assertTrue("Vertices should be empty initially", graph.vertices().isEmpty());
    }

    // Other tests...
}
