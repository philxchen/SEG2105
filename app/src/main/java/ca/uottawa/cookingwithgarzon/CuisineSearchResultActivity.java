package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        final long recipe_id = intent.getLongExtra("recipe_id", 0);
        final String name = intent.getStringExtra("name");

        DbHelper dbHelper = DbHelper.getInstance(this);

        ArrayList<Cuisine> result = dbHelper.getCuisines();
        Snackbar.make(findViewById(R.id.activity_cuisine_search_result), "Found " + result.size() + " cuisines.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        CuisineArrayAdapter adapter = new CuisineArrayAdapter(this, R.layout.cuisine_item, result);
        ListView listView = (ListView) findViewById(R.id.listview_cuisines);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (recipe_id == 0) {
                    Intent result = new Intent(CuisineSearchResultActivity.this,
                            CreateOrEditCuisineActivity.class);
                    Cuisine picked = (Cuisine) parent.getItemAtPosition(position);
                    result.putExtra("cuisine_id", picked.get_id());
                    result.putExtra("cuisine_name", picked.get_name());
                    result.putExtra("result", "Picked cuisine " + picked.get_name());
                    result.putExtra("result", "Picked meal type " + picked.get_name());
                    result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(result);
                    finish();
                } else {
                    Intent result = new Intent();
                    Cuisine picked = (Cuisine) parent.getItemAtPosition(position);
                    result.putExtra("cuisine_id", picked.get_id());
                    result.putExtra("cuisine_name", picked.get_name());
                    result.putExtra("result", "Picked cuisine " + picked.get_name());
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });

    }
}
