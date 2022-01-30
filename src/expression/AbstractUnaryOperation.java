package expression;

import java.math.BigInteger;

public abstract class AbstractUnaryOperation extends AbstractOperation {
    private AbstractOperation operation;

    protected AbstractUnaryOperation(AbstractOperation operation) {
        this.operation = operation;
    }

    protected abstract int doOperation(int x);

    protected abstract BigInteger doOperation(BigInteger x);

    @Override
    public int evaluate(int x) {
        if (operation == null) {
            return doOperation(x);
        }
        return doOperation(operation.evaluate(x));
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (operation == null) {
            return doOperation(x);
        }
        return doOperation(operation.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (operation == null) {
            return doOperation(0);
        }
        return doOperation(operation.evaluate(x, y, z));
    }

    @Override
    protected void toString(StringBuilder sb) {
        sb.append(getSymbolOfOperation());
        writeBrackets(sb, operation, false);
    }

    @Override
    protected void toMiniString(StringBuilder sb) {
        sb.append(getSymbolOfOperation());
        if (operation instanceof AbstractUnaryOperation) {
            sb.append(' ');
            sb.append(operation.toMiniString());
        } else {
            writeBrackets(sb, operation, true);
        }

    }

    @Override
    public int hashCode() {
        return operation.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof AbstractUnaryOperation) {
            return operation != null && operation.equals(((AbstractUnaryOperation) object).operation);
        }
        return false;
    }
}
