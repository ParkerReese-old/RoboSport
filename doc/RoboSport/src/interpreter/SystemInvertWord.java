package interpreter;

public class SystemInvertWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord first = runOn.pop();
        if (!(first.isBool()))
            throw new ForthRuntimeException("There must be 1 boolean item on the stack");

        runOn.push(new LiteralWord(!(first.getBool())));
    }

}
