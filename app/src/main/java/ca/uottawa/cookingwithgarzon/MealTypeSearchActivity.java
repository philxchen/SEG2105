package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MealTypeSearchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type_search);

        Button searchBtn = (Button) findViewById(R.id.meal_type_search_button);

        final EditText mealTypeName = (EditText) findViewById(R.id.meal_type_search_input);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealTypeSearchActivity.this, MealTypeSearchActivity.class);
                intent.putExtra("name", mealTypeName.getText().toString());
                startActivity(intent);
            }
        });
    }
}
