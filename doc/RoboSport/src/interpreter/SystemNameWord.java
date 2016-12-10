package interpreter;

public class SystemNameWord implements Word {

    //if name is not type int or string an exception is thrown
    //if name is of an accepted type then it is pushed back onto the stack
    //does not need to push names location because ! and ? can find that using name
    @Override
    public void exec(ForthInterpreter runOn) {
        LiteralWord name = runOn.pop();      
        if(!(name.isString()| name.isInt()))
            throw new ForthRuntimeException("There must be 1 string or int item on the stack");
        runOn.push(name);

    }

}
