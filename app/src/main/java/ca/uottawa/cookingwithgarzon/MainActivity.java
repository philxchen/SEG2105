package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button createRecipeBtn = (Button) findViewById(R.id.createRecipeBtn);
        final Button searchBtn = (Button) findViewById(R.id.searchBtn);
        final Button favouritesBtn = (Button) findViewById(R.id.favouritesBtn);
        final Button shoppingListBtn = (Button) findViewById(R.id.shoppingCartBtn);


        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RecipeSearch.class));
            }
        };

        View.OnClickListener oclCreateRecipeBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateRecipe.class));
            }
        };

        View.OnClickListener oclFavouritesBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FavoriteRecipe.class));
            }
        };

        View.OnClickListener oclShoppingCartBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShoppingCart.class));
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);
        createRecipeBtn.setOnClickListener(oclCreateRecipeBtn);
        favouritesBtn.setOnClickListener(oclFavouritesBtn);
        shoppingListBtn.setOnClickListener(oclShoppingCartBtn);


    }
}
