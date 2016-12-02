package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SelectSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_search);

        //Search Buttons
        final Button findRecipeBtn = (Button) findViewById(R.id.findRecipeBtn);
        final Button findIngredientBtn = (Button) findViewById(R.id.findIngredientBtn);
        final Button findCuisineBtn = (Button) findViewById(R.id.findCuisineBtn);
        final Button findMealBtn = (Button) findViewById(R.id.findMealBtn);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);


        //On-click functions for the search buttons
        findIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectSearchActivity.this, IngredientSearchActivity.class));
            }
        });

        findRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectSearchActivity.this, RecipeSearchActivity.class));
            }
        });

        findCuisineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectSearchActivity.this, CuisineSearchActivity.class));
            }
        });
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(SelectSearchActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "selectSearchHelp");
                startActivity(helpIntent);
            }
        });


        findMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectSearchActivity.this, MealTypeSearchActivity.class));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String message = data.getStringExtra("result");
            Snackbar.make(findViewById(R.id.activity_select_create), message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
}
