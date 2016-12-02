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


        /** if app crashes during recipe creation (Hopefully never) then invalid recipes may
         * exist in database. Just delete them here to make sure.
        */
        DbHelper db = DbHelper.getInstance(getApplicationContext());
        db.deleteInvalidRecipes();
        db.close();

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
                helpIntent.putExtra("helpTextName", "mainScreenHelp");
                startActivity(helpIntent);
            }
        });

        searchBtn.setOnClickListener(oclSearchBtn);
        createBtn.setOnClickListener(oclCreateBtn);
        favouritesBtn.setOnClickListener(oclFavouritesBtn);
        shoppingListBtn.setOnClickListener(oclShoppingCartBtn);
        clearDbBtn.setOnClickListener(oclClearDbBtn);
        clearDbBtn.setVisibility(View.INVISIBLE);
        clearDbBtn.setClickable(false);
    }
}
