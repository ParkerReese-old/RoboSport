package interpreter;

public class BeginLoopWord implements Word {

    protected String[] body;

    public BeginLoopWord(String body) {
        this.body = Script.tokenize(body);
    }

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord condition = new LiteralWord(true);
        while (condition.isBool() && condition.getBool()) {
            for (String t : this.body) {
                if (!t.equals("leave"))
                    runOn.exec(t);
                else
                    return;
            }
        }
    }

}
