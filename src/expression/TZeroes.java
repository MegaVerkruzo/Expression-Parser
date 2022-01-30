package expression;

import java.math.BigInteger;

public class TZeroes extends AbstractUnaryOperation {
    public TZeroes(AbstractOperation operation) {
        super(operation);
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getSign() {
        return "t0";
    }

    @Override
    protected int doOperation(int x) {
        return Integer.numberOfTrailingZeros(x);
    }

    @Override
    protected BigInteger doOperation(BigInteger x) {
        return BigInteger.ZERO;
    }
}