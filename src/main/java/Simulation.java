import Visualizer.Visualizer;

import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private static final Random RANDOM = new Random();
    private static final int[] OFFSETS = {1, 0, -1};

    public final int P1, P2, V, A, D;
    public final int maxX, maxY, size;

    private final Hood[][] grid;
    private final Hood[] allHoods;

    private final FunkyList<Hood> activeHoods; // iterates through only active hoods instead of all maxX*maxY hoods
    private final ArrayList<Hood> newlyInfectedHoods; // allHoods needed for shuffling newlyInfected needed for cycle sync

    public Simulation(int mX, int mY, int P1, int P2, int V, int A, int D) {
        grid = new Hood[mY][mX];
        activeHoods = new FunkyList<Hood>();

        newlyInfectedHoods = new ArrayList<Hood>();

        this.maxX = mX;
        this.maxY = mY;
        this.size = mX * mY;

        allHoods = new Hood[size];

        this.P1 = P1; // P1 = 25 -> 25% for example
        this.P2 = P2; // P2 = 75 -> 75% for example
        this.V = V;
        this.A = Math.min(size, A); // in case A is larger than size
        this.D = D;

        initializeCells();
    }

    // Simulation Start, repeating code but 2 functions is more convenient in Main.java -------------------------------
    public void startConsoleSimulation(int startX, int startY, int cycles, int delayInMS) throws InterruptedException{
        infectFirst(startX, startY);
        printCycle(0);
        for (int i = 1; i <= cycles; i ++) {
            Thread.sleep(delayInMS);
            cycle(i);
            printCycle(i);
        }
    }

    public void startVisualSimulation(int startX, int startY, int cycles, int delayInMS) throws InterruptedException{
        infectFirst(startX, startY);
        Visualizer visual = new Visualizer(this.toString());
        for (int i = 1; i <= cycles; i ++) {
            Thread.sleep(delayInMS);
            cycle(i);
            visual.update(this.toString());
        }
    }

    private void infectFirst(int startX, int startY) {
        Hood firstHood = grid[startY][startX];

        firstHood.infect(0);
        activeHoods.add(firstHood);
    }

    private void cycle(int currentCycle) {
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
        if (currentCycle <= D) { // If D days havent passed, doesnt do anything
            return;
        }

        Hood selected;
        int randomIndex;
        boolean successfullyVaccinated;

        for (int i = 0; i < A; i ++) { // Fisher-Yates shuffling because its most efficient
            randomIndex = RANDOM.nextInt(i, size);
            selected = allHoods[randomIndex];
            allHoods[randomIndex] = allHoods[i];
            allHoods[i] = selected;

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
        for (int i1 = 0; i1 < maxY; i1 ++) { // Loops through everything once to instantiate hoods
            for (int i2 = 0; i2 < maxX; i2 ++) {
                Hood newHood = new Hood();
                grid[i1][i2] = newHood;
                allHoods[i1*maxX + i2] = newHood;
            }
        }

        for (int i1 = 0; i1 < maxY; i1 ++) { // Loops again to set adjacents (slightly more efficient and convenient)
            for (int i2 = 0; i2 < maxX; i2 ++) {
                setAdjacents(i2, i1);
            }
        }
    }

    private void setAdjacents(int x, int y) {
        Hood current = grid[y][x];
        int newX, newY;

        // Check if all surrounding hoods are in boundaries then add them to that hood's adjacents if yes

        for (int xOffset:OFFSETS) {
            for (int yOffset:OFFSETS) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }
                newX = x + xOffset;
                newY = y + yOffset;
                if (0 <= newX && newX < maxX && 0 <= newY && newY < maxY) {
                    current.addAdjacent(grid[newY][newX]);
                }
            }
        }
    }

    // String override --------------------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder stringGrid = new StringBuilder();
        for (int i1 = 0; i1 < maxY; i1 ++) {
            for (int i2 = 0; i2 < maxX; i2 ++) {
                stringGrid.append(grid[i1][i2].toString());
            }
            stringGrid.append("\n");
        }

        return stringGrid.toString();
    }
}
