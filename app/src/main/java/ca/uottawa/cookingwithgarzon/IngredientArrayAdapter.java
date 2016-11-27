package ca.uottawa.cookingwithgarzon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by philxchen on 27/11/16.
 */

public class IngredientArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] names;

    public IngredientArrayAdapter(Context context, String[] names) {
        super(context, R.layout.content_shopping_cart);
        this.context = context;
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.content_shopping_cart, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.ingredient_name);
        nameTextView.setText(names[position]);
        return rowView;
    }
}
