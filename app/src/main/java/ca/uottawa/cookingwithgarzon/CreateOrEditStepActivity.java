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

public class CreateOrEditStepActivity extends AppCompatActivity {


    static final int PICK_STEP_REQUEST = 1;

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

        stepInstructionTxt = (EditText) findViewById(R.id.stepInstructionTxt);
        stepTimeTxt = (EditText) findViewById(R.id.stepTimeTxt);
        saveStepBtn = (Button) findViewById(R.id.saveStepBtn);
        deleteStepBtn = (Button) findViewById(R.id.deleteStepBtn);
        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);
        step_id = intent.getLongExtra("step_id", 0);
        step_number = intent.getIntExtra("step_number", 1);
        final FloatingActionButton helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);

        if (step_id != 0) {
            DbHelper db = DbHelper.getInstance(getApplicationContext());
            step = db.getStep(step_id);
            stepInstructionTxt.setText(step.get_instruction());
            stepTimeTxt.setText(((Integer)step.get_time()).toString());
        }
        else {
            step = new Step();
        }


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
                    if (stepTimeTxt.getText() == null) {
                        time = 0;
                    }
                    else {
                        time = Integer.parseInt(stepTimeTxt.getText().toString());
                    }
                    step.set_instruction(instruction);
                    step.set_time(time);
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
