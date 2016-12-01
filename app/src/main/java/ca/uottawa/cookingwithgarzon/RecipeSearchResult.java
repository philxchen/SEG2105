package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ca.uottawa.cookingwithgarzon.adapter.RecipeArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.*;

import java.util.ArrayList;


public class RecipeSearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        final String ingredient = intent.getStringExtra("ingredient");
        final String cuisine = intent.getStringExtra("cuisine");
        final String type = intent.getStringExtra("type");

        DbHelper dbHelper = DbHelper.getInstance(getApplicationContext());

        ArrayList<Recipe> result = dbHelper.findRecipe(name, ingredient, cuisine, type);
        Snackbar.make(findViewById(R.id.activity_recipe_search_result), "Found " + result.size() + " recipes.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        RecipeArrayAdapter adapter = new RecipeArrayAdapter(this, R.layout.recipe_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_recipes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(RecipeSearchResult.this, RecipeViewActivity.class);
                Recipe picked = (Recipe) parent.getItemAtPosition(position);
                intent.putExtra("recipe_id", picked.get_id());
                intent.putExtra("recipe_name", picked.get_name());
                intent.putExtra("result", "Opening recipe " + picked.get_name());
                startActivity(intent);
            }
        });

        dbHelper.close();
    }
}
