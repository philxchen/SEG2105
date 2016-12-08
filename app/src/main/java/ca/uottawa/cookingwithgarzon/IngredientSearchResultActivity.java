package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.adapter.IngredientArrayAdapter;

/**
 * Activity class for Ingredient search result
 */

public class IngredientSearchResultActivity extends AppCompatActivity {

    //Instance variables
    private DbHelper dbHelper;
    private ArrayList<Ingredient> result = new ArrayList<>();
    private IngredientArrayAdapter adapter;
    private FloatingActionButton createIngredientBtn;
    private String name;
    private long recipe_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search_result);
        createIngredientBtn = (FloatingActionButton) findViewById(R.id.ingredientSearchCreateBtn);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        recipe_id = intent.getLongExtra("recipe_id", 0);
        dbHelper = DbHelper.getInstance(this);

        /*
        Displays results of the search query, and creates a list array that for each object
        allows the user to click on it and display the ingredient
         */
        result = dbHelper.getIngredients(name);
        Snackbar.make(findViewById(R.id.activity_ingredient_search_result),
                "Found " + result.size() + " ingredients.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        adapter = new IngredientArrayAdapter(this, R.layout.ingredient_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_ingredients);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (recipe_id == 0) {
                    Intent result = new Intent(IngredientSearchResultActivity.this, CreateOrEditIngredientActivity.class);
                    Ingredient picked = (Ingredient) parent.getItemAtPosition(position);
                    result.putExtra("ingredient_id", picked.get_id());
                    result.putExtra("ingredient_name", picked.get_name());
                    result.putExtra("result", "Editing ingredient " + picked.get_name());
                    startActivity(result);
                    IngredientSearchActivity.ingredientSearchActivity.finish();
                    finish();
                } else {
                    Intent result = new Intent();
                    Ingredient picked = (Ingredient) parent.getItemAtPosition(position);
                    result.putExtra("ingredient_id", picked.get_id());
                    result.putExtra("ingredient_name", picked.get_name());
                    result.putExtra("result", "Added ingredient " + picked.get_name());
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });

        //Button when pressed sends user to create a new ingredient
        createIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createIngredientIntent = new Intent(IngredientSearchResultActivity.this,
                        CreateOrEditIngredientActivity.class);
                startActivity(createIngredientIntent);
            }
        });
    }

    @Override
    public void onResume() {
        result = dbHelper.getIngredients(name);
        adapter.clear();
        adapter.addAll(result);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
