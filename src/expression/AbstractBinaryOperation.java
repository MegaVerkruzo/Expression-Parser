package expression;

import java.math.BigInteger;
import java.util.Objects;

public abstract class AbstractBinaryOperation extends AbstractOperation {
    private final AbstractOperation firstOperation;
    private final AbstractOperation secondOperation;
    private int hashCode;

    protected AbstractBinaryOperation(final AbstractOperation firstOperation,
                                      final AbstractOperation secondOperation) {
        this.firstOperation = firstOperation;
        this.secondOperation = secondOperation;
    }

    protected abstract int doOperation(int x, int y);

    protected abstract BigInteger doOperation(BigInteger x, BigInteger y);

    protected void toString(final StringBuilder sb) {
        sb.append('(');
        firstOperation.toString(sb);
        correctWriteOperator(sb);
        secondOperation.toString(sb);
        sb.append(')');
    }

    protected void toMiniString(final StringBuilder sb) {
        correctPrintOperation(sb, firstOperation, false);
        correctWriteOperator(sb);
        correctPrintOperation(sb, secondOperation, true);
    }

    private void correctWriteOperator(final StringBuilder sb) {
        sb.append(' ');
        sb.append(getSymbolOfOperation());
        sb.append(' ');
    }

    private void correctPrintOperation(final StringBuilder sb, final AbstractOperation operation, final boolean rightOperation) {
        if (operation instanceof AbstractUnaryOperation
                || withoutBrackets((AbstractBinaryOperation) operation, rightOperation)) {
            operation.toMiniString(sb);
        } else {
            writeBrackets(sb, operation, true);
        }
    }

    private boolean withoutBrackets(final AbstractBinaryOperation lowerOperation, final boolean rightOperation) {
        if (!rightOperation) {
            return lowerOperation.getPriority() >= getPriority();
        }
        return getPriority() < lowerOperation.getPriority() || (
                getPriority() == lowerOperation.getPriority() && (
                        getCommutable()
                                && lowerOperation.getCommutable()
                                || getCommutable() != lowerOperation.getCommutable()
                                && getCommutable()
                                && lowerOperation.getInvert()
                )
        );
    }

    public abstract boolean getCommutable();

    public abstract boolean getInvert();

    @Override
    public int evaluate(final int x) {
        return doOperation(firstOperation.evaluate(x), secondOperation.evaluate(x));
    }

    @Override
    public int evaluate(final int x, final int y, final int z) {
        return doOperation(firstOperation.evaluate(x, y, z), secondOperation.evaluate(x, y, z));
    }

    @Override
    public BigInteger evaluate(final BigInteger x) {
        return doOperation(firstOperation.evaluate(x), secondOperation.evaluate(x));
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof final AbstractBinaryOperation operationObject) {
            return getSign().equals(operationObject.getSign())
                    && firstOperation.equals(operationObject.firstOperation)
                    && secondOperation.equals(operationObject.secondOperation);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (hashCode == 0) {
            hashCode = Objects.hash(
                    firstOperation,
                    secondOperation,
                    getSign()
            );
        }
        return hashCode;
    }
}