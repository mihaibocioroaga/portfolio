import java.util.*;

public class Main {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    public static void main(String[] args)  {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        Scanner scan = new Scanner(System.in);
        int simulations = Integer.parseInt(scan.nextLine());

        //Fill canvas based on user input
        for (int i = 0; i < WIDTH; i++) {
            String row = scan.nextLine();
            for (int j = 0; j < HEIGHT; j++) {
                canvas.createCell(i, j, row.charAt(j));
            }
        }

        while(simulations != 0) {
            if(simulations == 1)
                System.out.println(canvas.nextState());
            else
                canvas.nextState();
            simulations--;
        }
    }
}

class Canvas{
    private int width;
    private int height;
    private Cell[][] grid;

    /**
     * Creates a blank Canvas.
     * @param width of canvas.
     * @param height of canvas.
     */
    public Canvas(int width, int height){
        this.width = width;
        this.height = height;
        grid = new Cell[width][height];
    }

    /**
     * Creates a Cell on the Canvas.
     * @param x coordinate on Canvas
     * @param y coordinate on Canvas
     * @param val of Cell, expressed as a char, either '0' or '1'.
     */
    public void createCell(int x, int y, char val){
        grid[x][y] = new Cell( Integer.parseInt(val + "") );
    }

    /**
     * Computes and queues a Cell's next state based on its neighbouring Cells.
     * @param x coordinate on canvas of Cell to queue
     * @param y coordinate on canvas of Cell to queue
     */
    private void computeCellState(int x, int y){
        int neighboursAlive = 0;
        //Manual definition of neighbouring cells calculation.
        //If-statements are used for bounds detection.
        if (x > 0)
            neighboursAlive += grid[x-1][y].getState();
        if (x < width - 1)
            neighboursAlive += grid[x+1][y].getState();

        if (y > 0) {
            neighboursAlive += grid[x][y - 1].getState();
            if (x > 0)
                neighboursAlive += grid[x - 1][y - 1].getState();
            if (x < width - 1)
                neighboursAlive += grid[x + 1][y - 1].getState();
        }

        if (y < height - 1) {
            neighboursAlive += grid[x][y + 1].getState();
            if (x > 0)
                neighboursAlive += grid[x - 1][y + 1].getState();
            if (x < width - 1)
                neighboursAlive += grid[x + 1][y + 1].getState();
        }

        /*
            Any live cell with two or three live neighbors survives.
            Any dead cell with three live neighbors becomes a live cell.
            All other live cells die in the next generation. Similarly, all other dead cells stay dead.
         */
        if (grid[x][y].getState() == 1){
            if((neighboursAlive == 2 || neighboursAlive == 3))
                grid[x][y].queueState(1);
            else
                grid[x][y].queueState(0);
        }
        else if (grid[x][y].getState() == 0){
            if(neighboursAlive == 3)
                grid[x][y].queueState(1);
            else
                grid[x][y].queueState(0);
        }
    }

    /**
     * Calculates and applies the queued state of each Cell, then prints the canvas.
     */
    public int nextState(){
        //First pass calculates the next Canvas state non-disruptively by queueing Cell states.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++)
                computeCellState(i, j);
        }

        //Second pass applies the calculation to the Cells and displays the new Canvas state.
        int liveCellCount = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(grid[i][j].updateState() == 1)
                    liveCellCount++;
            }
        }
        return liveCellCount;
    }
}

class Cell{
    private int state;
    private int queuedState;

    public Cell(int state){ this.state = state; }

    /**
     * Prepares the Cell to be switched to the next state of the Canvas by queueing its next state.
     * @param state the state to switch to on the next simulation.
     */
    public void queueState(int state) { this.queuedState = state; }

    /**
     * Updates the Cell's stored state so it may run successive simulations.
     * @return int current cell's state after update.
     */
    public int updateState(){
        this.state = queuedState;
        return state;
    }

    public int getState(){
        return state;
    }
}