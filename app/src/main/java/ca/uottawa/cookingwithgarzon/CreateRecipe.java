package ca.uottawa.cookingwithgarzon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.adapter.HintAdapter;

public class CreateRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        /*
         * Spinner Object Lists
         */

        //Difficulty List
        List<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Moderate");
        difficultyList.add("Hard");
        difficultyList.add("Select Difficulty"); //Hint

        //Meal Type List
        List<String> mealTypeList = new ArrayList<>();
        mealTypeList.add("Lorem");
        mealTypeList.add("Ipsum");
        //INSERT LIST POPULATOR AND REMOVE LOREM IPSUM LINES
        mealTypeList.add("Select Meal Type"); //Hint

        //Cuisine Type List
        List<String> cuisineTypeList = new ArrayList<>();
        cuisineTypeList.add("Lorem");
        cuisineTypeList.add("Ipsum");
        //INSERT LIST POPULATOR AND REMOVE LOREM IPSUM LINES
        cuisineTypeList.add("Select Cuisine"); //Hint


        /*
         * Spinner Object HintAdapters and Hint initialization
         */

        //Difficulty Adapter
        HintAdapter difficultyAdapter = new HintAdapter(this, difficultyList, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficulty_select);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(difficultyAdapter.getCount());

        //Meal Type Adapter
        HintAdapter mealTypeAdapter = new HintAdapter(this, mealTypeList, android.R.layout.simple_spinner_item);
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner mealTypeSpinner = (Spinner) findViewById(R.id.meal_type_select);
        mealTypeSpinner.setAdapter(mealTypeAdapter);
        mealTypeSpinner.setSelection(mealTypeAdapter.getCount());

        //Cuisine Type Adapter
        HintAdapter cuisineTypeAdapter = new HintAdapter(this, cuisineTypeList, android.R.layout.simple_spinner_item);
        cuisineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner cuisineTypeSpinner = (Spinner) findViewById(R.id.cuisine_select);
        cuisineTypeSpinner.setAdapter(cuisineTypeAdapter);
        cuisineTypeSpinner.setSelection(cuisineTypeAdapter.getCount());


        //Floating Action Toolbar when submitting to the Database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipeBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
