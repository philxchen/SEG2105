package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class Cuisine {
    private long _id;
    private String _name;

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public Cuisine() {

    }
}
