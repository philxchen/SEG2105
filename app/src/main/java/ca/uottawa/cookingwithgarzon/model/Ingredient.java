package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class Ingredient {
    private long _id;
    private String _name;
    private double _price;
    private int _difficulty;

    public Ingredient() {
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

    public double get_price() {
        return _price;
    }

    public void set_price(double _price) {
        this._price = _price;
    }

    public void set_difficulty(int _difficulty) {this._difficulty = _difficulty; }

    public int get_difficulty() {return _difficulty; }
}
