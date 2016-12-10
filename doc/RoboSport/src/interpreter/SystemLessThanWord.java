package interpreter;

public class SystemLessThanWord extends ComparisonWord {

    @Override
    protected boolean compare(LiteralWord a, LiteralWord b) {
        if (!(a.isInt() && b.isInt()))
            throw new ForthRuntimeException("Both arguments must be ints");
        return a.getInt()<b.getInt();
    }

}
