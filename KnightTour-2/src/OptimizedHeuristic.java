import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class OptimizedHeuristic {
    private Timer ticker = new Timer(10, null);
    private JFrame frame = new JFrame("Knight's Tour!");
    private JPanel panel;
    private final int X_SHIFT = 145,Y_SHIFT = 50, SQ = 64;
    private boolean randTour = false;
    private Main kt = new Main();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OptimizedHeuristic gkt = new OptimizedHeuristic();
            gkt.setUpUI();
        });
    }

    public OptimizedHeuristic() {
        kt.setStartingLocation(0, 0);
    }

    public void setUpUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        setUpPanel();
        setUpButtons();

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void setUpButtons() {
        JButton next = new JButton("Next");
        JButton runStop = new JButton("Run");

        JPanel controlPanel = new JPanel(new GridLayout(1, 4));
        controlPanel.add(next);
        controlPanel.add(runStop);

        frame.add(controlPanel, BorderLayout.NORTH);

        next.addActionListener(e -> {
            if (!kt.trapped()) {
                kt.makeBestMove();
                frame.repaint();
            }
        });

        runStop.addActionListener(e -> {
            if (ticker.isRunning()) {
                stopTour();
                runStop.setText("Run");
            } else {
                startTour();
                runStop.setText("Stop");
            }
        });
    }

    public void setUpPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                kt.draw(g, X_SHIFT, Y_SHIFT, SQ);
            }
        };
        panel.setPreferredSize(new Dimension(800, 600));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }
        });

        frame.add(panel, BorderLayout.CENTER);
    }

    public void handleMousePress(MouseEvent e) {
        int x = (e.getX() - X_SHIFT) / SQ;
        int y = (e.getY() - Y_SHIFT) / SQ;
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            kt = new Main();
            kt.setStartingLocation(y, x);
            frame.repaint();
        }
    }

    public void startTour() {
        if (ticker.getActionListeners().length == 0) {
            ticker.addActionListener(e -> {
                if (kt.trapped()) {
                    stopTour();
                } else {
                    kt.makeBestMove();
                }
                frame.repaint();
            });
        }
        ticker.start();
    }

    public void stopTour() {
        ticker.stop();
        frame.repaint();
    }
}