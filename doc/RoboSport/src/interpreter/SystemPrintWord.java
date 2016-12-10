package interpreter;

public class SystemPrintWord implements Word {

    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord toPrint = runOn.pop();
        if (toPrint.isString())
            System.out.println(toPrint.getString());
        else if (toPrint.isInt())
            System.out.println(toPrint.getInt());
        else 
            System.out.println(toPrint.getBool());
    }

}
