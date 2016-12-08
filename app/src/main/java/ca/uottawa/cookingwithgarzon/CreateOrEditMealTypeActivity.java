package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.MealType;

/**
 * Activity class to create or edit a meal type
 */

public class CreateOrEditMealTypeActivity extends AppCompatActivity {

    //Instance variables
    private long mealtype_id;
    private MealType mealtype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_meal);

        //Objects in the activity
        final Button saveBtn = (Button) findViewById(R.id.saveMealBtn);
        final Button deleteBtn = (Button) findViewById(R.id.deleteMealTypeBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.createMealText);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        //Obtaining the intent
        Intent intent = getIntent();
        mealtype_id = intent.getLongExtra("type_id", 0);

        //Displays an existing meal type if editing, else a blank page to create a new meal type
        if (mealtype_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            mealtype = db.getMealType(mealtype_id);
            nameTxt.setText(mealtype.get_name());
        }
        else {
            mealtype = new MealType();
        }

        /** On-click listeners **/

        //Creates a new meal type if creating, or edits the information of an existing meal type in the database
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameTxt.getText() == null || nameTxt.getText().toString().trim().equals("")) {
                    Snackbar.make(findViewById(R.id.activity_create_cuisine), "You must enter " +
                            "a name for the mealtype!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
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

        //Deletes the meal type currently being editing in the database
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.deleteMealType(mealtype);
                finish();
            }
        });

        //Opens a help page for the respective activity
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(CreateOrEditMealTypeActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "createMealHelp");
                startActivity(helpIntent);
            }
        });
    }
}
