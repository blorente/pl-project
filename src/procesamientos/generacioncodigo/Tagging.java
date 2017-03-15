package procesamientos.generacioncodigo;

import procesamientos.Processing;
import programa.Program;
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
import programa.Program.Type;
import programa.Program.BinaryExp;
import programa.Program.UnaryExp;

public class Tagging extends Processing {
	private int etq;
	private Program program;

	public Tagging(Program program) {
		this.program = program;
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
		tagBinaryNumericExpression(exp);
	}
	public void process(Subtraction exp) {
		tagBinaryNumericExpression(exp);
	}

	public void process(Multiplication exp) {
		tagBinaryNumericExpression(exp);
	}
	public void process(Division exp) {
		tagBinaryNumericExpression(exp);
	}
	
	public void process(Modulus exp) {
		tagBinaryNumericExpression(exp);
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
		tagBinaryNumericExpression(exp);
	}
	
	public void process(NotEquals exp) {
		tagBinaryNumericExpression(exp);
	}
	
	public void process(Greater exp) {
		tagBinaryNumericExpression(exp);
	}
	
	public void process(GreaterEq exp) {
		tagBinaryNumericExpression(exp);
	}
	
	public void process(Less exp) {
		tagBinaryNumericExpression(exp);
	}
	public void process(LessEq exp) {
		tagBinaryNumericExpression(exp);
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
		tagCastExpression(exp);
	}

	public void process(RealCast exp) {
		tagCastExpression(exp);
	}
	public void process(BoolCast exp) {
		tagCastExpression(exp);
	}
	public void process(UniCharCast exp) {
		tagCastExpression(exp);
	}

	public void process(UniStrCast exp) {
		tagCastExpression(exp);
	}

	public void process(IAsig i) {
		i.putDirFirst(etq);
		i.exp().processWith(this);
		// desapilaDir
		i.putDirNext(++etq);
	}

	public void process(IBlock b) {
		b.putDirFirst(etq);
		for (Inst i : b.is()) {
			i.processWith(this);
		}
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
		etq++;
		i.putDirNext(++etq);
	}
	
	public void process(IWrite i){
		i.putDirFirst(etq);
		etq++;
		i.putDirNext(++etq);
	}

	private void tagBinaryNumericExpression(BinaryExp exp) {
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			etq++;
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			etq++;
		}
		exp.putDirNext(++etq);
	}

	private void tagCastExpression(UnaryExp exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		if (!exp.op().tipo().equals(exp.tipo()))
			exp.putDirNext(++etq);
	}
}
