/*
* Name - Darshan Patil
* ZID - Z1779946
* Assignment 4
* Due Date - Thrusday May 4th
*/
package edu.niu.cs.z1779946.assignment4;

/**
 * Created by DARSHAN on 4/28/2017.
 */

public class TodoList {
    private int id;
    private String item;
    private int selected;

    public TodoList(int id, String item, int selected) {
        this.id = id;
        this.item = item;
        this.selected = selected;
    }//end of constructor

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}//end of class
