package prak2;

import java.rmi.Naming;

public class QuadraticSolverClient {
    public static void main(String[] args) {
        try {
            QuadraticSolver solver = (QuadraticSolver) Naming.lookup("rmi://localhost:1100/QuadraticSolver");
            // корни: 1 и 2
            Solutions solutions = solver.solveQuadraticEquation(1, -3, 2);
            System.out.println(solutions);

            // нет действительных корней
            solutions = solver.solveQuadraticEquation(1, 2, 5);
            System.out.println(solutions);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
