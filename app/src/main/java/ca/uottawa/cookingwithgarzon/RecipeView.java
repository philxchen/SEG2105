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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Recipe;

public class RecipeView extends AppCompatActivity {

    private ImageView recipeImage;
    private RatingBar recipeRating;
    private TextView recipeDifficultyTxt;
    private TextView recipeMealTypeTxt;
    private TextView recipeCuisineTxt;
    private ListView viewRecipeIngredientList;
    private ListView viewRecipeStepList;
    private DbHelper dbHelper;
    private Toolbar toolbar;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Intent intent = getIntent();
        dbHelper = DbHelper.getInstance(getApplicationContext());
        long recipe_id = intent.getLongExtra("recipe_id", 0);

        Snackbar.make(findViewById(R.id.activity_recipe_view), "Loaded recipe id "+ recipe_id, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        Recipe recipe = dbHelper.getRecipe(recipe_id);

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


        dbHelper.close();

    }
}
