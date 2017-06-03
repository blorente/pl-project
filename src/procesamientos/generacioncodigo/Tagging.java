package procesamientos.generacioncodigo;

import procesamientos.Processing;
import program.Program;
import program.Program.Addition;
import program.Program.And;
import program.Program.BoolCast;
import program.Program.BoolCt;
import program.Program.Division;
import program.Program.Equals;
import program.Program.Greater;
import program.Program.GreaterEq;
import program.Program.IAsig;
import program.Program.IBlock;
import program.Program.IRead;
import program.Program.IWhile;
import program.Program.IDoWhile;
import program.Program.IIfThen;
import program.Program.IIfThenElse;
import program.Program.IWrite;
import program.Program.Inst;
import program.Program.IntCast;
import program.Program.IntCt;
import program.Program.Less;
import program.Program.LessEq;
import program.Program.Modulus;
import program.Program.Multiplication;
import program.Program.Negative;
import program.Program.Not;
import program.Program.NotEquals;
import program.Program.Or;
import program.Program.Prog;
import program.Program.RealCast;
import program.Program.RealCt;
import program.Program.StrElem;
import program.Program.Subtraction;
import program.Program.UniCharCast;
import program.Program.UniCharCt;
import program.Program.UniStrCast;
import program.Program.UniStringCt;
import program.Program.Var;
import program.Program.Type;
import program.Program.BinaryExp;
import program.Program.UnaryExp;
import program.Program.ISwitch;
import program.Program.ICase;
import program.Program.DecRef;
import program.Program.INew;
import program.Program.IFree;

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
	public void procesa(DecRef exp) {
		exp.putDirFirst(etq);
		exp.mem().processWith(this);
		// apilaind
		etq++;
		exp.putDirNext(etq);
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

	public void process(IDoWhile i) {
		i.putDirFirst(etq);
		i.getBody().processWith(this);
		i.getCond().processWith(this);
		// ir_f(...)
		etq++;
		// ir_a(...)
		etq++;
		i.putDirNext(etq);
	}

	public void process(IIfThen i) {
		i.putDirFirst(etq);
		i.getCond().processWith(this);
		// ir_f(...)
		etq++;
		i.getThen().processWith(this);
		i.putDirNext(etq);
	}

	public void process(IIfThenElse i) {
		i.putDirFirst(etq);
		i.getCond().processWith(this);
		// ir_f(...)
		etq++;
		i.getThen().processWith(this);
		// ir_a(...)
		etq++;
		i.getElse().processWith(this);
		i.putDirNext(etq);
	}

	public void process(ISwitch i) {
		i.putDirFirst(etq);
		i.getCond().processWith(this);
		for (ICase c : i.getCases()) {
			// dup
			etq++;
			c.processWith(this);
			// branch
			etq++;
		}
		// pop
		etq++;
		if (i.hasDefault()) {
			i.getDefault().processWith(this);
		}
		i.putDirNext(etq);
	}

	public void process(ICase i) {
		i.putDirFirst(etq);
		i.getExp().processWith(this);
		// equals
		etq++;
		// branch if false
		etq++;
		i.getBody().processWith(this);
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
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
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
		if (!exp.op().type().equals(exp.type()))
			exp.putDirNext(++etq);
	}

	public void procesa(INew i) {
		i.putDirFirst(etq);
		i.mem().processWith(this);
		// alloc desapilaind
		etq +=2;
		i.putDirNext(etq);
	}
	public void procesa(IFree i) {
		i.putDirFirst(etq);
		i.mem().processWith(this);
		// apilaind dealloc
		etq +=2;
		i.putDirNext(etq);
	}
}
