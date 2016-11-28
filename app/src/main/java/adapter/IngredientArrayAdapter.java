package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.uottawa.cookingwithgarzon.R;
import ca.uottawa.cookingwithgarzon.model.Ingredient;

/**
 * Created by joel on 28/11/16.
 */

public class IngredientArrayAdapter extends ArrayAdapter<Ingredient> {
    public IngredientArrayAdapter(Context context, int resource, List<Ingredient> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Ingredient ingredient = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_ingredient_row, parent, false);
        }
        // Lookup view for data population
        TextView ingredientName = (TextView) convertView.findViewById(R.id.ingredient_row_name);
        TextView ingredientPrice = (TextView) convertView.findViewById(R.id.ingredient_row_price);
        // Populate the data into the template view using the data object
        ingredientName.setText(ingredient.get_name());
        ingredientPrice.setText(((Double)ingredient.get_price()).toString());
        // Return the completed view to render on screen
        return convertView;

    }
}
