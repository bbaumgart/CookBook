package service;

import controller.CookbookController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Ingredient;
import model.Recipe;
import model.enums.Level;
import model.enums.Meal;
import model.enums.Type;
import utility.InMemoryDB;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import static controller.CookbookController.ingredients_fx;
import static controller.CookbookController.recipes_fx;


public class CookbookService {

    public void setSelectRecipe(Recipe recipe, TextField tf_title, TextArea ta_Description, TextField tf_type, TextField tf_level, TextField tf_meal, TextField tf_time, ListView tf_ingrediens, ImageView iv_image) {
        tf_title.setText(recipe.getTitle());
        ta_Description.setText(recipe.getDescription());
        tf_type.setText(recipe.getType().name());
        tf_level.setText(recipe.getLevel().name());
        tf_meal.setText(recipe.getMeal().name());
        tf_time.setText(String.valueOf(recipe.getPrepareTime()));
        ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
        ingredients.addAll(recipe.getIngredients());
        tf_ingrediens.setItems(ingredients);
        iv_image.setImage(new Image(recipe.getImagePath()));

    }

    public void setTableProperty(TableColumn c_title, TableColumn c_description, TableColumn c_time, TableColumn c_meal, TableColumn c_level, TableColumn c_type, TableColumn c_ingrediens) {
        c_title.setCellValueFactory(new PropertyValueFactory<>("title"));
        c_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        c_time.setCellValueFactory(new PropertyValueFactory<>("prepareTime"));
        c_meal.setCellValueFactory(new PropertyValueFactory<>("meal"));
        c_level.setCellValueFactory(new PropertyValueFactory<>("level"));
        c_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        c_ingrediens.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
    }

    public void setTableItems(TableView tbl_recipes, ObservableList recipes_fx) {
        tbl_recipes.setItems(recipes_fx);
    }

    public Recipe getRecipeFromSelectedRow(TableView<Recipe> tbl_recipes) {
        return tbl_recipes.getSelectionModel().getSelectedItem();
    }
//    metoda usuwająca rekord po id

    public void deleteRecord(Recipe recipe, ObservableList recipes_fx) {
        if (recipe != null) {
            recipes_fx.remove(recipe);
        }
    }

    public void editRecordById(Recipe recipe, ObservableList recipes_fx) {

    }

    public String uploadFile(ImageView e_view) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz zdjęcie");
        fileChooser.setInitialDirectory(new File("C:\\Users\\baumgarb\\Desktop\\Kurs Java SDA\\JavaFX\\CookBook\\src\\main\\resources\\img"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imeges", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            String imagepath = file.getPath();
            String direct_path = "C:\\Users\\baumgarb\\Desktop\\Kurs Java SDA\\JavaFX\\CookBook\\src\\main\\resources";
            imagepath = imagepath.replace(direct_path, "");
            System.out.println("file:" + imagepath);
            // wyświetlenie obrazka do podglądu
            e_view.setImage(new Image(imagepath));
            // zapis ścieżki do obiektu Recipe
            return imagepath;
        }
        return null;
    }

    public void setMealItems(ComboBox e_meal) {
        ObservableList<Meal> meals = FXCollections.observableArrayList();
        meals.addAll(Meal.values());
        e_meal.setItems(meals);
    }

    public void setLevelItems(ComboBox e_level) {
        ObservableList<Level> levels = FXCollections.observableArrayList();
        levels.addAll(Level.values());
        e_level.setItems(levels);
    }

    public void setTypeItems(ComboBox e_type) {
        ObservableList<Type> types = FXCollections.observableArrayList();
        types.addAll(Type.values());
        e_type.setItems(types);
    }

    public boolean validRecipe(TextField e_title, TextArea e_description, Spinner<Integer> e_time, ComboBox e_meal, ComboBox e_level, ComboBox e_type) {

        if (e_title.getText().equals("") || e_description.getText().equals("") || e_time.getValue() == null ||
                e_meal.getValue() == null || e_level.getValue() == null || e_type.getValue() == null) {
            new WindowService().getAlert(
                    Alert.AlertType.ERROR,
                    "Błąd dodawania receptury",
                    "Błąd dodawania receptury",
                    "Należy uzupełnić wszystkie wymagane pola");
            return false;
        }
        return true;
    }

    public void saveRecipe(TextField e_title, TextArea e_description, Spinner<Integer> e_time,
                           ComboBox e_meal, ComboBox e_level, ComboBox e_type, String imagePath, TableView tbl_recipes, ListView lv_ngrediens_view, ImageView e_image) {
        if (validRecipe(e_title, e_description, e_time, e_meal, e_level, e_type)) {
            Recipe recipe = new Recipe(e_title.getText(), e_description.getText(), e_time.getValue(),
                    imagePath, (Meal) e_meal.getValue(), (Level) e_level.getValue(), (Type) e_type.getValue(),
                    lv_ngrediens_view.getItems());

            if (imagePath == (null)) {
                recipe.setImagePath("img/jedzenie.gif");
            } else {
                recipe.setImagePath(imagePath);
            }

            InMemoryDB.recipes.add(recipe);
            recipes_fx.clear();
            recipes_fx.addAll(InMemoryDB.recipes);
            setTableItems(tbl_recipes, recipes_fx);
            e_title.clear();
            e_description.clear();
            e_time.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 180, 1, 3));
            e_level.setValue(null);
            e_meal.setValue(null);
            e_type.setValue(null);
            lv_ngrediens_view.setItems(null);
            e_image.setImage(null);


        }
    }

    public void setIngredientsCombo(ComboBox e_ingredients, ArrayList<Ingredient> ingredients) {
        CookbookController.ingredients_fx.addAll(InMemoryDB.ingredients);
        e_ingredients.setItems(CookbookController.ingredients_fx);
    }

    public void updateIngredientsCombo(ComboBox e_ingredients, Ingredient ingredient) {
        CookbookController.ingredients_fx.remove(ingredient);
        e_ingredients.setItems(CookbookController.ingredients_fx);
    }
}
