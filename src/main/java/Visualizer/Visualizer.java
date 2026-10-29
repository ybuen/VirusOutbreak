package Visualizer;

import javax.swing.*;
import java.awt.*;

/**
 * Example 2D String visualization
 * @author ICS4UE, Lightly Edited by Baron Yuan so grid size is always constant
 * @version Oct 29, 26
 *
 * Lightly modified by Baron Yuan
 */
public class Visualizer extends JFrame {

    private static final int GRIDSIZE = 4;

    private GraphicsPanel panel;
    private String twoDstring;

    private int rows, cols;

    public Visualizer(String twoDstring) {
        this.twoDstring = twoDstring;

        this.rows = twoDstring.split("\n").length;
        this.cols = twoDstring.split("\n")[0].length();

        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);

        this.getContentPane().add(BorderLayout.CENTER, panel);

        this.setTitle("Virus Outbreak Simulation");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Size the window based on the grid
        panel.setPreferredSize(new Dimension(
                cols * GRIDSIZE,
                rows * GRIDSIZE
        ));

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void update(String newGrid) {
        this.twoDstring = newGrid;

        String[] lines = newGrid.split("\n");
        this.rows = lines.length;
        this.cols = lines[0].length();

        panel.repaint();
    }

    private class GraphicsPanel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            String[] lines = twoDstring.split("\n");

            for (int row = 0; row < rows; row++) {
                String line = lines[row];

                for (int col = 0; col < cols; col++) {

                    char status = line.charAt(col);

                    if (status == Const.EMPTY) {
                        g.setColor(Const.EMPTY_CLR);

                    } else if (status == Const.INFECTED) {
                        g.setColor(Const.INFECTED_CLR);

                    } else if (status == Const.RESISTANT) {
                        g.setColor(Const.RESISTANT_CLR);

                    } else if (status == Const.VACCINATED) {
                        g.setColor(Const.VACCINATED_CLR);

                    } else {
                        g.setColor(Color.LIGHT_GRAY);
                    }

                    g.fillRect(
                            col * GRIDSIZE,
                            row * GRIDSIZE,
                            GRIDSIZE,
                            GRIDSIZE
                    );
                }
            }
        }
    }
}