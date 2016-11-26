package ca.uottawa.cookingwithgarzon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.*;

import java.util.ArrayList;


public class SearchResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String name = savedInstanceState.getString("name");
        final String ingredient = savedInstanceState.getString("ingredient");
        final String cuisine = savedInstanceState.getString("cuisine");
        final String type = savedInstanceState.getString("type");

        DbHelper dbHelper = new DbHelper(this);

        ArrayList<Recipe> result = dbHelper.findRecipe(name, ingredient, cuisine, type);

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
