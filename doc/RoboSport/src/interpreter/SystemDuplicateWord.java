package interpreter;

public class SystemDuplicateWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord dup = runOn.pop();
        runOn.push(dup);
        runOn.push(dup);
    }

}
