import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FilterEmpty {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.filterEmpty
    // Ignoring the initialization of arrays, your implementation must have linear work and log(n) span
    public static String[] filterEmpty(String[] arr, int cutoff){
        FilterEmpty.CUTOFF = cutoff;
        // TODO: Implement to match the filter/pack procedure discussed in class.
        // Reminder: the main steps are:
        // 1) do a map on the arr of strings
        // 2) do prefix sum on the map result (implementation provided for you in ParallelPrefix.java)
        // 3) initialize and array whose length matches the last value in the prefix sum result
        // 4) do a map to populate that new array.
        // Step 1
        int[] mapped = new int[arr.length];
        POOL.invoke(new MapTask(arr, mapped, 0, arr.length));

        // Step 2
        int[] prefixSum = ParallelPrefix.prefixSum(mapped, cutoff);

        // Step 3
        int totalNonEmpty = prefixSum[prefixSum.length - 1];
        String[] result = new String[totalNonEmpty];

        // Step 4
        POOL.invoke(new PopulateTask(arr, mapped, prefixSum, result, 0, arr.length));

        return result;
    }

    // Parallel map task
    private static class MapTask extends RecursiveAction {
        private String[] input;
        private int[] mapped; // 1 if non-empty, 0 if empty
        private int lo;
        private int hi;

        public MapTask(String[] input, int[] mapped, int lo, int hi) {
            this.input = input;
            this.mapped = mapped;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {
            // Base case (Sequential Case)
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    mapped[i] = input[i].isEmpty() ? 0 : 1;
                }
            } 
            // Recursive Case (Parallel/Forking case)
            else {
                int mid = lo + (hi - lo) / 2;
                MapTask left = new MapTask(input, mapped, lo, mid);
                MapTask right = new MapTask(input, mapped, mid, hi);

                left.fork();
                right.compute();
                left.join();
            }
        }
    }

    // Parallel population task
    private static class PopulateTask extends RecursiveAction {
        private String[] input;
        private int[] mapped;
        private int[] prefixSum;
        private String[] result;
        private int lo;
        private int hi;

        public PopulateTask(String[] input, int[] mapped, int[] prefixSum,
                            String[] result, int lo, int hi) {
            this.input = input;
            this.mapped = mapped;
            this.prefixSum = prefixSum;
            this.result = result;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        protected void compute() {
            // Base case (Sequential Case)
            if (hi - lo <= CUTOFF) {
                for (int i = lo; i < hi; i++) {
                    if (mapped[i] == 1) {
                        int pos = prefixSum[i] - 1;
                        result[pos] = input[i];
                    }
                }
            } 
            
            // Recursive Case (Parallel/Forking case)
            else {
                int mid = lo + (hi - lo) / 2;
                PopulateTask left = new PopulateTask(input, mapped, prefixSum, result, lo, mid);
                PopulateTask right = new PopulateTask(input, mapped, prefixSum, result, mid, hi);
                left.fork();
                right.compute();
                left.join();
            }
        }
    }
}