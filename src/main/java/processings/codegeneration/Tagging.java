package processings.codegeneration;

import processings.Processing;
import program.Program;
import program.Program.*;

import java.util.Stack;

public class Tagging extends Processing {
	private int etq;
	private Program program;
	private Stack<DecProc> remainingProcs;

	public Tagging(Program program) {
		this.program = program;
		etq = 0;
		remainingProcs = new Stack<DecProc>();
	}
	
	public void process(Prog p) {
		for(Dec d: p.decs()) {
			if (d instanceof DecProc) {
				remainingProcs.push((DecProc)d);
			}
		}
		p.inst().processWith(this);
		etq++; //stop
		while(! remainingProcs.isEmpty())
			remainingProcs.pop().processWith(this);
	}

	public void process(Var exp) {
		DecVar dvar =  exp.declaration();
		if (dvar.level() == 0)
			etq++; // apilaInt
		else {
			etq += 3; // apilad, apilaInt, suma
			if (dvar.isByReference())
				etq++; // apilaInd
		}
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
	public void process(NullCt exp) {
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
		tagBinaryExpressionSimple(exp);
	}
	public void process(Or exp) {
		tagBinaryExpressionSimple(exp);
	}
	public void process(Not exp) {
		tagUnaryExpressionSimple(exp);
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
		tagBinaryExpressionSimple(exp);
	}
	public void process(Negative exp) {
		tagUnaryExpressionSimple(exp);
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

	private void tagBinaryExpressionSimple(BinaryExp exp) {
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		if (exp.opnd1().isMem()) /* pushInd */ etq++;
		exp.opnd2().processWith(this);
		if (exp.opnd2().isMem()) /* pushInd */ etq++;
		exp.putDirNext(++etq);
	}
	private void tagUnaryExpressionSimple(UnaryExp exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		if (exp.op().isMem()) /* pushInd */ etq++;
		exp.putDirNext(++etq);
	}
	private void tagBinaryNumericExpression(BinaryExp exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.putDirFirst(etq);
		exp.opnd1().processWith(this);
		if (exp.opnd1().isMem()) /* pushInd */ etq++;
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			etq++;
		}
		exp.opnd2().processWith(this);
		if (exp.opnd2().isMem()) /* pushInd */ etq++;
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			etq++;
		}
		exp.putDirNext(++etq);
	}
	private void tagCastExpression(UnaryExp exp) {
		exp.putDirFirst(etq);
		exp.op().processWith(this);
		if (exp.op().isMem()) /* pushInd */ etq++;
		if (!exp.op().type().equals(exp.type()))
			exp.putDirNext(++etq);
	}

	public void process(IAsig i) {
		i.putAddrFirst(etq);
		i.mem().processWith(this);
		i.exp().processWith(this);
		if (i.exp().isMem()) /*pushInd*/ etq++;
		// move or popInd
		i.putAddrNext(++etq);
	}
	public void process(IBlock b) {
		b.putAddrFirst(etq);
		for(Dec dec: b.decs())
			if (dec instanceof DecProc)
				remainingProcs.push((DecProc)dec);
		for(Inst i: b.is())
			i.processWith(this);
		b.putAddrNext(etq);
	}
	public void process(IWhile i) {
		i.putAddrFirst(etq);
		i.getCond().processWith(this);
		if (i.getCond().isMem()) /*pushInd*/ etq++;
		// ir_f(...)
		etq++;
		i.getBody().processWith(this);
		// ir_a(...)
		etq++;
		i.putAddrNext(etq);
	}
	public void process(IDoWhile i) {
		i.putAddrFirst(etq);
		i.getBody().processWith(this);
		i.getCond().processWith(this);
		if (i.getCond().isMem()) /*pushInd*/ etq++;
		// ir_f(...)
		etq++;
		// ir_a(...)
		etq++;
		i.putAddrNext(etq);
	}
	public void process(IIfThen i) {
		i.putAddrFirst(etq);
		i.getCond().processWith(this);
		if (i.getCond().isMem()) /*pushInd*/ etq++;
		// ir_f(...)
		etq++;
		i.getThen().processWith(this);
		i.putAddrNext(etq);
	}
	public void process(IIfThenElse i) {
		i.putAddrFirst(etq);
		i.getCond().processWith(this);
		if (i.getCond().isMem()) /*pushInd*/ etq++;
		// ir_f(...)
		etq++;
		i.getThen().processWith(this);
		// ir_a(...)
		etq++;
		i.getElse().processWith(this);
		i.putAddrNext(etq);
	}
	public void process(ISwitch i) {
		i.putAddrFirst(etq);
		i.getCond().processWith(this);
		if (i.getCond().isMem()) /*pushInd*/ etq++;
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
		i.putAddrNext(etq);
	}
	public void process(ICase i) {
		i.putAddrFirst(etq);
		i.getExp().processWith(this);
		if (i.getExp().isMem()) /*pushInd*/ etq++;
		// equals
		etq++;
		// branch if false
		etq++;
		i.getBody().processWith(this);
		i.putAddrNext(etq);
	}
	public void process(IRead i){
		i.putAddrFirst(etq);
		etq++;
		i.putAddrNext(++etq);
	}
	public void process(IWrite i){
		i.putAddrFirst(etq);
		etq++;
		i.putAddrNext(++etq);
	}
	public void process(INew i) {
		i.putAddrFirst(etq);
		i.mem().processWith(this);
		// alloc desapilaind
		etq +=2;
		i.putAddrNext(etq);
	}
	public void process(IFree i) {
		i.putAddrFirst(etq);
		i.mem().processWith(this);
		// apilaind dealloc
		etq +=2;
		i.putAddrNext(etq);
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

	public void process(DecProc p) {
		p.body().processWith(this);
		etq += 2;
	}
	public void process(ICall c) {
		c.putAddrFirst(etq);
		DecProc p = c.declaration();
		etq++; //activa
		for(int i=0; i <  p.fparams().length; i++) {
			etq += 3; //dup, apilaInt, suma
			c.aparams()[i].processWith(this);
			etq++; //desapilaInd o mueve
		}
		etq += 2; // setD, irA
		c.putAddrNext(etq);
	}
}
