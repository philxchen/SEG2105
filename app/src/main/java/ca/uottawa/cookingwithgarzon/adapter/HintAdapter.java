package ca.uottawa.cookingwithgarzon.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import java.util.List;

/**
 * To be able to set hints in the spinner classes
 * Workaround found here: https://yakivmospan.wordpress.com/2014/03/31/spinner-hint/
 *
 * IMPORTANT: When setting object list, set the hint to be the last object!!!
 */


public class HintAdapter extends ArrayAdapter<String> {

    public HintAdapter(Context theContext, List<String> objects, int theLayoutResId) {
        super(theContext, theLayoutResId, android.R.id.text1, objects);
    }

    @Override
    public int getCount() {
        // don't display last item. It is used as hint.
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}
