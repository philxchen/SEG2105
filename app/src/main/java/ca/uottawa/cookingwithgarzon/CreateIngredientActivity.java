package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;

public class CreateIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredient);
        final Button saveBtn = (Button) findViewById(R.id.saveIngredientBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.nameTxt);
        final EditText priceTxt = (EditText) findViewById(R.id.priceTxt);

        nameTxt.setText("Test");

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientName = nameTxt.getText().toString();
                Double price = Double.parseDouble(priceTxt.getText().toString());
                ca.uottawa.cookingwithgarzon.model.Ingredient newIngredient = new ca.uottawa.cookingwithgarzon.model.Ingredient();
                newIngredient.set_name(ingredientName);
                newIngredient.set_price(price);
                DbHelper db = new DbHelper(getApplicationContext());
                Long id = db.createIngredient(newIngredient);
                Snackbar.make(findViewById(R.id.activity_create_ingredient), "Created ingredient with id " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
//                Intent result = new Intent();
//                result.putExtra("result", "Added ingredient " + ingredientName);
//                setResult(Activity.RESULT_OK, result);
//                finish();
            }
        });

    }
}
