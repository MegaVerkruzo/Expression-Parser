package expression;

import java.math.BigInteger;

public class Variable extends AbstractUnaryOperation {
    private String name;

    public Variable(String name) {
        super(null);
        this.name = name;
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getSign() {
        return "";
    }

    @Override
    protected int doOperation(int x) {
        return x;
    }

    @Override
    protected BigInteger doOperation(BigInteger x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> 0;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Variable) {
            return name.equals(((Variable) o).name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    protected void toString(StringBuilder sb) {
        sb.append(name);
    }

    @Override
    protected void toMiniString(StringBuilder sb) {
        sb.append(name);
    }
}
