package expression.exceptions;

import expression.exceptions.exception.OverflowArithmeticException;
import expression.*;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    public static int multiply(int x, int y) {
        int res = x * y;
        if (x > 0 && y > 0 && x * y <= 0
                || x < 0 && y < 0 && x * y <= 0
                || x != 0 && y != 0 && ((res / x) != y || (res / y) != x)) {
            throw new OverflowArithmeticException("Overflow operation: \"Multiply\"");
        }
        return x * y;
    }

    @Override
    protected int doOperation(int x, int y) {
        return multiply(x, y);
    }
}
