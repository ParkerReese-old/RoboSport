package interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A class used to interpret and execute Forth commands.
 * 
 * @author dylan
 *
 */
public class ForthInterpreter {
    private Stack<LiteralWord> stack;

    private Map<String, LiteralWord> variables;
    
    public Map<String, Word> words;


    /**
     * Creates a new ForthInterpreter with the system words already initialized
     */
    public ForthInterpreter() {
        stack = new Stack<LiteralWord>();
        words = new HashMap<String, Word>();
        variables = new HashMap<String, LiteralWord>();
        addSystemWords();
    }


    private String[] splitElses(String conditional) {
        conditional = conditional.substring(3, conditional.length() - 5);
        String[] conditionalTokens = Script.tokenize(conditional);
        int i, matches;
        matches = 1;
        for (i = 0; i < conditionalTokens.length; i++) {
            if (conditionalTokens[i].equals("if"))
                matches++;
            if (conditionalTokens[i].equals("else") | conditionalTokens[i].equals("then"))
                matches--;
            if (matches == 0) {
                break;
            }
        }
        if (i == conditionalTokens.length - 1)
            return new String[] {conditional};
        else { // Split on else
            String tr = "", fl = "";
            for (int j = 0; j < i; j++)
                tr = tr + " " + conditionalTokens[j];
            tr = tr.trim();
            for (int j = i + 1; j < conditionalTokens.length; j++)
                fl = fl + " " + conditionalTokens[j];
            fl = fl.trim();
            return new String[] {tr, fl};
        }
    }

    /**
     * Adds the system-defined Forth words to the ForthInterpreter
     */
    protected void addSystemWords() {
        // Stack Manipulators
        words.put("drop", new SystemDropWord());
        words.put("dup", new SystemDuplicateWord());
        words.put("swap", new SystemSwapWord());
        words.put("rot", new SystemRotateWord());

        // Arithmetic
        words.put("+", new SystemAddWord());
        words.put("-", new SystemSubtractWord());
        words.put("*", new SystemMultiplyWord());
        words.put("/mod", new SystemDivideWord());

        // Comparisons
        words.put("<", new SystemLessThanWord());
        words.put("<=", new SystemLessThanEqualsWord());
        words.put("=", new SystemEqualsWord());
        words.put("<>", new SystemNotEqualsWord());
        words.put(">", new SystemGreaterThanWord());
        words.put("=>", new SystemGreaterThanEqualsWord());

        // Miscellania
        words.put(".", new SystemPrintWord());
        words.put("random", new SystemRandomWord());

        // Logic and Control
        words.put("and", new SystemAndWord());
        words.put("or", new SystemOrWord());
        words.put("invert", new SystemInvertWord());
    }

    /**
     * Executes a word, given it's string which indexes it
     * 
     * @param word : the index of the word (eg. +, -, *, /mod)
     */
    public void exec(String token) {
        System.err.println(token);
        
        if (words.containsKey(token))
            exec(words.get(token));
        else if (token.matches("\\.\".*\"")) // word is a string literal
            exec(new LiteralWord(token.substring(2, token.length() - 1)));
        else if (token.matches("[0-9]+")) // word is an integer literal
            exec(new LiteralWord(Integer.valueOf(token)));
        else if (token.matches("true") || token.matches("false")) // word is a boolean
            exec(new LiteralWord(token.equals("true")));
        else if (token.matches(":.*;")) { // If this is a user-definition of a word
            token = token.substring(2, token.length() - 2);
            String[] tokens = Script.tokenize(token);
            String[] content = new String[tokens.length - 1];
            for (int i = 0; i < content.length; i++)
                content[i] = tokens[i + 1];
            words.put(tokens[0], new CombinationWord(content));
        } else if (token.matches("do .* loop")) { // If it is a loop
            token = token.substring(3, token.length() - 5);
            exec(new LoopWord(token));
        } else if (token.matches("begin .* until")) { // If it is a begin loop
            token = token.substring(6, token.length() - 6);
            exec(new BeginLoopWord(token));
        } else if (token.matches("if .* then")) { // If it's a conditional
            String[] tokens = splitElses(token);
            if (tokens.length == 1) {
                exec(new ConditionalWord(tokens[0]));
            } else if (tokens.length == 2) {
                exec(new ConditionalWord(tokens[0], tokens[1]));
            } else {
                throw new ForthRuntimeException("Malformed conditional statement");
            }
        } else if (token.matches("variable .* ;")) {
            token=token.substring(9,token.length()-2);
            variables.put(token, new LiteralWord(0));
        } else if (token.matches("\\?")) {
            LiteralWord varName = pop();
            if (!varName.isVar())
                throw new ForthRuntimeException("Not variable reference!");
            if (variables.containsKey(varName.getVar()))
                push(variables.get(varName.getVar()));
        } else if (token.matches("!")) {
            LiteralWord val = pop();
            LiteralWord varName = pop();
            if (!varName.isVar())
                throw new ForthRuntimeException("Not variable reference!");
            if (variables.containsKey(varName.getVar()))
                variables.put(varName.getVar(), val);
        } else if (token.matches("\\( .*\\)")) { // a comment
        } else if (variables.containsKey(token)) {
            LiteralWord vname = new LiteralWord(token);
            vname.setVar();
            push(vname); // for the "variables" thing
        } else { // Not really a recognized word
            throw new ForthRuntimeException("Unrecognized word '" + token + "'");
        }
    }

    /**
     * Manually executes a Word class
     * 
     * @param word : the word to execute
     */
    protected void exec(Word word) {
        word.exec(this);
    }

    /**
     * Executes the Forth code defined by the script
     * 
     * @param script : the script to execute
     */
    public void exec(Script script) throws ForthException {
        try {
            for (String token : script)
                exec(token);
        } catch (ForthRuntimeException e) {
            throw new ForthException("The Forth Script failed: " + e.getMessage());
        }
    }

    /**
     * Pushes a value to the ForthInterpreter's stack
     * 
     * @param val : the value to push
     */
    public void push(LiteralWord val) {
        this.stack.push(val);
    }

    /**
     * Pops a value from the ForthInterpreter's stack
     * 
     * @return the popped value
     */
    public LiteralWord pop() {
        if (this.stack.isEmpty())
            throw new ForthRuntimeException("The stack is empty; cannot pop");
        return this.stack.pop();
    }

    /**
     * Contains the tests for the ForthInterpreter class
     */
    public static void main(String[] args) {
        ForthInterpreter fi = new ForthInterpreter();

        // Arithmetic Operators Tests
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(2));
        fi.exec("+");
        assert (fi.stack.pop().getInt() == 6);
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(2));
        fi.exec("-");
        assert (fi.stack.pop().getInt() == 2);
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(2));
        fi.exec("*");
        assert (fi.stack.pop().getInt() == 8);
        fi.exec(new LiteralWord(5));
        fi.exec(new LiteralWord(2));
        fi.exec("/mod");
        assert (fi.stack.pop().getInt() == 2);
        assert (fi.stack.pop().getInt() == 1);
        System.out.println("Arithmetic Tests succeeded!");

        // Literal Tests
        fi.exec(".\"yo momma\"");
        fi.exec(".\"yo\"");
        fi.exec("31");
        fi.exec("true");
        assert (fi.stack.pop().getBool());
        assert (fi.stack.pop().getInt() == 31);
        assert (fi.stack.pop().getString().equals("yo"));
        assert (fi.stack.pop().getString().equals("yo momma"));
        System.out.println("Literal Tests Succeeded!");

        // Comparison Tests
        // < test
        fi.exec(new LiteralWord(5));
        fi.exec(new LiteralWord(4));
        fi.exec("<");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec("<");
        assert (fi.stack.pop().getBool());
        // <= test
        fi.exec(new LiteralWord(5));
        fi.exec(new LiteralWord(4));
        fi.exec("<=");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(4));
        fi.exec("<=");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec("<=");
        assert (fi.stack.pop().getBool());
        // = test
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec("=");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord(true));
        fi.exec(new LiteralWord(false));
        fi.exec("=");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord("a"));
        fi.exec(new LiteralWord("b"));
        fi.exec("=");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(4));
        fi.exec("=");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(true));
        fi.exec(new LiteralWord(true));
        fi.exec("=");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord("a"));
        fi.exec(new LiteralWord("a"));
        fi.exec("=");
        assert (fi.stack.pop().getBool());
        // <> test
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec("<>");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(true));
        fi.exec(new LiteralWord(false));
        fi.exec("<>");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord("a"));
        fi.exec(new LiteralWord("b"));
        fi.exec("<>");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(4));
        fi.exec("<>");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord(true));
        fi.exec(new LiteralWord(true));
        fi.exec("<>");
        assert (!fi.stack.pop().getBool());
        fi.exec(new LiteralWord("a"));
        fi.exec(new LiteralWord("a"));
        fi.exec("<>");
        assert (!fi.stack.pop().getBool());
        // > test
        fi.exec(new LiteralWord(5));
        fi.exec(new LiteralWord(4));
        fi.exec(">");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec(">");
        assert (!fi.stack.pop().getBool());
        // >= test
        fi.exec(new LiteralWord(5));
        fi.exec(new LiteralWord(4));
        fi.exec("=>");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(4));
        fi.exec("=>");
        assert (fi.stack.pop().getBool());
        fi.exec(new LiteralWord(4));
        fi.exec(new LiteralWord(5));
        fi.exec("=>");
        assert (!fi.stack.pop().getBool());
        System.out.println("Comparison Tests Succeeded!");

        // Conditional Test
        fi.exec("true");
        fi.exec("if 1 else 2 then");
        assert (fi.stack.pop().getInt() == 1);
        fi.exec("false");
        fi.exec("if 1 else 2 then");
        assert (fi.stack.pop().getInt() == 2);
        fi.exec("true");
        fi.exec("if 1 3 5 else 2 then");
        assert (fi.stack.pop().getInt() == 5);
        assert (fi.stack.pop().getInt() == 3);
        assert (fi.stack.pop().getInt() == 1);


        fi.exec("false");
        fi.exec("true");
        fi.exec("if if 0 else 10 then else 2 then");
        assert (fi.stack.pop().getInt() == 10);

        fi.exec("true");
        fi.exec("true");
        fi.exec("if if 0 else 10 then else 2 then");
        assert (fi.stack.pop().getInt() == 0);

        fi.exec("true");
        fi.exec("false");
        fi.exec("if if 0 else 10 then else 2 then");
        assert (fi.stack.pop().getInt() == 2);
        System.out.println("Conditional Tests Succeeded!");

        // Script Test
        Script ts = new Script(": tst ( b : - ) if .\"marvel\" else .\"dc\" then ;" + " true tst");
        try {
            fi.exec(ts);
        } catch (ForthException e) {
            e.printStackTrace();
        }
        assert (fi.stack.pop().getString().equals("marvel"));
        ts = new Script("false : tst ( b : - ) if .\"marvel\" else .\"dc\" then ;" + " tst");
        try {
            fi.exec(ts);
        } catch (ForthException e) {
            e.printStackTrace();
        }
        assert (fi.stack.pop().getString().equals("dc"));
        System.out.println("Script Tests Succeeded!");
        
        // Variable Test
        ts = new Script("variable doc ; doc .\"matt smith\" ! 2 4 5 doc ?");
        try {
            fi.exec(ts);
        } catch (ForthException e) {
            e.printStackTrace();
        }
        assert (fi.stack.pop().getString().equals("matt smith"));
        System.out.println("Variable Storage Tests Success!");
    }
}
