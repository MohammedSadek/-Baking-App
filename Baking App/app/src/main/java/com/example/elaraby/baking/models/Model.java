package com.example.elaraby.baking.models;

import java.util.ArrayList;

public class Model {

    private int id;
    private String name;
    private ArrayList<Ingredent> ingredients = null;
    private ArrayList<Steps> steps = null;
    private int servings;

    public Model(int id, String name, ArrayList<Ingredent> ingredients, ArrayList<Steps> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredent> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredent> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
