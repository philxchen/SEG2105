package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CuisineSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine_search);

        Button searchBtn = (Button) findViewById(R.id.search_cuisine_button);

        final EditText cuisineNameTxt = (EditText) findViewById(R.id.cuisine_search_input);

        View.OnClickListener oclSearchBtn = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuisineSearchActivity.this, CuisineSearchResultActivity.class);
                intent.putExtra("name", cuisineNameTxt.getText().toString());
                startActivity(intent);
            }
        };

        searchBtn.setOnClickListener(oclSearchBtn);
    }
}
