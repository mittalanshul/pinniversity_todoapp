package com.mitchellandwebb.todoapp;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by anshul on 7/3/15.
 */

@Table(name = "TodoItems")
public class TodoItem extends Model {

    @Column(name = "Text")
    public String text;

    public TodoItem() {
        super();
    }

    @Override
    public String toString() {
        return this.text;
    }
}
