package Visualizer;

import javax.swing.*;
import java.awt.*;

/**
 * Example 2D String visualization
 * @author ICS4UE // Edited by me Baron Yuan
 * @version Oct 2026
 */
public class Visualizer extends JFrame{
    final int GRIDSIZE = 3;
    final int WINDOW_WIDTH = 800;
    final int WINDOW_HEIGHT = 800;

    private GraphicsPanel panel;
    private String twoDstring;
    private int rows, cols;
    private int leftCol, topRow;

    public Visualizer (String twoDstring) {
        this.panel = new GraphicsPanel();
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setResizable(false);

        this.twoDstring = twoDstring;
        this.rows = twoDstring.split("\n").length;  //number of lines
        this.cols = twoDstring.indexOf("\n");       //length of the first line

        this.setVisible(true); // move a bit
    }

    public void update(String newGrid) {
        this.twoDstring = newGrid;
        panel.repaint();
    }

    private class GraphicsPanel extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            leftCol = (getWidth() - cols * GRIDSIZE) / 2; // more precise with integers
            topRow = (getHeight() - rows * GRIDSIZE) / 2;
            //draw the twoDstring
            String[] lines = twoDstring.split("\n");
            for (int row=0; row<rows; row++){
                String line = lines[row];
                for (int col=0; col<cols; col++){
                    g.setColor(Color.LIGHT_GRAY);
                    if (line.charAt(col) == Const.EMPTY){
                        g.setColor(Const.EMPTY_CLR);
                    }else if (line.charAt(col) == Const.INFECTED){
                        g.setColor(Const.INFECTED_CLR);
                    }else if (line.charAt(col) == Const.RESISTANT){
                        g.setColor(Const.RESISTANT_CLR);
                    }else if (line.charAt(col) == Const.VACCINATED){
                        g.setColor(Const.VACCINATED_CLR);
                    }
                    g.fillRect(leftCol + col * GRIDSIZE, topRow + row * GRIDSIZE, GRIDSIZE, GRIDSIZE);
                }
            }
            //this.repaint(); dont need
        }
    }
}