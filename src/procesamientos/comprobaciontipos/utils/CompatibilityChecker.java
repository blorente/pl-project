package procesamientos.comprobaciontipos.utils;

import programa.Program;
import programa.Program.Type;

public class CompatibilityChecker {
    private Program program;

    public CompatibilityChecker(Program program) {
        this.program = program;
    }

    public boolean sumCompatible(Type op1, Type op2) {
        return arithCompatible(op1, op2) || concatCompatible(op1, op2);
    }

    public boolean arithCompatible(Type op1, Type op2) {
        return isNumeric(op1) && isNumeric(op2);
    }

    public boolean concatCompatible(Type op1, Type op2) {
        return (isString(op1) && isString(op2));
    }

    public boolean boolCompatible(Type op1, Type op2) {
        return (isBool(op1) && isBool(op2));
    }

    public boolean isBool(Type t) {
        return t.equals(program.tBool());
    }

    public boolean isNumeric(Type t) {
        return t.equals(program.tInt()) || t.equals(program.tReal());
    }

    public boolean isString(Type t) {
        return t.equals(program.tUniString());
    }
}
