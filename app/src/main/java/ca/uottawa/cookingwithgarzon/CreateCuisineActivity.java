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
import ca.uottawa.cookingwithgarzon.model.Cuisine;

public class CreateCuisineActivity extends AppCompatActivity {

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

    }
}
