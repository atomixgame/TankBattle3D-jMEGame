package sg.games.tank.gameplay;

import java.util.HashMap;

/**
 * Basic mechanism of Tower defense game.
 * @author cuong.nguyen
 */
public class Wave {

    int waveCount = 0;
    int waveIndex = 0;
    int waveNum = 4;
    int waveEach = 10;
    float waveTime = 1;
    
    HashMap<String, Integer> numUnits;
    HashMap<String, Integer> results;
    public void init(){
        
    }
    public void start(){
        
    }
    
    public void end(){
        
    }
    
    public void update(float tpf){
        
    }

    public HashMap<String, Integer> getNumUnits() {
        return numUnits;
    }

    public HashMap<String, Integer> getResults() {
        return results;
    }
    
    
}
