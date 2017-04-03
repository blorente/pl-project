package procesamientos.typecheck.utils;

import programa.Program;
import programa.Program.TPointer;
import programa.Program.TRef;
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

	public boolean strElemCompatible(Type op1, Type op2) {
		return isString(op1) && isInt(op2);
	}

	public boolean boolCompatible(Type op1, Type op2) {
		return (isBool(op1) && isBool(op2));
	}

	public boolean comparatorCompatible(Type op1, Type op2) {
		if (isNumeric(op1) && isNumeric(op2)) {
			return true;
		}
		if (isBool(op1) && isBool(op2)) {
			return true;
		}
		if (isChar(op1) && isChar(op2)) {
			return true;
		}
		if (isString(op1) && isString(op2)) {
			return true;
		}
		return false;
	}

	public boolean isBool(Type t) {
		return t.equals(program.tBool());
	}

	public boolean isNumeric(Type t) {
		return isInt(t) || t.equals(program.tReal());
	}

	public boolean isString(Type t) {
		return t.equals(program.tUniString());
	}

	public boolean isInt(Type t) {
		return t.equals(program.tInt());
	}

	public boolean isReal(Type t) {
		return t.equals(program.tReal());
	}

	public boolean isChar(Type t) {
		return t.equals(program.tUniChar());
	}

	public boolean intCompatible(Type tipo) {
		return isBool(tipo) || isInt(tipo) || isReal(tipo) || isChar(tipo);
	}

	public boolean realCompatible(Type tipo) {
		return isBool(tipo) || isInt(tipo) || isReal(tipo) || isChar(tipo);
	}

	public boolean boolCompatible(Type tipo) {
		return isBool(tipo) || isInt(tipo);
	}

	public boolean charCompatible(Type tipo) {
		return isChar(tipo) || isInt(tipo);
	}

	public boolean strCompatible(Type tipo) {
		return isString(tipo) || isChar(tipo);
	}

	public static boolean esPointer(Type t) {

		return t instanceof TPointer;
	}

	public TPointer pointer(Type t) {
		return (TPointer) t;
	}

	public boolean isRef(Type t) {
		return t instanceof TRef;
	}

	public TRef asRef(Type t) {
		return (TRef) t;
	}

	public Type baseType(Type t) {
		while (isRef(t)) {
			t = asRef(t).declaration().decType();
		}
		return t;
	}
}
