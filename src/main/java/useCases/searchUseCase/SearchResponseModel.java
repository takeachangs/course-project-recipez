package useCases.searchUseCase;

import entities.Recipe;

import java.util.ArrayList;

public class SearchResponseModel {
    private final ArrayList<Recipe> recipes;

    public SearchResponseModel(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
}
