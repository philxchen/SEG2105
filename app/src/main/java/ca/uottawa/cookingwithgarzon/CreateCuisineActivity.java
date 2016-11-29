package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Cuisine;

public class CreateCuisineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cuisine);

        final Button saveBtn = (Button) findViewById(R.id.saveCuisineBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.createCuisineText);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cuisineName = nameTxt.getText().toString();
                Cuisine newCuisine= new Cuisine();
                newCuisine.set_name(cuisineName);
                DbHelper db = new DbHelper(getApplicationContext());
                Long id = db.createCuisine(newCuisine);
                Snackbar.make(findViewById(R.id.activity_create_cuisine), "Created cuisine with id " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent result = new Intent();
                result.putExtra("result", "Added cuisine " + cuisineName);
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        setContentView(R.layout.content_create_recipe);

        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficulty_select);
        ArrayAdapter<CharSequence> difficultyAdapter = ArrayAdapter.createFromResource(this,
                R.array.difficulty_array, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);

        Spinner mealTypeSpinner = (Spinner) findViewById(R.id.meal_type_select);
        ArrayAdapter<CharSequence> mealTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.meal_type_array, android.R.layout.simple_spinner_item);
        mealTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mealTypeSpinner.setAdapter(mealTypeAdapter);

        Spinner cuisineSpinner = (Spinner) findViewById(R.id.cuisine_select);
        ArrayAdapter<CharSequence> cuisineAdapter = ArrayAdapter.createFromResource(this,
                R.array.cuisine_array, android.R.layout.simple_spinner_item);
        cuisineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cuisineSpinner.setAdapter(cuisineAdapter);
    }
}
