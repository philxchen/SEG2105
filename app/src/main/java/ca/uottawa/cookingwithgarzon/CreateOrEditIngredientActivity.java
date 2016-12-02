package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;

public class CreateOrEditIngredientActivity extends AppCompatActivity {

    private long ingredient_id;
    private Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_ingredient);
        final Button saveBtn = (Button) findViewById(R.id.saveIngredientBtn);
        final Button deleteBtn = (Button) findViewById(R.id.deleteIngredientBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.nameTxt);
        final EditText priceTxt = (EditText) findViewById(R.id.priceTxt);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        Intent intent = getIntent();
        ingredient_id = intent.getLongExtra("ingredient_id", 0);

        if (ingredient_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            ingredient = db.getIngredient(ingredient_id);
            nameTxt.setText(ingredient.get_name());
            priceTxt.setText(((Double)ingredient.get_price()).toString());
        }
        else {
            ingredient = new Ingredient();
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameTxt.getText() == null || nameTxt.getText().toString().trim().equals("")) {
                    Snackbar.make(findViewById(R.id.activity_create_cuisine), "You must enter " +
                            "a name for the ingredient.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                String ingredientName = nameTxt.getText().toString();

                Double price;
                if (priceTxt.getText() == null || priceTxt.getText().toString().trim().equals("")) {
                    price = 0.0;
                }
                else {
                    price = Double.parseDouble(priceTxt.getText().toString());
                }
                ingredient.set_name(ingredientName);
                ingredient.set_price(price);
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.createIngredient(ingredient);
                Intent result = new Intent();
                result.putExtra("result", "Added ingredient " + ingredientName);
                result.putExtra("ingredient_id", ingredient.get_id());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.deleteIngredient(ingredient);
                finish();
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(CreateOrEditIngredientActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "createIngredientHelp");
                startActivity(helpIntent);
            }
        });
    }
}
