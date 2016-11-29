package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbContract;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.MealType;

public class CreateMealActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        final Button saveBtn = (Button) findViewById(R.id.saveMealBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.createMealText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mealTypeName = nameTxt.getText().toString();
                MealType newMeal = new MealType();
                newMeal.set_name(mealTypeName);
                DbHelper db = new DbHelper(getApplicationContext());
                db.createMealType(newMeal);
                Intent result = new Intent();
                result.putExtra("result", "Added meal type " + mealTypeName);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

    }
}
