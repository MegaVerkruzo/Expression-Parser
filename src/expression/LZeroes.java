package expression;

import java.math.BigInteger;

public class LZeroes extends AbstractUnaryOperation {
    public LZeroes(AbstractOperation operation) {
        super(operation);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getSign() {
        return "l0";
    }

    @Override
    protected int doOperation(int x) {
        return Integer.numberOfLeadingZeros(x);
    }

    @Override
    protected BigInteger doOperation(BigInteger x) {
        return BigInteger.ZERO;
    }

}