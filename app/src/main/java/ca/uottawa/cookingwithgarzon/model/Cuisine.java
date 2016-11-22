package ca.uottawa.cookingwithgarzon.model;

/**
 * Created by joel on 22/11/16.
 */

public class Cuisine {
    private long _id;
    private String name;

    public long get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cuisine() {

    }
}
