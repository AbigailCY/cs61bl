package bearmaps.utils.graph;
import bearmaps.utils.pq.DoubleMapPQ;
import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
//    private DoubleMapPQ<Vertex> PQ;
    private MinHeapPQ<Vertex> PQ;

    private HashMap<Vertex, Double> distance;
    private HashMap<Vertex, Vertex> edge;
    private AStarGraph<Vertex> input;
    private Vertex end;
    private SolverOutcome outcome;
    private double solutionWeight;
    private List<Vertex> solution;
    private double timeSpent;
    private int numStatesExplored;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        this.input = input;
        this.end = end;
        Stopwatch sw = new Stopwatch();
        PQ = new MinHeapPQ<>();
        distance = new HashMap<>();
        edge = new HashMap<>();
        numStatesExplored = 0;
        solution = new ArrayList<>();


        PQ.insert(start, 0 + input.estimatedDistanceToGoal(start, end));
        distance.put(start, 0.0);
        edge.put(start, null);


        while (PQ.size() != 0 && !PQ.peek().equals(end) && sw.elapsedTime() < timeout) {
            Vertex p = PQ.poll();
            numStatesExplored += 1;
            List<WeightedEdge<Vertex>> neighborEdges = input.neighbors(p);
            for (WeightedEdge<Vertex> e : neighborEdges) {
                relax(e, p);
            }
        }

        if (sw.elapsedTime() >= timeout) {
            outcome = SolverOutcome.TIMEOUT;
            timeSpent = sw.elapsedTime();
            return;
        }

        if (PQ.peek().equals(end)) {
            solutionWeight = distance.get(end);
            outcome = SolverOutcome.SOLVED;
            Vertex me = end;
            while (!me.equals(start)) {
                solution.add(0, me);
                me = edge.get(me);
            }
            solution.add(0, start);
            timeSpent = sw.elapsedTime();
            return;
        }

        if (PQ.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
            timeSpent = sw.elapsedTime();
        }
    }


    private void relax(WeightedEdge<Vertex> e, Vertex p) {
        Vertex q = e.to();
        if (!distance.keySet().contains(q)) {
            distance.put(q, Double.MAX_VALUE);
        }
        double w = e.weight();
        if (distance.get(p) + w < distance.get(q)) {
            distance.put(q, distance.get(p) + w);
            edge.put(q, p);
            if (PQ.contains(q)) {
                PQ.changePriority(q, distance.get(q) + input.estimatedDistanceToGoal(q, end));
            } else {
                PQ.insert(q, distance.get(q) + input.estimatedDistanceToGoal(q, end));
            }
        }
    }


    public SolverOutcome outcome() {
        return outcome;
    }
    public List<Vertex> solution() {
        return solution;
    }
    public double solutionWeight() {
        if (outcome.equals(SolverOutcome.TIMEOUT) || outcome.equals(SolverOutcome.UNSOLVABLE)) {
            return 0;
        }
        return solutionWeight;
    }
    public int numStatesExplored() {
        return numStatesExplored;
    }
    public double explorationTime() {
        return timeSpent;
    }
}