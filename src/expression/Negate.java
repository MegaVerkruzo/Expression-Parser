package expression;

import java.math.BigInteger;

public class Negate extends AbstractUnaryOperation {
    public Negate(AbstractOperation operation) {
        super(operation);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public String getSign() {
        return "-";
    }

    @Override
    protected int doOperation(int x) {
        return -x;
    }

    @Override
    protected BigInteger doOperation(BigInteger x) {
        return x.negate();
    }

}