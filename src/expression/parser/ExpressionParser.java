package expression.parser;

import expression.*;

import java.util.HashMap;
import java.util.Map;

public final class ExpressionParser implements Parser {
    public AbstractOperation parse(final String source) {
        return parse(new StringSource(source));
    }

    public AbstractOperation parse(final CharSource source) {
        return new ParserImpl(source).parseExpression();
    }

    private class ParserImpl extends BaseParser {
        private String lastOperation = "";

        private Map<String, Integer> prioritizes = new HashMap<>(Map.of(
                "", 0,
                "min", 1,
                "max", 1,
                "+", 2,
                "-", 2,
                "*", 3,
                "/", 3
                ));

        public ParserImpl(final CharSource source) {
            super(source);
        }

        public AbstractOperation parseExpression() {
            AbstractOperation answer = parsePriority(1);
            if (eof()) {
                return answer;
            }
            throw error("End of Expression expected");
        }

        private String token() {
            skipWhitespace();
            if (take('m')) {
                if (take('i')) {
                    expect('n');
                    return "min";
                } else if (take('a')) {
                    expect('x');
                    return "max";
                }
            } else if (take('+')) {
                return "+";
            } else if (take('/')) {
                return "/";
            } else if (take('*')) {
                return "*";
            } else if (take('-')) {
                return "-";
            }
            return "";
        }

        private AbstractOperation parsePriority(int priority) {
            AbstractOperation firstElement = parseElement();
            String operationToken;
            do {
                operationToken = (lastOperation.isEmpty() ? token() : lastOperation);
                lastOperation = "";
                boolean useOperation = false;
                if (priority <= prioritizes.get(operationToken)) {
                    useOperation = true;
                    firstElement = switch (operationToken) {
                        case ("min") -> new Minimum(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
                        case ("max") -> new Maximum(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
                        case ("+") -> new Add(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
                        case ("-") -> new Subtract(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
                        case ("*") -> new Multiply(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
                        case ("/") -> new Divide(firstElement, parsePriority(prioritizes.get(operationToken) + 1));
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

        private Const parseNumber(boolean wasMinus) {
            final StringBuilder sb = new StringBuilder();
            if (wasMinus) {
                sb.append('-');
            } else if (take('0')) {
                return new Const(0);
            }
            if (pick() == '0') {
                throw error("Incorrect const");
            }
            if (between('1', '9')) {
                sb.append(take());
            }
            while (between('0', '9')) {
                sb.append(take());
            }
            return new Const(Integer.parseInt(sb.toString()));
        }

        private AbstractOperation parseElement() {
            skipWhitespace();
            if (between('0', '9')) {
                return parseNumber(false);
            } else if (take('-')) {
                if (between('0', '9')) {
                    return parseNumber(true);
                } else {
                    return new Negate(parseElement());
                }
            } else if (take('l')) {
                expect("0");
                return new LZeroes(parseElement());
            } else if (take('t')) {
                expect("0");
                return new TZeroes(parseElement());
            } else if (between('x', 'z')) {
                return new Variable(new StringBuilder().append(take()).toString());
            } else if (take('(')) {
                AbstractOperation answer = parsePriority(1);
                expect(")");
                return answer;
            }
            throw error("Incorrect input");
        }

        private void skipWhitespace() {
            while (Character.isWhitespace(pick())) {
                take();
            }
        }
    }
}
