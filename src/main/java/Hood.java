import Enums.Status;

import java.util.ArrayList;

public class Hood extends Indexable {

    private ArrayList<Hood> adjacentHoods;
    private Status status;
    private int daysSinceVI; // Days since last vaccinated or infected
    private int lastCycleUpdated; // Last cycle where this hood was checked/updated

    public Hood() {
        super();

        this.adjacentHoods = new ArrayList<Hood>();

        this.status = Status.O;
        this.daysSinceVI = 0;
        this.lastCycleUpdated = 0;
    }

    public void addAdjacent(Hood h) {
        adjacentHoods.add(h);
    }

    public void incrementLastCycleUpdated(int currentCycle) {
        if (lastCycleUpdated == currentCycle - 1) {
            lastCycleUpdated = lastCycleUpdated + 1;
        }
    }

    @Override
    public String toString() {
        return this.status.toString();
    }
}