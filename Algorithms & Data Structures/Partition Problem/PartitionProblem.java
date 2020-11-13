package cs.cs210;
import java.util.Arrays;
import java.util.Collections;

public class PartitionProblem {

    /**
     * Insert a set of ints to be partitioned into two approximately equal sets.
     *
     * The sets are split by taking the sum of all of the numbers in the set and iteratively subtracting away from
     * the smallest number in the set until a value that crosses the bound of 0 is reached and eventually goes negative.
     * Once this occurs, the method determines the two sets that are approximately equal to each other.
     *
     * @param set An int array to be evaluated.
     * @return A char array of the operators that need to be applied to the numbers inputted.
     */
    public static char[] solve(int[] set){
        char[] operators = new char[set.length];
        Arrays.fill(operators, '+');
        Arrays.sort(set);

        //Find the sum of the ints within the set.
        double totalSum = 0;
        for(int n : set) totalSum += n;
        long midpoint = Math.round(totalSum / 2);

        for(int i = 0; i < set.length; i++){
            totalSum -= set[i];
            operators[i] = '-';
            if(totalSum <= midpoint) break;
        }
        return operators;
    }
}
