package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectCreateActivity extends AppCompatActivity {

    final int CREATE_RECIPE_REQUEST = 1;
    final int CREATE_INGREDIENT_REQUEST = 2;
    final int CREATE_MEALTYPE_REQUEST = 3;
    final int CREATE_CUISINE_REQUEST = 4;
    public static Activity selectCreateActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_create);
        selectCreateActivity = this; // used to destroy this activity later.
        //Create Buttons
        final Button createRecipeBtn = (Button) findViewById(R.id.createRecipeBtn);
        final Button createIngredientBtn = (Button) findViewById(R.id.createIngredientBtn);
        final Button createMealBtn = (Button) findViewById(R.id.createMealBtn);
        final Button createCuisineBtn = (Button) findViewById(R.id.createCuisineBtn);

        //On-click Functions
        createRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectCreateActivity.this, CreateOrEditRecipeActivity.class),
                        CREATE_RECIPE_REQUEST);
            }
        });

        createIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectCreateActivity.this, CreateIngredientActivity.class),
                        CREATE_INGREDIENT_REQUEST);
            }
        });

        createMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectCreateActivity.this, CreateOrEditMealTypeActivity.class),
                        CREATE_MEALTYPE_REQUEST);
            }
        });

        createCuisineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SelectCreateActivity.this, CreateOrEditCuisineActivity.class),
                        CREATE_CUISINE_REQUEST);
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
