package prak2;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class QuadraticSolverServer extends QuadraticSolverImpl{
    protected QuadraticSolverServer() throws RemoteException {
    }

    public static void main(String[] args) {
        try {
            QuadraticSolverImpl solver = new QuadraticSolverImpl();
            Registry registry = LocateRegistry.createRegistry(1100); // Создаём реестр RMI на порту 1099
            registry.rebind("QuadraticSolver", solver);
            System.out.println("QuadraticSolverServer is ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
