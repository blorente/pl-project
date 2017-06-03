package procesamientos.impresion;

import procesamientos.Processing;
import program.Program.Division;
import program.Program.Equals;
import program.Program.IWhile;
import program.Program.IDoWhile;
import program.Program.IIfThen;
import program.Program.IIfThenElse;
import program.Program.IntCt;
import program.Program.Less;
import program.Program.LessEq;
import program.Program.BoolCt;
import program.Program.Multiplication;
import program.Program.Negative;
import program.Program.Not;
import program.Program.NotEquals;
import program.Program.Or;
import program.Program.Modulus;
import program.Program.RealCt;
import program.Program.StrElem;
import program.Program.Subtraction;
import program.Program.UniCharCast;
import program.Program.UniCharCt;
import program.Program.UniStrCast;
import program.Program.UniStringCt;
import program.Program.Addition;
import program.Program.And;
import program.Program.BoolCast;
import program.Program.Dec;
import program.Program.DecVar;
import program.Program.Exp;
import program.Program.Greater;
import program.Program.GreaterEq;
import program.Program.IAsig;
import program.Program.IBlock;
import program.Program.IRead;
import program.Program.IWrite;
import program.Program.Inst;
import program.Program.IntCast;
import program.Program.Prog;
import program.Program.RealCast;
import program.Program.Var;
import program.Program.ISwitch;
import program.Program.ICase;
import program.Program.DecRef;
import program.Program.IFree;
import program.Program.INew;
import program.Program.DecType;
import program.Program.TPointer;
import program.Program.TRef;

public class Impresion extends Processing {
	private boolean attributes;
	private int indentation;

	public Impresion(boolean atributos) {
		this.attributes = atributos;
		indentation = 0;
	}

	public Impresion() {
		this(false);
	}

	private void printAttributes(Exp exp) {
		if (attributes) {
			System.out.print("@{t:" + exp.type() + ",");
			System.out.print("df:" + exp.dirFirst() + ",dn:" + exp.dirNext() + "}");
		}
	}

	private void printAttributes(Prog prog) {
		if (attributes) {
			System.out.print("@{t:" + prog.tipo() + "}");
		}
	}

	private void printAttributes(Inst i) {
		if (attributes) {
			System.out.print("@{t:" + i.tipo() + ",");
			System.out.print("df:" + i.dirFirst() + ", dn:" + i.dirNext() + "}");
		}
	}

	private void indent() {
		for (int i = 0; i < indentation; i++)
			System.out.print(" ");
	}

	public void process(IntCt exp) {
		System.out.print(exp.intVal());
		printAttributes(exp);
	}
	public void process(BoolCt exp) {
		System.out.print(exp.boolVal());
		printAttributes(exp);
	}
	public void process(RealCt exp) {
		System.out.print(exp.realVal());
		printAttributes(exp);
	}
	public void process(UniCharCt exp) {
		System.out.print(exp.charVal());
		printAttributes(exp);
	}
	public void process(UniStringCt exp) {
		System.out.print(exp.stringVal());
		printAttributes(exp);
	}
	public void process(Var exp) {
		System.out.print(exp.var());
		printAttributes(exp);
	}
	public void process(Addition exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print('+');
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Subtraction exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print('-');
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Multiplication exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print('*');
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Division exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print('/');
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Modulus exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print('%');
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(And exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("&&");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Or exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("||");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Not exp) {
		System.out.print('!');
		exp.op().processWith(this);
		printAttributes(exp);
	}
	public void process(Equals exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("==");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(NotEquals exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("!=");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Greater exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print(">");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(GreaterEq exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print(">=");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(Less exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("<");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(LessEq exp) {
		System.out.print('(');
		exp.opnd1().processWith(this);
		System.out.print("<=");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(')');
	}
	public void process(StrElem exp) {
		exp.opnd1().processWith(this);
		System.out.print("[");
		printAttributes(exp);
		exp.opnd2().processWith(this);
		System.out.print(']');
	}
	public void process(Negative exp) {
		System.out.print('(');
		System.out.print("-");
		printAttributes(exp);
		exp.op().processWith(this);
		System.out.print(')');
	}
	public void process(IntCast exp) {
		System.out.print('(');
		System.out.print("int");
		System.out.print(')');
		printAttributes(exp);
		exp.op().processWith(this);
	}
	public void process(RealCast exp) {
		System.out.print('(');
		System.out.print("real");
		System.out.print(')');
		printAttributes(exp);
		exp.op().processWith(this);
	}
	public void process(BoolCast exp) {
		System.out.print('(');
		System.out.print("bool");
		System.out.print(')');
		printAttributes(exp);
		exp.op().processWith(this);
	}
	public void process(UniCharCast exp) {
		System.out.print('(');
		System.out.print("char");
		System.out.print(')');
		printAttributes(exp);
		exp.op().processWith(this);
	}
	public void process(UniStrCast exp) {
		System.out.print('(');
		System.out.print("string");
		System.out.print(')');
		printAttributes(exp);
		exp.op().processWith(this);
	}
	public void process(Prog p) {
		for (Dec d : p.decs())
			d.processWith(this);
		p.inst().processWith(this);
		printAttributes(p);
		System.out.println();
	}
	public void process(DecVar t) {
		t.decType().accept(this);
		System.out.print(" "+t.var());
		System.out.println();
	}
	public void process(DecRef mem) {
		System.out.print("(*");
		mem.mem().processWith(this);
		System.out.print(")");
		printAttributes(mem);
	}
	public void process(DecType t) {
		System.out.print("typedef ");
		t.decType().accept(this);
		System.out.print(" "+t.typeId());
		System.out.println();
	}
	public void process(TPointer t) {
		System.out.print("(");
		t.tbase().accept(this);
		System.out.print("*)");
	}
	public void process(TRef t) {
		System.out.print(t.typeId());
	}
	public void process(IAsig i) {
		indent();
		i.mem().processWith(this);
		System.out.print("=");
		i.exp().processWith(this);
		printAttributes(i);
		System.out.println();
	}
	public void process(IBlock b) {
		indent();
		System.out.println("{");
		indentation += 3;
		for (Inst i : b.is())
			i.processWith(this);
		indentation -= 3;
		indent();
		System.out.print("}");
		printAttributes(b);
		System.out.println();
	}
	public void process(IRead r) {
		indent();
		System.out.println("read into " + r.var());
		printAttributes(r);
	}
	public void process(IWrite w) {
		indent();
		System.out.println("write from " + w.var());
		printAttributes(w);
	}
	public void process(IWhile wh) {
		indent();
		System.out.print("while(");
		wh.getCond().processWith(this);
		System.out.print(")");
		wh.getBody().processWith(this);
		printAttributes(wh);
	}
	public void process(IDoWhile i) {
		indent();
		System.out.print("do");
		i.getBody().processWith(this);
		indent();System.out.print("while (");
		i.getCond().processWith(this);
		System.out.print(")\n");
		printAttributes(i);
	}
	public void process(IIfThen i) {
		indent();
		System.out.print("if (");
		i.getCond().processWith(this);
		System.out.print(") then ");
		i.getThen().processWith(this);
		printAttributes(i);
	}
	public void process(IIfThenElse i) {
		indent();
		System.out.print("if (");
		i.getCond().processWith(this);
		System.out.print(") then");
		i.getThen().processWith(this);
		indent(); System.out.print("else");
		i.getElse().processWith(this);
		printAttributes(i);
	}
	public void process(ISwitch i) {
		indent();
		System.out.print("switch (");
		i.getCond().processWith(this);
		System.out.print(") {\n");
		for (Inst c : i.getCases())
			c.processWith(this);
		if (i.hasDefault()) {
			indent();
			System.out.print("default:");
			i.getDefault().processWith(this);
		}
		indent();
		System.out.print("}\n");
		printAttributes(i);
	}
	public void process(ICase i) {
		indent();
		System.out.print("case ");
		i.getExp().processWith(this);
		System.out.print(": ");
		i.getBody().processWith(this);
		printAttributes(i);
	}
	public void process(INew i) {
		System.out.print("new ");
		i.mem().processWith(this);
	}
	public void process(IFree i) {
		System.out.print("free ");
		i.mem().processWith(this);
	}
}
