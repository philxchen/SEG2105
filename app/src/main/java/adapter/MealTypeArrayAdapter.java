package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.uottawa.cookingwithgarzon.R;
import ca.uottawa.cookingwithgarzon.helper.DbContract;
import ca.uottawa.cookingwithgarzon.model.Cuisine;
import ca.uottawa.cookingwithgarzon.model.MealType;

/**
 * Created by joel on 28/11/16.
 */

public class MealTypeArrayAdapter extends ArrayAdapter<MealType> {
    public MealTypeArrayAdapter(Context context, int resource, List<MealType> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        MealType type = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.meal_type_item, parent, false);
        }
        // Lookup view for data population
        TextView mealTypeName = (TextView) convertView.findViewById(R.id.meal_type_item_name);
        // Populate the data into the template view using the data object
        mealTypeName.setText(type.get_name());
        // Return the completed view to render on screen
        return convertView;

    }
}
