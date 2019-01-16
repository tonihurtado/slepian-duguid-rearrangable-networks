package switching;

class matrix {
    //////////////////////////////////////////////////////////////////
    // Class matrix:
    //
    // This is a 2 dimensional real matrix class.  Objects of this
    // type are instantiated as e.g.
    //
    // matrix A = matrix(3, 2);
    //
    // in which case, A is a matrix with 3 rows and 2 columns of
    // doubles. The matrix elements are numerated from 1, i.e. the top
    // left element is (1, 1) and the bottom right is (3, 2) in the
    // example.  The class implements a number of methods:
    //
    // rows()
    //   Return the number of rows in A as an integer
    //
    // cols()
    //   Return the number of cols in A as an integer
    //
    // set(i, j, v)
    //   Store the double value v at row i and column j
    //
    // get(i, j)
    //   Return the value double at row i and column j
    //
    // println()
    //   Pretty-print the matrix' values on stdout ending with a
    //   new-line.
    //
    // transpose()
    //   Return a copy of the matrix transposed
    //
    // add(B)
    //   This function has 2 uses depending on the type of B:
    //     . When B is a matrix, a new matrix is returned wich is
    //       element-wise additions of this and B
    //     . When B is a double, a new matrix is returned which is the
    //       element-wise addition of this with the same value, B.
    //
    // mul(v)
    //   Return a copy of the matrix, where each element has been
    //   multiplied by v
    //
    // setIdentity()
    //   Modify the matrix to be 1 along the diagonal and 0 elsewhere
    //
    // setConstant(v)
    //   Modify the matrix to set every element to the value v
    //
    // concatenateRows(B)
    //   Return a new matrix wich is a concatenation of this with
    //   matrix B along the colums, i.e., [this | B]
    //
    // subMatrix(from_row, from_col, to_row, to_col)
    //   Return a new matrix copied from the rows and colums from
    //   this, i.e., this(from_row..to_row, from_col..to_col)
    //
    // swapRows(int a, int b)
    //   Return a new matrix as a copy of this but where row a and b
    //   are interchanged
    //
    // addMulRows(a, b, c, v)
    //   Return a new matrix as a copy of this but where row a has
    //   been replaced by row b plus v times row c, i.e.,
    //   A(a,:)=A(b,:)+v*A(c,:)
    //
    // deleteCol(int a)
    //   Return a new matrix as a copy of this except column a
    //
    //                              Author: Jon Sporring, 2013-10-5
    //                              Version: 1.0
    //////////////////////////////////////////////////////////////////

    String[][] val ;

    public matrix(int m, int n) {
        // Constructor

        if ((m < 1) || (n < 1)) {
            System.out.println("Cannot create a matrix smaller that 1x1");
            System.exit(0);
        }

        val = new String[m][n];
    }

    public int rows() {
        // Return the number of rows in the matrix.

        return val.length;
    }

    public int cols() {
        // Return the number of columns in the matrix.

        return val[0].length;
    }

    public void set(int m, int n, String v) {
        // Set value at row m and column n to be v.

        if ((m < 0) || (m > rows()) || (n < 0) || (n > cols())) {
            System.out.println("Elements outside the matrix cannot be assigned values");

            return;
        }

        val[m - 1][n - 1] = v;
    }

    public void setter(int m, int n, String v) {
        // Set value at row m and column n to be v.

        if ((m < 0) || (m > rows()) || (n < 0) || (n > cols())) {
            System.out.println("Elements outside the matrix cannot be assigned values");

            return;
        }

        val[m - 1][n - 1] = val[m - 1][n - 1] + v;
    }

    public String get(int m, int n) {
        // Return the matrix value at row m and column n.

        if ((m < 1) || (m > rows()) || (n < 1) || (n > cols())) {
            System.out.println("Elements outside the matrix cannot be accessed");
        }

        return val[m - 1][n - 1];
    }


    public void println() {
        // Pretty-print the matrix to stdout.

        String v;

        for (int i = 1; i <= rows(); i++) {
            System.out.format("[");
            for (int j = 1; j <= cols(); j++) {
                if (j > 1)
                    System.out.format(" ");
                v = get(i, j);
                System.out.format("%s", v);
            }
            System.out.format("]\n");
        }
    }


    public void printMatrix(matrix m){
        try{
            int rows = m.rows() ;
            int columns = m.cols();
            String str = "|\t";

            for(int i=1;i<=rows;i++){
                for(int j=1;j<=columns;j++){

                    if (m.get(i,j)==null){m.set(i,j,"-");}

                    str += m.get(i,j) + "\t";
                }

                System.out.println(str + "|");
                str = "|\t";
            }

        }catch(Exception e){System.out.println("Matrix is empty!!");}
    }

    public String[] getRoC(int n, boolean RoC) {

        int p;

        if (RoC == false){ p = cols();}else{ p=rows();}

        String[] result = new String[p];
        for (int i = 0; i < p ; i++)

            if (RoC == false) {
                result[i] = get(n, i+1);
            } else {
                result[i] = get(i+1, n);
            }

        return result;
    }
}
