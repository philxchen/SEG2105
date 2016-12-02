package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.Cuisine;

public class CreateOrEditCuisineActivity extends Activity {

    private long cuisine_id;
    private long recipe_id;
    private Cuisine cuisine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_cuisine);

        final Button saveBtn = (Button) findViewById(R.id.saveCuisineBtn);
        final Button deleteBtn = (Button) findViewById(R.id.deleteCuisineBtn);
        final EditText nameTxt = (EditText) findViewById(R.id.createCuisineText);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        Intent intent = getIntent();
        cuisine_id = intent.getLongExtra("cuisine_id", 0);
        recipe_id = intent.getLongExtra("recipe_id", 0);

        if (cuisine_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            cuisine = db.getCuisine(cuisine_id);
            nameTxt.setText(cuisine.get_name());
        }
        else {
            cuisine = new Cuisine();
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameTxt.getText() == null || nameTxt.getText().toString().trim().equals("")) {
                    Snackbar.make(findViewById(R.id.activity_create_cuisine), "You must enter " +
                            "a name for the cuisine.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                String cuisineName = nameTxt.getText().toString();
                cuisine.set_name(cuisineName);
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                Long id = db.createCuisine(cuisine);
                Snackbar.make(findViewById(R.id.activity_create_cuisine), "Created cuisine with id " + id, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent result = new Intent();
                result.putExtra("result", "Added cuisine " + cuisineName);
                result.putExtra("cuisine_id", cuisine.get_id());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = DbHelper.getInstance(getApplicationContext());
                db.deleteCuisine(cuisine);
                finish();
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(CreateOrEditCuisineActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "createCuisineHelp");
                startActivity(helpIntent);
            }
        });
    }
}
