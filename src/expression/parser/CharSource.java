package expression.parser;

import expression.exceptions.exception.SyntaxException;

public interface CharSource {
    boolean hasNext();
    char next();
    SyntaxException error(final String message);
}
