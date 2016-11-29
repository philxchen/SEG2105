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

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        DbHelper dbHelper = DbHelper.getInstance(this);

        ArrayList<MealType> result = dbHelper.getMealTypes();
        Snackbar.make(findViewById(R.id.activity_meal_type_search_result), "Found " + result.size() + " cuisines.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        MealTypeArrayAdapter adapter = new MealTypeArrayAdapter(this, meal_type_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_mealtypes);
        listView.setAdapter(adapter);
    }
}


