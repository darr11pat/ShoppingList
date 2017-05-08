/*
* Name - Darshan Patil
* ZID - Z1779946
* Assignment 4
* Due Date - Thrusday May 4th
*/

package edu.niu.cs.z1779946.assignment4;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.view.Gravity.CENTER_HORIZONTAL;
import static android.view.Gravity.CENTER_VERTICAL;
import static android.widget.GridLayout.HORIZONTAL;
import static android.widget.GridLayout.VERTICAL;

public class MainActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;
    private ScrollView scrollView;
    private int buttonWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        databaseManager = new DatabaseManager(this);
        scrollView = (ScrollView)findViewById(R.id.todoListScrollView);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        buttonWidth = size.x / 2;

        updateView();
    }//end of onCreate

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }//end of onResume

    public void updateView(){
        ArrayList<TodoList> todoLists = databaseManager.selectAll();
        int todoListsCount = todoLists.size();

        if(todoListsCount > 0){
            scrollView.removeAllViewsInLayout();

            GridLayout gridLayout = new GridLayout(this);

            gridLayout.setRowCount((todoListsCount + 1) / 2);
            gridLayout.setColumnCount(1);
            gridLayout.setPadding(20, 20, 20, 20);
            gridLayout.setBackgroundColor(Color.LTGRAY);

            final CheckBox[] checkBox = new CheckBox[todoListsCount];
            int sub = 0;

            TextView titleTV = new TextView(this);
            titleTV.setText("The List Contains");
            titleTV.setTextSize(15);
            gridLayout.addView(titleTV, ViewGroup.LayoutParams.WRAP_CONTENT);

            for (final TodoList toDoItem: todoLists){
                //create a individual checkbox
                checkBox[sub] = new CheckBox(this);

                //set id and text to checkbox
                checkBox[sub].setId(toDoItem.getId());
                checkBox[sub].setText(toDoItem.getItem());
                checkBox[sub].setPadding(10, 10, 15, 10);
                checkBox[sub].setButtonDrawable(R.drawable.custom_checklist);
                checkBox[sub].setTextColor(Color.BLACK);

                checkBox[sub].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        databaseManager.isDone(isChecked, toDoItem.getId());
                        if (isChecked)
                            buttonView.setTextColor(Color.GRAY);
                        else
                            buttonView.setTextColor(Color.BLACK);
                    }
                });

                if(toDoItem.getSelected() == 1) {
                    checkBox[sub].setChecked(true);
                    checkBox[sub].setTextColor(Color.GRAY);
                }

                //add checkboxs to gridlayout
                gridLayout.addView(checkBox[sub], buttonWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

                sub++;
            }//end for
            //add the gridlayout to scrollview
            scrollView.addView(gridLayout);
        }//end if
        else
            scrollView.removeAllViewsInLayout();
    }//end of updateView

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Toast.makeText(this, "Add selected", Toast.LENGTH_SHORT).show();

            Intent insertIntent = new Intent(MainActivity.this, InsertActivity.class);
            startActivity(insertIntent);

            return true;
        }
        else if (id == R.id.action_delete) {
            Toast.makeText(this, "List Deleted", Toast.LENGTH_SHORT).show();

            Intent deleteIntent = new Intent(MainActivity.this, DeleteActivity.class);
            startActivity(deleteIntent);

            return true;
        }
        else if (id == R.id.action_deleteAll) {
            Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();

            databaseManager.deleteAll();
            updateView();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
