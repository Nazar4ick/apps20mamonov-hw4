package ua.edu.ucu.collections;

import lombok.Getter;
import ua.edu.ucu.collections.immutable.ImmutableLinkedList;

public class Queue {
    @Getter
    private ImmutableLinkedList items;
    public Queue() {
        this.items = new ImmutableLinkedList();
    }

    public Object peek() {
        return items.getFirst();
    }

    public Object dequeue() {
        Object item = items.getFirst();
        items = items.removeFirst();
        return item;
    }

    public void enqueue(Object e) {
        items = items.addLast(e);
    }

    public int size() {
        return items.size();
    }

    public String toString() {
        return items.toString();
    }
}
