package ua.edu.ucu.collections.node;

import lombok.Getter;
import lombok.Setter;

public class Node {
    @Getter @Setter
    private Object data;
    @Getter @Setter
    private Node next;
    public Node() {
        this.data = null;
        this.next = null;
    }
    public Node(Object data) {
        this.data = data;
        this.next = null;
    }
    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }
}
