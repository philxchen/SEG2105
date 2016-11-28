package ca.uottawa.cookingwithgarzon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.uottawa.cookingwithgarzon.R;
import ca.uottawa.cookingwithgarzon.model.Cuisine;

/**
 * Created by joel on 28/11/16.
 */

public class CuisineArrayAdapter extends ArrayAdapter<Cuisine> {
    public CuisineArrayAdapter(Context context, int resource, List<Cuisine> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Cuisine cuisine = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cuisine_item, parent, false);
        }
        // Lookup view for data population
        TextView cuisineName = (TextView) convertView.findViewById(R.id.cuisine_item_name);
        // Populate the data into the template view using the data object
        cuisineName.setText(cuisine.get_name());
        // Return the completed view to render on screen
        return convertView;

    }
}
