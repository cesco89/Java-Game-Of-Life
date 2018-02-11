import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener{

    Timer timer = new Timer(1, this);
    private static int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
    private static int dim = 10;
    private static int cols = width / dim;
    private static int rows = height / dim;
    private ArrayList<ArrayList<Integer>> grid = new ArrayList<>();

    public GamePanel() {
        timer.start();
        setup();
    }

    private void setup() {

        System.out.println("CALLED SETUP");

        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point p = mouseEvent.getPoint();
                System.out.println("CLICKED");
                int mX = p.x/dim;
                int mY = p.y/dim;
                grid.get(mX).add(mY, 1);
            }

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
                Point p = mouseEvent.getPoint();
                System.out.println("CLICKED");
                int mX = p.x/dim;
                int mY = p.y/dim;
                grid.get(mX-1).add(mY-1, 1);
                grid.get(mX-1).add(mY, 1);
                grid.get(mX).add(mY, 1);
                grid.get(mX-1).add(mY+1, 1);

            }
        };


        grid = makeGrid(cols, rows);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid.get(i).add(j, Math.random() < 0.7 ? 1 : 0);
            }
        }

        addMouseListener(ma);
        addMouseMotionListener(ma);

    }

    private void draw(Graphics g) {

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                int x = i * dim;
                int y = j * dim;
                if (grid.get(i).get(j) == 1) {
                    g.fillRect(x, y, dim, dim);
                }
                g.drawRect(x, y, dim, dim);
            }
        }

        ArrayList<ArrayList<Integer>> next = makeGrid(cols, rows);

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                int state = grid.get(i).get(j);
                int neighbors = countNeighbors(grid, i, j);

                if (state == 0 && neighbors == 3) {
                    next.get(i).add(j, 1);
                } else if (state == 1 && (neighbors < 2 || neighbors > 3)) {
                    next.get(i).add(j, 0);
                } else {
                    next.get(i).add(j, state);
                }
            }


        }

        grid = next;
    }

    private int countNeighbors(ArrayList<ArrayList<Integer>> grid, int x, int y) {
        int sum = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                int col = (x + i + cols) % cols;
                int row = (y + j + rows) % rows;
                sum += grid.get(col).get(row);
            }
        }
        sum -= grid.get(x).get(y);
        return sum;
    }

    private ArrayList<ArrayList<Integer>> makeGrid(int _cols, int _rows) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            temp.add(i, new ArrayList<>());

        }
        return temp;
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        draw(graphics);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == timer) {
            repaint();
        }
    }
}