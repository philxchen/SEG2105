package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.IngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.MealTypeArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbContract;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.MealType;

public class MealTypeSearchResultActivity extends Activity {

    private DbHelper dbHelper;
    private ArrayList<MealType> result = new ArrayList<>();
    private MealTypeArrayAdapter adapter;
    private FloatingActionButton createMealTypeBtn;
    private String name;
    private long recipe_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type_search_result);

        createMealTypeBtn = (FloatingActionButton) findViewById(R.id.mealTypeSearchCreateBtn);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        recipe_id = intent.getLongExtra("recipe_id", 0);

        dbHelper = DbHelper.getInstance(this);

        result = dbHelper.getMealTypes();
        Snackbar.make(findViewById(R.id.activity_meal_type_search_result), "Found " + result.size() + " meal types.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        adapter = new MealTypeArrayAdapter(this, R.layout.meal_type_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_mealtypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (recipe_id == 0) {
                    Intent result = new Intent(MealTypeSearchResultActivity.this,
                            CreateOrEditMealTypeActivity.class);
                    MealType picked = (MealType) parent.getItemAtPosition(position);
                    result.putExtra("type_id", picked.get_id());
                    result.putExtra("type_name", picked.get_name());
                    result.putExtra("result", "Picked meal type " + picked.get_name());
                    // result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(result);
                    finish();
                } else {
                    Intent result = new Intent();
                    MealType picked = (MealType) parent.getItemAtPosition(position);
                    result.putExtra("type_id", picked.get_id());
                    result.putExtra("type_name", picked.get_name());
                    result.putExtra("result", "Picked meal type " + picked.get_name());
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });
        createMealTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createMealTypeIntent = new Intent(MealTypeSearchResultActivity.this,
                        CreateOrEditMealTypeActivity.class);
                startActivity(createMealTypeIntent);
            }
        });
    }

    @Override
    public void onResume() {
        result = dbHelper.getMealTypes();
        adapter.clear();
        adapter.addAll(result);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}