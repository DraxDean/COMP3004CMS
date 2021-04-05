package com.COMP3004CMS.cms.Storage;

public class SubNode {

    private SubNode next;
    private SubNode prev;
    private Submission sub;

    public SubNode(Submission sub) {
        this.sub = sub;
        this.next = null;
        this.prev = null;
    }

    public void setInfo(Submission sub) {
        this.sub = sub;
    }

    public void setNext(SubNode node) {
        next = node;
    }

    public void setPrev(SubNode node) {
        prev = node;
    }

    public Submission getSub() {
        return sub;
    }

    public SubNode getNext() {
        return next;
    }

    public SubNode getPrev() {
        return prev;
    }
}
