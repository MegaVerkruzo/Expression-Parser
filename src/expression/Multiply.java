package expression;

import java.math.BigInteger;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    public boolean getCommutable() {
        return true;
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
        return "*";
    }

    @Override
    protected int doOperation(int x, int y) {
        return x * y;
    }

    @Override
    protected BigInteger doOperation(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }
}
