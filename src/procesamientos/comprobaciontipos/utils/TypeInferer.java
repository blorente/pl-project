package procesamientos.comprobaciontipos.utils;

import programa.Program;

public class TypeInferer {
    private final CompatibilityChecker compat;
    private Program program;

    public TypeInferer(Program program) {
        this.program = program;
        this.compat = new CompatibilityChecker(program);
    }

    public Program.Type inferSum(Program.Type op1, Program.Type op2) {
        if (!compat.sumCompatible(op1, op2)) {
            return program.tError();
        }

        if (compat.isString(op1)) {
            return program.tUniString();
        }

        if (op1.equals(program.tReal()) || op2.equals(program.tReal())) {
            return program.tReal();
        }

        return program.tInt();
    }

    public Program.Type inferAnd(Program.Type op1, Program.Type op2) {
        if (compat.boolCompatible(op1, op2)) {
            return program.tBool();
        }

        return program.tError();
    }
}
