package interpreter;

public class SystemOrWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord first = runOn.pop();
        LiteralWord second = runOn.pop();
        if (!(first.isBool() & second.isBool()))
            throw new ForthRuntimeException("There must be 2 boolean items on the stack");

        runOn.push(new LiteralWord(first.getBool() | second.getBool()));
    }
    

}
