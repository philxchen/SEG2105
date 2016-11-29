package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.adapter.HintAdapter;
import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbContract;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.*;

public class CreateRecipe extends AppCompatActivity {


    final int GET_INGREDIENT_REQUEST = 1;

    private DbHelper dbHelper;
    private Recipe newRecipe;
    private long recipe_id;
    private ArrayList<RecipeIngredient> recipeIngredients;
    private ArrayList<Step> steps;
    private ListView recipeIngredients_listView;
    private RecipeIngredientArrayAdapter recipeIngredientArrayAdapter;
    private StepArrayAdapter stepArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        newRecipe = new Recipe();
        recipe_id = dbHelper.createRecipe(newRecipe);

        Snackbar.make(findViewById(R.id.activity_create_recipe), "New recipe with id: "+recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        View content = findViewById(R.id.content_create_recipe);
        final FloatingActionButton newIngredientBtn = (FloatingActionButton) content.findViewById(R.id.addIngredientToRecipeBtn);
        final FloatingActionButton newStepBtn = (FloatingActionButton) content.findViewById(R.id.addStepToRecipeBtn);

        recipeIngredients = new ArrayList<RecipeIngredient>();
        recipeIngredients_listView = (ListView) content.findViewById(R.id.listOfRecipeIngredient);
        recipeIngredientArrayAdapter = new RecipeIngredientArrayAdapter(this, R.layout.recipe_item, recipeIngredients);
        recipeIngredients_listView.setAdapter(recipeIngredientArrayAdapter);
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
        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySelect);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(difficultyAdapter.getCount());

        //Meal Type Adapter
        HintAdapter mealTypeAdapter = new HintAdapter(this, mealTypeList, android.R.layout.simple_spinner_item);
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner mealTypeSpinner = (Spinner) findViewById(R.id.mealTypeSelect);
        mealTypeSpinner.setAdapter(mealTypeAdapter);
        mealTypeSpinner.setSelection(mealTypeAdapter.getCount());

        //Cuisine Type Adapter
        HintAdapter cuisineTypeAdapter = new HintAdapter(this, cuisineTypeList, android.R.layout.simple_spinner_item);
        cuisineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner cuisineTypeSpinner = (Spinner) findViewById(R.id.cuisineTypeSelect);
        cuisineTypeSpinner.setAdapter(cuisineTypeAdapter);
        cuisineTypeSpinner.setSelection(cuisineTypeAdapter.getCount());



        //Floating Action Toolbar when submitting to the Database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipeBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.updateRecipe(newRecipe);
                Snackbar.make(view, "Saved!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        newIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIngredientIntent = new Intent(CreateRecipe.this, CreateRecipeIngredientActivity.class);
                newIngredientIntent.putExtra("recipe_id", recipe_id);
                startActivityForResult(newIngredientIntent, GET_INGREDIENT_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String message = data.getStringExtra("result");
            Snackbar.make(findViewById(R.id.activity_create_recipe), message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            switch(resultCode) {
                case 1:
                    recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
                    break;
            }
        }
    }
}
