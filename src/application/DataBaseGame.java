package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataBaseGame {
    private List<String> savedGames;
    
    public DataBaseGame() {
        savedGames = new ArrayList<>();
        updateFiles();
    }
    
    public List<String> updateFiles() {
        savedGames.clear();
        File dir = new File(".");
        File[] files = dir.listFiles((d, name) -> name.startsWith(Constants.FILE_START_STRING));
        for(int i=0; i< files.length; i++) {
            String name = files[i].getName();
            savedGames.add(name);
        }
        Collections.sort(savedGames, Collections.reverseOrder());
        return savedGames;
    }
    
}
