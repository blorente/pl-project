package procesamientos.typecheck.utils;

import programa.Program;
import programa.Program.TPointer;
import programa.Program.TRef;
import programa.Program.Type;

import java.util.HashSet;
import java.util.Set;

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

	public static boolean isPointer(Type t) {

		return t instanceof TPointer;
	}
	public static TPointer pointer(Type t) {
		return (TPointer) t;
	}

	public static boolean isRef(Type t) {
		return t instanceof TRef;
	}

	public static TRef asRef(Type t) {
		return (TRef) t;
	}

	public static Type baseType(Type t) {
		while (CompatibilityChecker.isRef(t)) {
			t = CompatibilityChecker.asRef(t).declaration().decType();
		}
		return t;
	}

	private static class Tipox2 {
		private Type t1;
		private Type t2;
		public Tipox2 (Type t1, Type t2) {
			this.t1 = t1;
			this.t2 = t2;
		}
		public boolean equals(Object o) {
			return (o instanceof Tipox2) &&
					t1.equals(((Tipox2)o).t1) &&
					t2.equals(((Tipox2)o).t2);
		}
		public int hashCode() {
			return t1.hashCode()+t2.hashCode();
		}
	}

	public static boolean areCompatible(Type t1, Type t2) {
		return areCompatible(t1,t2,new HashSet<Tipox2>());
	}
	public static boolean areCompatible(Type t1, Type t2, Set<Tipox2> considered) {
		t1 = CompatibilityChecker.baseType(t1);
		t2 = CompatibilityChecker.baseType(t2);
		if(considered.add(new Tipox2(t1,t2))) {
			if(t1.equals(t2)) return true;
			else if (CompatibilityChecker.isPointer(t1) && CompatibilityChecker.isPointer(t2)) {
				return areCompatible(CompatibilityChecker.pointer(t1).tbase(),CompatibilityChecker.pointer(t2).tbase(),considered);
			} else
				return false;
		} else {
			return true;
		}
	}
}
