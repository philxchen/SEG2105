package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.adapter.HintAdapter;
import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.*;

public class CreateOrEditRecipeActivity extends AppCompatActivity {

    final int GET_INGREDIENT_REQUEST = 1;
    final int GET_STEP_REQUEST = 2;
    final int GET_CUISINE_REQUEST = 3;
    final int GET_MEALTYPE_REQUEST = 4;

    private DbHelper dbHelper;
    private Recipe recipe;
    private Cuisine cuisine;
    private MealType mealtype;
    private long recipe_id;
    private ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();
    private ListView recipeIngredients_listView;
    private ListView step_listView;
    private RecipeIngredientArrayAdapter recipeIngredientArrayAdapter;
    private StepArrayAdapter stepArrayAdapter;
    private ImageView image;
    private EditText recipeTitleTxt;
    private RatingBar recipeRatingBar;
    private FloatingActionButton newIngredientBtn;
    private FloatingActionButton newStepBtn;
    private Spinner difficultySpinner;
    private TextView mealTypeTxt;
    private TextView cuisineTxt;
    private boolean saved;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View content = findViewById(R.id.content_create_recipe);
        newIngredientBtn = (FloatingActionButton) content.findViewById(R.id.createRecipeAddIngredientBtn);
        newStepBtn = (FloatingActionButton) content.findViewById(R.id.createRecipeAddStepBtn);
        recipeTitleTxt = (EditText) content.findViewById(R.id.createRecipeTitleTxt);
        recipeRatingBar = (RatingBar) content.findViewById(R.id.createRecipeRating);
        difficultySpinner = (Spinner) content.findViewById(R.id.createRecipeDifficultySpinner);
        mealTypeTxt = (TextView) content.findViewById(R.id.createRecipeMealTypeTxt);
        cuisineTxt = (TextView) content.findViewById(R.id.createRecipeCuisineTxt);
        recipeIngredients_listView = (ListView) content.findViewById(R.id.createRecipeIngredientList);
        step_listView = (ListView) content.findViewById(R.id.createRecipeStepList);

        dbHelper = DbHelper.getInstance(getApplicationContext());

        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);

        if (recipe_id != 0) {
            saved=true;
            loadRecipe();
        }
        else {
            saved = false;
            recipe = new Recipe();
            recipe_id = dbHelper.createRecipe(recipe);
            recipe.set_id(recipe_id);
            Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), "New recipe with id: "+recipe_id, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

        recipeIngredientArrayAdapter = new RecipeIngredientArrayAdapter(this, R.layout.recipe_ingredient_item, recipeIngredients);
        recipeIngredients_listView.setAdapter(recipeIngredientArrayAdapter);

        stepArrayAdapter = new StepArrayAdapter(this, R.layout.step_item, steps);
        step_listView.setAdapter(stepArrayAdapter);

        /*
         * Spinner Object Lists
         */

        //Difficulty List
        List<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Moderate");
        difficultyList.add("Hard");
        difficultyList.add("Select Difficulty"); //Hint

        /*
         * Spinner Object HintAdapters and Hint initialization
         */

        //Difficulty Adapter
        HintAdapter difficultyAdapter = new HintAdapter(this, difficultyList, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(difficultyAdapter.getCount());

        //Floating Action Toolbar when submitting to the Database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipeBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeTitleTxt.getText() == null || recipeTitleTxt.getText().toString().isEmpty()) {
                    Snackbar.make(view, "You must name your recipe", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                recipe.set_meal_type_id(1);
                recipe.set_difficulty(difficultySpinner.getSelectedItemPosition());
                recipe.set_name(recipeTitleTxt.getText().toString());
                recipe.set_servings(0);
                recipe.set_rating((int)recipeRatingBar.getRating());
                dbHelper.updateRecipe(recipe);
                saved = true;
                Snackbar.make(view, "Saved recipe " + recipe.get_name(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        newIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIngredientIntent = new Intent(CreateOrEditRecipeActivity.this, CreateRecipeIngredientActivity.class);
                newIngredientIntent.putExtra("recipe_id", recipe_id);
                startActivityForResult(newIngredientIntent, GET_INGREDIENT_REQUEST);
            }
        });


        newStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIngredientIntent = new Intent(CreateOrEditRecipeActivity.this, CreateOrEditStepActivity.class);
                newIngredientIntent.putExtra("recipe_id", recipe_id);
                startActivityForResult(newIngredientIntent, GET_STEP_REQUEST);
            }
        });

        cuisineTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickCuisine = new Intent(CreateOrEditRecipeActivity.this, CuisineSearchActivity.class);
                pickCuisine.putExtra("recipe_id", recipe_id);
                startActivityForResult(pickCuisine, GET_CUISINE_REQUEST);
            }
        });

        mealTypeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickType = new Intent(CreateOrEditRecipeActivity.this, MealTypeSearchActivity.class);
                pickType.putExtra("recipe_id", recipe_id);
                startActivityForResult(pickType, GET_CUISINE_REQUEST);
            }
        });
    }

    private void loadRecipe() {
        recipe = dbHelper.getRecipe(recipe_id);
        recipeTitleTxt.setText(recipe.get_name());
        if (recipe.get_rating() != 0) {
            recipeRatingBar.setRating(recipe.get_rating());
        }
        if (recipe.get_cuisine_id() != 0) {
            cuisine = dbHelper.getCuisine(recipe.get_cuisine_id());
            cuisineTxt.setText(cuisine.get_name());
        }
        if (recipe.get_meal_type_id() != 0) {
            mealtype = dbHelper.getMealType(recipe.get_meal_type_id());
            mealTypeTxt.setText(mealtype.get_name());
        }

        recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
        steps = dbHelper.getRecipeSteps(recipe_id);
        Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), "Editing recipe id: "+recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_INGREDIENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
                    recipeIngredientArrayAdapter.clear();
                    recipeIngredientArrayAdapter.addAll(recipeIngredients);
                    recipeIngredientArrayAdapter.notifyDataSetChanged();
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message
                            + " recipeIngredients has size " + recipeIngredients.size(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case GET_STEP_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    steps = dbHelper.getRecipeSteps(recipe_id);
                    stepArrayAdapter.clear();
                    stepArrayAdapter.addAll(steps);
                    stepArrayAdapter.notifyDataSetChanged();
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message
                            + " steps has size " + steps.size(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case GET_CUISINE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    long cuisine_id = data.getLongExtra("cuisine_id", 0);
                    if (cuisine_id != 0) {
                        recipe.set_cuisine_id(cuisine_id);
                        String cuisine = data.getStringExtra("cuisine_name");
                        cuisineTxt.setText(cuisine);
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    }
                }
                break;
            case GET_MEALTYPE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    long type_id = data.getLongExtra("type_id", 0);
                    if (type_id != 0) {
                        recipe.set_meal_type_id(type_id);
                        String type = data.getStringExtra("type_name");
                        mealTypeTxt.setText(type);
                        Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message,
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!saved) {
            dbHelper.deleteRecipe(recipe);
        }

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }
}
