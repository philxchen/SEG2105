package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.MealType;

public class CreateOrEditMealTypeActivity extends AppCompatActivity {

    private long mealtype_id;
    private MealType mealtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        final Button saveBtn = (Button) findViewById(R.id.saveMealBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.createMealText);

        Intent intent = getIntent();
        mealtype_id = intent.getLongExtra("type_id", 0);

        if (mealtype_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            mealtype = db.getMealType(mealtype_id);
            nameTxt.setText(mealtype.get_name());
        }
        else {
            mealtype = new MealType();
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mealTypeName = nameTxt.getText().toString();
                mealtype.set_name(mealTypeName);
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.createMealType(mealtype);
                Intent result = new Intent();
                result.putExtra("result", "Added meal type " + mealTypeName);
                result.putExtra("type_id", mealtype.get_id());
                result.putExtra("type_name", mealtype.get_name());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

    }
}
