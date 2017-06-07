import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class MyFrame extends JFrame {
    int x,y;
    int w,h;
    JLabel l;

    public MyFrame() throws HeadlessException {
        super("MyFrame");
        x = y = 10;
        w = h = 200;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 300));
        JPanel p = new JPanel();
        l = new JLabel(textData());
        p.add(l);
        p.add(new MyPanel());
        add(p);
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (e.isShiftDown()) {
                        h -= 10;
                    } else {
                        y -= 10;
                    }
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (e.isShiftDown()) {
                        h += 10;
                    } else {
                        y += 10;
                    }
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (e.isShiftDown()) {
                        w -= 10;
                    } else {
                        x -= 10;
                    }
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (e.isShiftDown()) {
                        w += 10;
                    }
                    else {
                        x += 10;
                    }
                    repaint();
                }
                l.setText(textData());
            }
        });
    }

    private String textData() {
        return String.format("%d,%d,%d,%d", x, y, w, h);
    }

    public class MyPanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.YELLOW);
            g.fillRect(x,y,w,h);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }
    }

    public static void main(String[] args) {
        MyFrame frm = new MyFrame();
        frm.setVisible(true);
    }

}
