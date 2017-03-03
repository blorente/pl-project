package procesamientos.typecheck.utils;

import programa.Program;
import programa.Program.Type;

public class TypeInferer {
    private final CompatibilityChecker compat;
    private Program program;

    public TypeInferer(Program program) {
        this.program = program;
        this.compat = new CompatibilityChecker(program);
    }

    public Type inferSum(Type op1, Type op2) {
        if (!compat.sumCompatible(op1, op2)) {
            return program.tError();
        }

        if (compat.isString(op1)) {
            return program.tUniString();
        }

        return inferArith(op1, op2);
    }

	public Type inferArith(Type op1, Type op2) {
		if (!compat.arithCompatible(op1, op2)) {
            return program.tError();
        }
		
		if (op1.equals(program.tReal()) || op2.equals(program.tReal())) {
            return program.tReal();
        }

        return program.tInt();
	}
	
	public Type inferModulus(Type op1, Type op2) {
		
		if (!op1.equals(program.tInt()) || !op2.equals(program.tInt())) {
            return program.tError();
        }

        return program.tInt();
	}

    public Type inferAnd(Type op1, Type op2) {
        if (compat.boolCompatible(op1, op2)) {
            return program.tBool();
        }

        return program.tError();
    }
}
