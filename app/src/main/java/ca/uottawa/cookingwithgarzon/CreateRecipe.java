package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.adapter.HintAdapter;
import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
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
    private ListView recipeStep_listView;
    private RecipeIngredientArrayAdapter recipeIngredientArrayAdapter;
    private StepArrayAdapter stepArrayAdapter;
    private ImageView image;
    private EditText recipeTitleTxt;
    private RatingBar recipeRatingBar;
    private FloatingActionButton newIngredientBtn;
    private FloatingActionButton newStepBtn;
    private Spinner difficultySpinner;
    private Spinner mealTypeSpinner;
    private Spinner cuisineTypeSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View content = findViewById(R.id.content_create_recipe);
        newIngredientBtn = (FloatingActionButton) content.findViewById(R.id.addIngredientToRecipeBtn);
        newStepBtn = (FloatingActionButton) content.findViewById(R.id.addStepToRecipeBtn);
        recipeTitleTxt = (EditText) content.findViewById(R.id.createRecipeTitle);
        recipeRatingBar = (RatingBar) content.findViewById(R.id.createRecipeRating);
        difficultySpinner = (Spinner) content.findViewById(R.id.difficultySelect);
        mealTypeSpinner = (Spinner) content.findViewById(R.id.mealTypeSelect);
        cuisineTypeSpinner = (Spinner) content.findViewById(R.id.cuisineTypeSelect);
        recipeIngredients_listView = (ListView) content.findViewById(R.id.listOfRecipeIngredient);
        recipeStep_listView = (ListView) content.findViewById(R.id.listOfRecipeStep);

        dbHelper = DbHelper.getInstance(getApplicationContext());
        newRecipe = new Recipe();
        recipe_id = dbHelper.createRecipe(newRecipe);
        newRecipe.set_id(recipe_id);

        Snackbar.make(findViewById(R.id.activity_create_recipe), "New recipe with id: "+recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        recipeIngredients = new ArrayList<>();
        recipeIngredientArrayAdapter = new RecipeIngredientArrayAdapter(this, R.layout.recipe_ingredient_item, recipeIngredients);
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
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(difficultyAdapter.getCount());

        //Meal Type Adapter
        HintAdapter mealTypeAdapter = new HintAdapter(this, mealTypeList, android.R.layout.simple_spinner_item);
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(mealTypeAdapter);
        mealTypeSpinner.setSelection(mealTypeAdapter.getCount());

        //Cuisine Type Adapter
        HintAdapter cuisineTypeAdapter = new HintAdapter(this, cuisineTypeList, android.R.layout.simple_spinner_item);
        cuisineTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineTypeSpinner.setAdapter(cuisineTypeAdapter);
        cuisineTypeSpinner.setSelection(cuisineTypeAdapter.getCount());



        //Floating Action Toolbar when submitting to the Database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipeBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRecipe.set_meal_type_id(1);
                newRecipe.set_difficulty(difficultySpinner.getSelectedItemPosition());
                newRecipe.set_name(recipeTitleTxt.getText().toString());
                newRecipe.set_servings(0);
                newRecipe.set_rating(recipeRatingBar.getNumStars());
                dbHelper.updateRecipe(newRecipe);
                Snackbar.make(view, "Saved recipe " + newRecipe.get_name(), Snackbar.LENGTH_LONG)
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
            recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
            recipeIngredientArrayAdapter.notifyDataSetChanged();
            Snackbar.make(findViewById(R.id.activity_create_recipe), message
                    + " recipeIngredients has size " + recipeIngredients.size(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
