package expression.parser;

import expression.exceptions.exception.SyntaxException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public class StringSource implements CharSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    @Override
    public boolean hasNext() {
        return pos < data.length();
    }

    @Override
    public char next() {
        return data.charAt(pos++);
    }

    @Override
    public SyntaxException error(final String message) {
        return new SyntaxException(message + " | Piece of text: \"" + data.substring(Math.max(pos - 5, 0), Math.min(data.length(), pos + 5)) + "\"");
    }
}
