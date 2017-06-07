import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import javax.swing.*;

public class Labirinth extends JFrame {
    final static int LAB_WIDTH = 100;
    final static int LAB_HEIGHT = 100;
    final static int CELL_SIZE = 7;
    static Cell[][] cells = new Cell[LAB_WIDTH + 1][LAB_HEIGHT + 1];
    static Coords ccc = new Coords(0, 0);
    static Coords nnn = new Coords(1, 1);

    public Labirinth() throws HeadlessException {
        super("Lab");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(LAB_WIDTH * CELL_SIZE + 150, LAB_HEIGHT * CELL_SIZE + 150));
        JPanel p = new JPanel();

        JButton shuffle = new JButton("Shuffle");
        shuffle.addActionListener(e -> {
            new Thread(() -> {generate();repaint();}).start();
        });
        p.add(shuffle);
        p.add(new MyPanel());
        add(p);
        //randomize();
    }

    void setup() {
        for (int i = 0; i < LAB_WIDTH; i++) {
            for (int j = 0; j < LAB_HEIGHT; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    void randomize() {
        setup();
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < LAB_WIDTH * LAB_HEIGHT / 3 * 4; i++) {
            if (r.nextBoolean()) {
                cells[r.nextInt(LAB_WIDTH)][r.nextInt(LAB_HEIGHT-1)].bottom = false;
            } else {
                cells[r.nextInt(LAB_WIDTH-1)][r.nextInt(LAB_HEIGHT)].right = false;
            }
        }
    }

    void generate() {
        setup();
        Random r = new Random(System.currentTimeMillis());
        ArrayList<Coords> cc = new ArrayList<>();
        for (int i = 1; i < LAB_WIDTH; i++) {
            for (int j = 1; j < LAB_HEIGHT; j++) {
                cc.add(new Coords(i, j));
            }
        }
        Stack<Coords> st = new Stack<>();
        Set<Coords> notVisited = new HashSet<>(cc);
        Coords cur = cc.get(0);
        st.push(cur);
        while (!notVisited.isEmpty()) {
            ArrayList<Coords> ns = neibs(notVisited, cur);
            if (ns.size() > 0) {
                Coords next = ns.size() > 0 ? ns.get(r.nextInt(ns.size())) : ns.get(0);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ccc = cur;
                nnn = next;
                repaint();
                removeWall(cur, next);
                cur = next;
                notVisited.remove(cur);
                st.push(cur);
            } else {
                cur = st.pop();
            }

        }
    }

    private static void removeWall(Coords cur, Coords next) {
        if (cur.x < next.x) {
            cells[cur.x][cur.y].right = false;
            return;
        }
        if (cur.x > next.x) {
            cells[next.x][next.y].right = false;
            return;
        }
        if (cur.y < next.y) {
            cells[cur.x][cur.y].bottom = false;
            return;
        }
        if (cur.y > next.y) {
            cells[next.x][next.y].bottom = false;
            return;
        }
    }

    ArrayList<Coords> neibs(Set<Coords> notVisited, Coords cur) {
        ArrayList<Coords> res = new ArrayList<>();

        if (cur.y > 1) {
            Coords nn = new Coords(cur.x, cur.y - 1);
            if (notVisited.contains(nn)) {
                res.add(nn);
            }
        }
        if (cur.x < LAB_WIDTH) {
            Coords nn = new Coords(cur.x + 1, cur.y);
            if (notVisited.contains(nn)) {
                res.add(nn);
            }
        }
        if (cur.y < LAB_HEIGHT) {
            Coords nn = new Coords(cur.x, cur.y + 1);
            if (notVisited.contains(nn)) {
                res.add(nn);
            }
        }
        if (cur.x > 1) {
            Coords nn = new Coords(cur.x - 1, cur.y);
            if (notVisited.contains(nn)) {
                res.add(nn);
            }
        }
        return res;
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
                    if (ccc.x == i && ccc.y == j) {
                        g.setColor(Color.YELLOW);
                        g.fillRect(i*CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
                    }
                    if (nnn.x == i && nnn.y == j) {
                        g.setColor(Color.GREEN);
                        g.fillRect(i*CELL_SIZE, j * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
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
