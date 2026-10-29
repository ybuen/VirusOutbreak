public class Main {
    public static void main(String[] args) throws InterruptedException {
        Simulation sim = new Simulation(10, 10, 35, 75, 6, 2, 6);
        //                              MaxX, MaxY, P1, P2, V, A, D

        sim.startSimulation(0, 0, 50, 300);
        //                  StartX, StartY, Cycles, delayInMs

    }
}
