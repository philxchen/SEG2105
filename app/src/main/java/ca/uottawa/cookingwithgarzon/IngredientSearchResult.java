package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.adapter.IngredientArrayAdapter;


public class IngredientSearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search_result);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        DbHelper dbHelper = new DbHelper(this);

        ArrayList<Ingredient> result = dbHelper.getIngredients(name);
        Snackbar.make(findViewById(R.id.activity_ingredient_search_result), "Found " + result.size() + " ingredients.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        IngredientArrayAdapter adapter = new IngredientArrayAdapter(this, R.layout.ingredient_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_ingredients);
        listView.setAdapter(adapter);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



    }
}
