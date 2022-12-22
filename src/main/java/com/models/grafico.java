package main.java.com.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class grafico {

    private static final String[] VERTEX_LIST = {"A", "B", "C", "D", "E"};
    private final List<nodo>[] adj;
    private List<String> allPath;
    private int to;
    private int maxDistance;
    private int stops;
    private int routesCount;
    private int tripsCount;

    private grafico(int n) {
        if (n < 0) throw new IllegalArgumentException("El número de vértices en un gráfico debe ser positivo");
        adj = (LinkedList<nodo>[]) new LinkedList[n];
        for (int i = 0; i < n; i++)
            adj[i] = new LinkedList<>();
    }

    public grafico(String inputGraph) {
        this(VERTEX_LIST.length);
        initializeGraph(inputGraph);
    }

    private void initializeGraph(String inputGraph){
        String[] inputArr = inputGraph.split(",");
        for (String s : inputArr) {
            s = s.trim();
            int from = getIndex(s.substring(0, 1));
            int to = getIndex(s.substring(1, 2));
            int weight = Integer.parseInt(s.substring(2));
            nodo e = new nodo(from, to, weight);
            addGrafico(e);
        }
    }

    private void addGrafico(nodo e) {
        int v = e.from();
        adj[v].add(e);
    }

    private List<nodo> adj(int v) {
        if (v < 0 || v >= VERTEX_LIST.length) throw new IndexOutOfBoundsException("el vertice " + v + " no existe");
        return adj[v];
    }

    public String displayDistance(String route){
        int distance = calculateDistance(route);
        return (distance != -1) ? String.valueOf(distance) : "no existe la ruta";
    }

    private int calculateDistance(String route){
        if(route == null) throw new IllegalArgumentException("ruta dañada");
        int distance = 0;
        String[] vertex = route.trim().split("");
        int from, to;

        for (int i = 0; i < vertex.length-1;) {
            boolean hasPath = false;
            from = getIndex(vertex[i++]);
            to = getIndex(vertex[i]);
            List<nodo> nodoList = adj(from);
            for (nodo nodo : nodoList)
                if (nodo.to() == to) {
                    distance += nodo.weight();
                    hasPath = true;
                    break;
                }
            if(!hasPath) return -1;
        }

        return distance;
    }


    public int calculateTripsCount(String from, String to, Predicate<Integer> p, int stops){
        this.to = getIndex(to);
        this.stops = stops;
        this.tripsCount = 0;
        int startIndex = getIndex(from);
        calculateTripsCount(startIndex, String.valueOf(startIndex), p);

        return tripsCount;
    }

    private void calculateTripsCount(int from, String path, Predicate<Integer> p) {
        List<nodo> nodos = adj(from);
        for (nodo e: nodos) {

            String next = path + e.to();
            int stopCount = next.length()-1;

            if (this.to == e.to() && p.test(stopCount))
                tripsCount++;

            if(stopCount <= stops)
                calculateTripsCount(e.to(), next, p);
        }
    }


    public int calculateShortestPath(String from, String to){
        allPath = new ArrayList<>();
        this.to = getIndex(to);
        int startIndex = getIndex(from);
        calculateShortestPath(startIndex, String.valueOf(startIndex));

        int shortestDistance = Integer.MAX_VALUE, currentDistance;
        for(String s: allPath){
            currentDistance = calculateDistance(s);
            if(shortestDistance > currentDistance)
                shortestDistance = currentDistance;
        }

        if(shortestDistance == Integer.MAX_VALUE) return 0;

        return shortestDistance;
    }

    private void calculateShortestPath(int from, String path) {
        List<nodo> nodos = adj(from);
        for (nodo e: nodos) {

            if (path.length()>1 && path.substring(1).contains(String.valueOf(e.to())))
                continue;

            String next = path + e.to();

            if (this.to == e.to())
                allPath.add(getPathName(next));

            calculateShortestPath(e.to(), next);
        }
    }


    public int calculateRoutesCount(String from, String to, int maxDistance){
        this.to = getIndex(to);
        this.maxDistance = maxDistance;
        this.routesCount = 0;
        int startIndex = getIndex(from);
        calculateRoutesCount(startIndex, String.valueOf(startIndex));

        return routesCount;
    }

    private void calculateRoutesCount(int from, String path) {
        List<nodo> nodos = adj(from);
        for (nodo e: nodos) {

            String next = path + e.to();
            int distance = calculateDistance(getPathName(next));

            if (this.to == e.to() && (distance < maxDistance))
                routesCount++;

            if(distance < maxDistance)
                calculateRoutesCount(e.to(), next);
        }
    }


    private static int getIndex(String vertex) {
        int index = Arrays.binarySearch(VERTEX_LIST, vertex);
        if (index < 0)
            throw new IllegalArgumentException("Entrada incorrecta");

        return index;
    }

    private String getVertexName(int index) {
        if (index < 0 || index >= VERTEX_LIST.length)
            throw new IllegalArgumentException("Índice incorrecto");

        return VERTEX_LIST[index];
    }

    private String getPathName(String path){
        String arr[] = path.trim().split("");
        String name = "";
        for(String v: arr)
            name += getVertexName(Integer.parseInt(v));

        return name;
    }
}
