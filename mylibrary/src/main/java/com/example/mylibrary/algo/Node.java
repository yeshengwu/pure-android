package com.example.mylibrary.algo;

public class Node {
    private Integer count;
    private Node nextNode;

    public Node() {
    }

    public Node(int count) {
        this.count = new Integer(count);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

}
