package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_create);


        final Button createRecipeBtn = (Button) findViewById(R.id.createRecipeBtn);
        final Button createIngredientBtn = (Button) findViewById(R.id.createIngredientBtn);


        View.OnClickListener oclCreateIngredientBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateIngredient.class));
            }
        };

        View.OnClickListener oclCreateRecipeBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SelectCreateActivity.this, CreateRecipe.class));
            }
        };

        createRecipeBtn.setOnClickListener(oclCreateRecipeBtn);
        createIngredientBtn.setOnClickListener(oclCreateIngredientBtn);
    }
}
