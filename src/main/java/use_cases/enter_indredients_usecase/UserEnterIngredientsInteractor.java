package use_cases.enter_indredients_usecase;

import database.DatabaseGateway;
import entities.ingredient.CommonIngredient;
import entities.ingredient.IngredientFactory;
import entities.user.CommonUser;
import presenters.enter_ingredient.UserEnterIngredientPresenter;


import java.util.Objects;

/**
 * Class UserEnterIngredientsInteractor (Implements the setEnterIngredientsInputBoundary)
 */
public class UserEnterIngredientsInteractor implements UserEnterIngredientsInputBoundary {
    UserEnterIngredientPresenter userEnterIngredientPresenter;
    IngredientFactory ingredientFactory;

    DatabaseGateway database;

    /**
     * @param ingredientFactory : An ingredient factory to create an ingredient object based on the string
     * @param userEnterIngredientPresenter Presenter that will call view model based on if ingredient is added or not
     * @param database                     Need to add database inside this
     */
    public UserEnterIngredientsInteractor(IngredientFactory ingredientFactory,
                                          UserEnterIngredientPresenter userEnterIngredientPresenter,
                                          DatabaseGateway database) {
        this.ingredientFactory = ingredientFactory;
        this.userEnterIngredientPresenter = userEnterIngredientPresenter;
        this.database = database;
    }

    /**
     * @param requestModel the request model is passed in
     * @return CommonIngredient, if button is pressed and user does not enter anything an error is presented,
     * Otherwise ingredient is created using ingredient factory, and added to the user's fridge
     */
    @Override
    public UserEnterIngredientResponseModel create(UserEnterIngredientRequestModel requestModel) {
        if (Objects.equals(requestModel.getIngredient_in_String_Format(), "")) {
            return userEnterIngredientPresenter.prepareFailView("Nothing");
        }
        else {
            CommonIngredient ingredient = (CommonIngredient) ingredientFactory.create(requestModel.getIngredient_in_String_Format());
            CommonUser Curr_User = (CommonUser) database.get(requestModel.getUserName()).getUser();
            Curr_User.getFridge().addIngredient(ingredient);
            database.save();
            UserEnterIngredientResponseModel responseModel = new UserEnterIngredientResponseModel(ingredient);
            return userEnterIngredientPresenter.prepareSuccessView(responseModel);
        }
    }
}
