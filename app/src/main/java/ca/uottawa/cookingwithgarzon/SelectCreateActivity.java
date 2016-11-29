package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.app.Activity.RESULT_OK;

public class SelectCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_create);

        //Create Buttons
        final Button createRecipeBtn = (Button) findViewById(R.id.createRecipeBtn);
        final Button createIngredientBtn = (Button) findViewById(R.id.createIngredientBtn);
        final Button createMealBtn = (Button) findViewById(R.id.createMealBtn);
        final Button createCuisineBtn = (Button) findViewById(R.id.createCuisineBtn);

        //On-click Functions
        createRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateRecipe.class));
            }
        });

        createIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateIngredientActivity.class));
            }
        });

        createMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateMealActivity.class));
            }
        });

        createCuisineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateCuisineActivity.class));
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
