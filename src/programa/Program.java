package programa;

import procesamientos.Processing;

public abstract class Program {
	private final Type TINT;
	private final Type TBOOL;
	private final Type TREAL;
	private final Type TUNICHAR;
	private final Type TUNISTRING;
	private final Type TOK;
	private final Type TERROR;

	public Program() {
		TINT = new Int();
		TBOOL = new Bool();
		TREAL = new Real();
		TUNICHAR = new UniChar();
		TUNISTRING = new UniString();
		TOK = new Ok();
		TERROR = new Error();
	}

	public interface Type {
		void accept(Processing p);
	}

	public class Int implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "INT";
		}
	}

	public class Bool implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "BOOL";
		}
	}

	public class Real implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "REAL";
		}
	}

	public class UniChar implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "UNICHAR";
		}
	}

	public class UniString implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "UNISTRING";
		}
	}

	public class Ok implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "OK";
		}
	}

	public class Error implements Type {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "ERROR";
		}
	}

	public class Prog {
		private Dec[] decs;
		private Inst i;
		private Type tipo;

		public Prog(Dec[] decs, Inst i) {
			this.decs = decs;
			this.i = i;
			this.tipo = null;
		}

		public Dec[] decs() {
			return decs;
		}

		public Inst inst() {
			return i;
		}

		public Type tipo() {
			return tipo;
		}

		public void ponTipo(Type tipo) {
			this.tipo = tipo;
		}

		public void procesaCon(Processing p) {
			p.process(this);
		}
	}

	public abstract class Dec {
		public abstract void procesaCon(Processing p);
	}

	public class DecVar extends Dec {
		private String enlaceFuente;
		private String var;
		private Type tipoDec;
		private int dir;

		public DecVar(Type tipo, String var) {
			this(tipo, var, null);
		}

		public DecVar(Type tipo, String var, String enlaceFuente) {
			this.tipoDec = tipo;
			this.enlaceFuente = enlaceFuente;
			this.var = var;
		}

		public Type tipoDec() {
			return tipoDec;
		}

		public String var() {
			return var;
		}

		public int dir() {
			return dir;
		}

		public void ponDir(int dir) {
			this.dir = dir;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public void procesaCon(Processing p) {
			p.process(this);
		}
	}

	public abstract class Inst {
		private Type tipo;

		public Inst() {
			tipo = null;
		}

		public Type tipo() {
			return tipo;
		}

		public void ponTipo(Type tipo) {
			this.tipo = tipo;
		}

		public abstract void procesaCon(Processing p);
	}

	public class IAsig extends Inst {
		private String var;
		private Exp exp;
		private String enlaceFuente;
		private DecVar declaracion;

		public IAsig(String var, Exp exp, String enlaceFuente) {
			this.var = var;
			this.exp = exp;
			this.declaracion = null;
			this.enlaceFuente = enlaceFuente;
		}

		public IAsig(String var, Exp exp) {
			this(var, exp, null);
		}

		public String var() {
			return var;
		}

		public Exp exp() {
			return exp;
		}

		public DecVar declaracion() {
			return declaracion;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public void ponDeclaracion(DecVar d) {
			declaracion = d;
		}

		public void procesaCon(Processing p) {
			p.process(this);
		}
	}

	public class IBloque extends Inst {
		private Inst[] is;

		public IBloque(Inst[] is) {
			this.is = is;
		}

		public Inst[] is() {
			return is;
		}

		public void procesaCon(Processing p) {
			p.process(this);
		}
	}

	public abstract class Exp {
		private Type tipo;

		public Exp() {
			tipo = null;
		}

		public void ponTipo(Type tipo) {
			this.tipo = tipo;
		}

		public Type tipo() {
			return tipo;
		}

		public abstract void processWith(Processing p);
	}

	public class Var extends Exp {
		private String var;
		private DecVar declaracion;
		private String enlaceFuente;

		public Var(String var) {
			this(var, null);
		}

		public Var(String var, String enlaceFuente) {
			this.var = var;
			declaracion = null;
			this.enlaceFuente = enlaceFuente;
		}

		public String var() {
			return var;
		}

		public DecVar declaracion() {
			return declaracion;
		}

		public void ponDeclaracion(DecVar dec) {
			declaracion = dec;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class IntCt extends Exp {
		private int val;

		public IntCt(int val) {
			this.val = val;
		}

		public int intVal() {
			return val;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class BoolCt extends Exp {
		private boolean val;

		public BoolCt(boolean val) {
			this.val = val;
		}

		public boolean boolVal() {
			return val;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class RealCt extends Exp {
		private double val;

		public RealCt(double val) {
			this.val = val;
		}

		public double realVal() {
			return val;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class UniCharCt extends Exp {
		private char val;

		public UniCharCt(char val) {
			this.val = val;
		}

		public char charVal() {
			return val;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class UniStringCt extends Exp {
		private String val;

		public UniStringCt(String val) {
			this.val = val;
		}

		public String stringVal() {
			return val;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	private abstract class BinaryExp extends Exp {
		private Exp opnd1;
		private Exp opnd2;
		private String enlaceFuente;

		public BinaryExp(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public BinaryExp(Exp opnd1, Exp opnd2, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
			this.opnd1 = opnd1;
			this.opnd2 = opnd2;
		}

		public Exp opnd1() {
			return opnd1;
		}

		public Exp opnd2() {
			return opnd2;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}
	}

	public class Addition extends BinaryExp {
		public Addition(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Addition(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Subtraction extends BinaryExp {
		public Subtraction(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Subtraction(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Multiplication extends BinaryExp {
		public Multiplication(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Multiplication(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Division extends BinaryExp {
		public Division(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Division(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Modulus extends BinaryExp {
		public Modulus(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Modulus(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class And extends BinaryExp {
		public And(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public And(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Or extends BinaryExp {
		public Or(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Or(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Not extends UnaryExp {
		public Not(Exp opnd1) {
			this(opnd1, null);
		}

		public Not(Exp opnd1, String enlaceFuente) {
			super(opnd1, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Equals extends BinaryExp {
		public Equals(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Equals(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class NotEquals extends BinaryExp {
		public NotEquals(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public NotEquals(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Greater extends BinaryExp {
		public Greater(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Greater(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class GreaterEq extends BinaryExp {
		public GreaterEq(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public GreaterEq(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class Less extends BinaryExp {
		public Less(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Less(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class LessEq extends BinaryExp {
		public LessEq(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public LessEq(Exp opnd1, Exp opnd2, String enlaceFuente) {
			super(opnd1, opnd2, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class StrElem extends BinaryExp {
		public StrElem(Exp source, Exp index) {
			this(source, index, null);
		}

		public StrElem(Exp source, Exp index, String enlaceFuente) {
			super(source, index, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	private abstract class UnaryExp extends Exp {
		private Exp op;
		private String enlaceFuente;

		public UnaryExp(Exp op) {
			this(op, null);
		}

		public UnaryExp(Exp op, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
			this.op = op;
		}

		public Exp op() {
			return op;
		}

		public String enlaceFuente() {
			return enlaceFuente;
		}
	}

	public class Negative extends UnaryExp {
		public Negative(Exp op) {
			this(op, null);
		}

		public Negative(Exp op, String enlaceFuente) {
			super(op, enlaceFuente);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class IntCast extends UnaryExp {
		public IntCast(Exp op) {
			this(op, null);
		}

		public IntCast(Exp exp, String sourceLink) {
			super(exp, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class RealCast extends UnaryExp {
		public RealCast(Exp op) {
			this(op, null);
		}

		public RealCast(Exp exp, String sourceLink) {
			super(exp, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class BoolCast extends UnaryExp {
		public BoolCast(Exp op) {
			this(op, null);
		}

		public BoolCast(Exp exp, String sourceLink) {
			super(exp, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class UniCharCast extends UnaryExp {
		public UniCharCast(Exp op) {
			this(op, null);
		}

		public UniCharCast(Exp exp, String sourceLink) {
			super(exp, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public class UniStrCast extends UnaryExp {
		public UniStrCast(Exp op) {
			this(op, null);
		}

		public UniStrCast(Exp exp, String sourceLink) {
			super(exp, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public Prog prog(Dec[] decs, Inst i) {
		return new Prog(decs, i);
	}

	public Dec decvar(Type t, String v) {
		return new DecVar(t, v);
	}

	public Dec decvar(Type t, String v, String enlaceFuente) {
		return new DecVar(t, v, enlaceFuente);
	}

	public Inst iasig(String v, Exp e) {
		return new IAsig(v, e);
	}

	public Inst iasig(String v, Exp e, String enlaceFuente) {
		return new IAsig(v, e, enlaceFuente);
	}

	public Inst ibloque(Inst[] is) {
		return new IBloque(is);
	}

	public Exp var(String id) {
		return new Var(id);
	}

	public Exp var(String id, String enlaceFuente) {
		return new Var(id, enlaceFuente);
	}

	public Exp intct(int val) {
		return new IntCt(val);
	}

	public Exp boolct(boolean val) {
		return new BoolCt(val);
	}

	public Exp realct(double val) {
		return new RealCt(val);
	}

	public Exp unicharct(char val) {
		return new UniCharCt(val);
	}

	public Exp unistringct(String val) {
		return new UniStringCt(val);
	}

	public Exp add(Exp exp1, Exp exp2) {
		return new Addition(exp1, exp2);
	}

	public Exp subtract(Exp exp1, Exp exp2) {
		return new Subtraction(exp1, exp2);
	}

	public Exp multiply(Exp exp1, Exp exp2) {
		return new Multiplication(exp1, exp2);
	}

	public Exp divide(Exp exp1, Exp exp2) {
		return new Division(exp1, exp2);
	}

	public Exp and(Exp exp1, Exp exp2) {
		return new And(exp1, exp2);
	}

	public Exp add(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Addition(exp1, exp2, enlaceFuente);
	}

	public Exp subtract(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Subtraction(exp1, exp2, enlaceFuente);
	}

	public Exp multiply(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Multiplication(exp1, exp2, enlaceFuente);
	}

	public Exp divide(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Division(exp1, exp2, enlaceFuente);
	}

	public Exp modulus(Exp exp1, Exp exp2, String enlaceFuente) {
		return new Modulus(exp1, exp2, enlaceFuente);
	}

	public Exp and(Exp exp1, Exp exp2, String enlaceFuente) {
		return new And(exp1, exp2, enlaceFuente);
	}

	public Exp or(Exp exp1, Exp exp2) {
		return new Or(exp1, exp2);
	}

	public Exp not(Exp exp) {
		return new Not(exp);
	}

	public Exp equals(Exp exp1, Exp exp2) {
		return new Equals(exp1, exp2);
	}

	public Exp notequals(Exp exp1, Exp exp2) {
		return new NotEquals(exp1, exp2);
	}

	public Exp greater(Exp exp1, Exp exp2) {
		return new Greater(exp1, exp2);
	}

	public Exp greatereq(Exp exp1, Exp exp2) {
		return new GreaterEq(exp1, exp2);
	}

	public Exp less(Exp exp1, Exp exp2) {
		return new Less(exp1, exp2);
	}

	public Exp lesseq(Exp exp1, Exp exp2) {
		return new LessEq(exp1, exp2);
	}

	public Exp strElem(Exp string, Exp index, String enlaceFuente) {
		return new StrElem(string, index);
	}

	public Exp negative(Exp exp1, String enlaceFuente) {
		return new Negative(exp1, enlaceFuente);
	}

	public Exp intcast(Exp exp) {
		return new IntCast(exp);
	}

	public Exp realcast(Exp exp) {
		return new RealCast(exp);
	}

	public Exp boolcast(Exp exp) {
		return new BoolCast(exp);
	}

	public Exp charcast(Exp exp) {
		return new UniCharCast(exp);
	}

	public Exp strcast(Exp exp) {
		return new UniStrCast(exp);
	}

	public Type tInt() {
		return TINT;
	}

	public Type tBool() {
		return TBOOL;
	}

	public Type tReal() {
		return TREAL;
	}

	public Type tUniChar() {
		return TUNICHAR;
	}

	public Type tUniString() {
		return TUNISTRING;
	}

	public Type tOk() {
		return TOK;
	}

	public Type tError() {
		return TERROR;
	}

	public abstract Prog root();

}
