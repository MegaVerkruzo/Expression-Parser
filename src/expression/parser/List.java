package expression.parser;

import expression.AbstractOperation;

import java.util.Arrays;

public class List<T> {
    private T[] array;
    private int len;

    public List() {
        array = (T[]) new Object[1];
        len = 0;
    }

    public List(T... list) {
        array = list;
        len = list.length;
    }

    private void updateLength() {
        if (array.length == len) {
            array = Arrays.copyOf(array, len * 2);
        }
    }

    public void add(T operation) {
        updateLength();
        array[len++] = operation;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] getArray() {
        return array;
    }

    public int length() {
        return len;
    }
}
