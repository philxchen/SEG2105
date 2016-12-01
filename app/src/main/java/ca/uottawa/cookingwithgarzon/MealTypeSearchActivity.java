package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.MealTypeArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.MealType;

import static ca.uottawa.cookingwithgarzon.R.layout.meal_type_item;

public class MealTypeSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type_search);

        Button searchBtn = (Button) findViewById(R.id.meal_type_search_button);

        final EditText mealTypeNameTxt = (EditText) findViewById(R.id.meal_type_search_input);

        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealTypeSearchActivity.this, MealTypeSearchResultActivity.class);
                intent.putExtra("name", mealTypeNameTxt.getText().toString());
                startActivity(intent);
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);

    }
}


