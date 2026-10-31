public class Main {
    public static void main(String[] args) throws InterruptedException {
        int startX = 132; // starting coord x, based on 0 to MaxX - 1 indexing
        int startY = 132; // starting coord y, based on 0 to MaxY - 1
        int cycles = 150;
        int msDelay = 50;

        // Constructor params: MaxX (length), MaxY (height), P1, P2, V, A, D
        Simulation sim = new Simulation(265, 265, 25, 40, 2, 2, 15);

        sim.startVisualSimulation(startX, startY, cycles, msDelay); // For Visual Simulation
        //sim.startConsoleSimulation(startX, startY, cycles, msDelay); // For Console Printing
    }
}