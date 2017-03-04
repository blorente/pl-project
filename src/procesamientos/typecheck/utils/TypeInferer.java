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
		
		if (compat.isInt(op1) && compat.isInt(op2)) {
            return program.tInt();
        }

        return program.tError();
	}

    public Type inferBoolBinExp(Type op1, Type op2) {
        if (compat.boolCompatible(op1, op2)) {
            return program.tBool();
        }

        return program.tError();
    }
    
    public Type inferBoolUnExp(Type op) {
        if (compat.isBool(op)) {
            return program.tBool();
        }

        return program.tError();
    }
    
    public Type inferComparator(Type op1, Type op2) {
    	if(compat.comparatorCompatible(op1, op2)) {
    		return program.tBool();
    	}
    	
    	return program.tError();
    }
    
    public Type inferNegative(Type op) {
    	if (compat.isNumeric(op)) {
    		return op;
    	}
    	return program.tError();
    }
    
    public Type inferStrElem(Type op1, Type op2) {
    	if (compat.strElemCompatible(op1, op2)) {
    		return program.tUniChar();
    	}
    	
    	return program.tError();
    }

	public Type inferIntCast(Type tipo) {
		if (compat.intCompatible(tipo)) {
			return program.tInt();
		}
		return program.tError();
	}

	public Type inferRealCast(Type tipo) {
		if (compat.realCompatible(tipo)) {
			return program.tReal();
		}
		return program.tError();
	}

	public Type inferBoolCast(Type tipo) {
		if (compat.boolCompatible(tipo)) {
			return program.tBool();
		}
		return program.tError();
	}

	public Type inferUniCharCast(Type tipo) {
		if (compat.charCompatible(tipo)) {
			return program.tUniChar();
		}
		return program.tError();
	}
	
	public Type inferUniStrCast(Type tipo) {
		if (compat.strCompatible(tipo)) {
			return program.tUniString();
		}
		return program.tError();
	}
}
