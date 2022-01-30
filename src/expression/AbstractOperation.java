package expression;

public abstract class AbstractOperation implements BigIntegerExpression, TripleExpression, Expression {
    protected abstract void toString(StringBuilder sb);

    protected abstract void toMiniString(StringBuilder sb);

    protected void writeBrackets(StringBuilder sb, AbstractOperation operation, boolean toMiniString) {
        sb.append('(');
        if (toMiniString) {
            operation.toMiniString(sb);
        } else {
            operation.toString(sb);
        }
        sb.append(')');
    }

    public String getSymbolOfOperation() {
        return getSign();
    }

    public abstract int getPriority();

    public abstract String getSign();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString(sb);
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        toMiniString(sb);
        return sb.toString();
    }
}
