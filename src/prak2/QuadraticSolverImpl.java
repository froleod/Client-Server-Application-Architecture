package prak2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class QuadraticSolverImpl extends UnicastRemoteObject implements QuadraticSolver {

    protected QuadraticSolverImpl() throws RemoteException {
    }

    @Override
    public Solutions solveQuadraticEquation(double a, double b, double c) throws RemoteException {
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new Solutions(0, 0, false);
        } else {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new Solutions(root1, root2, true);
        }
    }
}
