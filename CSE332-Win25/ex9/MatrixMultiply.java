import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class MatrixMultiply {
    static ForkJoinPool POOL = new ForkJoinPool();
    static int CUTOFF;

    // Behavior should match Sequential.multiply.
    // Ignoring the initialization of arrays, your implementation should have n^3 work and log(n) span
    public static int[][] multiply(int[][] a, int[][] b, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        int[][] product = new int[a.length][b[0].length];
        POOL.invoke(new MatrixMultiplyAction(a, b, product, 0, a.length, 0, a.length)); // TODO: add parameters to match your constructor
        return product;
    }

    // Behavior should match the 2d version of Sequential.dotProduct.
    // Your implementation must have linear work and log(n) span
    public static int dotProduct(int[][] a, int[][] b, int row, int col, int cutoff){
        MatrixMultiply.CUTOFF = cutoff;
        return POOL.invoke(new DotProductTask(a, b, row, col)); // TODO: add parameters to match your constructor
    }

    private static class MatrixMultiplyAction extends RecursiveAction{
        // TODO: select fields
        private int[][] A, B, product;
        private int left, right, top, bottom;

        public MatrixMultiplyAction(int[][] A, int[][] B, int[][] product, int left, int right, int top, int bottom){
            // TODO: implement constructor
            this.A = A;
            this.B = B;
            this.product = product;
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;

        }

        @Override
        public void compute(){
            // TODO: implement
            int width = right-left;
            int height = bottom - top;
            int area  = width * height; // element in submatrix

            // Base case (Sequential Case)
            if (area <= CUTOFF) {
                List<DotProductTask> tasks = new ArrayList<>();
                for (int i = top; i < bottom; i++) {
                    for (int j = left; j < right; j++) {
                        DotProductTask task = new DotProductTask(A, B, i, j);
                        task.fork();
                        tasks.add(task);
                    }
                }

                int index = 0;
                for (int i = top; i < bottom; i++) {
                    for (int j = left; j < right; j++) {
                        product[i][j] = tasks.get(index++).join();
                    }
                }
            } 
            // Recursive Case (Parallel/Forking case)
            else {
                // divide submatrix into 4 parts
                int midX = (left + right) / 2;
                int midY = (top + bottom) / 2;

                invokeAll(
                    new MatrixMultiplyAction(A, B, product, left, midX, top, midY),
                    new MatrixMultiplyAction(A, B, product, midX, right, top, midY),
                    new MatrixMultiplyAction(A, B, product, left, midX, midY, bottom),
                    new MatrixMultiplyAction(A, B, product, midX, right, midY, bottom)
                );
            }
        }
    }

    private static class DotProductTask extends RecursiveTask<Integer>{
        // TODO: select fields
        private int[][] A, B;
        private int row, col;

        public DotProductTask(int[][] A, int[][] B, int row, int col){
            // TODO: implement constructor
            this.A = A;
            this.B = B;
            this.row = row;
            this.col = col;
        }

        @Override
        public Integer compute(){
            // TODO: implement
            // dot product of row A col B
            int sum = 0;
            for (int i=0; i<A.length; i++){
                sum += A[row][i] * B[i][col];
            }
            return sum;
        }

    }
    
}
