package expression;

import java.math.BigInteger;

public class Minimum extends AbstractBinaryOperation {
    public Minimum(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    public boolean getCommutable() {
        return true;
    }

    @Override
    public boolean getInvert() {
        return true;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getSign() {
        return "min";
    }

    @Override
    protected int doOperation(int x, int y) {
        return Math.min(x, y);
    }

    @Override
    protected BigInteger doOperation(BigInteger x, BigInteger y) {
        return x.min(y);
    }
}
