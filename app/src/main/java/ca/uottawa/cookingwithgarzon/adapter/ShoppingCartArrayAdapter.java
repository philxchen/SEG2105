package ca.uottawa.cookingwithgarzon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.uottawa.cookingwithgarzon.R;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Ingredient;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;

/**
 * Created by philxchen on 12/1/16.
 */

public class ShoppingCartArrayAdapter extends ArrayAdapter<RecipeIngredient> {

    DbHelper dbHelper;
    public ShoppingCartArrayAdapter(Context context, int resource, List<RecipeIngredient> ingredients) {
        super(context, resource, ingredients);
        dbHelper = DbHelper.getInstance(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecipeIngredient recipeIngredient = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.shopping_cart_list_item, parent, false);
        }
        TextView ingredientName = (TextView) convertView.findViewById(R.id.shopping_cart_ingredient_name);
        TextView ingredientQuantity = (TextView) convertView.findViewById(R.id.shopping_cart_ingredient_quantity);
        TextView ingredientUnit = (TextView) convertView.findViewById(R.id.shopping_cart_ingredient_unit);

        Ingredient ingredient = dbHelper.getIngredient(recipeIngredient.get_ingredient_id());
        ingredientName.setText(ingredient.get_name());
        ingredientQuantity.setText(String.valueOf(recipeIngredient.get_quantity()));
        ingredientUnit.setText(recipeIngredient.get_unit());
        return convertView;
    }
}
