package expression.exceptions;

import expression.exceptions.exception.OverflowArithmeticException;
import expression.exceptions.exception.PowArithmeticException;
import expression.*;

import java.math.BigInteger;

public class CheckedPow extends AbstractBinaryOperation {
    public CheckedPow(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    public boolean getCommutable() {
        return false;
    }

    @Override
    public boolean getInvert() {
        return false;
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public String getSign() {
        return "**";
    }

    @Override
    protected int doOperation(int x, int y) {
        return pow(x, y);
    }

    public static int pow(final int x, final int y) {
        int temporary = x;
        if (x == 0 && y == 0 || y < 0) {
            throw new PowArithmeticException("0");
        }
        if (y == 0 || x == 1 || x == -1 && y % 2 == 0) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }
        if (x == -1 && y % 2 != 0) {
            return -1;
        }
        try {
            for (int i = 1; i < y; i++) {
                temporary = CheckedMultiply.multiply(temporary, x);
            }
        } catch (OverflowArithmeticException e) {
            throw new OverflowArithmeticException("Overflow operation: \"Pow\"");
        }
        return temporary;
    }

    @Override
    protected BigInteger doOperation(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

}
