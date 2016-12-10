package interpreter;

public class SystemDropWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        runOn.pop();
    }

}
