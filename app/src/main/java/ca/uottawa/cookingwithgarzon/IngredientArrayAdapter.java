package ca.uottawa.cookingwithgarzon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ca.uottawa.cookingwithgarzon.model.Ingredient;

/**
 * Created by philxchen on 27/11/16.
 */

public class IngredientArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<Ingredient> ingredients;

    public IngredientArrayAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, R.layout.content_shopping_cart);
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.shopping_cart_list_item, parent, false);
        TextView nameTextView = (TextView) rowView.findViewById(R.id.ingredient_name);
        nameTextView.setText(ingredients.get(position).get_name());
        return rowView;
    }
}
