package com.mitchellandwebb.todoapp;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;


import com.activeandroid.query.Select;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by anshul on 7/5/15.
 */
public class TodoItemPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public TodoItemPagerAdapter(FragmentManager fm) {
        super(fm);
        readItemsFromBackup();
    }

    public void readItemsFromBackup() {
        List<TodoItem> items = new Select()
                                .from(TodoItem.class)
                                .execute();
        for(TodoItem item: items) {
            fragments.add(TodoItemFragment.newInstance(item.toString()));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
