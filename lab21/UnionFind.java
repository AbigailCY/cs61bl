
public class UnionFind {

    int[] array;

    /* Creates a UnionFind data structure holding N vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int N) {
        this.array = new int[N];
        for (int i = 0; i < N; i++) {
            this.array[i] = -1;
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v >= array.length) {
            return -1;
        }
        return array[v];
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid vertices are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v >= array.length) {
            throw new IllegalArgumentException();
        } else {
            int u = v;
            while (array[u] >= 0) {
                u = array[u];
            }
            return u;
        }
    }

    /* Connects two elements V1 and V2 together. V1 and V2 can be any element,
       and a union-by-size heuristic is used. If the sizes of the sets are
       equal, tie break by choosing V1 to be the new root. Union-ing a vertex
       with itself or vertices that are already connected should not change the
       structure. */
    public void union(int v1, int v2) {

        if (array[find(v1)] > array[find(v2)]) {
            array[find(v2)] += array[find(v1)];
            array[find(v1)] = find(v2);
        } else {
            array[find(v1)] += array[find(v2)];
            array[find(v2)] = find(v1);
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return Math.abs(array[find(v)]);
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (find(v1) == find(v2)) {
            return true;
        }
        return false;
    }
}
