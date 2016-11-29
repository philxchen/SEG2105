package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Recipe;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;

public class CreateRecipeIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_ingredient);

        final Button saveBtn = (Button) findViewById(R.id.saveRecipeIngredientBtn);
        final EditText unitTxt = (EditText) findViewById(R.id.recipeIngredientUnitTxt);
        final EditText quantityTxt = (EditText) findViewById(R.id.recipeIngredientQuantityTxt);
        Intent intent = getIntent();
        final long recipe_id = Long.parseLong(intent.getStringExtra("recipe_id"));
        final long ingredient_id = Long.parseLong(intent.getStringExtra("ingredient_id"));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String unit = unitTxt.getText().toString();
                Long quantity = Long.parseLong(quantityTxt.getText().toString());
                RecipeIngredient newRecipeIngredient = new RecipeIngredient();
                newRecipeIngredient.set_quantity(quantity);
                newRecipeIngredient.set_unit(unit);
                newRecipeIngredient.set_recipe_id(recipe_id);
                newRecipeIngredient.set_ingredient_id(ingredient_id);
                DbHelper db = new DbHelper(getApplicationContext());
                db.createRecipeIngredient(newRecipeIngredient);
                Intent result = new Intent();
                result.putExtra("result", "Added recipe ingredient!");
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
