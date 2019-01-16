package switching;

//import javax.naming.SizeLimitExceededException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
//import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Created by Jose Antonio on 21/06/2017.
 */
 class PaulMatrix  extends Main {

     //MAIN FUNCTION OF THE ALGORITHM

    public void randomConnections(matrix A, String[] connections) {
    	
    	
        int counter = 0;
        String v = "";
        int failed = 0;
        int numberRearrange = 0;
        //String cdn;
        long totalTime, initTime;
        int iterations;
        boolean policy = false;
        Scanner keyboard = new Scanner(System.in);
        XYSeries series = new XYSeries("Graph number one");
        XYSeries serie2 = new XYSeries("Graph number two");
        
        try{
	        System.out.println("enter the number of iterations: ");
	        iterations = keyboard.nextInt();
            System.out.println("Enter true for the first policy and false for the second: ");
            policy = keyboard.nextBoolean();  
            
            initTime = System.currentTimeMillis();
	        while ( counter < iterations ){
	
	            int i = ThreadLocalRandom.current().nextInt(1, A.rows() + 1 );
	            int j = ThreadLocalRandom.current().nextInt(1, A.cols() + 1);
	            String[] missingRow;
	            String[] missingCol;
	            String[] free = new String[connections.length];
	            String[] rearrangeRow = new String[connections.length];
	            String[] rearrangeCol = new String[connections.length];
	
	            missingRow=getMissing(A,connections,i,false);
	            missingCol=getMissing(A,connections,j,true);
	
	
	            classifier(missingRow,missingCol,free,rearrangeRow,rearrangeCol);
	
	            System.out.print("\nANALISIS\n");
	            System.out.print("\ncoordenadas\n");
	            System.out.print(i+","+j+"\n\n");
	            System.out.print("\nfree on both\n");
	            System.out.print(Arrays.toString(free));
	            System.out.print("\nfree on row\n");
	            System.out.print(Arrays.toString(rearrangeRow));
	            System.out.print("\nfree on column\n");
	            System.out.print(Arrays.toString(rearrangeCol));
	
	            System.out.print("\n\n");
	
	
	            String[] free2 = new String[connections.length];
	
	            boolean empty = false; boolean impossible = false;
	
	
	            
	            if (free[0] == null) {
	                empty=true;
	                if (rearrangeRow[0]==null || rearrangeCol[0]==null){
	                    System.out.print("Is not possible to set up the connection");
	                    failed++;
	                    empty=false;impossible=true;
	                }
	            }
	
	
	            if (empty){
	
	                SlepianDuguid(A,i,j,rearrangeRow,rearrangeCol,policy);
	                numberRearrange++;
	                
	            }
	
	            else if (!impossible){
	
	                int l=0;
	              	
	                for (int g=0; g<free.length ; g++){
	                    if(free[g]!=null){
	                        free2[l]=free[g];
	                        l++;
	                    }
	                }
	
	                int k = ThreadLocalRandom.current().nextInt(0, l );
	                v =  free2[k];
	
	                if (A.get(i,j)==null || A.get(i,j)=="-"){A.set(i,j,v);}
	                else{A.setter(i,j,v);}
	            }
	
	            counter++;
	            series.add(counter,numberRearrange);
	            
	            System.out.print("\nTOTAL NUMBER OF FAILED CONNECTIONS :");
	            System.out.print(failed);
	            System.out.print("\nTOTAL NUMBER OF REARRANGED CONNECTIONS :");
	            System.out.print(numberRearrange);
	            
	            System.out.print("\n\n\n->PAUL MATRIX iteration " + counter + "\n\n");
	            A.printMatrix(A);
	            
	            totalTime = System.currentTimeMillis() - initTime;
	            serie2.add(counter,totalTime);
	            
		      	System.out.println("Computation time:" + totalTime + " miliseconds");
	
	        }
	       
	        XYSeriesCollection dataset = new XYSeriesCollection();
	      	dataset.addSeries(series);
	        
	      	XYSeriesCollection dataset2 = new XYSeriesCollection();
	      	dataset2.addSeries(serie2);
	      	
	      	JFreeChart chart = ChartFactory.createXYLineChart("Statistics with policy "+ policy, "No of simulations" , "Rearragments",dataset, PlotOrientation.VERTICAL,true, false, false);
	      	JFreeChart chart2 = ChartFactory.createXYLineChart("Statistics with policy "+ policy, "No of simulations" , "Computation time",dataset2, PlotOrientation.VERTICAL,true, false, false);

	      	ChartFrame frame = new ChartFrame("Graphic 1", chart);
	      	frame.pack();
	      	frame.setVisible(true);
	      	
	      	ChartFrame frame2 = new ChartFrame("Graphic 2", chart2);
	      	frame2.pack();
	      	frame2.setVisible(true);
	      	
        }catch (InputMismatchException e) {
            System.out.println("Invalid input!");
        }
      	keyboard.close();
    }

    //SECONDARY FUNCTIONS

    public String[] getMissing(matrix A, String[] connections, int v, boolean RoC){



        String[] missing = new String[connections.length];

        String[] vector ;
        boolean container = false;

        vector = A.getRoC(v,RoC);


        for (int i = 0; i < vector.length ; i++) {
            for (int q = 0; vector[i]!=null && q < vector[i].length(); q++) {
                for (int j = 0; j < connections.length; j++) {

                    if (vector[i] != null) {
                        container = vector[i].substring(q).contains(connections[j]);
                    }

                    if (container) {
                        missing[j] = connections[j];
                    }

                    container = false;
                }
            }
        }

            String[] real = new String[connections.length];

        for (int r = 0; r < connections.length  ; r++) {

            if (missing[r]==null) {
                real[r] = connections[r];
            }
        }
        return real;
    }

    public void classifier( String[] row ,String[] col, String[] free,String[] rearrangeRow, String[] rearrangeCol){


        //////////////////////////////////////////////////////////////////////////////////
        /////////////  THIS FUNCTION RECIEVES 5 ARRAYS, BOTH ROW AND  ////////////////////
        /////////////  COL WITH THE MISSING VALUES FOR EACH ROW/COL   ////////////////////
        /////////////  AND FREE WILL INCLUDE THE VALUES THAT IS PO-  /////////////////////
        /////////////  SSIBLE TO MAKE A CONNECTION WITHOUT REARRAG    ////////////////////
        /////////////  AND IN REARRANGE THE R2 MATRIX WHERE IF NECCE  ////////////////////
        /////////////  SSARI A REARRANGEMENT IF WE WANT TO MAKE A NEW  ///////////////////
        /////////////  CONNECTION.                                    ////////////////////
        //////////////////////////////////////////////////////////////////////////////////

        int k=0; int l= 0; int z=0;
        int length = row.length;

        for (int i = 0; i<length ; i++){

            if (row[i]==col[i] && row[i]!=null) {

                    free[k]=row[i];
                    k++;

            } else if (row [i]!=col[i]){

                if (row[i]!=null) {

                    rearrangeRow[l] = row[i];
                    l++;
                }else {

                    rearrangeCol[z] = col[i];
                    z++;
                }

            }
        }
    }

    public void SlepianDuguid(matrix A, int coor1 , int coor2 ,String[] rearrangeRow ,String[] rearrangeCol ,boolean policy) {


        if (policy) {

            System.out.print("\n EH1! \n");

            String[] pair = new String[2];
            int threshold = A.cols() + A.rows();
            String[] Chain = new String[threshold];
            int[] PositionRow = new int[threshold];
            int[] PositionCol = new int[threshold];
            boolean betterSolution = true;

            while (betterSolution){

                Arrays.fill(Chain, null);
                Arrays.fill(PositionCol, 0);
                Arrays.fill(PositionRow,0);

                pair = getPair(rearrangeRow, rearrangeCol);

                int i = ThreadLocalRandom.current().nextInt(0, 2);

                int countCon = 0;
                int countPos = 0;
                int coor;
                int coor0;
                boolean RoC;
                int size;
                boolean container = false;
                if (i == 0) {
                    coor0 = coor1;
                    coor = coor2;
                } else {
                    coor0 = coor2;
                    coor = coor1;
                }


                Chain[countCon] = pair[i];
                countCon++;
                PositionRow[countPos] = coor1;
                PositionCol[countPos] = coor2;
                countPos++;

                boolean next = true;


                String connection = pair[i];

                int bestSolution = Math.min(A.rows(),A.cols())-1;

                while (next) {


                    if (i % 2 == 0) {
                        RoC = true;
                        size = A.rows();
                    } else {
                        RoC = false;
                        size = A.cols();
                    }

                    String[] vector;
                    String content;
                    vector = A.getRoC(coor, RoC);


                    System.out.print(Arrays.toString(vector));
                    System.out.print("\n");

                    System.out.print(connection);

                    boolean stop = false;

                    for (int k = 0; k < size && !stop; k++) {

                        content = vector[k];
                        container = false;

                        container = content.contains(connection);

                        if (container) {

                            if (i % 2 == 0) {
                                PositionRow[countPos] = k + 1;
                                PositionCol[countPos] = PositionCol[countPos - 1];
                                countPos++;
                            } else {
                                PositionCol[countPos] = k + 1;
                                PositionRow[countPos] = PositionRow[countPos - 1];
                                countPos++;
                            }

                            i++;

                            coor = k + 1;
                            connection = pair[i % 2];
                            Chain[countCon] = connection;
                            countCon++;
                            stop = true;


                        } else if (k == size - 1 && !container) {

                            next = false;
                        }
                    }
                    System.out.print("\n\nCHAIN\n\n");
                    System.out.print(Arrays.toString(Chain));
                    System.out.print("\n");
                    System.out.print(Arrays.toString(PositionRow));
                    System.out.print(Arrays.toString(PositionCol));
                    System.out.print("\n\n");



                }
                if(countCon<=bestSolution){

                    betterSolution = false;


                    System.out.print("\n\nBEST SOLUTION!\n\n");
                }
            }

                for (int z=Chain.length-1; z>=0; z--){

                    String patch;

                    if (Chain[z]!=null) {


                        if (pair[0] == Chain[z]) {
                            patch = pair[1];
                        } else {
                            patch = pair[0];
                        }

                        String remove = A.get(PositionRow[z], PositionCol[z]);
                        remove = remove.replace("-", "");
                        remove = remove.replace(patch, "");

                        A.set(PositionRow[z], PositionCol[z], remove + Chain[z]);

                    }
                }
                

        } else {

            System.out.print("\n EH! \n");

            String[] pair;

            pair = getPair(rearrangeRow, rearrangeCol);
            int threshold =  A.cols() + A.rows() ;

            String[] Chain = new String[threshold];
            int[] PositionRow = new int[threshold];
            int[] PositionCol = new int[threshold];


            int i = ThreadLocalRandom.current().nextInt(0, 2);

            int countCon = 0;
            int countPos = 0;
            int coor;
            int coor0;
            boolean RoC;
            int size;
            boolean container = false;
            int inversePath= i%2;
            
            if (i == 0) {
                coor0 = coor1;
                coor = coor2;
            } else {
                coor0 = coor2;
                coor = coor1;
            }

            Chain[countCon] = pair[i];
            countCon++;
            PositionRow[countPos] = coor1;
            PositionCol[countPos] = coor2;
            countPos++;

            boolean next = true;
            
            String connection = pair[i];

            //Bucle
            while (next) {


                if (i%2 == 0) {
                    RoC = true;
                    size = A.rows();
                } else {
                    RoC = false;
                    size = A.cols();
                }

                String[] vector;
                String content;
                vector = A.getRoC(coor, RoC);


                System.out.print(Arrays.toString(vector));
                System.out.print("\n");

                System.out.print(connection);

                boolean stop= false;

                for (int k=0;k<size && !stop;k++){

                    content=vector[k];
                    container=false;

                        container = content.contains(connection);

                        if (container){

                            if (i%2 == 0){
                                PositionRow[countPos]=k+1;
                                PositionCol[countPos] = PositionCol[countPos-1];
                                countPos++;
                            }else{
                                PositionCol[countPos]=k+1;
                                PositionRow[countPos] = PositionRow[countPos-1];
                                countPos++;
                            }

                            i++;

                            coor=k+1;
                            connection = pair[inversePath];
                            Chain[countCon]=connection;countCon++;
                            stop=true;


                        }else if (k==size-1 && !container){

                            next=false;
                        }
                }
                System.out.print("\n\nCHAIN\n\n");
                System.out.print(Arrays.toString(Chain));
                System.out.print("\n");
                System.out.print(Arrays.toString(PositionRow));
                System.out.print(Arrays.toString(PositionCol));
                System.out.print("\n\n");

            }

           for (int z=Chain.length-1; z>=0; z--){

                String patch;

                if (Chain[z]!=null) {


                    if (pair[0] == Chain[z]) {
                        patch = pair[1];
                    } else {
                        patch = pair[0];
                    }

                    String remove = A.get(PositionRow[z], PositionCol[z]);
                    remove = remove.replace("-", "");
                    remove = remove.replace(patch, "");

                    A.set(PositionRow[z], PositionCol[z], remove + Chain[z]);

                }
          }
        }
    }

    public String[] getPair (String[] rearrangeRow, String[] rearrangeCol){

        //SELECT A RANDOM PAIR FROM OF FREE R2 MATRIX, ONE SYMBOL MISSING IN THE ROW, AND ONE IN THE COLUMN

        String[] pair = new String[2];

        int l=0;

        for (int g=0; g<rearrangeRow.length ; g++){
            if(rearrangeRow[g]!=null){
                l++;
            }
        }
        System.out.print(l);
        int c=0;

        for (int g=0; g<rearrangeCol.length ; g++){
            if(rearrangeCol[g]!=null){
                c++;
            }
        }
        System.out.print(c);

        int x = ThreadLocalRandom.current().nextInt(0, l);
        int k = ThreadLocalRandom.current().nextInt(0, c);

        pair[0]= rearrangeRow[x];
        pair[1]= rearrangeCol[k];

        return pair;
    }

 }
