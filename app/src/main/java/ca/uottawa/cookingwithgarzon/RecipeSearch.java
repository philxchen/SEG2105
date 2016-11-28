package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RecipeSearch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search);

        Button searchBtn = (Button) findViewById(R.id.searchBtn);

        final EditText nameTxt = (EditText) findViewById(R.id.nameTxt);
        final EditText ingredientTxt = (EditText) findViewById(R.id.ingredientTxt);
        final EditText cuisineTxt = (EditText) findViewById(R.id.cuisineTxt);
        final EditText mealTypeTxt = (EditText) findViewById(R.id.mealTypeTxt);

        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecipeSearch.this, RecipeSearchResult.class);
                intent.putExtra("name", nameTxt.getText().toString());
                intent.putExtra("ingredient", ingredientTxt.getText().toString());
                intent.putExtra("cuisine", cuisineTxt.getText().toString());
                intent.putExtra("type", mealTypeTxt.getText().toString());
                startActivity(intent);
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);
    }
}
