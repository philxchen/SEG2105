package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Activity class to allow user to search for recipes
 */

public class RecipeSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        //Activity objects
        Button searchBtn = (Button) findViewById(R.id.search_recipe_button);

        final EditText nameTxt = (EditText) findViewById(R.id.nameTxt);
        final EditText ingredientTxt = (EditText) findViewById(R.id.ingredientTxt);
        final EditText cuisineTxt = (EditText) findViewById(R.id.cuisineTxt);
        final EditText mealTypeTxt = (EditText) findViewById(R.id.mealTypeTxt);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        //Sets onclick listener for search button, which sends user to activity to view results of query
        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeSearchActivity.this, RecipeSearchResult.class);
                intent.putExtra("name", nameTxt.getText().toString());
                intent.putExtra("ingredient", ingredientTxt.getText().toString());
                intent.putExtra("cuisine", cuisineTxt.getText().toString());
                intent.putExtra("type", mealTypeTxt.getText().toString());
                startActivity(intent);
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);

        //Opens up help page based on current activity
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(RecipeSearchActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "searchRecipeHelp");
                startActivity(helpIntent);
            }
        });
    }
}