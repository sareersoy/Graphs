
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph {
    private final List<Vertex> vertexes;
    private final List<Edge> edges;
    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;}
    public List<Vertex> getVertexes() {
        return vertexes;
    }
    public List<Edge> getEdges() {
        return edges;
    }
}
    class Vertex {
        final private String id;
        final private String name;
        public Router router;
        public Vertex(String id, String name, Router r) {
            this.id = id;
            this.name = name;
            this.router=r;}
        public String getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;}
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertex other = (Vertex) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;}
        @Override
        public String toString() {
            return name;
        }
}
    class Edge  {
        private final Vertex source;
        private final Vertex destination;
        private final double weight;
        public Edge(Vertex source, Vertex destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
        public Vertex getDestination() {
            return destination;
        }
        public Vertex getSource() {
            return source;
        }
        public double getWeight() {
            return weight;
        }
        @Override
        public String toString() {
            return source + " " + destination;
        }
}

class DijkstraAlgorithm {
    private final List<Vertex> nodes;
    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Double> distance;
    public DijkstraAlgorithm(Graph graph) {
        this.nodes = new ArrayList<Vertex>(graph.getVertexes());
        this.edges = new ArrayList<Edge>(graph.getEdges());
    }
    public void execute(Vertex source) {
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Double>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);}}
    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);}}}

    private double getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException(" ");
    }
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<Vertex>();
        for (Edge edge : edges) {
            if (edge.getSource().equals(node) && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());}}
        return neighbors;}

    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;}
            else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;}}}
        return minimum;}
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }
    private double getShortestDistance(Vertex destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;}
        else {return d;}}

    public LinkedList<Vertex> getPath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        Vertex step = target;
        if (predecessors.get(step) == null) {
            return null;}
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);}
        Collections.reverse(path);
        return path;}}