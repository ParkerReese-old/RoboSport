package interpreter;

public class SystemRotateWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord top = runOn.pop();
        LiteralWord mid = runOn.pop();
        LiteralWord bot = runOn.pop();
        runOn.push(top);
        runOn.push(bot);
        runOn.push(mid);
    }

}