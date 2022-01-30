package expression;

import java.math.BigInteger;

public class Maximum extends AbstractBinaryOperation {
    public Maximum(AbstractOperation firstOperator, AbstractOperation secondOperator) {
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
        return 1;
    }

    @Override
    public String getSign() {
        return "max";
    }

    @Override
    protected int doOperation(int x, int y) {
        return Math.max(x, y);
    }

    @Override
    protected BigInteger doOperation(BigInteger x, BigInteger y) {
        return x.max(y);
    }
}
