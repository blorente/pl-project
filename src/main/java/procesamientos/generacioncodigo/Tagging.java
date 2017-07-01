package procesamientos.generacioncodigo;

import procesamientos.Processing;
import program.Program;
import program.Program.*;

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
	public void process(DRefPtr exp) {
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
		i.mem().processWith(this);
		i.exp().processWith(this);
		// move or popInd
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
	public void process(INew i) {
		i.putDirFirst(etq);
		i.mem().processWith(this);
		// alloc desapilaind
		etq +=2;
		i.putDirNext(etq);
	}
	public void process(IFree i) {
		i.putDirFirst(etq);
		i.mem().processWith(this);
		// apilaind dealloc
		etq +=2;
		i.putDirNext(etq);
	}
	public void process(ArrayIndex exp) {
		exp.putDirFirst(etq);
		exp.var().processWith(this);
		exp.index().processWith(this);

		if (exp.index().isMem()) {
			etq++;
		}
		etq++; // inRange
		etq++; // pushInt
		etq++; // Mul
		etq++; // Sum
		exp.putDirNext(etq);
	}
	public void process(StructField exp) {
		exp.putDirFirst(etq);
		exp.var().processWith(this);
		etq++;// pushInt(struct.addr)
		etq++;// pushInt(field.offset)
		etq++;// Sum
		exp.putDirNext(etq);
	}
}
