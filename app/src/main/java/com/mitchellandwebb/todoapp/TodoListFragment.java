package com.mitchellandwebb.todoapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


import com.activeandroid.query.Select;


import java.util.List;


public class TodoListFragment extends android.support.v4.app.Fragment implements OnClickListener{

    // views
    private ListView lvItems;
    private EditText etNewItem;
    private Button btnAddItem;

    //class variables
    private List<TodoItem> todoItems;
    private ArrayAdapter<TodoItem> todoItemsAdapter;

    public static TodoListFragment newInstance() {
        TodoListFragment fragment = new TodoListFragment();
        return fragment;
    }

    public TodoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_todo_list, container, false);

        lvItems = (ListView) view.findViewById(R.id.lvItems);
        setupListView();
        etNewItem = (EditText) view.findViewById(R.id.etNewItem);
        btnAddItem = (Button) view.findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddItem:
                onAddItem();
                break;
        }
    }

    private void setupListView() {
        todoItems = readItemsFromBackup();
        todoItemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoItemsAdapter);

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                long id) {
                TodoItem item = todoItems.get(position);
                todoItemsAdapter.remove(item);
                // save items to backup
                item.delete();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                                                               .beginTransaction();
                transaction.replace(R.id.flContainer, new TodoPagerFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private List<TodoItem> readItemsFromBackup() {
        return new Select()
            .from(TodoItem.class)
            .execute();
    }

    public void onAddItem() {
        // Get the value from the text field
        TodoItem newTodoItem = new TodoItem();
        newTodoItem.text = etNewItem.getText().toString();
        // Add the value from the adapter
        todoItemsAdapter.add(newTodoItem);
        // Clear the text box to default text
        etNewItem.setText("");
        // save items to backup
        newTodoItem.save();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
