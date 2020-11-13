package cs;
import cs.cs210.*;
import java.util.Scanner;

public class Main {

    public static void main (String[] args){
        Scanner scan = new Scanner(System.in);
        int items = scan.nextInt();         //Determine # of inputs (n)
        int[] contents = new int[items];    //Form array of size n

        for(int i=0;i<items;i++){
            contents[i]=scan.nextInt();     //Take in the set of numbers to solve for.
        }

        char[] solution = PartitionProblem.solve(contents);
        long subset1=0;
        long subset2=0;

        for(int i=0;i<items;i++){
            if(solution[i]=='-'){
                subset1+=contents[i];   //Subset 1 contains the set of all numbers to add.

            }else{
                subset2+=contents[i];   //Subset 2 contains the set of all numbers to subtract.
            }

        }
        System.out.println(Math.abs(subset1-subset2));
    }
}
