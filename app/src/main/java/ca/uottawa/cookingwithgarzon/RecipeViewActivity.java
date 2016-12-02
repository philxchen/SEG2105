package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbBitMapUtility;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.MealType;
import ca.uottawa.cookingwithgarzon.model.Recipe;
import ca.uottawa.cookingwithgarzon.model.Cuisine;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;
import ca.uottawa.cookingwithgarzon.model.Step;

public class RecipeViewActivity extends AppCompatActivity {

    final int EDIT_RECIPE_REQUEST = 1;
    private long recipe_id;
    private TextView recipeTitle;
    private ImageView recipeImage;
    private RatingBar recipeRating;
    private TextView recipeDifficultyTxt;
    private TextView recipeMealTypeTxt;
    private TextView recipeCuisineTxt;
    private ListView viewRecipeIngredientList;
    private ListView viewRecipeStepList;
    private DbHelper dbHelper;
    private Button deleteBtn;
    private FloatingActionButton addAllIngToCartBtn;
    private Recipe recipe;
    private Cuisine cuisine;
    private MealType mealType;
    private ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();
    private StepArrayAdapter stepArrayAdapter;
    private RecipeIngredientArrayAdapter recipeIngredientArrayAdapter;
    private DbBitMapUtility dbBitMap = new DbBitMapUtility();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        recipeTitle = (TextView) findViewById(R.id.viewRecipeTitle);
        recipeImage = (ImageView) findViewById(R.id.viewRecipeImage);
        recipeRating = (RatingBar) findViewById(R.id.viewRecipeRating);
        recipeDifficultyTxt = (TextView) findViewById(R.id.viewRecipeDifficultyTxt);
        recipeMealTypeTxt = (TextView) findViewById(R.id.viewRecipeMealTypeTxt);
        recipeCuisineTxt = (TextView) findViewById(R.id.viewRecipeCuisineTxt);
        viewRecipeIngredientList = (ListView) findViewById(R.id.viewRecipeIngredientList);
        viewRecipeStepList = (ListView) findViewById(R.id.viewRecipeStepList);
        deleteBtn = (Button) findViewById(R.id.deleteRecipeBtn);
        addAllIngToCartBtn = (FloatingActionButton) findViewById(R.id.addToCartBtn);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);
        loadRecipe();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(RecipeViewActivity.this, CreateOrEditRecipeActivity.class);
                editIntent.putExtra("recipe_id", recipe_id);
                startActivityForResult(editIntent, EDIT_RECIPE_REQUEST);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteRecipe(recipe);
                finish();
            }
        });

        addAllIngToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (RecipeIngredient ingredient : recipeIngredients) {
                    dbHelper.createShoppingCartIngredient(ingredient);
                }
                Snackbar.make(findViewById(R.id.activity_recipe_view),
                        "Added all ingredients to your cart.",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        }); helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(RecipeViewActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "recipeViewHelp");
                startActivity(helpIntent);
            }
        });


    }

    private void loadRecipe() {
        dbHelper = DbHelper.getInstance(getApplicationContext());
        recipe = dbHelper.getRecipe(recipe_id);
        Snackbar.make(findViewById(R.id.activity_recipe_view), "Loaded recipe id "+ recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        recipeTitle.setText(recipe.get_name());

        recipeRating.setNumStars(5);

        if (recipe.get_rating() != 0) {
            recipeRating.setRating(recipe.get_rating());
        }
        if (recipe.get_cuisine_id() != 0) {
            cuisine = dbHelper.getCuisine(recipe.get_cuisine_id());
            recipeCuisineTxt.setText(cuisine.get_name());
        }
        if (recipe.get_meal_type_id() != 0) {
            mealType = dbHelper.getMealType(recipe.get_meal_type_id());
            recipeMealTypeTxt.setText(mealType.get_name());
        }
        recipeDifficultyTxt.setText(recipe.get_difficulty());

        recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);

        recipeIngredientArrayAdapter =
                new RecipeIngredientArrayAdapter(this,
                        R.layout.recipe_ingredient_item, recipeIngredients);
        viewRecipeIngredientList.setAdapter(recipeIngredientArrayAdapter);

        steps = dbHelper.getRecipeSteps(recipe_id);

        stepArrayAdapter = new StepArrayAdapter(this, R.layout.step_item, steps);
        viewRecipeStepList.setAdapter(stepArrayAdapter);

        if(recipe.get_image() != null)
            recipeImage.setImageBitmap(dbBitMap.getImage(recipe.get_image()));

        dbHelper.close();
    }

    @Override
    public void onResume() {
        loadRecipe();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EDIT_RECIPE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    recipe = dbHelper.getRecipe(recipe_id);
                    loadRecipe();
                }
                break;
        }
    }
}
