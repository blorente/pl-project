package procesamientos.generacioncodigo;

import procesamientos.Processing;
import programa.Program.Addition;
import programa.Program.And;
import programa.Program.BoolCast;
import programa.Program.BoolCt;
import programa.Program.Division;
import programa.Program.Equals;
import programa.Program.Greater;
import programa.Program.GreaterEq;
import programa.Program.IAsig;
import programa.Program.IBlock;
import programa.Program.IRead;
import programa.Program.IWhile;
import programa.Program.IWrite;
import programa.Program.Inst;
import programa.Program.IntCast;
import programa.Program.IntCt;
import programa.Program.Less;
import programa.Program.LessEq;
import programa.Program.Modulus;
import programa.Program.Multiplication;
import programa.Program.Negative;
import programa.Program.Not;
import programa.Program.NotEquals;
import programa.Program.Or;
import programa.Program.Prog;
import programa.Program.RealCast;
import programa.Program.RealCt;
import programa.Program.StrElem;
import programa.Program.Subtraction;
import programa.Program.UniCharCast;
import programa.Program.UniCharCt;
import programa.Program.UniStrCast;
import programa.Program.UniStringCt;
import programa.Program.Var;

public class Tagging extends Processing {
	private int etq;

	public Tagging() {
		etq = 0;
	}
	
	public void process(Prog p) {
		p.inst().processWith(this);
	}

	public void process(Var exp) {
		exp.putDirFirst(etq);
		// apilaDir(...dir variable...)
		exp.putDirNext(++etq);
	}

	public void process(IntCt exp) {
		exp.putDirFirst(etq);
		exp.putDirNext(++etq);
	}

	public void process(BoolCt exp) {
		exp.putDirFirst(etq);
		exp.putDirNext(++etq);
	}
	
	public void process(RealCt exp) {
		exp.putDirFirst(etq);
		exp.putDirNext(++etq);
	}
	
	public void process(UniCharCt exp) {
		exp.putDirFirst(etq);
		exp.putDirNext(++etq);
	}
	
	public void process(UniStringCt exp) {
		exp.putDirFirst(etq);
		exp.putDirNext(++etq);
	}

	public void process(Addition exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(Subtraction exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(Multiplication exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(Division exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Modulus exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}	

	public void process(And exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Or exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Not exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Equals exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(NotEquals exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Greater exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(GreaterEq exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Less exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(LessEq exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(StrElem exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		exp.putDirNext(++etq);
	}
	
	public void process(Negative exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(IntCast exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(RealCast exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(BoolCast exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}
	public void process(UniCharCast exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}

	public void process(UniStrCast exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		exp.putDirNext(++etq);
	}

	public void process(IAsig i) {
		i.putDirFirst(etq);
		i.exp().processWith(this);
		// desapilaDir
		i.putDirNext(++etq);
	}

	public void process(IBlock b) {
		b.putDirFirst(etq);
		for (Inst i : b.is())
			i.processWith(this);
		b.putDirNext(etq);
	}

	public void process(IWhile i) {
		i.putDirFirst(etq);
		i.getCond().processWith(this);
		// ir_f(...)
		etq++;
		i.getBody().processWith(this);
		// ir_a(...)
		etq++;
		i.putDirNext(etq);
	}

	public void process(IRead i){
		i.putDirFirst(etq);
		i.putDirNext(++etq);
	}
	
	public void process(IWrite i){
		i.putDirFirst(etq);
		i.putDirNext(++etq);
	}
}
