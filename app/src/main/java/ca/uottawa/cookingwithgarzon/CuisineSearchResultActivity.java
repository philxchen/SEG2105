package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.CuisineArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Cuisine;


public class CuisineSearchResultActivity extends AppCompatActivity {

    private final int MODIFY_CUISINE_REQUEST = 1;

    private ArrayList<Cuisine> result = new ArrayList<>();
    private FloatingActionButton createCuisineBtn;
    private long recipe_id;
    private String name;
    CuisineArrayAdapter adapter;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_search_result);
        Intent intent = getIntent();
        createCuisineBtn = (FloatingActionButton) findViewById(R.id.cuisineSearchCreateBtn);
        recipe_id = intent.getLongExtra("recipe_id", 0);
        name = intent.getStringExtra("name");

        dbHelper = DbHelper.getInstance(this);

        result = dbHelper.getCuisines();
        Snackbar.make(findViewById(R.id.activity_cuisine_search_result),
                "Found " + result.size() + " cuisines.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        adapter = new CuisineArrayAdapter(this, R.layout.cuisine_item, result);
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
                    // result.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        createCuisineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createCuisineIntent = new Intent(CuisineSearchResultActivity.this,
                        CreateOrEditCuisineActivity.class);
                startActivity(createCuisineIntent);
            }
        });
    }

    @Override
    public void onResume() {
        result = dbHelper.getCuisines();
        adapter.clear();
        adapter.addAll(result);
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}
