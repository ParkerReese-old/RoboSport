package interpreter;

public class SystemEqualsWord extends ComparisonWord {

    @Override
    protected boolean compare(LiteralWord a, LiteralWord b) {
        if (a.isInt() && b.isInt())
            return a.getInt()==b.getInt();
        else if (a.isString() && b.isString())
            return a.getString().equals(b.getString());
        else if (a.isBool() && b.isBool())
            return a.getBool()==b.getBool();
        return false;
    }

}
