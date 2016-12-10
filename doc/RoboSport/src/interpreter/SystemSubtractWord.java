package interpreter;

public class SystemSubtractWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord first = runOn.pop();
        LiteralWord second = runOn.pop();
        if (!(first.isInt() & second.isInt()))
            throw new ForthRuntimeException("There must be 2 integer items on the stack");
        
        runOn.push(new LiteralWord(second.getInt()-first.getInt()));
    }

}
