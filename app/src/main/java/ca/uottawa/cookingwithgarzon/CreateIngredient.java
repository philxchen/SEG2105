package ca.uottawa.cookingwithgarzon;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;

public class CreateIngredient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ingredient);

        final Button saveBtn = (Button) findViewById(R.id.saveIngredientBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.ingredientTxt);
        final EditText priceTxt = (EditText) findViewById(R.id.priceTxt);

        View.OnClickListener oclSaveBtn= new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientName = nameTxt.getText().toString();
                Double price = Double.parseDouble(priceTxt.getText().toString());
                ca.uottawa.cookingwithgarzon.model.Ingredient newIngredient = new ca.uottawa.cookingwithgarzon.model.Ingredient();
                newIngredient.set_name(ingredientName);
                newIngredient.set_price(price);
                DbHelper db = new DbHelper(getApplicationContext());
                db.createIngredient(newIngredient);
            }
        };

        saveBtn.setOnClickListener(oclSaveBtn);

    }

}
