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
import ca.uottawa.cookingwithgarzon.model.Cuisine;
import ca.uottawa.cookingwithgarzon.model.MealType;
import ca.uottawa.cookingwithgarzon.model.Recipe;

/**
 * Created by joel on 28/11/16.
 */

public class RecipeArrayAdapter extends ArrayAdapter<Recipe> {

    DbHelper dbHelper;
    public RecipeArrayAdapter(Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        dbHelper = DbHelper.getInstance(getContext());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Recipe recipe = getItem(position);
        if (recipe == null) return null;

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_item, parent, false);
        }
        // Lookup view for data population
        TextView recipeName = (TextView) convertView.findViewById(R.id.recipe_item_name);
        TextView recipeCuisine = (TextView) convertView.findViewById(R.id.recipe_item_cuisine);
        TextView recipeMealType = (TextView) convertView.findViewById(R.id.recipe_item_meal_type);

        // Populate the data into the template view using the data object
        Cuisine cuisine = dbHelper.getCuisine(recipe.get_cuisine_id());
        MealType type = dbHelper.getMealType(recipe.get_meal_type_id());
        recipeName.setText(recipe.get_name());
        if (cuisine != null) {
            recipeCuisine.setText(cuisine.get_name());
        }
        if (type != null) {
            recipeMealType.setText(type.get_name());
        }

        // Return the completed view to render on screen
        return convertView;
    }

}
