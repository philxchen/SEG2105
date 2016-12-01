package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.RecipeIngredient;
import ca.uottawa.cookingwithgarzon.model.Step;

import static ca.uottawa.cookingwithgarzon.R.id.recipeIngredientTxt;
import static ca.uottawa.cookingwithgarzon.R.id.stepInstructionTxt;

public class CreateOrEditStepActivity extends AppCompatActivity {


    static final int PICK_STEP_REQUEST = 1;

    private EditText stepInstructionTxt;
    private EditText stepTimeTxt;
    private Button saveStepBtn;
    private long recipe_id;
    private long step_id;
    private Step newStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_step);

        stepInstructionTxt = (EditText) findViewById(R.id.stepInstructionTxt);
        stepTimeTxt = (EditText) findViewById(R.id.stepTimeTxt);
        saveStepBtn = (Button) findViewById(R.id.saveStepBtn);

        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);
        step_id = intent.getLongExtra("step_id", -1);

        if (step_id != -1) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            step = db.getStep(step_id);
        }


        saveStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepInstructionTxt.getText() == null || stepInstructionTxt.getText().toString().equals("")) {
                    String message = "You need to write an instruction for this step!";
                    Snackbar.make(findViewById(R.id.activity_create_recipe_ingredient), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else {
                    String instruction = stepInstructionTxt.getText().toString();
                    Long quantity = Long.parseLong(quantityTxt.getText().toString()); //TODO crash if null
                    RecipeIngredient newRecipeIngredient = new RecipeIngredient();
                    newRecipeIngredient.set_quantity(quantity);
                    newRecipeIngredient.set_unit(unit);
                    newRecipeIngredient.set_recipe_id(recipe_id);
                    newRecipeIngredient.set_ingredient_id(ingredient_id);
                    DbHelper db = DbHelper.getInstance(getApplicationContext());
                    db.createRecipeIngredient(newRecipeIngredient);
                    Intent result = new Intent();
                    result.putExtra("result", "Added recipe ingredient!");
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == PICK_INGREDIENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ingredientPicked=true;
                ingredient_id = data.getLongExtra("ingredient_id", 0);
                recipeIngredientTxt.setText(data.getStringExtra("ingredient_name"));

            }
        }
    }
}
