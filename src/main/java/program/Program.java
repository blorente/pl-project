package program;

import procesamientos.Processing;
import procesamientos.typecheck.utils.CompatibilityChecker;

import java.util.ArrayList;
import java.util.List;

public abstract class Program {
	private final DeclaredType TINT;
	private final DeclaredType TBOOL;
	private final DeclaredType TREAL;
	private final DeclaredType TUNICHAR;
	private final DeclaredType TUNISTRING;
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

	public abstract class DeclaredType implements Type {
		private int size;

		public DeclaredType() {
			size = 0;
		}

		public int size() {
			return size;
		}

		public void putSize(int tam) {
			this.size = tam;
		}
	}

	public class Int extends DeclaredType {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "INT";
		}
	}
	public class Bool extends DeclaredType {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "BOOL";
		}
	}
	public class Real extends DeclaredType {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "REAL";
		}
	}
	public class UniChar extends DeclaredType {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "UNICHAR";
		}
	}
	public class UniString extends DeclaredType {
		public void accept(Processing p) {
			p.process(this);
		}

		public String toString() {
			return "UNISTRING";
		}
	}
	public class TPointer extends DeclaredType {
		private DeclaredType tbase;
		public TPointer(DeclaredType tbase) {
			this.tbase = tbase;
		}
		public DeclaredType tbase() {
			return tbase;
		}
		public void accept(Processing p) {
			p.process(this);
		}
	}
	public class TRef extends DeclaredType {
		private String typeId;
		private DecType vinculo;
		private String sourceLink;

		public TRef(String typeId) {
			this(typeId, null);
		}
		public TRef(String typeId, String sourceLink) {
			this.typeId = typeId;
			this.sourceLink = sourceLink;
		}
		public DecType declaration() {
			return vinculo;
		}
		public void ponDeclaracion(DecType decTipo) {
			this.vinculo = decTipo;
		}
		public String typeId() {
			return typeId;
		}
		public String sourceLink() {
			return sourceLink;
		}
		public void accept(Processing p) {
			p.process(this);
		}
	}
	public class TArray extends DeclaredType {
		private DeclaredType tbase;
		private int size;
		public TArray(DeclaredType tbase, int size) {
			this.size = size;
			this.tbase = tbase;
		}
		public DeclaredType tbase() {
			return tbase;
		}
		@Override
		public int size() {
			return size;
		}
		public void accept(Processing p) {
			p.process(this);
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

		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public abstract class Dec {
		public abstract void processWith(Processing p);
	}
	public class DecType extends Dec {
		private String sourceLink;
		private String typeId;
		private DeclaredType decType;

		public DecType(DeclaredType type, String typeId) {
			this(type, typeId, null);
		}

		public DecType(DeclaredType tipo, String typeId, String sourceLink) {
			this.decType = tipo;
			this.sourceLink = sourceLink;
			this.typeId = typeId;
		}

		public DeclaredType decType() {
			return decType;
		}

		public String typeId() {
			return typeId;
		}

		public String sourceLink() {
			return sourceLink;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class DecVar extends Dec {
		private String sourceLink;
		private String var;
		private DeclaredType decType;
		private int addr;

		public DecVar(DeclaredType tipo, String var) {
			this(tipo, var, null);
		}

		public DecVar(DeclaredType tipo, String var, String sourceLink) {
			this.decType = tipo;
			this.sourceLink = sourceLink;
			this.var = var;
		}

		public DeclaredType decType() {
			return decType;
		}

		public String var() {
			return var;
		}

		public int addr() {
			return addr;
		}

		public void assignAddr(int addr) {
			this.addr = addr;
		}

		public String sourceLink() {
			return sourceLink;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class FParam extends DecVar {
		private boolean byReference;
		public FParam(String id, DeclaredType type, boolean byReference) {
			this(null,id,type,byReference);
		}
		public FParam(String sourceLink, String id, DeclaredType type, boolean porReferencia) {
			super(type,id,sourceLink);
			this.byReference = byReference;
		}
		public boolean esParametroPorReferencia() {return byReference;}
	}

	public abstract class Inst {
		private Type tipo;
		protected int firstInstDir;
		protected int nextInstDir;

		public Inst() {
			tipo = null;
		}

		public Type tipo() {
			return tipo;
		}

		public void ponTipo(Type tipo) {
			this.tipo = tipo;
		}

		public int dirFirst() {
			return firstInstDir;
		}

		public void putDirFirst(int dir) {
			firstInstDir = dir;
		}

		public int dirNext() {
			return nextInstDir;
		}

		public void putDirNext(int dir) {
			nextInstDir = dir;
		}

		public abstract void processWith(Processing p);
	}
	public class IAsig extends Inst {
		private Mem mem;
		private Exp exp;
		private String sourceLink;

		public IAsig(Mem mem, Exp exp, String sourceLink) {
			this.mem = mem;
			this.exp = exp;
			this.sourceLink = sourceLink;
		}
		public IAsig(Mem mem, Exp exp) {
			this(mem, exp, null);
		}
		public Mem mem() {
			return mem;
		}
		public Exp exp() {
			return exp;
		}
		public String sourceLink() {
			return sourceLink;
		}
		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class INew extends Inst {
		private Mem mem;
		private String sourceLink;
		public INew(Mem mem) {
			this(mem,null);
		}
		public INew(Mem mem, String sourceLink) {
			this.mem = mem;
			this.sourceLink = sourceLink;
		}
		public Mem mem() {return mem;}
		public String sourceLink() {return sourceLink;}
		public void sourceLink(String sourceLink) {this.sourceLink = sourceLink;}
		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class IFree extends Inst {
		private Mem mem;
		private String sourceLink;
		public IFree(Mem mem) {
			this(mem,null);
		}
		public IFree(Mem mem, String sourceLink) {
			this.mem = mem;
			this.sourceLink = sourceLink;
		}
		public Mem mem() {return mem;}
		public String sourceLink() {return sourceLink;}
		public void sourceLink(String sourceLink) {this.sourceLink = sourceLink;}
		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class IBlock extends Inst {
		private Inst[] is;

		public IBlock(Inst[] is) {
			this.is = is;
		}

		public Inst[] is() {
			return is;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class IRead extends Inst {
		private String variable;
		private DecVar declaracion;

		public IRead(String refvar) {
			this.variable = refvar;
		}

		public String var() {
			return variable;
		}

		public DecVar declaracion() {
			return declaracion;
		}

		public void ponDeclaracion(DecVar d) {
			declaracion = d;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class IWrite extends Inst {
		private String variable;
		private DecVar declaration;

		public IWrite(String refvar) {
			this.variable = refvar;
		}

		public String var() {
			return variable;
		}

		public DecVar declaration() {
			return declaration;
		}

		public void putDeclaration(DecVar d) {
			declaration = d;
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class IWhile extends Inst {

		private Exp cond;
		private Inst body;
		private String sourceLink;

		public IWhile(Exp cond, Inst body) {
			this.cond = cond;
			this.body = body;
		}

        public IWhile(Exp cond, Inst body, String sourceLink) {
            this.cond = cond;
            this.body = body;
            this.sourceLink = sourceLink;
        }

        public Exp getCond() {
			return cond;
		}
		public Inst getBody() {
			return body;
		}
		public String sourceLink() {return sourceLink;}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}

	}
	public class IIfThen extends Inst {

		private Exp cond;
		private Inst body;

		public IIfThen(Exp cond, Inst body) {
			this.cond = cond;
			this.body = body;
		}

		public Exp getCond() {
			return cond;
		}

		public Inst getThen() {
			return body;
		}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}

	}
	public class IIfThenElse extends Inst {

		private Exp cond;
		private Inst thenBlock;
		private Inst elseBlock;

		public IIfThenElse(Exp cond, Inst thenBlock, Inst elseBlock) {
			this.cond = cond;
			this.thenBlock = thenBlock;
			this.elseBlock = elseBlock;
		}

		public Exp getCond() {
			return cond;
		}

		public Inst getThen() {
			return thenBlock;
		}

		public Inst getElse() {
			return elseBlock;
		}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}

	}
	public class IDoWhile extends Inst {

		private Exp cond;
		private Inst body;

		public IDoWhile(Exp cond, Inst body) {
			this.cond = cond;
			this.body = body;
		}

		public Exp getCond() {
			return cond;
		}

		public Inst getBody() {
			return body;
		}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}

	}
	public class ISwitch extends Inst {

		private List<ICase> cases;
		private Exp cond;
		private Inst defaul;
		private boolean hasDefault;

		public ISwitch(Exp cond, Inst defaul, ICase[] cases) {
			this.cond = cond;
			this.defaul = defaul;
			hasDefault = true;
			this.cases = new ArrayList<>();
			for (ICase c : cases) {
				this.cases.add(c);
			}
		}

		public ISwitch(Exp cond, ICase[] cases) {
			this.cond = cond;
			this.defaul = null;
			hasDefault = false;
			this.cases = new ArrayList<>();
			for (ICase c : cases) {
				this.cases.add(c);
			}
		}

		public List<ICase> getCases() {
			return cases;
		}

		public Exp getCond() {
			return cond;
		}

		public Inst getDefault() {
			return defaul;
		}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}

		public boolean hasDefault() {
			return hasDefault;
		}
	}
	public class ICase extends Inst {

		private Exp exp;
		private Inst body;

		public ICase(Exp cond, Inst body) {
			this.exp = cond;
			this.body = body;
		}

		public Exp getExp() {
			return exp;
		}

		public Inst getBody() {
			return body;
		}

		@Override
		public void processWith(Processing p) {
			p.process(this);
		}
	}

	public abstract class Exp {
		private Type tipo;
		protected int firstInstDir;
		protected int nextInstDir;

		public Exp() {
			tipo = null;
		}

		public void ponTipo(Type tipo) {
			this.tipo = CompatibilityChecker.baseType(tipo);
		}

		public Type type() {
			return tipo;
		}

		public int dirFirst() {
			return firstInstDir;
		}

		public void putDirFirst(int dir) {
			firstInstDir = dir;
		}

		public int dirNext() {
			return nextInstDir;
		}

		public void putDirNext(int dir) {
			nextInstDir = dir;
		}

		public abstract void processWith(Processing p);

		public boolean isMem() {return false;}
	}
	public abstract class Mem extends Exp {
		private String sourcelink;
		public Mem(String sourcelink) {
			this.sourcelink = sourcelink;
		}
		public String sourcelink() {
			return sourcelink;
		}
		public boolean isMem() {return true;}
	}
	public class DecRef extends Mem {
		private Mem mem;
		public DecRef(Mem mem) {
			this(mem,null);
		}
		public DecRef(Mem mem, String sourcelink) {
			super(sourcelink);
			this.mem = mem;
		}
		public Mem mem() {
			return mem;
		}
		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Var extends Mem {
		private String var;
		private DecVar declaracion;
		private String sourceLink;

		public Var(String var) {
			this(var, null);
		}

		public Var(String var, String sourceLink) {
			super(sourceLink);
			this.var = var;
			declaracion = null;
			this.sourceLink = sourceLink;
		}

		public String var() {
			return var;
		}

		public DecVar declaration() {
			return declaracion;
		}

		public void putDeclaration(DecVar dec) {
			declaracion = dec;
		}

		public String sourceLink() {
			return sourceLink;
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
	public abstract class BinaryExp extends Exp {
		private Exp opnd1;
		private Exp opnd2;
		private String sourceLink;

		public BinaryExp(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public BinaryExp(Exp opnd1, Exp opnd2, String sourceLink) {
			this.sourceLink = sourceLink;
			this.opnd1 = opnd1;
			this.opnd2 = opnd2;
		}

		public Exp opnd1() {
			return opnd1;
		}

		public Exp opnd2() {
			return opnd2;
		}

		public String sourceLink() {
			return sourceLink;
		}
	}
	public class Addition extends BinaryExp {
		public Addition(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Addition(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Subtraction extends BinaryExp {
		public Subtraction(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Subtraction(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Multiplication extends BinaryExp {
		public Multiplication(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Multiplication(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Division extends BinaryExp {
		public Division(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Division(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Modulus extends BinaryExp {
		public Modulus(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Modulus(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class And extends BinaryExp {
		public And(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public And(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Or extends BinaryExp {
		public Or(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Or(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Not extends UnaryExp {
		public Not(Exp opnd1) {
			this(opnd1, null);
		}

		public Not(Exp opnd1, String sourceLink) {
			super(opnd1, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Equals extends BinaryExp {
		public Equals(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Equals(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class NotEquals extends BinaryExp {
		public NotEquals(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public NotEquals(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Greater extends BinaryExp {
		public Greater(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Greater(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class GreaterEq extends BinaryExp {
		public GreaterEq(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public GreaterEq(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class Less extends BinaryExp {
		public Less(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public Less(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class LessEq extends BinaryExp {
		public LessEq(Exp opnd1, Exp opnd2) {
			this(opnd1, opnd2, null);
		}

		public LessEq(Exp opnd1, Exp opnd2, String sourceLink) {
			super(opnd1, opnd2, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public class StrElem extends BinaryExp {
		public StrElem(Exp source, Exp index) {
			this(source, index, null);
		}

		public StrElem(Exp source, Exp index, String sourceLink) {
			super(source, index, sourceLink);
		}

		public void processWith(Processing p) {
			p.process(this);
		}
	}
	public abstract class UnaryExp extends Exp {
		private Exp op;
		private String sourceLink;

		public UnaryExp(Exp op) {
			this(op, null);
		}

		public UnaryExp(Exp op, String sourceLink) {
			this.sourceLink = sourceLink;
			this.op = op;
		}

		public Exp op() {
			return op;
		}

		public String sourceLink() {
			return sourceLink;
		}
	}
	public class Negative extends UnaryExp {
		public Negative(Exp op) {
			this(op, null);
		}

		public Negative(Exp op, String sourceLink) {
			super(op, sourceLink);
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

	public Dec decvar(DeclaredType t, String v) {
		return new DecVar(t, v);
	}
	public Dec decvar(DeclaredType t, String v, String sourceLink) {
		return new DecVar(t, v, sourceLink);
	}
	public Dec dectype(DeclaredType t, String v) {
		return new DecType(t,v);
	}
	public Dec dectype(DeclaredType t, String v, String sourceLink) {
		return new DecType(t,v,sourceLink);
	}

	public Inst iasig(Mem m, Exp e) {
		return new IAsig(m, e);
	}
	public Inst iasig(Mem m, Exp e, String sourceLink) {
		return new IAsig(m, e, sourceLink);
	}
	public Inst iblock(Inst[] is) {
		return new IBlock(is);
	}
	public Inst iread(String v) {
		return new IRead(v);
	}
	public Inst iwrite(String v) {
		return new IWrite(v);
	}
	public Inst iwhile(Exp cond, Inst body) {
		return new IWhile(cond, body);
	}
    public Inst iwhile(Exp cond, Inst body, String sourceLink) {
        return new IWhile(cond, body, sourceLink);
    }
	public Inst iifthen(Exp cond, Inst body) {
		return new IIfThen(cond, body);
	}
	public Inst iifthenelse(Exp cond, Inst thenBl, Inst elseBl) {
		return new IIfThenElse(cond, thenBl, elseBl);
	}
	public Inst idowhile(Inst body, Exp cond) {
		return new IDoWhile(cond, body);
	}
	public Inst iswitch(Exp exp, Inst defaul, ICase... cases) {
		return new ISwitch(exp, defaul, cases);
	}
	public Inst iswitch(Exp exp, ICase... cases) {
		return new ISwitch(exp, cases);
	}
	public ICase icase(Exp exp, Inst body) {
		return new ICase(exp, body);
	}
	public Inst inew(Mem mem) {return new INew(mem);}
	public Inst inew(Mem mem, String sourceLink) {return new INew(mem,sourceLink);}
	public Inst ifree(Mem mem) {return new IFree(mem);}
	public Inst ifree(Mem mem, String sourceLink) {return new IFree(mem,sourceLink);}

	public Mem var(String id) {return new Var(id);}
	public Mem var(String id, String sourceLink) {return new Var(id, sourceLink);}
	public Mem dref(Mem m) {return new DecRef(m);}
	public Mem dref(Mem m, String sourceLink) {return new DecRef(m,sourceLink);}
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
	public Exp add(Exp exp1, Exp exp2, String sourceLink) {
		return new Addition(exp1, exp2, sourceLink);
	}
	public Exp subtract(Exp exp1, Exp exp2, String sourceLink) {
		return new Subtraction(exp1, exp2, sourceLink);
	}
	public Exp multiply(Exp exp1, Exp exp2, String sourceLink) {
		return new Multiplication(exp1, exp2, sourceLink);
	}
	public Exp divide(Exp exp1, Exp exp2, String sourceLink) {
		return new Division(exp1, exp2, sourceLink);
	}
	public Exp modulus(Exp exp1, Exp exp2, String sourceLink) {
		return new Modulus(exp1, exp2, sourceLink);
	}
	public Exp and(Exp exp1, Exp exp2, String sourceLink) {
		return new And(exp1, exp2, sourceLink);
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
	public Exp strElem(Exp string, Exp index, String sourceLink) {
		return new StrElem(string, index);
	}
	public Exp negative(Exp exp1, String sourceLink) {
		return new Negative(exp1, sourceLink);
	}
	public Exp intcast(Exp exp, String sourceLink) {
		return new IntCast(exp, sourceLink);
	}
	public Exp realcast(Exp exp, String sourceLink) {
		return new RealCast(exp, sourceLink);
	}
	public Exp boolcast(Exp exp, String sourceLink) {
		return new BoolCast(exp, sourceLink);
	}
	public Exp charcast(Exp exp, String sourceLink) {
		return new UniCharCast(exp, sourceLink);
	}
	public Exp strcast(Exp exp, String sourceLink) {
		return new UniStrCast(exp, sourceLink);
	}
	public DeclaredType tInt() {
		return TINT;
	}
	public DeclaredType tBool() {
		return TBOOL;
	}
	public DeclaredType tReal() {
		return TREAL;
	}
	public DeclaredType tUniChar() {
		return TUNICHAR;
	}
	public DeclaredType tUniString() {
		return TUNISTRING;
	}
	public Type tOk() {
		return TOK;
	}
	public Type tError() {
		return TERROR;
	}
	public DeclaredType tipoPointer(DeclaredType tbase) {return new TPointer(tbase);}

	public DeclaredType tref(String typeId) {return new TRef(typeId);}
	public DeclaredType tref(String typeId, String sourceLink) {return new TRef(typeId,sourceLink);}
	public DeclaredType tarray(DeclaredType tbase, int size) {
		return new TArray(tbase, size);
	}
	public abstract Prog root();

	public Inst icall(String name, Exp[] params, String sourceLink) {
		throw new RuntimeException("Function calls not implemented");
	}
	public Dec decProc(String id, FParam[] fparams, Inst i, String enlaceFuente) {
		throw new RuntimeException("Procedure declaration not implemented");
	}
	public FParam fparam(DeclaredType tipo,boolean porReferencia, String id,String enlaceFuente) {
		throw new RuntimeException("Procedure parameters not implemented");
	}
	public Inst iblock(Dec[] decs, Inst[] is) {
		if (decs.length != 0) {
			throw new RuntimeException("Blocks with declarations not implemented");
		}
		return iblock(is);
	}
}
