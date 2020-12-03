package ua.edu.ucu.collections.immutable;

import ua.edu.ucu.collections.node.Node;

import java.util.Arrays;

public final class ImmutableLinkedList implements ImmutableList {
    private final Node head;

    public ImmutableLinkedList() {
        this.head = new Node();
    }

    public ImmutableLinkedList(Node head) {
        this.head = head;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        ImmutableLinkedList newList = new ImmutableLinkedList();
        if (this.head.getData() == null) {
            newList.head.setData(e);
        }
        else {
            newList.head.setData(this.head.getData());
            Node newNode = newList.head;
            newNode = this.cloneNode(this.head, newNode);
            newNode.setNext(new Node(e));
        }
        return newList;
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (index >= this.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newList = new ImmutableLinkedList();
        Node current = this.head;
        if (index == 0) {
            newList.head.setData(e);
            newList.head.setNext(new Node(this.head.getData()));
            this.cloneNode(this.head, newList.head.getNext());
        }
        else {
            Node newNode = newList.head;
            newNode.setData(this.head.getData());
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
                newNode.setNext(new Node(current.getData()));
                newNode = newNode.getNext();
            }
            newNode.setNext(new Node(e));
            newNode = newNode.getNext();
            this.cloneNode(current, newNode);
        }
        return newList;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        if (this.head.getData() == null) {
            Node newNode = new Node();
            this.addArray(c, newNode);
            return new ImmutableLinkedList(newNode.getNext());
        }
        else {
            ImmutableLinkedList newList = new ImmutableLinkedList();
            newList.head.setData(this.head.getData());
            Node newNode = newList.head;
            newNode = this.cloneNode(this.head, newNode);
            this.addArray(c, newNode);
            return newList;
        }
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index >= this.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        int i = index;
        ImmutableLinkedList newList = this.add(i, c[0]);
        i += 1;
        for (int j = 1; j < c.length; j++) {
            newList = newList.add(i, c[j]);
            i += 1;
        }
        return newList;
    }

    @Override
    public Object get(int index) {
        if (index >= this.size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return this.toArray()[index];
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newList = new ImmutableLinkedList();
        newList.head.setData(this.head.getData());
        Node current = this.head;
        if (index == 0) {
            return new ImmutableLinkedList(this.head.getNext());
        }
        Node newNode = newList.head;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNext();
            newNode.setNext(new Node(current.getData()));
            newNode = newNode.getNext();
        }
        current = current.getNext();
        this.cloneNode(current, newNode);
        return newList;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newList = new ImmutableLinkedList();
        Node current = this.head;
        newList.head.setData(this.head.getData());
        Node newNode = newList.head;
        if (index == 0) {
            newNode.setData(e);
        }
        else {
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
                newNode.setNext(new Node(current.getData()));
                newNode = newNode.getNext();
            }
            current = current.getNext();
            newNode.setNext(new Node(e));
            newNode = newNode.getNext();
        }
        this.cloneNode(current, newNode);
        return newList;
    }

    @Override
    public int indexOf(Object e) {
        Node current = this.head;
        int i = 0;
        while (current != null && current.getData() != e) {
            current = current.getNext();
            i += 1;
        }
        if (current == null) {
            return -1;
        }
        return i;
    }

    @Override
    public int size() {
        if (this.isEmpty()) {
            return 0;
        }
        int size = 0;
        Node current = head;
        while (current != null) {
            size += 1;
            current = current.getNext();
        }
        return size;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public Object[] toArray() {
        if (this.isEmpty()) {
            return new Object[]{};
        }
        Object[] array = new Object[this.size()];
        Node current = head;
        int i = 0;
        while (current != null) {
            array[i] = current.getData();
            current = current.getNext();
            i += 1;
        }
        return array;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }

    private Node cloneNode(Node current, Node newNode) {
        while (current.getNext() != null) {
            current = current.getNext();
            newNode.setNext(new Node(current.getData()));
            newNode = newNode.getNext();
        }
        return newNode;
    }

    private void addArray(Object[] array, Node newNode) {
        for (Object o : array) {
            newNode.setNext(new Node(o));
            newNode = newNode.getNext();
        }
    }

    public ImmutableLinkedList addFirst(Object e) {
        if (this.isEmpty()) {
            return this.add(e);
        }
        return this.add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        ImmutableLinkedList newList = new ImmutableLinkedList();
        if (this.isEmpty()) {
            newList.head.setData(e);
        }
        else {
            Node currentNode = this.head;
            Node newNode = newList.head;
            newNode.setData(currentNode.getData());
            newNode = this.cloneNode(currentNode, newNode);
            newNode.setNext(new Node(e));
        }
        return newList;
    }

    public Object getFirst() {
        return head.getData();
    }

    public Object getLast() {
        return this.toArray()[this.size() - 1];
    }

    public ImmutableLinkedList removeFirst() {
        return this.remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return this.remove(this.size() - 1);
    }
}
