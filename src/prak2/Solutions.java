package prak2;

import java.io.Serializable;

public class Solutions implements Serializable {

    private static final long serialVersionUID = 1L;
    private final double root1;
    private final double root2;
    private final boolean hasRealRoots;

    public Solutions(double root1, double root2, boolean hasRealRoots) {
        this.root1 = root1;
        this.root2 = root2;
        this.hasRealRoots = hasRealRoots;
    }

    public double getRoot1() {
        return root1;
    }

    public double getRoot2() {
        return root2;
    }

    public boolean hasRealRoots() {
        return hasRealRoots;
    }

    @Override
    public String toString() {
        if (hasRealRoots) {
            return "Roots: " + root1 + " and " + root2;
        } else {
            return "No real roots :/";
        }
    }
}
