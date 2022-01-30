package expression.exceptions;

import expression.exceptions.exception.OverflowArithmeticException;
import expression.*;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    protected int doOperation(int x, int y) {
        if (x == 0 && y == Integer.MIN_VALUE
                || x > 0 && y < 0 && Integer.MIN_VALUE + x - y >= 0
                || x < 0 && y > 0 && Integer.MAX_VALUE + x - y + 1 < 0) {
            throw new OverflowArithmeticException("Overflow operation: \"Subtract\"");
        }
        return x - y;
    }
}
