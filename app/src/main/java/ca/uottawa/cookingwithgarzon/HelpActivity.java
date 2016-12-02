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
                break;
            case "searchMealHelp":
                break;
            case "searchCuisineHelp":
                break;
            case "searchIngredientHelp":
                break;
            case "createHelp":
                break;
            case "createMealHelp":
                break;
            case "createCuisineHelp":
                break;
            case "createIngredientHelp":
                break;
            case "shoppingCartHelp":
                break;
            case "createRecipeHelp":
                break;
        }


    }
}
