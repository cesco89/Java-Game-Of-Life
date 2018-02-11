import java.awt.*;
import java.util.ArrayList;

public class Cell {

    public int x;
    public int y;
    public int size;
    public int state;
    private ArrayList<Integer> generations;


    public Cell(int x, int y, int size, int state) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.state = state;
        this.generations = new ArrayList<>();
    }

    public void addToGenerations(int i) {
        this.generations.add(i);
    }


    public void show(Graphics g) {
        if(state == 1) {
            g.fillRect(x, y, size, size);
        }
    }

    public int countNeighbors(ArrayList<ArrayList<Cell>> grid, int cols, int rows) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x + i + cols) % cols;
                int row = (y + j + rows) % rows;
                sum += grid.get(col).get(row).state;
            }
        }
        sum -= grid.get(x/size).get(y/size).state;
        return sum;
    }

    public void changeState(ArrayList<ArrayList<Cell>> next,int neighbors, int i, int j) {

        int tempState = state;
        if (tempState == 0 && neighbors == 3) {
           this.state = 1;
        } else if (tempState == 1 && (neighbors < 2 || neighbors > 3)) {
            this.state = 0;
        }else{
            this.state = tempState;
        }
        generations.add(tempState);
        next.get(i).add(j, this);
    }


}
