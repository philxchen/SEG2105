package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button createBtn = (Button) findViewById(R.id.createBtn);
        final Button searchBtn = (Button) findViewById(R.id.searchBtn);
        final Button favouritesBtn = (Button) findViewById(R.id.favouritesBtn);
        final Button shoppingListBtn = (Button) findViewById(R.id.shoppingCartBtn);
        final Button clearDbBtn = (Button) findViewById(R.id.clearDbBtn);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);


        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectSearchActivity.class));
            }
        };

        View.OnClickListener oclCreateBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectCreateActivity.class));
            }
        };

        View.OnClickListener oclFavouritesBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavouriteRecipeActivity.class));
            }
        };

        View.OnClickListener oclShoppingCartBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
            }
        };

        View.OnClickListener oclClearDbBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper.clearDatabase(getApplicationContext());
            }
        };

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "recipeSearchHelp");
                startActivity(helpIntent);
            }
        });

        searchBtn.setOnClickListener(oclSearchBtn);
        createBtn.setOnClickListener(oclCreateBtn);
        favouritesBtn.setOnClickListener(oclFavouritesBtn);
        shoppingListBtn.setOnClickListener(oclShoppingCartBtn);
        clearDbBtn.setOnClickListener(oclClearDbBtn);
    }
}
