package expression;

import java.math.BigInteger;

public class Const extends AbstractUnaryOperation {
    private final Number value;

    public Const(final BigInteger x) {
        super(null);
        this.value = x;
    }

    public Const(final int x) {
        super(null);
        this.value = new BigInteger(numberToString(x));
    }

    private String numberToString(final int number) {
        return (new StringBuilder()).append(number).toString();
    }

    @Override
    public int getPriority() {
        return 5;
    }

    @Override
    public String getSign() {
        return "";
    }

    @Override
    protected int doOperation(final int x) {
        return this.value.intValue();
    }

    @Override
    protected BigInteger doOperation(final BigInteger x) {
        return (BigInteger) this.value;
    }

    @Override
    protected void toString(final StringBuilder sb) {
        sb.append(value);
    }

    @Override
    protected void toMiniString(final StringBuilder sb) {
        sb.append(value);
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Const) {
            return value.equals(((Const) object).value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value.intValue());
    }
}
