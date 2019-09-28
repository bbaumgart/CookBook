package utility;

import model.Ingredient;
import model.Recipe;
import model.User;
import model.enums.Level;
import model.enums.Meal;
import model.enums.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InMemoryDB {
    public static List<User> users = new ArrayList<User>(
            Arrays.asList(
                    new User("test", "test"),
                    new User("test1", "test1"),
                    new User("test2", "test2")
            )
    );

    public static List<Ingredient> ingredients = new ArrayList<>(Arrays.asList(
            new Ingredient("masło",100,1000),
            new Ingredient("nutella",25,10000),
            new Ingredient("mąka",1000,100),
            new Ingredient("rodzynki",50,200),
            new Ingredient("brokuł",1000,5),
            new Ingredient("mięso z kurczaka",1000,50)

    ));

    public static List<Recipe> recipes = new ArrayList<>(
            Arrays.asList(
                    new Recipe("Bigos", "Bigos – tradycyjna dla kuchni polskiej, litewskiej i białoruskiej potrawa z kapusty i mięsa", 120, "/img/bigos.png", Meal.kolacja, Level.średni, Type.tradycyjny,
                            new ArrayList<Ingredient>(Arrays.asList(new Ingredient("kapusta kiszona", 100, 300), new Ingredient("kiełbasa", 100, 100)))),
                    new Recipe("Jajecznica", "Jajecznica – potrawa z rozmąconych, usmażonych na patelni jajek. Jest domeną prostej kuchni, ponieważ nie wymaga umiejętności kulinarnych ani techniki.", 15, "/img/jajecznica.jpg", Meal.śniadanie, Level.łatwy, Type.tradycyjny,
                            new ArrayList<Ingredient>(Arrays.asList(new Ingredient("jaja", 3, 200), new Ingredient("kiełbasa", 100, 100)))),
                    new Recipe("Schabowy", "Kotlet schabowy – kotlet panierowany ze schabu przypominający sznycel wiedeński. Jest to jedna z tradycyjnych i najbardziej popularnych potraw w kuchni polskiej. Historia polskich kotletów schabowych sięga XIX wieku. Przepis na schabowy występuje w książce Lucyny Ćwierczakiewiczowej pt. 365 obiadów za pięć złotych.", 60, "/img/schabowy.jpg", Meal.obiad, Level.średni, Type.tradycyjny,
                            new ArrayList<Ingredient>(Arrays.asList(new Ingredient("ziemniaki", 10, 500), new Ingredient("mięso", 100, 100), new Ingredient("buraczki", 24, 250), new Ingredient("frytki", 100, 1000))))

            )
    );
}