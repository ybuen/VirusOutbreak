public class Main {
    public static void main(String[] args) throws InterruptedException {
        int startX = 99;
        int startY = 99;
        int cycles = 400;
        int msDelay = 100;

        Simulation sim = new Simulation(200, 200, 30, 75, 6, 6, 10);
        //                              MaxX, MaxY, P1, P2, V, A, D

        sim.startVisualSimulation(startX, startY, cycles, msDelay);
        //sim.startConsoleSimulation(startX, startY, cycles, msDelay);

    }

}