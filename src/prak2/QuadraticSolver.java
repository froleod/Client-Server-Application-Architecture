package prak2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface QuadraticSolver extends Remote {
    Solutions solveQuadraticEquation(double a, double b, double c) throws RemoteException;
}
