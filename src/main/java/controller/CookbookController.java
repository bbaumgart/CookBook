package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Ingredient;
import model.Recipe;
import model.enums.Level;
import model.enums.Meal;
import model.enums.Type;
import service.CookbookService;
import service.LoginService;
import service.WindowService;
import utility.InMemoryDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CookbookController {
    private LoginService loginService;
    private WindowService windowService;
    private CookbookService cookbookService;
    Recipe recipeToDelete;
    // obiekty globalne
    public static ObservableList<Recipe> recipes_fx = FXCollections.observableArrayList();
    public static ObservableList<Ingredient> ingredients_fx = FXCollections.observableArrayList();
    private String imagePath;

    // metoda implementująca instrukcje rozpoczynające działanie aplikacji
    public void initialize() {
        loginService = new LoginService();
        windowService = new WindowService();
        cookbookService = new CookbookService();
        recipes_fx.addAll((InMemoryDB.recipes).stream()
                .sorted((Comparator.comparing(Recipe::getTitle)))
                .collect(Collectors.toList()));
        cb_recipe.setItems(recipes_fx);

        cookbookService.setTableProperty(c_title, c_description, c_time, c_meal, c_level, c_type, c_ingrediens);
//        wprowadzanie danych do tabeli
        cookbookService.setTableItems(tbl_recipes, recipes_fx);
        cookbookService.setLevelItems(e_level);
        cookbookService.setMealItems(e_meal);
        cookbookService.setTypeItems(e_type);
        e_time.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 180, 1, 3));
        cookbookService.setIngredientsCombo(e_ingrediens, (ArrayList<Ingredient>) InMemoryDB.ingredients);

    }

    @FXML
    void selectRowAction(MouseEvent event) {
        recipeToDelete = cookbookService.getRecipeFromSelectedRow(tbl_recipes);
        btn_delete.setDisable(false);
    }

    @FXML
    void getRecipeAction(ActionEvent event) {
        Recipe selectedrecipe = cb_recipe.getValue();
        cookbookService.setSelectRecipe(selectedrecipe, tf_title, tf_description, tf_type, tf_level, tf_meal, tf_prepareTime, tf_ingredient, iv_image);
    }

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        windowService.openNewWindow("/view/loginView.fxml", "Panel Logowania");
        windowService.closeCurrentWindow(tf_title);
    }

    public void uploadImageAction(ActionEvent actionEvent) {
        imagePath = cookbookService.uploadFile(e_view);

    }

    public void addIngredientsAction(ActionEvent actionEvent) {
//        cookbookService.updateIngredientsCombo(e_ingrediens, e_ingrediens.getValue());


        ObservableList<Ingredient> ingredients = lv_ngrediens_view.getItems();
        ingredients.add(e_ingrediens.getValue());
        lv_ngrediens_view.setItems(ingredients);

    }

    public void deleteRecipeAction(ActionEvent actionEvent) {
        cookbookService.deleteRecord(recipeToDelete, recipes_fx);
        cookbookService.setTableItems(tbl_recipes, recipes_fx);
        btn_delete.setDisable(true);
    }

    public void saveRecipeAction(ActionEvent actionEvent) {
        cookbookService.saveRecipe(e_title, e_description, e_time, e_meal, e_level, e_type, imagePath, tbl_recipes,lv_ngrediens_view,e_view);
    }

    @FXML
    private TableView<Recipe> tbl_recipes;
    @FXML
    private TableColumn<Recipe, String> c_title;

    @FXML
    private TableColumn<Recipe, String> c_description;

    @FXML
    private TableColumn<Recipe, Integer> c_time;

    @FXML
    private TableColumn<Recipe, String> c_meal;

    @FXML
    private TableColumn<Recipe, String> c_level;

    @FXML
    private TableColumn<Recipe, String> c_type;

    @FXML
    private TableColumn<Recipe, String> c_ingrediens;

    @FXML
    private TextField e_title;

    @FXML
    private TextArea e_description;

    @FXML
    private Spinner<Integer> e_time;

    @FXML
    private ComboBox<Meal> e_meal;

    @FXML
    private ComboBox<Level> e_level;

    @FXML
    private ComboBox<Type> e_type;

    @FXML
    private Button btn_image;

    @FXML
    private ComboBox<Ingredient> e_ingrediens;

    @FXML
    private ImageView e_view;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_delete;

    @FXML
    private ComboBox<Recipe> cb_recipe;

    @FXML
    private TextField tf_title;

    @FXML
    private TextArea tf_description;

    @FXML
    private TextField tf_type;

    @FXML
    private TextField tf_level;

    @FXML
    private TextField tf_meal;

    @FXML
    private TextField tf_prepareTime;

    @FXML
    private ListView<Ingredient> tf_ingredient;

    @FXML
    private ListView<Ingredient> lv_ngrediens_view;

    @FXML
    private ImageView iv_image;

}
