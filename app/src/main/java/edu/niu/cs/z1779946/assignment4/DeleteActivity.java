/*
* Name - Darshan Patil
* ZID - Z1779946
* Assignment 4
* Due Date - Thrusday May 4th
*/
package edu.niu.cs.z1779946.assignment4;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {

    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(this);
        updateView();
    }//end of onCreate

    public void updateView(){
        ArrayList<TodoList> todoLists = databaseManager.selectAll();
        int todoListsCount = todoLists.size();

        RelativeLayout relativeLayout = new RelativeLayout(this);

        ScrollView scrollView = new ScrollView(this);

        RadioGroup radioGroup = new RadioGroup(this);

        GridLayout gridLayout = new GridLayout(this);

        if(todoListsCount > 0){
            scrollView.removeAllViewsInLayout();

            gridLayout.setRowCount((todoListsCount + 1) / 2);
            gridLayout.setColumnCount(1);

            for (TodoList toDoItem: todoLists){
                //create a individual checkbox
                RadioButton radioButton = new RadioButton(this);

                radioButton.setId(toDoItem.getId());
                radioButton.setText(toDoItem.getItem());
                radioButton.setPadding(10, 10, 15, 10);
                radioButton.setTextColor(Color.BLACK);

                radioGroup.addView(radioButton);
            }//end for

            gridLayout.addView(radioGroup);

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    databaseManager.deleteById(checkedId);
                    Toast.makeText(DeleteActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();
                    updateView();
                }
            });

        }//end if

        Button backBT = new Button(this);
        backBT.setText("Back");
        backBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scrollView.addView(gridLayout);

        relativeLayout.addView(scrollView);

        //set up parameters to make back button be at the bottom of the screen
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.setMargins(0,0,0,50);
        //add the button to the relative layout using the parameters
        relativeLayout.addView(backBT, params);

        //put the relative layout on the screen
        setContentView(relativeLayout);
    }//end of updateView
}//end of class
