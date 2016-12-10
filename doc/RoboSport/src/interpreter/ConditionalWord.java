package interpreter;

public class ConditionalWord implements Word {
    
    protected String[] trueBranch;
    protected String[] falseBranch;
    
    public ConditionalWord(String trueBranch) {
        this.trueBranch = Script.tokenize(trueBranch);
        this.falseBranch = null;
    }
    
    public ConditionalWord(String trueBranch, String falseBranch) {
        this.trueBranch = Script.tokenize(trueBranch);
        this.falseBranch = Script.tokenize(falseBranch);
    }

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord condition = runOn.pop();
        if (!condition.isBool())
            throw new ForthRuntimeException("Top of the stack is not boolean");
        if (condition.getBool())
            for (String t : trueBranch)
                runOn.exec(t);
        else if (this.falseBranch!=null)
            for (String t : falseBranch)
                runOn.exec(t);
    }

}
