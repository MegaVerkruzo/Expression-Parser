package expression.exceptions;

import expression.*;
import expression.exceptions.exception.LogarithmArithmeticException;

import java.math.BigInteger;

public class CheckedLog extends AbstractBinaryOperation {
    public CheckedLog(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }

    @Override
    protected BigInteger doOperation(BigInteger x, BigInteger y) {
        return x.multiply(y);
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
        return "//";
    }

    @Override
    protected int doOperation(int x, int y) {
        if (y <= 1) {
            throw new LogarithmArithmeticException("Base of the logarithm <= 1");
        }
        if (x <= 0) {
            throw new LogarithmArithmeticException("Logarithm argument <= 0");
        }
        if (x == 1) {
            return 0;
        }
        int cnt = 0;
        try {
            while (CheckedPow.pow(y, cnt) <= x) {
                cnt++;
            }
        } finally {
            return cnt - 1;
        }
    }
}
