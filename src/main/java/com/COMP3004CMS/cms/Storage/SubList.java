package com.COMP3004CMS.cms.Storage;

import com.COMP3004CMS.cms.utility.exceptions.MaxStudentSubmissions;

/**
 * Linked list that stores as many sub
 */

public class SubList{
    private SubNode head;
    private SubNode tail;
    private int size;
    //limit of potential upleads a student can do for any given deliverable
    private int subLimit;

    public SubList(){
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.subLimit = 50;
    }
    /*Allow professor to submit a custom limit if they want as well*/
    public SubList(int customLimit){
        this.head = null;
        this.tail = null;
        this.size = 0;
        this.subLimit = customLimit;
    }

    public int getSize(){ return size; }

    public Submission getLast(){
        return tail.getSub();
    }

    /**
     *
     * @param sub most recent submission to add
     * @throws MaxStudentSubmissions error user reaches their submission submission limit for that deliverable
     */
    public void addBack(Submission sub) throws MaxStudentSubmissions {
        try{
            if (size >= subLimit){
                throw new MaxStudentSubmissions("Error - Max submissions reached contact administration");
            }
            SubNode newN = new SubNode(sub);
            SubNode curr = head;
            SubNode prev = null;

            if (head == null){
                head = newN;

            } else{
                while (head.getNext()!= null){
                    prev = curr;
                    curr = curr.getNext();
                }
                curr.setNext(newN);
                newN.setPrev(curr);
            }
            tail = newN;
            this.size++;
        } catch (NullPointerException en){
            System.out.println("Error - could not addBack to Sublist");
            en.printStackTrace();
        }
    }



    /* dont need right now
    public void addFront(SubNode sub){
        if (size == 0){
            head = sub;
            tail = sub;
        }
    }

     */
}

