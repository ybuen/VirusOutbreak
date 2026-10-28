public class Simulation {
    public final int maxX;
    public final int maxY;

    Hood[][] grid;

    public Simulation(int mX, int mY) {
        grid = new Hood[mY][mX];
        this.maxX = mX;
        this.maxY = mY;

        initializeCells();
    }

    private void initializeCells() {
        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                grid[i1][i2] = new Hood();
            }
        }

        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                setAdjacents(i2, i1);
            }
        }
    }

    private void setAdjacents(int x, int y) {
        Hood current = grid[y][x];

        // Check if up down left right is within boundaries, and then add them to that hood's adjacents
        if (x - 1 >= 0) {
            current.addAdjacent(grid[y][x - 1]);
        }
        if (x + 1 < maxX) {
            current.addAdjacent(grid[y][x + 1]);
        }
        if (y - 1 >= 0) {
            current.addAdjacent(grid[y-1][x]);
        }
        if (y + 1 < maxY) {
            current.addAdjacent(grid[y+1][x]);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringGrid = new StringBuilder();
        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                stringGrid.append(grid[i1][i2].toString());
                stringGrid.append(" ");
            }
            stringGrid.append("\n");
        }

        return stringGrid.toString();
    }
}
