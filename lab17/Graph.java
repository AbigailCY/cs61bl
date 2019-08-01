import java.util.*;

public class Graph implements Iterable<Integer> {

    private LinkedList<Edge>[] adjLists;
    private int vertexCount;

    /* Initializes a graph with NUMVERTICES vertices and no Edges. */
    public Graph(int numVertices) {
        adjLists = (LinkedList<Edge>[]) new LinkedList[numVertices];
        for (int k = 0; k < numVertices; k++) {
            adjLists[k] = new LinkedList<Edge>();
        }
        vertexCount = numVertices;
    }

    /* Adds a directed Edge (V1, V2) to the graph. */
    public void addEdge(int v1, int v2) {
        addEdge(v1, v2, 0);
    }

    /* Adds an undirected Edge (V1, V2) to the graph. */
    public void addUndirectedEdge(int v1, int v2) {
        addUndirectedEdge(v1, v2, 0);
    }

    /* Adds a directed Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addEdge(int v1, int v2, int weight) {
        LinkedList<Edge> me = adjLists[v1];
        for (int i = 0; i < me.size(); i++) {
            if (me.get(i).to == v2) {
                me.get(i).weight = weight;
                return;
            }
        }
        me.add(new Edge(v1, v2, weight));
    }

    /* Adds an undirected Edge (V1, V2) to the graph with weight WEIGHT. If the
       Edge already exists, replaces the current Edge with a new Edge with
       weight WEIGHT. */
    public void addUndirectedEdge(int v1, int v2, int weight) {
        addEdge(v1, v2, weight);
        addEdge(v2, v1, weight);
    }

    /* Returns true if there exists an Edge from vertex FROM to vertex TO.
       Returns false otherwise. */
    public boolean isAdjacent(int from, int to) {
        LinkedList<Edge> me = adjLists[from];
        for (int i = 0; i < me.size(); i++) {
            if (me.get(i).to == to) {
                return true;
            }
        }
        return false;
    }

    /* Returns a list of all the vertices u such that the Edge (V, u)
       exists in the graph. */
    public List<Integer> neighbors(int v) {
        if (v < 0 || v >= adjLists.length || adjLists[v].isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> me = new ArrayList<>(adjLists[v].size());
        for (Edge myEdge : adjLists[v]) {
            me.add(myEdge.to);
        }
        return me;
    }
    /* Returns the number of incoming Edges for vertex V. */
    public int inDegree(int v) {
        int degree = 0;
        for (LinkedList<Edge> myList : adjLists) {
            for (Edge myEdge : myList) {
                if (myEdge.to == v) {
                    degree += 1;
                }
            }
        }
        return degree;
    }

    /* Returns an Iterator that outputs the vertices of the graph in topological
       sorted order. */
    public Iterator<Integer> iterator() {
        return new TopologicalIterator();
    }

    /* A class that iterates through the vertices of this graph, starting with
       vertex START. If the iteration from START has no path from START to some
       vertex v, then the iteration will not include v. */
    private class DFSIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;
        private HashSet<Integer> visited;

        DFSIterator(int start) {
            fringe = new Stack<>();
            fringe.push(start);
            visited = new HashSet<>();
        }

        public boolean hasNext() {
            if (fringe.empty()) {
                return false;
            }
//            not empty; front not visit, return true, else keep looking
            int visit = 0;
            for (int me : fringe) {
                if (visited.contains(me)) {
                    visit += 1;
                }
            }
            return !(visit == fringe.size());
        }

        public Integer next() {
            if (hasNext()) {
                int toReturn = fringe.pop();
                while (visited.contains(toReturn)) {
                    toReturn = fringe.pop();
                }
                fringe.addAll(neighbors(toReturn));
                visited.add(toReturn);
                return toReturn;
            }
            return null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    /* Returns the collected result of performing a depth-first search on this
       graph's vertices starting from V. */
    public List<Integer> dfs(int v) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new DFSIterator(v);
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /* Returns true iff there exists a path from START to STOP. Assumes both
       START and STOP are in this graph. If START == STOP, returns true. */
    public boolean pathExists(int start, int stop) {
        if (start < 0 || start >= adjLists.length) {
            return false;
        }
        return dfs(start).contains(stop);
    }


    /* Returns the path from START to STOP. If no path exists, returns an empty
       List. If START == STOP, returns a List with START. */
    public List<Integer> path(int start, int stop) {

        ArrayList<Integer> result = new ArrayList<>();
        if (start == stop) {
            result.add(start);
        } else {
            if (!pathExists(start, stop)) {
                return new ArrayList<>();
            }
            List<Integer> temp = dfs(start);
            temp = temp.subList(0, temp.indexOf(stop) + 1);

            result.add(stop);
            for (int index = temp.size() - 2; index >= 0; index --) {
                if (isAdjacent(temp.get(index), result.get(0))) {
                    result.add(0, temp.get(index));
                    if (result.get(0) == start) {
                        break;
                    }
                }

            }
        }
        return result;
    }


//    public Edge getEdge(int u, int v) {
//        LinkedList<Edge> me = adjLists[u];
//        for (int i = 0; i < me.size(); i++) {
//            if (me.get(i).to == v) {
//                return me.get(i);
//            }
//        }
//        return null;
//    }

    public List<Integer> shortestPath(int start, int stop) {

        Double[] distTo = new Double[adjLists.length];
        Integer[] prev = new Integer[adjLists.length];
        HashSet<Integer> visited = new HashSet<>();
        PriorityQueue<Integer> myQueue = new PriorityQueue<>((v1, v2) -> Double.compare(distTo[v1], distTo[v2]));

        for (int i = 0; i < adjLists.length; i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
            prev[i] = null;
        }
        distTo[start] = 0.0;

        for (int v = 0; v < adjLists.length; v++) {
            myQueue.add(v);
        }

        while (!myQueue.isEmpty()) {
            int u = myQueue.poll();
            visited.add(u);
            if (u == stop) {
                break;
            }
            for (Edge thisEdge : adjLists[u]) {
                int v = thisEdge.to;
                if (!visited.contains(v) && distTo[v] > distTo[u] + thisEdge.weight) {
                    distTo[v] = distTo[u] + thisEdge.weight;
                    prev[v] = u;
                    myQueue.remove(thisEdge.to);
                    myQueue.add(thisEdge.to);
                }
            }
        }


        List<Integer> toReturn = new ArrayList<>();
        toReturn.add(stop);
        while (prev[toReturn.get(0)] != start) {
            toReturn.add(0, prev[toReturn.get(0)]);
        }
        toReturn.add(0, start);

        return toReturn;
    }





    public List<Integer> topologicalSort() {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Iterator<Integer> iter = new TopologicalIterator();
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    private class TopologicalIterator implements Iterator<Integer> {

        private Stack<Integer> fringe;

        // TODO: Instance variables here!

        TopologicalIterator() {
            fringe = new Stack<Integer>();
            // TODO: YOUR CODE HERE
        }

        public boolean hasNext() {
            // TODO: YOUR CODE HERE
            return false;
        }

        public Integer next() {
            // TODO: YOUR CODE HERE
            return 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    private class Edge {

        private int from;
        private int to;
        private int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public String toString() {
            return "(" + from + ", " + to + ", weight = " + weight + ")";
        }

    }

    private void generateG1() {
        addEdge(0, 1, 1);
        addEdge(0, 3, 2);
        addEdge(1, 2, 3);
        addEdge(2, 5, 2);
        addEdge(2, 6, 4);
        addEdge(5, 6, 1);
        addEdge(3, 4, 3);
        addEdge(4, 6, 3);
    }

    private void generateG2() {
        addEdge(0, 1);
        addEdge(0, 2);
        addEdge(0, 4);
        addEdge(1, 2);
        addEdge(2, 3);
        addEdge(4, 3);
    }

    private void generateG3() {
        addUndirectedEdge(0, 1);
        addUndirectedEdge(0, 2);
        addUndirectedEdge(1, 3);
        addUndirectedEdge(1, 4);
        addUndirectedEdge(2, 5);
        addUndirectedEdge(2, 6);
    }

    private void generateG4() {
        addEdge(0, 1);
        addEdge(1, 2);
        addEdge(2, 0);
        addEdge(2, 3);
        addEdge(4, 2);
    }

    private void printDFS(int start) {
        System.out.println("DFS traversal starting at " + start);
        List<Integer> result = dfs(start);
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printPath(int start, int end) {
        System.out.println("Path from " + start + " to " + end);
        List<Integer> result = shortestPath(start, end);
        if (result.size() == 0) {
            System.out.println("No path from " + start + " to " + end);
            return;
        }
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
        System.out.println();
        System.out.println();
    }

    private void printTopologicalSort() {
        System.out.println("Topological sort");
        List<Integer> result = topologicalSort();
        Iterator<Integer> iter = result.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next() + " ");
        }
    }

    public static void main(String[] args) {

        Graph g1 = new Graph(7);
        g1.generateG1();

        g1.printPath(0, 2);
        g1.printPath(0, 3);
        g1.printPath(0, 4);
        g1.printPath(0, 5);
        g1.printPath(0, 6);

//        Graph g2 = new Graph(5);
//        g2.generateG2();
//        g2.printTopologicalSort();
    }
}
