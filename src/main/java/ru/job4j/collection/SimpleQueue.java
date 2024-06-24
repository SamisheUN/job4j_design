package ru.job4j.collection;

import java.util.NoSuchElementException;

/**
 * Метод poll() - должен возвращать первое значение и удалять его из коллекции.
 * Метод push(T value) - помещает значение в конец.
 * <p>
 * Данные очереди нужно хранить в ru.job4j.collection.SimpleStack. Для этого задания нужны два стека.
 * Представьте, что у вас стопка с тарелками. Вам нужно достать нижнюю тарелку. Для этого вы перекладываете все тарелки в другую стопку.
 * Стопка - это стек. Для операции извлечения первой тарелки нужны две стопки.
 */
public class SimpleQueue<T> {
    private final SimpleStack<T> input = new SimpleStack<>();
    private final SimpleStack<T> output = new SimpleStack<>();
    private int queueSize = 0;

    public T poll() {
        T rsl;
        if (queueSize <= 0) {
            throw new NoSuchElementException("Queue is empty");
        }
        for (int i = queueSize; i > 0; i--) {
            output.push(input.pop());
        }
        rsl = output.pop();
        queueSize--;
        for (int i = queueSize; i > 0; i--) {
            input.push(output.pop());
        }
        return rsl;
    }

    public void push(T value) {
        queueSize++;
        input.push(value);
    }
}