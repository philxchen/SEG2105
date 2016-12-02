package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CuisineSearchActivity extends AppCompatActivity {

    private final int GET_CUISINE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_search);

        Button searchBtn = (Button) findViewById(R.id.search_cuisine_button);

        final EditText cuisineNameTxt = (EditText) findViewById(R.id.cuisine_search_input);
        Intent intent = getIntent();
        final long recipe_id = intent.getLongExtra("recipe_id", 0);

        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuisineSearchActivity.this, CuisineSearchResultActivity.class);
                intent.putExtra("name", cuisineNameTxt.getText().toString());
                intent.putExtra("recipe_id", recipe_id);
                startActivityForResult(intent, GET_CUISINE_REQUEST);
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_CUISINE_REQUEST:
                if (resultCode == RESULT_OK) {
                    setResult(Activity.RESULT_OK, data);
                    finish();
                }
                break;
        }
    }
}
