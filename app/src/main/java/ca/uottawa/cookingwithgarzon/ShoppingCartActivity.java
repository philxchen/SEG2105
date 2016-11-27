package ca.uottawa.cookingwithgarzon;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ca.uottawa.cookingwithgarzon.helper.DbContract.Ingredient;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;

public class ShoppingCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        IngredientArrayAdapter adapter = new IngredientArrayAdapter(this, getIngredients());
        ListView cartList = (ListView) findViewById(R.id.shopping_cart_list);
        cartList.setAdapter(adapter);
        cartList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private String[] getIngredients() {
        DbHelper mDbHelper = new DbHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                Ingredient._ID,
                Ingredient.COLUMN_INGREDIENT_NAME,
                Ingredient.COLUMN_INGREDIENT_PRICE
        };

        String selection = Ingredient.COLUMN_INGREDIENT_NAME + " = ? ";

        Cursor c = db.query(
                Ingredient.TABLE_NAME,
                projection,
                selection,
                null,
                null,
                null,
                Ingredient.COLUMN_INGREDIENT_NAME + "DESC"
        );

        // add all ingredients to a list
        final ArrayList<String> ingredients = new ArrayList<>();
        c.moveToFirst();
        while (!c.isLast()) {
            ingredients.add(c.getString(c.getColumnIndexOrThrow(Ingredient.COLUMN_INGREDIENT_NAME)));
            c.moveToNext();
        }
        return (String[]) ingredients.toArray();
    }

}
