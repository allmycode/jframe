import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class Labirinth extends JFrame {
    final static int LAB_WIDTH = 100;
    final static int LAB_HEIGHT = 100;
    final static int CELL_SIZE = 10;
    static Cell[][] cells = new Cell[LAB_WIDTH + 1][LAB_HEIGHT + 1];

    public Labirinth() throws HeadlessException {
        super("Lab");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(LAB_WIDTH * CELL_SIZE + 150, LAB_HEIGHT * CELL_SIZE + 150));
        JPanel p = new JPanel();

        JButton shuffle = new JButton("Shuffle");
        shuffle.addActionListener(e -> {
            randomize();
            repaint();
        });
        p.add(shuffle);
        p.add(new MyPanel());
        add(p);
        randomize();
    }

    void randomize() {
        for (int i = 0; i < LAB_WIDTH; i++) {
            for (int j = 0; j < LAB_HEIGHT; j++) {
                cells[i][j] = new Cell();
            }
        }
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < LAB_WIDTH * LAB_HEIGHT / 3 * 4; i++) {
            if (r.nextBoolean()) {
                cells[r.nextInt(LAB_WIDTH)][r.nextInt(LAB_HEIGHT-1)].bottom = false;
            } else {
                cells[r.nextInt(LAB_WIDTH-1)][r.nextInt(LAB_HEIGHT)].right = false;
            }
        }
    }

    public class MyPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            for (int i = 1; i < LAB_WIDTH; i++) {
                drawBottom(g, i, 0);
            }

            for (int i = 1; i < LAB_HEIGHT; i++) {
                drawRight(g, 0, i);
            }

            for (int i = 1; i < LAB_WIDTH; i++) {
                for (int j = 1; j < LAB_HEIGHT; j++) {
                    Cell c = cells[i][j];
                    if (c.right) {
                        drawRight(g, i, j);
                    }
                    if (c.bottom) {
                        drawBottom(g, i, j);
                    }
                }
            }
        }

        void drawRight(Graphics g, int x, int y) {
            g.drawLine(x*CELL_SIZE + CELL_SIZE, y * CELL_SIZE, x*CELL_SIZE + CELL_SIZE, y * CELL_SIZE + CELL_SIZE);
        }

        void drawBottom(Graphics g, int x, int y) {
            g.drawLine(x*CELL_SIZE, y * CELL_SIZE + CELL_SIZE, x*CELL_SIZE + CELL_SIZE, y * CELL_SIZE + CELL_SIZE);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(LAB_WIDTH * CELL_SIZE + 10, LAB_HEIGHT * CELL_SIZE + 10);
        }
    }

    public static void main(String[] args) {
        Labirinth frm = new Labirinth();
        frm.setVisible(true);
    }

}
