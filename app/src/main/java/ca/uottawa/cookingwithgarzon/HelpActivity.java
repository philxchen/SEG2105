package ca.uottawa.cookingwithgarzon;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent intent = getIntent();
        final TextView helpTxt = (TextView) findViewById(R.id.helpTextView);

        String helpTextName = intent.getStringExtra("helpTextName");

        switch(helpTextName) {
            case "mainScreenHelp":
                helpTxt.setText(getString(R.string.mainScreenHelp));
                break;
            case "searchHelp":
                helpTxt.setText(getString(R.string.searchHelp));
                break;
            case "searchRecipeHelp":
                helpTxt.setText(getString(R.string.searchRecipeHelp));
                break;
            case "searchMealHelp":
                helpTxt.setText(getString(R.string.searchMealHelp));
                break;
            case "searchCuisineHelp":
                helpTxt.setText(getString(R.string.searchCuisineHelp));
                break;
            case "searchIngredientHelp":
                helpTxt.setText(getString(R.string.searchIngredientHelp));
                break;
            case "createHelp":
                helpTxt.setText(getString(R.string.createHelp));
                break;
            case "createMealHelp":
                helpTxt.setText(getString(R.string.createMealHelp));
                break;
            case "createCuisineHelp":
                helpTxt.setText(getString(R.string.createCuisineHelp));
                break;
            case "createIngredientHelp":
                helpTxt.setText(getString(R.string.createIngredientHelp));
                break;
            case "shoppingCartHelp":
                helpTxt.setText(getString(R.string.shoppingCartHelp));
                break;
            case "createRecipeHelp":
                helpTxt.setText(getString(R.string.createRecipeHelp));
                break;
            case "createOrEditStepHelp":
                helpTxt.setText(getString(R.string.createOrEditStepHelp));
                break;
        }
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}
