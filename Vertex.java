import java.util.ArrayList;
import java.util.NoSuchElementException;
public class Vertex {
    private String name;
    private ArrayList<Edge> edges;
    private boolean visited;
    private Vertex parent;
    private int cost;

    private String parentSubwayNo;


    public String getParentSubwayNo() {
        return parentSubwayNo;
    }

    public void setParentSubwayNo(String parentSubwayNo) {
        this.parentSubwayNo = parentSubwayNo;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }
    public Vertex(String name) {
        this.name = name;
        edges = new ArrayList<Edge>();
        visited = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addEdge(Edge e) {
        edges.add(e);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void visit() {
        this.visited = true;
    }

    public void unvisit() {
        this.visited = false;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Vertex getUnvisitedNeighbor() {
        Vertex result = null;

        NeighborVertex neighbors = new NeighborVertex();
        while (neighbors.hasNext() && (result == null))
        {
            Vertex nextNeighbor = neighbors.next();
            if (!nextNeighbor.isVisited())
                result = nextNeighbor;
        } // end while

        return result;
    }

    public boolean hasEdge(String destination, String subwayNo) {

        boolean found = false;
        NeighborVertex neighbors = new NeighborVertex();

        while (neighbors.hasNext())
        {
            Vertex nextNeighbor = neighbors.next();
            EdgeIterator edgeIterator = new EdgeIterator();


            String neighbor_subwayNo = edgeIterator.edges.get(edgeIterator.currentIndex).getSubwayNo();

            if (nextNeighbor.getName().equalsIgnoreCase(destination) && neighbor_subwayNo.equalsIgnoreCase(subwayNo))
            {
                found = true;
                break;
            }
        } // end while

        return found;
    }

    public NeighborVertex getNeighborVertex()
    {
        return new NeighborVertex();
    }

    public class NeighborVertex
    {
        int edgeIndex = 0;
        private NeighborVertex()
        {
            edgeIndex = 0;
        } // end default constructor

        public boolean hasNext()
        {
            return edgeIndex < edges.size();
        } // end hasNext

        public Vertex next()
        {
            Vertex nextNeighbor = null;

            if (hasNext())
            {
                nextNeighbor = edges.get(edgeIndex).getDestination();
                edgeIndex++;
            }
            else
                throw new NoSuchElementException();

            return nextNeighbor;
        } // end next

        public String getSubwayNoFunc()
        {
            String nextNeighbor = null;
            edgeIndex--;
            if (hasNext() && !(edgeIndex < 0))
            {
                nextNeighbor = edges.get(edgeIndex).getSubwayNo();
            }
            edgeIndex++;
            return nextNeighbor;
        } // end nextSubwayNo


        public void remove()
        {
            throw new UnsupportedOperationException();
        } // end remove

    } // end NeighborIterator


    public EdgeIterator getEdgeIterator()
    {
        return new EdgeIterator();
    }

    public class EdgeIterator{
        private final ArrayList<Edge> edges = getEdges();
        int currentIndex;

        public boolean hasNext() {
            return currentIndex < edges.size();
        }

        public Edge next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Edge edge = edges.get(currentIndex);
            currentIndex++;
            return edge;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
