class SegmentTree {

    double[] tree;
    double[] lazy;
    int n;

    SegmentTree(int size) {

        n = size;

        tree = new double[4 * n];
        lazy = new double[4 * n];

        build(1, 0, n - 1);
    }

    // Build Tree
    void build(int node, int start, int end) {

        if (start == end) {

            tree[node] = 1.0;
            return;
        }

        int mid = (start + end) / 2;

        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);

        tree[node] = Math.max(tree[2 * node],
                              tree[2 * node + 1]);
    }

    // Push Lazy Updates
    void push(int node, int start, int end) {

        if (lazy[node] != 0) {

            tree[node] += lazy[node];

            if (start != end) {

                lazy[2 * node] += lazy[node];
                lazy[2 * node + 1] += lazy[node];
            }

            lazy[node] = 0;
        }
    }

    // Range Update
    void update(int node, int start, int end,
                int left, int right, double value) {

        push(node, start, end);

        // No Overlap
        if (start > right || end < left)
            return;

        // Complete Overlap
        if (start >= left && end <= right) {

            lazy[node] += value;
            push(node, start, end);
            return;
        }

        int mid = (start + end) / 2;

        update(2 * node, start, mid,
               left, right, value);

        update(2 * node + 1, mid + 1, end,
               left, right, value);

        tree[node] = Math.max(tree[2 * node],
                              tree[2 * node + 1]);
    }

    // Range Maximum Query
    double query(int node, int start, int end,
                 int left, int right) {

        push(node, start, end);

        // No Overlap
        if (start > right || end < left)
            return Double.MIN_VALUE;

        // Complete Overlap
        if (start >= left && end <= right)
            return tree[node];

        int mid = (start + end) / 2;

        double q1 = query(2 * node, start, mid,
                          left, right);

        double q2 = query(2 * node + 1,
                          mid + 1, end,
                          left, right);

        return Math.max(q1, q2);
    }

    // Print Zones
    void printZones() {

        System.out.println("\nCurrent Surge Multipliers:\n");

        for (int i = 0; i < n; i++) {

            double value = query(1, 0, n - 1,
                                 i, i);

            System.out.printf("Zone %d = %.1f\n",
                              i, value);
        }
    }
}

public class UberSurgeSystem {

    public static void main(String[] args) {

        int zones = 16;

        SegmentTree st = new SegmentTree(zones);

        System.out.println(
            "Initial Surge Multipliers:");

        st.printZones();

        // Operation 1
        // update [3,9] += 0.5
        st.update(1, 0, zones - 1,
                  3, 9, 0.5);

        // Operation 2
        // update [7,14] += 0.3
        st.update(1, 0, zones - 1,
                  7, 14, 0.3);

        // Operation 3
        // query max [0,15]
        double max1 = st.query(1, 0,
                               zones - 1,
                               0, 15);

        System.out.printf(
            "\nMaximum Surge in [0,15] = %.1f\n",
            max1);

        // Operation 4
        // update [2,6] += 0.7
        st.update(1, 0, zones - 1,
                  2, 6, 0.7);

        // Operation 5
        // query max [4,10]
        double max2 = st.query(1, 0,
                               zones - 1,
                               4, 10);

        System.out.printf(
            "\nMaximum Surge in [4,10] = %.1f\n",
            max2);

        // Final Zone Values
        st.printZones();
    }
}