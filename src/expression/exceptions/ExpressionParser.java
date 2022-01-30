package expression.exceptions;

import expression.*;
import expression.exceptions.exception.*;
import expression.parser.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public final class ExpressionParser implements Parser, expression.parser.Parser {
    public AbstractOperation parse(final String source) {
        return parse(new StringSource(source));
    }

    public AbstractOperation parse(final CharSource source) {
        return new Parser(source).parseExpression();
    }

    private class Parser extends BaseParser {
        private char previousCh = ' ';
        private String lastOperation = "";

        private Map<String, Integer> prioritizes = new HashMap<>(Map.of(
                "", 0,
                "min", 1,
                "max", 1,
                "+", 2,
                "-", 2,
                "*", 3,
                "/", 3,
                "**", 4,
                "//", 4
        ));

        public Parser(final CharSource source) {
            super(source);
        }

        private boolean correctStartForMinMax(final char previousCh) {
            return previousCh == ')' || Character.isWhitespace(previousCh);
        }

        private boolean correctEndForMinMax() {
            return pick() == '(' || Character.isWhitespace(pick()) || pick() == '-' || eof();
        }

        private boolean correctEndForAbs() {
            return pick() == '(' || Character.isWhitespace(pick()) || eof();
        }

        private String token(final boolean withoutMinus) {
            skipWhitespace();
            final char startCh = previousCh;
            if (take('m')) {
                try {
                    if (take('i')) {
                        expect('n');
                        if (correctStartForMinMax(startCh) && correctEndForMinMax()) {
                            return "min";
                        }
                        throw new OperationSyntaxException("Expected space between \"min\" and argument");
                    } else if (take('a')) {
                        expect('x');
                        if (correctStartForMinMax(startCh) && correctEndForMinMax()) {
                            return "max";
                        }
                        throw new OperationSyntaxException("Expected space between \"max\" and argument");
                    }
                } catch (final SymbolSyntaxException e) {
                    throw new OperationSyntaxException("Expected \"min\" or \"max\"");
                }
            } else if (take('+')) {
                return "+";
            } else if (take('/')) {
                if (take('/')) {
                    return "//";
                }
                return "/";
            } else if (take('*')) {
                if (take('*')) {
                    return "**";
                }
                return "*";
            } else if (!withoutMinus && take('-')) {
                return "-";
            }
            return "";
        }

        private Const parseNumber(final boolean wasMinus) {
            final StringBuilder sb = new StringBuilder();
            if (wasMinus) {
                sb.append('-');
                if (pick() == '0') {
                    throw new ArgumentSyntaxException("Incorrect number: " + sb.append('0'));
                }
            } else if (take('0')) {
                sb.append('0');
                if (between('0', '9')) {
                    throw new ArgumentSyntaxException("Incorrect number: " + sb.append(take()));
                }
            }
            if (between('1', '9')) {
                sb.append(take());
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            try {
                return new Const(Integer.parseInt(sb.toString()));
            } catch (final NumberFormatException e) {
                throw new OverflowArithmeticException("Overflow number: " + sb);
            }
        }

        private AbstractOperation parseElement() {
            skipWhitespace();
            if (between('0', '9')) {
                return parseNumber(false);
            } else if (take('-')) {
                if (between('0', '9')) {
                    return parseNumber(true);
                } else {
                    try {
                        return new CheckedNegate(parseElement());
                    } catch (final ArgumentSyntaxException e) {
                        throw new OperationSyntaxException("Bare operation: -");
                    }
                }
            } else if (take('a')) {
                try {
                    expect("bs");
                    if (correctEndForAbs()) {
                        return new CheckedAbs(parseElement());
                    }
                    throw new OperationSyntaxException("Expected space between \"abs\" and argument");
                } catch (final SymbolSyntaxException e) {
                    if (previousCh == 'a') {
                        throw new VariableSyntaxException("'a' - incorrect variable");
                    } else {
                        throw new OperationSyntaxException("Expected \"abs\"");
                    }
                }
            } else if (between('a', 'w')) {
                throw new VariableSyntaxException('\'' + take() + '\'' + " - incorrect variable");
            } else if (between('x', 'z')) {
                return new Variable(new StringBuilder().append(take()).toString());
            } else if (take('(')) {
                try {
                    final AbstractOperation answer = parsePriority(1);
                    skipWhitespace();
                    if (!take(')')) {
                        throw new ParenthesisSyntaxException("No correct close bracket");
                    }
                    return answer;
                } catch (final ArgumentSyntaxException e) {
                    throw new ParenthesisSyntaxException("No expression in brackets");
                }
            }
            throw new ArgumentSyntaxException("Expected any argument");
        }

        private AbstractOperation parsePriority(final int priority) {
            final String bareOperation = token(true);
            AbstractOperation firstElement;
            try {
                firstElement = parseElement();
            } catch (final ArgumentSyntaxException e) {
                if (!bareOperation.isEmpty()) {
                    throw new OperationSyntaxException("Bare operation: " + bareOperation);
                }
                if (priority == 1) {
                    throw new ArgumentSyntaxException("Expected first argument");
                } else {
                    throw new ArgumentSyntaxException("Expected last argument");
                }
            }
            if (!bareOperation.isEmpty()) {
                throw new OperationSyntaxException("Expected first argument");
            }
            String operationToken;
            do {
                operationToken = (lastOperation.isEmpty() ? token(false) : lastOperation);
                if (!token(true).isEmpty()) {
                    throw new ArgumentSyntaxException("Expected middle argument");
                }
                lastOperation = "";
                boolean useOperation = false;
                if (priority <= prioritizes.get(operationToken)) {
                    useOperation = true;
                    firstElement = switch (operationToken) {
                        case "min" -> new Minimum(firstElement, parseArg(operationToken));
                        case "max" -> new Maximum(firstElement, parseArg(operationToken));
                        case "+" -> new CheckedAdd(firstElement, parseArg(operationToken));
                        case "-" -> new CheckedSubtract(firstElement, parseArg(operationToken));
                        case "*" -> new CheckedMultiply(firstElement, parseArg(operationToken));
                        case "/" -> new CheckedDivide(firstElement, parseArg(operationToken));
                        case "**" -> new CheckedPow(firstElement, parseArg(operationToken));
                        case "//" -> new CheckedLog(firstElement, parseArg(operationToken));
                        default -> firstElement;
                    };
                }
                if (!useOperation) {
                    lastOperation = operationToken;
                    operationToken = "";
                }
            } while (!operationToken.isEmpty());
            return firstElement;
        }

        private AbstractOperation parseArg(final String operationToken) {
            return parsePriority(prioritizes.get(operationToken) + 1);
        }

        private void skipWhitespace() {
            while (Character.isWhitespace(pick())) {
                take();
            }
        }

        public AbstractOperation parseExpression() {
            final AbstractOperation answer = parsePriority(1);
            if (eof()) {
                return answer;
            }
            if (take(')')) {
                throw new ParenthesisSyntaxException("No correct open bracket");
            }
            throw new SyntaxException("Expected end of file");
        }

        @Override
        protected char take() {
            previousCh = pick();
            return super.take();
        }
    }
}
