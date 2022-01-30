package expression.exceptions;

import expression.exceptions.exception.DivideByZeroArithmeticException;
import expression.exceptions.exception.OverflowArithmeticException;
import expression.*;

public class CheckedDivide extends Divide {
    public CheckedDivide(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    protected int doOperation(int x, int y) {
        if (y == 0) {
            throw new DivideByZeroArithmeticException("Division by zero");
        } else if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowArithmeticException("Overflow operation: \"Divide\"");
        }
        return x / y;
    }
}
