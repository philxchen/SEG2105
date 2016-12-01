package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.MealTypeArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbContract;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.MealType;

public class MealTypeSearchResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type_search_result);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        DbHelper dbHelper = DbHelper.getInstance(this);

        ArrayList<MealType> result = dbHelper.getMealTypes();
        Snackbar.make(findViewById(R.id.activity_meal_type_search_result), "Found " + result.size() + " meal types.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        MealTypeArrayAdapter adapter = new MealTypeArrayAdapter(this, R.layout.meal_type_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_mealtypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent result = new Intent();
                MealType picked = (MealType) parent.getItemAtPosition(position);
                result.putExtra("type_id", picked.get_id());
                result.putExtra("type_name", picked.get_name());
                result.putExtra("result", "Picked meal type " + picked.get_name());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });
    }
}
