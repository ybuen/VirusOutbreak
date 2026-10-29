import Enums.Status;
import java.util.Random;
import java.util.ArrayList;

public class Hood extends Indexable {

    private static final Random RANDOM = new Random();

    private final ArrayList<Hood> adjacentHoods;
    private Status status;
    private int cycleOfVI; // Cycle when vaccinated or infected
    private int lastCycleUpdated; // Last cycle where this hood was checked/updated

    public Hood() {
        super();

        this.adjacentHoods = new ArrayList<Hood>();

        this.status = Status.O;
        this.cycleOfVI = -1;
        this.lastCycleUpdated = 0;
    }

    public boolean updateResistance(int currentCycle, int V) {
        if (status != Status.I && status != Status.V) {
            return false;
        }

        if (currentCycle - cycleOfVI >= V) {
            status = Status.R;
            return true;
        }
        return false;
    }

    public boolean updateInfection(int currentCycle, int P1, int P2) {
        if (currentCycle <= lastCycleUpdated || status != Status.O) {
            return false;
        }

        lastCycleUpdated = currentCycle;

        int neighbouringInfected = 0;
        boolean randomInfectionRoll = false;

        for (Hood hood: adjacentHoods) {
            if (hood.isInfected()) {
                neighbouringInfected = neighbouringInfected + 1;
            }
        }

        if (neighbouringInfected > 1) {
            randomInfectionRoll = rollRandomInfection(P2);
        } else if (neighbouringInfected == 1) {
            randomInfectionRoll = rollRandomInfection(P1);
        }

        return randomInfectionRoll;
    }

    public void infect(int currentCycle) {
        status = Status.I;
        cycleOfVI = currentCycle;
    }

    public boolean vaccinate(int currentCycle) {
        if (status != Status.O) {
            return false;
        }
        status = Status.V;
        cycleOfVI = currentCycle;
        return true;
    }

    public boolean isInfected() {
        return (status == Status.I);
    }

    public void addAdjacent(Hood h) {
        adjacentHoods.add(h);
    }

    public ArrayList<Hood> getAdjacents() {
        return this.adjacentHoods;
    }

    public boolean rollRandomInfection(int P) {
        int randomInt = RANDOM.nextInt(1, 101);
        return (randomInt <= P);
    }

    @Override
    public String toString() {
        return this.status.toString();
    }
}