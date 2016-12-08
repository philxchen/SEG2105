package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;

/**
 * Activity class for the main activity
 */

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

        //Objects in the activity
        final Button createBtn = (Button) findViewById(R.id.createBtn);
        final Button searchBtn = (Button) findViewById(R.id.searchBtn);
        final Button favouritesBtn = (Button) findViewById(R.id.favouritesBtn);
        final Button shoppingListBtn = (Button) findViewById(R.id.shoppingCartBtn);
        final Button clearDbBtn = (Button) findViewById(R.id.clearDbBtn);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        /** Onclick listeners for the buttons **/

        //Sends user to search menu
        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectSearchActivity.class));
            }
        };

        //Sends user to create stage
        View.OnClickListener oclCreateBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectCreateActivity.class));
            }
        };

        //Sends user to favourites activity
        View.OnClickListener oclFavouritesBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavouriteRecipeActivity.class));
            }
        };

        //Sends user to shopping cart activity
        View.OnClickListener oclShoppingCartBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShoppingCartActivity.class));
            }
        };

        //Clears database: NOTE: Not visible currently on screen
        View.OnClickListener oclClearDbBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper.clearDatabase(getApplicationContext());
            }
        };

        //Sends user to help page for respective activity
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
