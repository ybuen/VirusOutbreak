import Visualizer.Visualizer;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private static final Random RANDOM = new Random();

    public final int P1, P2, V, A, D;

    public final int maxX;
    public final int maxY;
    public final int size;

    Hood[][] grid;

    FunkyList<Hood> activeHoods;
    ArrayList<Hood> allHoods, newlyInfectedHoods;

    public Simulation(int mX, int mY, int P1, int P2, int V, int A, int D) {
        grid = new Hood[mY][mX];

        activeHoods = new FunkyList<Hood>();
        allHoods = new ArrayList<Hood>();
        newlyInfectedHoods = new ArrayList<Hood>();

        this.maxX = mX;
        this.maxY = mY;
        this.size = mX * mY;

        this.P1 = P1; // P1 = 25 -> 25% for example
        this.P2 = P2; // P2 = 75 -> 75% for example
        this.V = V;
        this.A = A;
        this.D = D;

        initializeCells();
    }

    public void startConsoleSimulation(int startX, int startY, int cycles, int delayInMS) throws InterruptedException{
        Hood firstHood = grid[startY][startX];

        firstHood.infect(0);
        activeHoods.add(firstHood);

        printCycle(0);

        for (int i = 1; i <= cycles; i ++) {
            Thread.sleep(delayInMS);
            cycle(i);
            printCycle(i);
        }
    }

    public void startVisualSimulation(int startX, int startY, int cycles, int delayInMS) throws InterruptedException{
        Hood firstHood = grid[startY][startX];

        firstHood.infect(0);
        activeHoods.add(firstHood);

        Visualizer visual = new Visualizer(this.toString());

        for (int i = 1; i <= cycles; i ++) {
            Thread.sleep(delayInMS);
            cycle(i);
            visual.update(this.toString());
        }
    }

    @Override
    public String toString() {
        StringBuilder stringGrid = new StringBuilder();
        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                stringGrid.append(grid[i1][i2].toString());
                //stringGrid.append("  ");
            }
            stringGrid.append("\n");
        }

        return stringGrid.toString();
    }

    public void cycle(int currentCycle) {
        spreadInfection(currentCycle);
        updateResistances(currentCycle);
        updateNewlyInfected(currentCycle);
        vaccinateHoods(currentCycle, A, D);
    }

    // MAIN PROCESSES THAT HAPPEN IN CYCLE() ^^ ---------------------------------------------------------------
    private void spreadInfection(int currentCycle) {
        boolean infectionSucceeded;

        for (Hood hood:activeHoods) {
            if (hood.isInfected()) {

                for (Hood adjacent:hood.getAdjacents()) {
                    infectionSucceeded = adjacent.updateInfection(currentCycle, P1, P2);
                    if (infectionSucceeded) {
                        newlyInfectedHoods.add(adjacent);
                    }

                }
            }
        }
    }

    private void updateResistances(int currentCycle) {
        int i = 0;
        while (i < activeHoods.size()) {
            Hood hood = activeHoods.get(i);

            if (hood.updateResistance(currentCycle, V)) {
                activeHoods.funkyRemove(hood);
            } else {
                i = i + 1;
            }
        }
    }

    private void updateNewlyInfected(int currentCycle) {
        for (Hood newActive:newlyInfectedHoods) {
            newActive.infect(currentCycle);
            activeHoods.add(newActive);
        }
        newlyInfectedHoods.clear();
    }

    private void vaccinateHoods(int currentCycle, int A, int D) {
        if (currentCycle <= D) {
            return;
        }

        Hood selected;
        int randomIndex;
        boolean successfullyVaccinated;

        for (int i = 0; i < A; i ++) {
            randomIndex = RANDOM.nextInt(i, size);
            selected = allHoods.get(randomIndex);
            allHoods.set(randomIndex, allHoods.get(i));
            allHoods.set(i, selected);

            successfullyVaccinated = selected.vaccinate(currentCycle);
            if (successfullyVaccinated) {
                activeHoods.add(selected);
            }
        }
    }

    private void printCycle(int currentCycle) {
        System.out.println("Current day: " + currentCycle);
        System.out.println(this);
    }

    // INITIALIZATION HELPERS ------------------------------------------------------------------------------------
    private void initializeCells() {
        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                Hood newHood = new Hood();
                grid[i1][i2] = newHood;
                allHoods.add(newHood);
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
}
