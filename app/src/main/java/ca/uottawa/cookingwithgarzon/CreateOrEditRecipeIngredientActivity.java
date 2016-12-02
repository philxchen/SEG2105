package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;


public class CreateOrEditRecipeIngredientActivity extends AppCompatActivity {


    static final int PICK_INGREDIENT_REQUEST = 1;
    private long ingredient_id;

    private TextView recipeIngredientTxt;
    private Button saveBtn;
    private Button getIngredientBtn;
    private EditText unitTxt;
    private EditText quantityTxt;
    private long recipe_id;
    private long recipe_ingredient_id;
    private boolean ingredientPicked=false;
    private RecipeIngredient recipeIngredient;
    private Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe_ingredient);

        recipeIngredientTxt = (TextView) findViewById(R.id.recipeIngredientTxt);
        saveBtn = (Button) findViewById(R.id.saveRecipeIngredientBtn);
        getIngredientBtn = (Button) findViewById(R.id.chooseIngredientBtn);
        unitTxt = (EditText) findViewById(R.id.recipeIngredientUnitTxt);
        quantityTxt = (EditText) findViewById(R.id.recipeIngredientQuantityTxt);

        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);
        recipe_ingredient_id = intent.getLongExtra("recipe_ingredient_id", 0);

        if (recipe_ingredient_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            recipeIngredient = db.getRecipeIngredient(recipe_ingredient_id);
            ingredient = db.getIngredient(recipeIngredient.get_ingredient_id());
            if (ingredient != null) {
                recipeIngredientTxt.setText(ingredient.get_name());
            }
            quantityTxt.setText(((Long)recipeIngredient.get_quantity()).toString());
            unitTxt.setText(recipeIngredient.get_unit());
        }
        else {
            recipeIngredient = new RecipeIngredient();
        }

        getIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent getIngredientIntent = new Intent(CreateOrEditRecipeIngredientActivity.this, IngredientSearchActivity.class);
                getIngredientIntent.putExtra("recipe_id", recipe_id);
                startActivityForResult(getIngredientIntent, PICK_INGREDIENT_REQUEST);
            }
        });


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ingredientPicked ) {
                    String message = "You need to pick an ingredient!";
                    Snackbar.make(findViewById(R.id.activity_create_recipe_ingredient), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (quantityTxt.getText() == null || quantityTxt.getText().toString().equals("")) {
                    String message = "You need to specify a quantity!";
                    Snackbar.make(findViewById(R.id.activity_create_recipe_ingredient), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                String unit = unitTxt.getText().toString();
                Long quantity = Long.parseLong(quantityTxt.getText().toString());
                recipeIngredient.set_quantity(quantity);
                recipeIngredient.set_unit(unit);
                recipeIngredient.set_recipe_id(recipe_id);
                recipeIngredient.set_ingredient_id(ingredient_id);
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.createRecipeIngredient(recipeIngredient);
                Intent result = new Intent();
                result.putExtra("result", "Added recipe ingredient!");
                setResult(Activity.RESULT_OK, result);
                finish();
        }
    });
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_INGREDIENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ingredientPicked=true;
                ingredient_id = data.getLongExtra("ingredient_id", 0);
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                ingredient = db.getIngredient(ingredient_id);
                recipeIngredientTxt.setText(ingredient.get_name());

            }
        }
    }
}
