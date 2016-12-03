package ca.uottawa.cookingwithgarzon;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ca.uottawa.cookingwithgarzon.adapter.HintAdapter;
import ca.uottawa.cookingwithgarzon.adapter.RecipeIngredientArrayAdapter;
import ca.uottawa.cookingwithgarzon.adapter.StepArrayAdapter;
import ca.uottawa.cookingwithgarzon.helper.DbBitMapUtility;
import ca.uottawa.cookingwithgarzon.helper.DbHelper;
import ca.uottawa.cookingwithgarzon.model.*;

public class CreateOrEditRecipeActivity extends AppCompatActivity {

    private final int GET_INGREDIENT_REQUEST = 1;
    private final int GET_STEP_REQUEST = 2;
    private final int GET_CUISINE_REQUEST = 3;
    private final int GET_MEALTYPE_REQUEST = 4;
    private static final int CAMERA_REQUEST = 5;
    private final int MODIFY_INGREDIENT_REQUEST = 6;
    private final int MODIFY_STEP_REQUEST = 7;


    private DbHelper dbHelper;
    private DbBitMapUtility dbBitMap = new DbBitMapUtility();
    private Recipe recipe;
    private Cuisine cuisine;
    private MealType mealtype;
    private long recipe_id;
    private ArrayList<RecipeIngredient> recipeIngredients = new ArrayList<>();
    private ArrayList<Step> steps = new ArrayList<>();
    private ListView recipeIngredients_listView;
    private ListView step_listView;
    private RecipeIngredientArrayAdapter recipeIngredientArrayAdapter;
    private StepArrayAdapter stepArrayAdapter;
    private ImageView image;
    private EditText recipeTitleTxt;
    private EditText servingTxt;
    private RatingBar recipeRatingBar;
    private Switch favouriteSwitch;
    private Button newIngredientBtn;
    private Button newStepBtn;
    private Spinner difficultySpinner;
    private TextView mealTypeTxt;
    private TextView cuisineTxt;
    private TextView costTxt;
    private boolean saved;
    private boolean addedPicture;
    private FloatingActionButton helpBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_recipe);


        addedPicture = false; //initialized for added image

        newIngredientBtn = (Button) findViewById(R.id.createRecipeAddIngredientBtn);
        newStepBtn = (Button) findViewById(R.id.createRecipeAddStepBtn);
        recipeTitleTxt = (EditText) findViewById(R.id.createRecipeTitleTxt);
        recipeRatingBar = (RatingBar) findViewById(R.id.createRecipeRating);
        difficultySpinner = (Spinner) findViewById(R.id.createRecipeDifficultySpinner);
        mealTypeTxt = (TextView) findViewById(R.id.createRecipeMealTypeTxt);
        cuisineTxt = (TextView) findViewById(R.id.createRecipeCuisineTxt);
        recipeIngredients_listView = (ListView) findViewById(R.id.createRecipeIngredientList);
        step_listView = (ListView) findViewById(R.id.createRecipeStepList);
        image = (ImageView) findViewById(R.id.createRecipeImage);
        favouriteSwitch = (Switch) findViewById(R.id.favouritesSwitch);
        helpBtn = (FloatingActionButton) findViewById(R.id.helpBtn);
        costTxt = (TextView) findViewById(R.id.createRecipeCostTxt);
        servingTxt = (EditText) findViewById(R.id.createRecipeServingTxt);

        //Difficulty List
        List<String> difficultyList = new ArrayList<>();
        difficultyList.add("Easy");
        difficultyList.add("Moderate");
        difficultyList.add("Hard");
        difficultyList.add("Select Difficulty"); //Hint

        //Difficulty Adapter
        HintAdapter difficultyAdapter = new HintAdapter(this, difficultyList, android.R.layout.simple_spinner_item);
        difficultyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyAdapter);
        difficultySpinner.setSelection(difficultyAdapter.getCount());

        dbHelper = DbHelper.getInstance(getApplicationContext());

        Intent intent = getIntent();
        recipe_id = intent.getLongExtra("recipe_id", 0);

        if (recipe_id != 0) {
            saved=true;
            loadRecipe();
        }
        else {
            saved = false;
            recipe = new Recipe();
            recipe_id = dbHelper.createRecipe(recipe);
            recipe.set_id(recipe_id);
//            Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), "New recipe with id: "+recipe_id, Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();

        }

        recipeIngredientArrayAdapter = new RecipeIngredientArrayAdapter(this, R.layout.recipe_ingredient_item, recipeIngredients);
        recipeIngredients_listView.setAdapter(recipeIngredientArrayAdapter);
        recipeIngredients_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent editRecipeIngredient =
                        new Intent(CreateOrEditRecipeActivity.this, CreateOrEditRecipeIngredientActivity.class);
                RecipeIngredient picked = (RecipeIngredient) parent.getItemAtPosition(position);
                editRecipeIngredient.putExtra("recipe_ingredient_id", picked.get_id());
                editRecipeIngredient.putExtra("recipe_id", recipe.get_id());
                editRecipeIngredient.putExtra("result", "Modifying ingredient");
                startActivityForResult(editRecipeIngredient, MODIFY_INGREDIENT_REQUEST);
            }
        });
        stepArrayAdapter = new StepArrayAdapter(this, R.layout.step_item, steps);
        step_listView.setAdapter(stepArrayAdapter);
        step_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent editStepIngredient =
                        new Intent(CreateOrEditRecipeActivity.this, CreateOrEditStepActivity.class);
                Step picked = (Step) parent.getItemAtPosition(position);
                editStepIngredient.putExtra("step_id", picked.get_id());
                editStepIngredient.putExtra("recipe_id", recipe.get_id());
                editStepIngredient.putExtra("result", "Modifying ingredient");
                // editRecipeIngredient.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(editStepIngredient, MODIFY_STEP_REQUEST);
            }
        });
        setListViewHeightBasedOnChildren(step_listView);
        setListViewHeightBasedOnChildren(recipeIngredients_listView);

        //Floating Action Toolbar when submitting to the Database
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addRecipeBtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recipeTitleTxt.getText() == null || recipeTitleTxt.getText().toString().isEmpty()) {
                    Snackbar.make(view, "You must name your recipe", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (recipeIngredients.size() == 0 ) {
                    Snackbar.make(view, "You probably need some ingredients", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (steps.size() == 0 ) {
                    Snackbar.make(view, "A recipe without steps?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (servingTxt.getText() != null && !servingTxt.getText().toString().equals("")) {
                    Integer servings = Integer.parseInt(servingTxt.getText().toString());
                    recipe.set_servings(servings);
                }
                recipe.set_difficulty(difficultySpinner.getSelectedItem().toString());
                recipe.set_name(recipeTitleTxt.getText().toString());
                recipe.set_rating((int)recipeRatingBar.getRating());
                if(addedPicture)
                    recipe.set_image(dbBitMap.getBytes(((BitmapDrawable)image.getDrawable()).getBitmap()));
                dbHelper.updateRecipe(recipe);
                saved = true;
                Intent finishedIntent = new Intent(CreateOrEditRecipeActivity.this, RecipeViewActivity.class);
                finishedIntent.putExtra("recipe_id", recipe.get_id());
                finishedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(finishedIntent);
                finish();
            }
        });

        newIngredientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIngredientIntent = new Intent(CreateOrEditRecipeActivity.this, CreateOrEditRecipeIngredientActivity.class);
                newIngredientIntent.putExtra("recipe_id", recipe.get_id());
                // newIngredientIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(newIngredientIntent, GET_INGREDIENT_REQUEST);
            }
        });


        newStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newStepIntent = new Intent(CreateOrEditRecipeActivity.this, CreateOrEditStepActivity.class);
                newStepIntent.putExtra("recipe_id", recipe.get_id());
                newStepIntent.putExtra("step_number", steps.size());
                startActivityForResult(newStepIntent, GET_STEP_REQUEST);
            }
        });

        cuisineTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickCuisine = new Intent(CreateOrEditRecipeActivity.this, CuisineSearchActivity.class);
                pickCuisine.putExtra("recipe_id", recipe.get_id());
                startActivityForResult(pickCuisine, GET_CUISINE_REQUEST);
            }
        });

        mealTypeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickType = new Intent(CreateOrEditRecipeActivity.this, MealTypeSearchActivity.class);
                pickType.putExtra("recipe_id", recipe.get_id());
                startActivityForResult(pickType, GET_MEALTYPE_REQUEST);
            }
        });

        favouriteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favouriteSwitch.isChecked()) {
                    recipe.set_favourite(1);
                }
                else {
                    recipe.set_favourite(0);
                }
            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helpIntent = new Intent(CreateOrEditRecipeActivity.this, HelpActivity.class);
                helpIntent.putExtra("helpTextName", "createRecipeHelp");
                startActivity(helpIntent);
            }
        });
    }

    private void loadRecipe() {
        recipe = dbHelper.getRecipe(recipe_id);
        recipeTitleTxt.setText(recipe.get_name());
        servingTxt.setText(((Integer)recipe.get_servings()).toString());
        costTxt.setText(((Double)recipe.get_cost()).toString());
        if (recipe.get_rating() != 0) {
            recipeRatingBar.setRating(recipe.get_rating());
        }
        if (recipe.get_cuisine_id() != 0) {
            cuisine = dbHelper.getCuisine(recipe.get_cuisine_id());
            cuisineTxt.setText(cuisine.get_name());
        }
        if (recipe.get_meal_type_id() != 0) {
            mealtype = dbHelper.getMealType(recipe.get_meal_type_id());
            mealTypeTxt.setText(mealtype.get_name());
        }
        if(recipe.get_image() != null) {
            image.setImageBitmap(dbBitMap.getImage(recipe.get_image()));
        }
        switch(recipe.get_difficulty()) {
            case "Easy":
                difficultySpinner.setSelection(0);
                break;
            case "Moderate":
                difficultySpinner.setSelection(1);
                break;
            case "Hard":
                difficultySpinner.setSelection(2);
                break;
        }
        favouriteSwitch.setChecked(recipe.get_favourite() > 0);
        recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
        steps = dbHelper.getRecipeSteps(recipe_id);
        Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe),
                "Editing recipe "+ recipe.get_name(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    //Helper method to set Recipe Image
    public void takeImageFromCamera(View view){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private double calculateCost() {
        double cost = 0;
        for (RecipeIngredient item : recipeIngredients) {
            Ingredient i = dbHelper.getIngredient(item.get_ingredient_id());
            cost += i.get_price();
        }
        return cost;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GET_INGREDIENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
                    recipeIngredientArrayAdapter.clear();
                    recipeIngredientArrayAdapter.addAll(recipeIngredients);
                    recipeIngredientArrayAdapter.notifyDataSetChanged();
                    recipe.set_cost(calculateCost());
                    setListViewHeightBasedOnChildren(recipeIngredients_listView);

                    costTxt.setText(((Double)recipe.get_cost()).toString());
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe),
                            message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case GET_STEP_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    steps = dbHelper.getRecipeSteps(recipe_id);
                    stepArrayAdapter.clear();
                    stepArrayAdapter.addAll(steps);
                    stepArrayAdapter.notifyDataSetChanged();
                    setListViewHeightBasedOnChildren(step_listView);

                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe),
                            message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case GET_CUISINE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    long cuisine_id = data.getLongExtra("cuisine_id", 0);
                    if (cuisine_id != 0) {
                        cuisine = dbHelper.getCuisine(cuisine_id);
                        recipe.set_cuisine_id(cuisine.get_id());
                        cuisineTxt.setText(cuisine.get_name());
                        Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message,
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                break;
            case GET_MEALTYPE_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    long type_id = data.getLongExtra("type_id", 0);
                    if (type_id != 0) {
                        Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message,
                                Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        mealtype = dbHelper.getMealType(type_id);
                        recipe.set_meal_type_id(mealtype.get_id());
                        mealTypeTxt.setText(mealtype.get_name());
                    }
                }
                break;
            case CAMERA_REQUEST:
                if (resultCode == RESULT_OK) {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    image.setImageBitmap(photo);
                    addedPicture = true;
                }
                break;
            case MODIFY_INGREDIENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    recipeIngredients = dbHelper.getRecipeIngredients(recipe_id);
                    recipeIngredientArrayAdapter.clear();
                    recipeIngredientArrayAdapter.addAll(recipeIngredients);
                    recipeIngredientArrayAdapter.notifyDataSetChanged();
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe),
                            message, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
            case MODIFY_STEP_REQUEST:
                if (resultCode == RESULT_OK) {
                    String message = data.getStringExtra("result");
                    steps = dbHelper.getRecipeSteps(recipe_id);
                    stepArrayAdapter.clear();
                    stepArrayAdapter.addAll(steps);
                    stepArrayAdapter.notifyDataSetChanged();
                    Snackbar.make(findViewById(R.id.activity_create_or_edit_recipe), message,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        if (!saved) {
            dbHelper.deleteRecipe(recipe);
        }

        // Otherwise defer to system default behavior.
        super.onBackPressed();
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
