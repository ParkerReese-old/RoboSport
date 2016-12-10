package interpreter;

public class LiteralWord implements Word {
    protected int which;
    
    protected int integerVal;
    protected String stringVal;
    
    public LiteralWord(int val) {
        which = 0;
        integerVal = val;
    }
    
    public LiteralWord(String val) {
        which = 1;
        stringVal = val;
    }
    
    public LiteralWord(boolean val) {
        which = 2;
        integerVal = val?1:0;
    }
    
    public boolean isInt() {
        return which==0;
    }
    
    public boolean isString() {
        return which==1;
    }
    
    public boolean isBool() {
        return which==2;
    }
    
    public boolean isVar() {
        return which==3;
    }
    
    public void setVar() {
        if (!(which==1 || which==3))
            throw new ForthRuntimeException("Variables must be stored as strings");
        which=3;
    }
    
    public int getInt() {
        if (!isInt())
            throw new RuntimeException("Item is not an integer");
        return integerVal;
    }
    
    public String getString() {
        if (!isString())
            throw new RuntimeException("Item is not a string");
        return stringVal;
    }
    
    public boolean getBool() {
        if (!isBool())
            throw new RuntimeException("Item is not a boolean");
        return integerVal==1;
    }
    
    public String getVar() {
        if (!isVar())
            throw new RuntimeException("Item is not a variable");
        return stringVal;
    }

    @Override
    public void exec(ForthInterpreter runOn) {
        // Pushes the literal to the stack
        runOn.push(this);
    }
    
}
