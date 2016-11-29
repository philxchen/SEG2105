package ca.uottawa.cookingwithgarzon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ca.uottawa.cookingwithgarzon.R;
import ca.uottawa.cookingwithgarzon.model.Cuisine;
import ca.uottawa.cookingwithgarzon.model.Step;

/**
 * Created by joel on 28/11/16.
 */

public class StepArrayAdapter extends ArrayAdapter<Step> {
    public StepArrayAdapter(Context context, int resource, List<Step> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Step step = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.step_item, parent, false);
        }
        // Lookup view for data population
        TextView stepNumber = (TextView) convertView.findViewById(R.id.step_item_number);
        TextView stepInstruction = (TextView) convertView.findViewById(R.id.step_item_instruction);
        // Populate the data into the template view using the data object
        stepNumber.setText(step.get_stepNumber());
        stepInstruction.setText(step.get_instruction());
        // Return the completed view to render on screen
        return convertView;

    }
}
