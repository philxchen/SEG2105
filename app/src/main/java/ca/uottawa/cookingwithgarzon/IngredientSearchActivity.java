package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class IngredientSearchActivity extends AppCompatActivity {
    static final int PICK_INGREDIENT_REQUEST = 1;
    private long ingredient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search);

        Button searchBtn = (Button) findViewById(R.id.search_ingredient_button);

        final EditText ingredientNameTxt = (EditText) findViewById(R.id.ingredient_search_input);

        searchBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IngredientSearchActivity.this, IngredientSearchResultActivity.class);
                intent.putExtra("name", ingredientNameTxt.getText().toString());
                startActivityForResult(intent, PICK_INGREDIENT_REQUEST);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_INGREDIENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Intent result = new Intent();
                ingredient_id = data.getLongExtra("ingredient_id", 0);
                result.putExtra("ingredient_id", ingredient_id);
                result.putExtra("ingredient_name", data.getStringExtra("ingredient_name"));
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        }
    }
}
