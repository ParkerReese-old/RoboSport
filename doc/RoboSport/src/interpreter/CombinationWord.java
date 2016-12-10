package interpreter;

import java.util.ArrayList;
import java.util.List;

public class CombinationWord implements Word {

    protected List<String> wordOrder;
    
    public CombinationWord(String[] words) {
        this.wordOrder = new ArrayList<String>(words.length);
        for (String w : words)
            this.wordOrder.add(w);
    }

    @Override
    public void exec(ForthInterpreter runOn) {
        for (String w : wordOrder)
            runOn.exec(w);
    }
}
