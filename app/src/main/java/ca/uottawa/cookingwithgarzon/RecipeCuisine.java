package ca.uottawa.cookingwithgarzon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Cuisine;


public class RecipeCuisine extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_cuisine);
       }

        final Button saveBtn = (Button) findViewById(R.id.saveCuisineButton);
        final EditText nameTxt = (EditText)findViewById(R.id.editText);

        View.OnClickListener oclSaveBtn= new View.OnClickListener() {
            @Override

                public void onClick(View view) {
                    String ingredientName = nameTxt.getText().toString();
                    DbHelper db = new DbHelper(getApplicationContext());
                    Cuisine newCuisine = new Cuisine();
                    newCuisine.set_name(nameTxt.getText().toString());
                    db.createCuisine(newCuisine);
                }
        };

         saveBtn.setOnClickListener(oclSaveBtn);

    }
}

