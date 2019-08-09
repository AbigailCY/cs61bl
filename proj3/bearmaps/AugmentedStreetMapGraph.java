package bearmaps;

import bearmaps.utils.graph.streetmap.Node;
import bearmaps.utils.graph.streetmap.StreetMapGraph;
import bearmaps.utils.ps.KDTree;
import bearmaps.utils.ps.MyTrieSet;
import bearmaps.utils.ps.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Yuan Chen
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private HashMap<Point, Long> vertices = new HashMap<>();
    private List<Point> points = new LinkedList<>();
    private KDTree kd;
    private HashMap<String, ArrayList<Node>> names = new HashMap<>();
    private MyTrieSet trieSet = new MyTrieSet();

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = this.getNodes();
        for (Node node : nodes) {
            if (!this.neighbors(node.id()).isEmpty()) {
                vertices.put(new Point(node.lon(), node.lat()), node.id());
                points.add(new Point(node.lon(), node.lat()));
            }

            if (node.name() != null) {
                String simpleName = node.name().replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!names.keySet().contains(simpleName)) {
                    names.put(simpleName, new ArrayList<>());
                }
                names.get(simpleName).add(node);
                trieSet.add(simpleName);
            }
        }
        kd = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {

        Point target = kd.nearest(lon, lat);
        return vertices.get(target);
    }


    /**
     * For Project Part III (extra credit)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> simples = trieSet.keysWithPrefix(prefix);
        List<String> toReturn = new LinkedList<>();
        for (String simple : simples) {
            toReturn.add(names.get(simple).get(0).name());
        }
        return toReturn;
    }

    /**
     * For Project Part III (extra credit)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanName = locationName.replaceAll("[^a-zA-Z]", "").toLowerCase();
        List<Map<String, Object>> toReturn = new ArrayList<>();

        for (Node node : names.get(cleanName)) {
            Map<String, Object> nodeMap = new HashMap<>();
            nodeMap.put("lat", node.lat());
            nodeMap.put("lon", node.lon());
            nodeMap.put("name", node.name());
            nodeMap.put("id", node.id());
            toReturn.add(nodeMap);
        }

        return toReturn;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
