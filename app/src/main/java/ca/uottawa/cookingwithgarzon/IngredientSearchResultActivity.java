package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class IngredientSearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search_result);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final long recipe_id = intent.getLongExtra("recipe_id", 0);
        DbHelper dbHelper = DbHelper.getInstance(this);

        ArrayList<Ingredient> result = dbHelper.getIngredients(name);
        Snackbar.make(findViewById(R.id.activity_ingredient_search_result), "Found " + result.size() + " ingredients.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        IngredientArrayAdapter adapter = new IngredientArrayAdapter(this, R.layout.ingredient_item, result);
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
    }
}
