package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.CuisineArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Cuisine;


public class CuisineSearchResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_search_result);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");

        DbHelper dbHelper = DbHelper.getInstance(this);

        ArrayList<Cuisine> result = dbHelper.getCuisines();
        Snackbar.make(findViewById(R.id.activity_cuisine_search_result), "Found " + result.size() + " meal types.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        CuisineArrayAdapter adapter = new CuisineArrayAdapter(this, R.layout.cuisine_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_cuisines);
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
