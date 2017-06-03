package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Processing;
import program.Program;
import program.Program.Type;
import program.Program.UniCharCast;
import program.Program.IntCt;
import program.Program.Less;
import program.Program.LessEq;
import program.Program.Modulus;
import program.Program.BoolCt;
import program.Program.Addition;
import program.Program.Subtraction;
import program.Program.Multiplication;
import program.Program.Negative;
import program.Program.Not;
import program.Program.NotEquals;
import program.Program.Or;
import program.Program.Division;
import program.Program.Equals;
import program.Program.Greater;
import program.Program.GreaterEq;
import program.Program.And;
import program.Program.BoolCast;
import program.Program.Prog;
import program.Program.RealCast;
import program.Program.IBlock;
import program.Program.IRead;
import program.Program.IWrite;
import program.Program.IntCast;
import program.Program.IAsig;
import program.Program.RealCt;
import program.Program.StrElem;
import program.Program.UniCharCt;
import program.Program.UniStrCast;
import program.Program.UniStringCt;
import program.Program.Var;
import program.Program.IWhile;
import program.Program.IDoWhile;
import program.Program.IIfThen;
import program.Program.IIfThenElse;
import program.Program.ISwitch;
import program.Program.ICase;
import program.Program.DecRef;
import program.Program.DeclaredType;
import program.Program.INew;
import program.Program.TPointer;
import program.Program.IFree;

public class CodeGeneration extends Processing {
	private MaquinaP maquina;
	private Program program;

	public CodeGeneration(Program program, MaquinaP maquina) {
		this.program = program;
		this.maquina = maquina;
	}
	public void process(Var exp) {
		maquina.addInstruction(maquina.pushInt(exp.declaration().addr()));
	}
	public void process(DecRef exp) {
		exp.mem().processWith(this);
		maquina.addInstruction(maquina.pushInd());
	}
	public void process(IntCt exp) {
		maquina.addInstruction(maquina.pushInt(exp.intVal()));
	}
	public void process(RealCt exp) {
		maquina.addInstruction(maquina.pushReal(exp.realVal()));
	}
	public void process(UniCharCt exp) {
		maquina.addInstruction(maquina.pushUniChar(exp.charVal()));
	}
	public void process(UniStringCt exp) {
		maquina.addInstruction(maquina.pushUniString(exp.stringVal()));
	}
	public void process(BoolCt exp) {
		maquina.addInstruction(maquina.pushBool(exp.boolVal()));
	}
	public void process(Addition exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			maquina.addInstruction(maquina.addInt());
		}
		if (exp.type().equals(program.tReal())) {
			maquina.addInstruction(maquina.addReal());
		}
		if (exp.type().equals(program.tUniString())) {
			maquina.addInstruction(maquina.concat());
		}
	}
	public void process(Subtraction exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			maquina.addInstruction(maquina.subInt());
		}
		if (exp.type().equals(program.tReal())) {
			maquina.addInstruction(maquina.subReal());
		}
	}
	public void process(Multiplication exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			maquina.addInstruction(maquina.mulInt());
		}
		if (exp.type().equals(program.tReal())) {
			maquina.addInstruction(maquina.mulReal());
		}
	}
	public void process(Division exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			maquina.addInstruction(maquina.divInt());
		}
		if (exp.type().equals(program.tReal())) {
			maquina.addInstruction(maquina.divReal());
		}
	}
	public void process(Modulus exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		maquina.addInstruction(maquina.mod());
	}
	public void process(And exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		maquina.addInstruction(maquina.and());
	}
	public void process(Or exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		maquina.addInstruction(maquina.or());
	}
	public void process(Not exp) {
		exp.op().processWith(this);
		maquina.addInstruction(maquina.not());
	}
	public void process(Equals exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.equalsReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.equalsInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.equalsBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.equalsChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.equalsString());			
		}
	}
	public void process(NotEquals exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.notEqualsReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.notEqualsInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.notEqualsBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.notEqualsChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.notEqualsString());			
		}
	}
	public void process(Greater exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.greaterReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.greaterInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.greaterBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.greaterChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.greaterString());			
		}
	}
	public void process(GreaterEq exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.greaterEqReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.greaterEqInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.greaterEqBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.greaterEqChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.greaterEqString());			
		}
	}
	public void process(Less exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.lessReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.lessInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.lessBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.lessChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.lessString());			
		}
	}
	public void process(LessEq exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())){
			maquina.addInstruction(maquina.lessEqReal());
		} else if (t1.equals(program.tInt())) {
			maquina.addInstruction(maquina.lessEqInt());
		} else if (t1.equals(program.tBool())) {
			maquina.addInstruction(maquina.lessEqBool());			
		} else if (t1.equals(program.tUniChar())) {
			maquina.addInstruction(maquina.lessEqChar());			
		} else if (t1.equals(program.tUniString())) {
			maquina.addInstruction(maquina.lessEqString());			
		}
	}
	public void process(StrElem exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		maquina.addInstruction(maquina.strElem());
	}
	public void process(Negative exp) {
		exp.op().processWith(this);
		if (exp.type().equals(program.tInt())) {
			maquina.addInstruction(maquina.negInt());
		} else if (exp.type().equals(program.tReal())) {
			maquina.addInstruction(maquina.negReal());
		}
	}
	public void process(IntCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tReal())) {
			maquina.addInstruction(maquina.realToInt());
		} else if (exp.op().type().equals(program.tBool())) {
			maquina.addInstruction(maquina.boolToInt());
		} else if (exp.op().type().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToInt());
		}
	}
	public void process(RealCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		} else if (exp.op().type().equals(program.tBool())) {
			maquina.addInstruction(maquina.boolToReal());
		} else if (exp.op().type().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToReal());
		}
	}
	public void process(BoolCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToBool());
		}
	}
	public void process(UniCharCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToChar());
		}
	}
	public void process(UniStrCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToString());
		}
	}
	public void process(Prog p) {
		p.inst().processWith(this);
	}
	public void process(IAsig i) {
		i.mem().processWith(this);
		i.exp().processWith(this);
		if (i.exp().isMem()) {
			maquina.addInstruction(maquina.move(((DeclaredType)i.exp().type()).size()));
		} else {
			maquina.addInstruction(maquina.popInd());
		}
	}
	public void process(IBlock b) {
		for (Program.Inst i : b.is())
			i.processWith(this);
	}
	public void process(IRead r) {
		if (r.declaracion().decType().equals(program.tInt())) {
			maquina.addInstruction(maquina.readInt());
		} else if (r.declaracion().decType().equals(program.tReal())) {
			maquina.addInstruction(maquina.readReal());
		}  else if (r.declaracion().decType().equals(program.tBool())) {
			maquina.addInstruction(maquina.readBool());
		}  else if (r.declaracion().decType().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.readChar());
		}  else if (r.declaracion().decType().equals(program.tUniString())) {
			maquina.addInstruction(maquina.readString());
		} 
		maquina.addInstruction(maquina.popInd());
	}
	public void process(IWrite w) {
		maquina.addInstruction(maquina.pushInd());
		if (w.declaration().decType().equals(program.tInt())) {
			maquina.addInstruction(maquina.writeInt());
		} else if (w.declaration().decType().equals(program.tReal())) {
			maquina.addInstruction(maquina.writeReal());
		}  else if (w.declaration().decType().equals(program.tBool())) {
			maquina.addInstruction(maquina.writeBool());
		}  else if (w.declaration().decType().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.writeChar());
		}  else if (w.declaration().decType().equals(program.tUniString())) {
			maquina.addInstruction(maquina.writeString());
		}
	}
	public void process(IWhile wh) {
		wh.getCond().processWith(this);
		maquina.addInstruction(maquina.branchIfFalse(wh.dirNext()));
		wh.getBody().processWith(this);
		maquina.addInstruction(maquina.branch(wh.dirFirst()));
	}
	public void process(IDoWhile i) {
		i.getBody().processWith(this);
		i.getCond().processWith(this);
		maquina.addInstruction(maquina.branchIfFalse(i.dirNext()));
		maquina.addInstruction(maquina.branch(i.dirFirst()));
	}
	public void process(IIfThen i) {
		i.getCond().processWith(this);
		maquina.addInstruction(maquina.branchIfFalse(i.dirNext()));
		i.getThen().processWith(this);
	}
	public void process(IIfThenElse i) {
		i.getCond().processWith(this);
		maquina.addInstruction(maquina.branchIfFalse(i.getElse().dirFirst()));
		i.getThen().processWith(this);
		maquina.addInstruction(maquina.branch(i.dirNext()));
		i.getElse().processWith(this);
	}
	public void process(ISwitch i) {
		i.getCond().processWith(this);
		for (ICase c : i.getCases()) {
			maquina.addInstruction(maquina.dup());
			c.processWith(this);
			maquina.addInstruction(maquina.branch(i.dirNext() - 1));
		}
		if (i.hasDefault()) {
			i.getDefault().processWith(this);
		}
		maquina.addInstruction(maquina.pop());
	}
	public void process(ICase i) {
		i.getExp().processWith(this);
		if (i.getExp().type().equals(program.tReal())) {
			maquina.addInstruction(maquina.equalsReal());
		} else if (i.getExp().type().equals(program.tInt())) {
			maquina.addInstruction(maquina.equalsInt());
		} else if (i.getExp().type().equals(program.tBool())) {
			maquina.addInstruction(maquina.equalsBool());
		} else if (i.getExp().type().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.equalsChar());
		}
		maquina.addInstruction(maquina.branchIfFalse(i.dirNext() + 1));
		i.getBody().processWith(this);
	}
	public void process(INew i) {
		i.mem().processWith(this);
		maquina.addInstruction(maquina.alloc(((TPointer)i.mem().type()).tbase().size()));
		maquina.addInstruction(maquina.popInd());
	}
	public void process(IFree i) {
		i.mem().processWith(this);
		maquina.addInstruction(maquina.pushInd());
		maquina.addInstruction(maquina.dealloc(((TPointer)i.mem().type()).tbase().size()));
	}

}
