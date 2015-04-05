package sg.games.tank.gameplay;

import sg.games.tank.gameplay.Match.Difficult;

public class Map {

    String path;
    int id;
    int status;
    Difficult difficult;
    //Infos
    String author;
    String description;

    public Map() {
        this.path = "maps/level1.tmx";
    }

    public void init() {
    }

    public String getPath() {
        return path;
    }
}
