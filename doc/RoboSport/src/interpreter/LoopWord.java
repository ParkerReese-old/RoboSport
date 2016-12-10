package interpreter;

public class LoopWord implements Word {
    protected String[] body;
    
    public LoopWord(String body) {
        this.body = Script.tokenize(body);
    }

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord lowerBound = runOn.pop();
        LiteralWord upperBound = runOn.pop();
        if (!lowerBound.isInt() || !upperBound.isInt())
            throw new ForthRuntimeException("Top of the stack are not integers");
        for (int i=lowerBound.getInt(); i<upperBound.getInt(); i++) {
            for (String t : this.body) {
                if (t.equals("leave"))
                    return;
                else if (t.equals("I"))
                    runOn.exec(new LiteralWord(i));
                else
                    runOn.exec(t);
            }
        }
    }
}
