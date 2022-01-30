package expression.exceptions;

import expression.exceptions.exception.OverflowArithmeticException;
import expression.*;

public class CheckedNegate extends Negate {
    public CheckedNegate(AbstractOperation operation) {
        super(operation);
    }

    @Override
    protected int doOperation(int x) {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowArithmeticException("Overflow operation: \"Negate\"");
        }
        return -x;
    }
}