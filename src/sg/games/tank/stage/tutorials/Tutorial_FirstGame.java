package sg.games.tank.stage.tutorials;

/**
 *
 * @author cuong.nguyen
 */
public class Tutorial_FirstGame {
    public static Tutorial create(){
        return new Tutorial.Builder()
                .instructor("Captain")
                .dialogue("Hello folk.")
                .dialogue("Welcome on board.")
                .dialogue("We will guide you through this space station")
                .build();
    }
}
