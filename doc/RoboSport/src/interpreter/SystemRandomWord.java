package interpreter;

public class SystemRandomWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord bound = runOn.pop();
        if (!bound.isInt())
            throw new ForthRuntimeException("Top of stack should be an int");
        runOn.exec(new LiteralWord((int) (Math.random()%(bound.getInt()+1))));
    }

}
