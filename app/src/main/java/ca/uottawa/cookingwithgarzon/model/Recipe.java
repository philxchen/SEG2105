package ca.uottawa.cookingwithgarzon.model;

import java.util.ArrayList;

/**
 * Created by joel on 22/11/16.
 * Java RecipeViewActivity Object
 */

public class Recipe {

    private long _id;
    private String _name;
    private double _cost;
    private String _difficulty;
    private int _servings;
    private int _rating;
    private long _meal_type_id;
    private long _cuisine_id;
    private byte[] _image;
    private int _favourite;

    public Recipe() {
    }

    public int get_favourite() {
        return _favourite;
    }

    public void set_favourite(int _favourite) {
        this._favourite = _favourite;
    }


    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_cost() {
        return _cost;
    }

    public void set_cost(double _cost) {
        this._cost = _cost;
    }

    public String get_difficulty() {
        return _difficulty;
    }

    public void set_difficulty(String _difficulty) {
        this._difficulty = _difficulty;
    }

    public int get_servings() {
        return _servings;
    }

    public void set_servings(int _servings) {
        this._servings = _servings;
    }

    public long get_meal_type_id() {
        return _meal_type_id;
    }

    public void set_meal_type_id(long _type_id) {
        this._meal_type_id = _type_id;
    }

    public long get_cuisine_id() {
        return _cuisine_id;
    }

    public void set_cuisine_id(long _cuisine_id) {
        this._cuisine_id = _cuisine_id;
    }

    public int get_rating() {return _rating; }

    public void set_rating(int _rating) { this._rating = _rating; }

    public byte[] get_image() {return _image; }

    public void set_image(byte[] image) {this._image = image;}

}
