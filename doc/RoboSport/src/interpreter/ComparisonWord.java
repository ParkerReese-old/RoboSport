package interpreter;

public abstract class ComparisonWord implements Word {

    protected abstract boolean compare(LiteralWord a, LiteralWord b);
    
    @Override
    public final void exec(ForthInterpreter runOn) {
        LiteralWord a = runOn.pop();
        LiteralWord b = runOn.pop();
        runOn.exec(new LiteralWord(compare(b,a)));
    }
}
