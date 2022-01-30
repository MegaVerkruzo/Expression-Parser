package expression.exceptions;

import expression.AbstractOperation;
import expression.AbstractUnaryOperation;
import expression.exceptions.exception.OverflowArithmeticException;

import java.math.BigInteger;

public class CheckedAbs extends AbstractUnaryOperation {
    public CheckedAbs(AbstractOperation operation) {
        super(operation);
    }

    @Override
    protected int doOperation(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowArithmeticException("Overflow operation: \"Abs\"");
        }
        return Math.abs(x);
    }

    @Override
    protected BigInteger doOperation(BigInteger x) {
        return x.abs();
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public String getSign() {
        return "abs";
    }
}