/*
* Name - Darshan Patil
* ZID - Z1779946
* Assignment 4
* Due Date - Thrusday May 4th
*/
package edu.niu.cs.z1779946.assignment4;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class    InsertActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private ScrollView scrollView;
    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        databaseManager = new DatabaseManager(this);
        scrollView = (ScrollView)findViewById(R.id.displayScrollView);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        buttonWidth = size.x / 2;

        updateView();
    }//end of onCreate

    public void insert(View view){
        EditText itemET = (EditText)findViewById(R.id.itemEditText);

        String itemStr = itemET.getText().toString();

        TodoList todoList = new TodoList(0, itemStr, 0);

        databaseManager.insert(todoList);

        Toast.makeText(this, " Item Added ", Toast.LENGTH_SHORT).show();
        itemET.setText("");

        updateView();
    }//end of insert

    public void updateView(){
        ArrayList<TodoList> todoLists = databaseManager.selectAll();
        int todoListsCount = todoLists.size();

        if(todoListsCount > 0){
            scrollView.removeAllViewsInLayout();

            GridLayout gridLayout = new GridLayout(this);
            gridLayout.setRowCount((todoListsCount + 1) / 2);
            gridLayout.setColumnCount(1);

            TextView[] itemTV = new TextView[todoListsCount];
            TextView titleTV = new TextView(this);
            titleTV.setText("The List Contains");
            gridLayout.addView(titleTV, ViewGroup.LayoutParams.WRAP_CONTENT);

            int sub = 0;
            for (TodoList toDoItem: todoLists){
                //create a individual checkbox
                itemTV[sub] = new TextView(this);

                //set text to checkbox
                itemTV[sub].setText(toDoItem.getItem());

                //add checkboxs to gridlayout
                gridLayout.addView(itemTV[sub], ViewGroup.LayoutParams.WRAP_CONTENT);

                sub++;
            }//end for

            //add the gridlayout to scrollview
            scrollView.addView(gridLayout);
        }//end if
    }//end of updateView


    public void goBack(View view){
        finish();
    }//end of goBack
}//end of insert class
