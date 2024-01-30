public class Edge {
    private Vertex source;
    private Vertex destination;
    private int weight;
    private String subwayNo;


    public Edge(Vertex source, Vertex destination, int weight, String subwayNo) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.subwayNo = subwayNo;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSubwayNo() {
        return subwayNo;
    }

    public void setSubwayNo(String subwayNo) {
        this.subwayNo = subwayNo;
    }

}
