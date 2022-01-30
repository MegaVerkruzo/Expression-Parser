package expression.exceptions;

import expression.exceptions.exception.OverflowArithmeticException;
import expression.*;

public class CheckedAdd extends Add {
    public CheckedAdd(AbstractOperation firstOperator, AbstractOperation secondOperator) {
        super(firstOperator, secondOperator);
    }
    /*
     Исправил при x < 0 и y < 0 на код, который был ранее, т.к.
     x + y должны быть >= Integer.MIN_VALUE, иначе Overflow,
     Поэтому мы к Integer.MAX_VALUE + 1 добавим отрицательные x и y и если это меньше нуля, то это overflow,
     но мы получим переполнение для Integer.MAX_VALUE + 1, поэтому давайте добавим еденицу позже
     Тогда из-за суммы с отрицательными числами всё будет хорошо*/

    @Override
    protected int doOperation(int x, int y) {
        if (x > 0 && y > 0 && Integer.MIN_VALUE + x + y >= 0
                || x < 0 && y < 0 && Integer.MAX_VALUE + x + y + 1 < 0) {
            throw new OverflowArithmeticException("Overflow operation: \"Add\"");
        }
        return x + y;
    }
}
