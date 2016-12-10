package interpreter;

public class SystemSwapWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord top = runOn.pop();
        LiteralWord bot = runOn.pop();
        runOn.push(top);
        runOn.push(bot);
    }

}
