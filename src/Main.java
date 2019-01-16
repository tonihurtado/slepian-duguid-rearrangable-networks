package switching;

import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.concurrent.ThreadLocalRandom;
//import javax.swing.JOptionPane;


public class Main {

    public static void main(String[] args) {
    	
        //String cdn;
        PaulMatrix a = new PaulMatrix();
        int r2, r1,r3;
        Scanner keyboard = new Scanner(System.in);
        
        //cdn = JOptionPane.showInputDialog("enter the number of inputs (r1): ");
        //r1 = Integer.parseInt(cdn);
        //cdn = JOptionPane.showInputDialog("enter the number of outputs (r3): ");
        try{
        System.out.println("Enter the number of inputs (r1):");
        r1 = keyboard.nextInt();
        System.out.println("Enter the number of outputs (r3): ");
        r3 = keyboard.nextInt();

        //r3 = Integer.parseInt(cdn);
        matrix A ;
        
        A = new matrix(r1,r3);
        
        
        boolean ea=true;
        

        do {

            System.out.println("Enter the number of avalible connections (r2):");
            r2 = keyboard.nextInt();
        	//cdn = JOptionPane.showInputDialog("enter the number of avalible connections (r2): ");
        	//r2 = Integer.parseInt(cdn);
            if (r2 >= Math.max(r1, r3)) {ea=false;}else{System.out.print("The number of connections should satisfy the Slepian-Duguid theorem (  r2 >= max(r1,r3)  )");
            }
        }while (ea);

        //inicializamos el array de conexiones

        String[] connections = new String[r2] ;

        int k = 0;
        for(int i = 0; i < r2 ; i++){
            char j = (char)(97 + (k++));
            connections[i] = Character.toString(j);
        }
        
        a.randomConnections(A,connections);
        
        }catch(InputMismatchException e) {
            System.out.println("Invalid input!");
        }
        keyboard.close();
    }
    
    
}
