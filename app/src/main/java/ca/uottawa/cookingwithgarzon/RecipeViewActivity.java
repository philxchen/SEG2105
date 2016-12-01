package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Recipe;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;
import ca.uottawa.cookingwithgarzon.model.Step;

public class RecipeViewActivity extends AppCompatActivity {

    final int EDIT_RECIPE_REQUEST = 1;
    private long recipe_id;
    private ImageView recipeImage;
    private RatingBar recipeRating;
    private TextView recipeDifficultyTxt;
    private TextView recipeMealTypeTxt;
    private TextView recipeCuisineTxt;
    private ListView viewRecipeIngredientList;
    private ListView viewRecipeStepList;
    private DbHelper dbHelper;
    private Toolbar toolbar;
    private Button deleteBtn;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);
        toolbar = (Toolbar) findViewById(R.id.recipeViewToolbar);
        setSupportActionBar(toolbar);
        View content = findViewById(R.id.content_recipe_view);
        recipeImage = (ImageView) content.findViewById(R.id.viewRecipeImage);
        recipeRating = (RatingBar) content.findViewById(R.id.viewRecipeRating);
        recipeDifficultyTxt = (TextView) content.findViewById(R.id.viewDifficultyText);
        recipeMealTypeTxt = (TextView) content.findViewById(R.id.viewRecipeMealType);
        recipeCuisineTxt = (TextView) content.findViewById(R.id.viewRecipeCusineType);
        viewRecipeIngredientList = (ListView) content.findViewById(R.id.viewRecipeIngredientList);
        viewRecipeStepList = (ListView) content.findViewById(R.id.viewRecipeStepList);
        deleteBtn = (Button) content.findViewById(R.id.deleteRecipeBtn);

        Intent intent = getIntent();
        dbHelper = DbHelper.getInstance(getApplicationContext());
        recipe_id = intent.getLongExtra("recipe_id", 0);
        recipe = dbHelper.getRecipe(recipe_id);
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
    }

    private void loadRecipe() {
        Snackbar.make(findViewById(R.id.activity_recipe_view), "Loaded recipe id "+ recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();


        getSupportActionBar().setTitle(recipe.get_name()); // Display recipe name in toolbar
        recipeRating.setNumStars(5);
        recipeRating.setRating(recipe.get_rating());
        String difficulty;
        switch(recipe.get_difficulty()) {
            case 2:
                difficulty = new String("Easy");
                break;
            case 3:
                difficulty = new String("Moderate");
                break;
            case 4:
                difficulty = new String("Hard");
                break;
            default:
                difficulty = new String("Not rated");
                break;
        }
        recipeDifficultyTxt.setText(difficulty);

        ArrayList<RecipeIngredient> recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);

        RecipeIngredientArrayAdapter recipeIngredientArrayAdapter =
                new RecipeIngredientArrayAdapter(this, R.layout.recipe_ingredient_item, recipeIngredients);
        viewRecipeIngredientList.setAdapter(recipeIngredientArrayAdapter);

        ArrayList<Step> steps = dbHelper.getRecipeSteps(recipe_id);
        StepArrayAdapter stepArrayAdapter = new StepArrayAdapter(this, R.layout.step_item, steps);
        viewRecipeStepList.setAdapter(stepArrayAdapter);

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
