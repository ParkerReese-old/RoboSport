package interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a Forth Script, which can be executed by the ForthInterpreter
 * @author Dylan McInnes
 *
 */
public class Script implements Iterable<String> {

    protected String[] tokens;

    /**
     * Creates a new Forth Script
     * 
     * @param forth : the code to use
     */
    public Script(String forth) {
        forth = forth.replaceAll("\n\r", " ");
        forth = forth.replaceAll("\n", " ");
        forth = forth.replaceAll("\r", " ");

        this.tokens = tokenize(forth);
    }


    private static void squish(List<String> manip, int start, int end) {
        String squishedToken = "";
        for (; end >= start; end--) // go back from end to start
            squishedToken = manip.remove(end) + " " + squishedToken;
        squishedToken = squishedToken.trim();
        manip.add(start, squishedToken);
    }
    
    private static void combine(List<String> manip, String start, String end, int index) {
        int matches;
        if (manip.get(index).equals(start)) {
            // Search for the first ; after this
            // (since it's a user-word definition)
            matches = 1;
            int j;
            for (j = index + 1; j < manip.size(); j++) {
                if (manip.get(j).equals(start))
                    matches++;
                if (manip.get(j).equals(end))
                    matches--;
                if (matches == 0)
                    break;
            }

            // squish the values between i and j
            // and put them into position i

            /*
            String squishedToken = "";
            for (; j >= index; j--) // go back from j to i
                squishedToken = manip.remove(j) + " " + squishedToken;
            squishedToken = squishedToken.trim();
            manip.add(index, squishedToken); */
            squish(manip, index, j);
        }
    }
    
    private static void cleanComments(List<String> manip, int index) {
        if (manip.get(index).equals("("))
            for (int i=index+1; i<manip.size(); i++)
                if (manip.get(i).equals(")")) {
                    squish(manip, index, i);
                    break;
                }
    }
    
    private static void fuseStrings(List<String> manip, int index) {
        if (manip.get(index).matches("\\.\".*") && !manip.get(index).matches(".*\""))
            for (int i=index+1; i<manip.size(); i++)
                if (manip.get(i).matches(".*\"")) {
                    squish(manip, index, i);
                    break;
                }
    }
    
    protected static String[] tokenize(String tokenstring) {
        String[] initialTokens = tokenstring.split(" ");
        ArrayList<String> tokenList = new ArrayList<String>(initialTokens.length);
        for (String s : initialTokens)
            tokenList.add(s);

        for (int i = 0; i < tokenList.size(); i++)
            cleanComments(tokenList, i);
        for (int i = 0; i < tokenList.size(); i++) {
            fuseStrings(tokenList, i);
            combine(tokenList, "variable", ";", i);
            combine(tokenList, ":", ";", i);
            combine(tokenList, "do", "loop", i);
            combine(tokenList, "begin", "until", i);
            combine(tokenList, "if", "then", i);
        }
        while (tokenList.remove(""));
        while (tokenList.remove(" "));


        return tokenList.toArray(new String[0]);
    }

    @Override
    public Iterator<String> iterator() {
        return new ScriptIterator();
    }
    
    /**
     * Iterates over the script's tokens
     * 
     * @author Dylan McInnes
     *
     */
    protected class ScriptIterator implements Iterator<String> {
        int i;
        
        public ScriptIterator() {
            this.i=0;
        }
        
        @Override
        public boolean hasNext() {
            return this.i<tokens.length;
        }

        @Override
        public String next() {
            return tokens[this.i++];
        }
        
    }
}
