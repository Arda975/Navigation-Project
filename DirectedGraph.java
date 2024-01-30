import java.util.*;

public class DirectedGraph {

    private HashMap<String, Vertex> vertices;

    public DirectedGraph() {
        this.vertices = new HashMap<>();
    }

    public void addEdge(String source, String destination, int weight, String subwayNo) {

        Vertex source_v = vertices.get(source);
        Vertex destination_v = vertices.get(destination);

        if (source_v != null && destination_v != null && source_v.hasEdge(destination,subwayNo)) {
            //System.out.println("This edge has already added! " + source + "-->" + destination + "---" + subwayNo);
            //if we have this edge we don't add again.!!!
        }
        else
        {
            if (vertices.get(source) == null) {
                source_v = new Vertex(source);
                vertices.put(source, source_v);
            }

            if (vertices.get(destination) == null) {
                destination_v = new Vertex(destination);
                vertices.put(destination, destination_v);
            }

            Edge edge = new Edge(source_v, destination_v, weight,subwayNo);
            source_v.addEdge(edge);
        }
    }

    public int size() {
        return vertices.size();
    }

    private void resetVertices() {
        for (Vertex v : vertices.values()) {
            v.unvisit();
            v.setCost(0);
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Aşşağıdakiler asıl cevaplar
    public int getCheapestPath(String start, String destinaton, boolean flag) {
        // Reset vertices before finding the cheapest path
        resetVertices();

        Vertex startVertex = vertices.get(start);
        Vertex endVertex = vertices.get(destinaton);

        // Use PriorityQueue to get the minimum cost vertex efficiently
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getCost));

        // Set the cost of the start vertex to 0
        startVertex.setCost(0);

        // Add the start vertex to the priority queue
        priorityQueue.add(startVertex);

        PriorityQueue<Vertex> TempPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getCost));
        Map<Vertex, Integer> shortestPathMap = new HashMap<>();

        while (!priorityQueue.isEmpty()) {
            // Get the vertex with the minimum cost
            Vertex currentVertex = priorityQueue.poll();

            // Mark the current vertex as visited
            currentVertex.visit();

            // Check each neighbor of the current vertex
            Vertex.NeighborVertex neighbors = currentVertex.getNeighborVertex();
            while (neighbors.hasNext()) {
                Vertex neighbor = neighbors.next();
                String subwayNo = neighbors.getSubwayNoFunc();

                // Calculate the new cost to reach the neighbor via the current vertex
                int newCost = currentVertex.getCost() + getEdgeCost(currentVertex, neighbor, subwayNo);


                // If the new cost is less than the current cost of the neighbor, update it
                if (!neighbor.isVisited() && (shortestPathMap.get(neighbor) == null || newCost < shortestPathMap.get(neighbor)))
                {//(!neighbor.isVisited() && newCost < neighbor.getCost())
                    neighbor.setCost(newCost);
                    neighbor.setParentSubwayNo(subwayNo);
                    // Set the parent to keep track of the path
                    neighbor.setParent(currentVertex);

                    shortestPathMap.put(neighbor, newCost);

                    // Add the updated neighbor to the priority queue
                    priorityQueue.add(neighbor);
                }
            }
        }

        // After the loop, the cost and path information is available for the target vertex
        int totalCost = endVertex.getCost();

        if(flag){

            System.out.println("Total Cost: " + totalCost/60 + "min\n");

            // Print the path
            printPath(endVertex);
        }

        return totalCost;
    }    //minimum time
    private int getEdgeCost(Vertex source, Vertex destination, String subwayNo) {
        for (Edge edge : source.getEdges()) {
            if (edge.getDestination().equals(destination) && edge.getSubwayNo().equals(subwayNo)) {
                return edge.getWeight();
            }
        }
        throw new NoSuchElementException("Edge not found");
    }
    private void printPath(Vertex vertex) {
        List<String> path = new ArrayList<>();
        String oldWayNo = vertex.getParentSubwayNo();

        while (vertex != null) {

            Vertex nextVertex = vertex.getParent();

            if(nextVertex == null){
                //Collections.reverse(path);
                //System.out.println("Line: " + vertex.getParentSubwayNo() + ":\nPath: " + String.join(" -> ", path + "\n"));
                break;
            }

            else if(oldWayNo.equals(nextVertex.getParentSubwayNo()))
            {
                path.add(vertex.getName());
                vertex = vertex.getParent();
            }

            else
            {
                path.add(vertex.getName());
                path.add(vertex.getParent().getName());
                Collections.reverse(path);
                System.out.println("Line: " + oldWayNo + ":\n" + path + "\n");

                path.clear();
                oldWayNo=nextVertex.getParentSubwayNo();
                vertex = vertex.getParent();
            }
        }
    }// end print
    public int getShortestPath(String startVertex, String endVertex) {
        // Reset vertices before finding the cheapest path
        resetVertices();

        Vertex source_v = vertices.get(startVertex);
        Vertex destination_v = vertices.get(endVertex);

        if (source_v == null || destination_v == null) {
            System.out.println("There is no Start or Destination Vertex");
        }

        Queue<List<Vertex>> queue = new LinkedList<>();
        queue.add(new ArrayList<>(Collections.singletonList(source_v)));

        while (!queue.isEmpty()) {
            List<Vertex> path = queue.poll();
            Vertex currentVertex = path.get(path.size() - 1);

            if (currentVertex == destination_v) {
                for (Vertex vertex : path) {
                    if(vertex == path.get(path.size() - 1))
                        System.out.print(vertex.getName());
                    else
                        System.out.print(vertex.getName() + " --> ");
                }
                return -1;
            }

            Vertex.EdgeIterator edgeIterator = currentVertex.getEdgeIterator();
            while (edgeIterator.hasNext())
            {
                Edge tempEdge = edgeIterator.next();
                if (tempEdge.getSource() == currentVertex) {
                    Vertex nextVertex = tempEdge.getDestination();
                    if(!path.contains(nextVertex)){
                        List<Vertex> newPath = new ArrayList<>(path);
                        newPath.add(nextVertex);
                        queue.add(newPath);
                    }
                }
            }
        }
        return -1;
    }//end the fewest stop


//////////////////////////////////////////////////////////////////////////////////////////////////////////////
// alternative way
    public void getMoreAffordablePath(String start, String destination, int cost) {
        // Now, reset the vertices and find an alternative path with cost less than the cheapest
        resetVertices();
            if(cost == -1) {
                int cost2 = getCheapestPath(start, destination, false);
                getMoreAffordablePath(start, destination, cost2);
            }
            else {
                // After the loop, the cost and path information is available for the target vertex
                int cheapestCost = cost;

                Vertex startVertex = vertices.get(start);
                Vertex endVertex = vertices.get(destination);

                // Use PriorityQueue to get the minimum cost vertex efficiently
                PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Vertex::getCost));

                // Set the cost of the start vertex to 0
                startVertex.setCost(0);

                // Add the start vertex to the priority queue
                priorityQueue.add(startVertex);

                while (!priorityQueue.isEmpty()) {
                    Vertex currentVertex = priorityQueue.poll();
                    currentVertex.visit();

                    Vertex.NeighborVertex neighbors = currentVertex.getNeighborVertex();
                    while (neighbors.hasNext()) {
                        Vertex neighbor = neighbors.next();
                        String subwayNo = neighbors.getSubwayNoFunc();

                        int newCost = currentVertex.getCost() + getEdgeCost(currentVertex, neighbor, subwayNo);

                        if (!neighbor.isVisited() && newCost < cheapestCost) {
                            neighbor.setCost(newCost);
                            neighbor.setParent(currentVertex);
                            priorityQueue.add(neighbor);
                        }
                    }
            }
                // Print the more affordable path
                System.out.println("\nMore Affordable Path:");
                System.out.println("Time: " + cheapestCost/60 + "\n");
                printPath(endVertex);
        }


    } //getMoreAffordablePath

}//end class


