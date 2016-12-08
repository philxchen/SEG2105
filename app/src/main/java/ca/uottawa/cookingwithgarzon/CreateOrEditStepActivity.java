package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

/**
 * Activity class for Creating or editing a step in a recipe
 */

public class CreateOrEditStepActivity extends AppCompatActivity {

    //Class variables
    static final int PICK_STEP_REQUEST = 1;

    //Instance variables
    private EditText stepInstructionTxt;
    private EditText stepTimeTxt;
    private Button saveStepBtn;
    private Button deleteStepBtn;
    private long recipe_id;
    private long step_id;
    private Step step;
    private int step_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_step);

        //Acitivty objects
        stepInstructionTxt = (EditText) findViewById(R.id.stepInstructionTxt);
        stepTimeTxt = (EditText) findViewById(R.id.stepTimeTxt);
        saveStepBtn = (Button) findViewById(R.id.saveStepBtn);
        deleteStepBtn = (Button) findViewById(R.id.deleteStepBtn);
        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);
        step_id = intent.getLongExtra("step_id", 0);
        step_number = intent.getIntExtra("step_number", 1);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        //Loads an existing step from the database, else creates blank page to enter a new step
        if (step_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            step = db.getStep(step_id);
            stepInstructionTxt.setText(step.get_instruction());
            stepTimeTxt.setText(((Integer)step.get_time()).toString());
        }
        else {
            step = new Step();
        }

        //Saves the recipe step into the database or edits an existing step
        saveStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stepInstructionTxt.getText() == null || stepInstructionTxt.getText().toString().equals("")) {
                    String message = "You need to write an instruction for this step!";
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_step), message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                } else {
                    int time;
                    String instruction = stepInstructionTxt.getText().toString();
                    if (stepTimeTxt.getText() != null && !stepTimeTxt.getText().toString().equals("")) {
                        time = Integer.parseInt(stepTimeTxt.getText().toString());
                    step.set_time(time);
                    }
                    step.set_instruction(instruction);
                    step.set_stepNumber(step_number +1);
                    step.set_recipe_id(recipe_id);

                    DbHelper db = DbHelper.getInstance(getApplicationContext());
                    step_id = db.createStep(step);
                    Intent result = new Intent();
                    result.putExtra("step_id", step_id);
                    result.putExtra("result", "Added recipe step!");
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
            }
        });

        //Deletes the step from the recipe and database
        deleteStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    DbHelper db = DbHelper.getInstance(getApplicationContext());
                    db.deleteStep(step);
                    Intent result = new Intent();
                    result.putExtra("step_deleted", 1);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                }
        });

        //Opens up a help page for the respective activity
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(CreateOrEditStepActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "createOrEditStepHelp");
                startActivity(helpIntent);
            }
        });
    }
}
