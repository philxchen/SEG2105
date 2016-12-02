package ca.uottawa.cookingwithgarzon;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.adapter.ShoppingCartArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;

public class ShoppingCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);


        final DbHelper dbHelper = DbHelper.getInstance(this);
        final ArrayList<RecipeIngredient> recipeIngredients = dbHelper.getShoppingCartIngredients();
        final ListView listView = (ListView) findViewById(R.id.shopping_cart_list);
        listView.setAdapter(new ShoppingCartArrayAdapter(this, R.layout.shopping_cart_list_item, recipeIngredients));

        final FloatingActionButton clearCartBtn = (FloatingActionButton) findViewById(R.id.clearCartBtn);
        clearCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.clearShoppingCartIngredient();
                recipeIngredients.clear();
                listView.invalidateViews();
                Snackbar.make(view, "Your cart cleared.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
