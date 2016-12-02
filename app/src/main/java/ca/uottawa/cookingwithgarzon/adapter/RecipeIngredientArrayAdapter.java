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
 * Created by joel on 28/11/16.
 */

public class RecipeIngredientArrayAdapter extends ArrayAdapter<RecipeIngredient> {

    DbHelper dbHelper;
    public RecipeIngredientArrayAdapter(Context context, int resource, List<RecipeIngredient> objects) {
        super(context, resource, objects);
        dbHelper = DbHelper.getInstance(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        RecipeIngredient recipeIngredient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_ingredient_item, parent, false);
        }
        // Lookup view for data population
        TextView recipeIngredientName = (TextView) convertView.findViewById(R.id.recipe_ingredient_item_name);
        TextView recipeIngredientQuantity = (TextView) convertView.findViewById(R.id.recipe_ingredient_quantity);
        TextView recipeIngredientUnit = (TextView) convertView.findViewById(R.id.recipe_ingredient_unit);
        // Populate the data into the template view using the data object
        Ingredient ingredient = dbHelper.getIngredient(recipeIngredient.get_ingredient_id());
        recipeIngredientName.setText(ingredient.get_name());
        recipeIngredientQuantity.setText(String.valueOf(recipeIngredient.get_quantity()));
        recipeIngredientUnit.setText(recipeIngredient.get_unit());
        // Return the completed view to render on screen
        return convertView;
    }

}
