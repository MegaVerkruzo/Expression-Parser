package expression;

import java.math.BigInteger;

public class Divide extends AbstractBinaryOperation {
    public Divide(final AbstractOperation firstOperator, final AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    public boolean getCommutable() {
        return false;
    }

    @Override
    public boolean getInvert() {
        return false;
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public String getSign() {
        return "/";
    }

    @Override
    protected int doOperation(final int x, final int y) {
        return x / y;
    }

    @Override
    protected BigInteger doOperation(final BigInteger x, final BigInteger y) {
        return x.divide(y);
    }
}
