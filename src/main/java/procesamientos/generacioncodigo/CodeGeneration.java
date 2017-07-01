package procesamientos.generacioncodigo;

import maquinaP.PMachine;
import procesamientos.Processing;
import program.Program;
import program.Program.*;

import java.util.Map;

public class CodeGeneration extends Processing {
	private PMachine m;
	private Program program;

	public CodeGeneration(Program program, PMachine maquina) {
		this.program = program;
		this.m = maquina;
	}
	public void process(Var exp) {
		m.addInstruction(m.pushInt(exp.declaration().addr()));
	}
	public void process(DecRef exp) {
		exp.mem().processWith(this);
		m.addInstruction(m.pushInd());
	}
	public void process(IntCt exp) {
		m.addInstruction(m.pushInt(exp.intVal()));
	}
	public void process(RealCt exp) {
		m.addInstruction(m.pushReal(exp.realVal()));
	}
	public void process(UniCharCt exp) {
		m.addInstruction(m.pushUniChar(exp.charVal()));
	}
	public void process(UniStringCt exp) {
		m.addInstruction(m.pushUniString(exp.stringVal()));
	}
	public void process(BoolCt exp) {
		m.addInstruction(m.pushBool(exp.boolVal()));
	}
	public void process(Addition exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			m.addInstruction(m.addInt());
		}
		if (exp.type().equals(program.tReal())) {
			m.addInstruction(m.addReal());
		}
		if (exp.type().equals(program.tUniString())) {
			m.addInstruction(m.concat());
		}
	}
	public void process(Subtraction exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			m.addInstruction(m.subInt());
		}
		if (exp.type().equals(program.tReal())) {
			m.addInstruction(m.subReal());
		}
	}
	public void process(Multiplication exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			m.addInstruction(m.mulInt());
		}
		if (exp.type().equals(program.tReal())) {
			m.addInstruction(m.mulReal());
		}
	}
	public void process(Division exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}

		if (exp.type().equals(program.tInt())) {
			m.addInstruction(m.divInt());
		}
		if (exp.type().equals(program.tReal())) {
			m.addInstruction(m.divReal());
		}
	}
	public void process(Modulus exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		m.addInstruction(m.mod());
	}
	public void process(And exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		m.addInstruction(m.and());
	}
	public void process(Or exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
		m.addInstruction(m.or());
	}
	public void process(Not exp) {
		exp.op().processWith(this);
		m.addInstruction(m.not());
	}
	public void process(Equals exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			m.addInstruction(m.equalsReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.equalsInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.equalsBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.equalsChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.equalsString());
		}
	}
	public void process(NotEquals exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			m.addInstruction(m.notEqualsReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.notEqualsInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.notEqualsBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.notEqualsChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.notEqualsString());
		}
	}
	public void process(Greater exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			m.addInstruction(m.greaterReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.greaterInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.greaterBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.greaterChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.greaterString());
		}
	}
	public void process(GreaterEq exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			m.addInstruction(m.greaterEqReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.greaterEqInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.greaterEqBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.greaterEqChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.greaterEqString());
		}
	}
	public void process(Less exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())) {
			m.addInstruction(m.lessReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.lessInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.lessBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.lessChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.lessString());
		}
	}
	public void process(LessEq exp) {
		Type t1 = exp.opnd1().type();
		Type t2 = exp.opnd2().type();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			m.addInstruction(m.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		}
		
		if (t1.equals(program.tReal()) || t2.equals(program.tReal())){
			m.addInstruction(m.lessEqReal());
		} else if (t1.equals(program.tInt())) {
			m.addInstruction(m.lessEqInt());
		} else if (t1.equals(program.tBool())) {
			m.addInstruction(m.lessEqBool());
		} else if (t1.equals(program.tUniChar())) {
			m.addInstruction(m.lessEqChar());
		} else if (t1.equals(program.tUniString())) {
			m.addInstruction(m.lessEqString());
		}
	}
	public void process(StrElem exp) {
		exp.opnd1().processWith(this);
		if (exp.opnd1().isMem()) {
			m.addInstruction(m.pushInd());
		}
		exp.opnd2().processWith(this);
		if (exp.opnd2().isMem()) {
			m.addInstruction(m.pushInd());
		}
		m.addInstruction(m.strElem());
	}
	public void process(Negative exp) {
		exp.op().processWith(this);
		if (exp.type().equals(program.tInt())) {
			m.addInstruction(m.negInt());
		} else if (exp.type().equals(program.tReal())) {
			m.addInstruction(m.negReal());
		}
	}
	public void process(IntCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tReal())) {
			m.addInstruction(m.realToInt());
		} else if (exp.op().type().equals(program.tBool())) {
			m.addInstruction(m.boolToInt());
		} else if (exp.op().type().equals(program.tUniChar())) {
			m.addInstruction(m.charToInt());
		}
	}
	public void process(RealCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			m.addInstruction(m.intToReal());
		} else if (exp.op().type().equals(program.tBool())) {
			m.addInstruction(m.boolToReal());
		} else if (exp.op().type().equals(program.tUniChar())) {
			m.addInstruction(m.charToReal());
		}
	}
	public void process(BoolCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			m.addInstruction(m.intToBool());
		}
	}
	public void process(UniCharCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tInt())) {
			m.addInstruction(m.intToChar());
		}
	}
	public void process(UniStrCast exp) {
		exp.op().processWith(this);
		if (exp.op().type().equals(program.tUniChar())) {
			m.addInstruction(m.charToString());
		}
	}
	public void process(Prog p) {
		p.inst().processWith(this);
	}
	public void process(IAsig i) {
		i.mem().processWith(this);
		i.exp().processWith(this);
		if (i.exp().isMem()) {
			m.addInstruction(m.move(((DeclaredType)i.exp().type()).size()));
		} else {
			m.addInstruction(m.popInd());
		}
	}
	public void process(IBlock b) {
		for (Program.Inst i : b.is())
			i.processWith(this);
	}
	public void process(IRead r) {
		if (r.declaracion().decType().equals(program.tInt())) {
			m.addInstruction(m.readInt());
		} else if (r.declaracion().decType().equals(program.tReal())) {
			m.addInstruction(m.readReal());
		}  else if (r.declaracion().decType().equals(program.tBool())) {
			m.addInstruction(m.readBool());
		}  else if (r.declaracion().decType().equals(program.tUniChar())) {
			m.addInstruction(m.readChar());
		}  else if (r.declaracion().decType().equals(program.tUniString())) {
			m.addInstruction(m.readString());
		} 
		m.addInstruction(m.popInd());
	}
	public void process(IWrite w) {
		m.addInstruction(m.pushInd());
		if (w.declaration().decType().equals(program.tInt())) {
			m.addInstruction(m.writeInt());
		} else if (w.declaration().decType().equals(program.tReal())) {
			m.addInstruction(m.writeReal());
		}  else if (w.declaration().decType().equals(program.tBool())) {
			m.addInstruction(m.writeBool());
		}  else if (w.declaration().decType().equals(program.tUniChar())) {
			m.addInstruction(m.writeChar());
		}  else if (w.declaration().decType().equals(program.tUniString())) {
			m.addInstruction(m.writeString());
		}
	}
	public void process(IWhile wh) {
		wh.getCond().processWith(this);
		m.addInstruction(m.branchIfFalse(wh.dirNext()));
		wh.getBody().processWith(this);
		m.addInstruction(m.branch(wh.dirFirst()));
	}
	public void process(IDoWhile i) {
		i.getBody().processWith(this);
		i.getCond().processWith(this);
		m.addInstruction(m.branchIfFalse(i.dirNext()));
		m.addInstruction(m.branch(i.dirFirst()));
	}
	public void process(IIfThen i) {
		i.getCond().processWith(this);
		m.addInstruction(m.branchIfFalse(i.dirNext()));
		i.getThen().processWith(this);
	}
	public void process(IIfThenElse i) {
		i.getCond().processWith(this);
		m.addInstruction(m.branchIfFalse(i.getElse().dirFirst()));
		i.getThen().processWith(this);
		m.addInstruction(m.branch(i.dirNext()));
		i.getElse().processWith(this);
	}
	public void process(ISwitch i) {
		i.getCond().processWith(this);
		for (ICase c : i.getCases()) {
			m.addInstruction(m.dup());
			c.processWith(this);
			m.addInstruction(m.branch(i.dirNext() - 1));
		}
		if (i.hasDefault()) {
			i.getDefault().processWith(this);
		}
		m.addInstruction(m.pop());
	}
	public void process(ICase i) {
		i.getExp().processWith(this);
		if (i.getExp().type().equals(program.tReal())) {
			m.addInstruction(m.equalsReal());
		} else if (i.getExp().type().equals(program.tInt())) {
			m.addInstruction(m.equalsInt());
		} else if (i.getExp().type().equals(program.tBool())) {
			m.addInstruction(m.equalsBool());
		} else if (i.getExp().type().equals(program.tUniChar())) {
			m.addInstruction(m.equalsChar());
		}
		m.addInstruction(m.branchIfFalse(i.dirNext() + 1));
		i.getBody().processWith(this);
	}
	public void process(INew i) {
		i.mem().processWith(this);
		m.addInstruction(m.alloc(((TPointer)i.mem().type()).tbase().size()));
		m.addInstruction(m.popInd());
	}
	public void process(IFree i) {
		i.mem().processWith(this);
		m.addInstruction(m.pushInd());
		m.addInstruction(m.dealloc(((TPointer)i.mem().type()).tbase().size()));
	}
	public void process(ArrayIndex arr) {
		DeclaredType t = (DeclaredType) arr.var().type();
		arr.var().processWith(this);
		arr.index().processWith(this);
		if (arr.index().isMem()) {
			m.addInstruction(m.pushInd());
		}
		m.addInstruction(m.inRange(t.size()));
		m.addInstruction(m.pushInt(1));
		m.addInstruction(m.mulInt());
		m.addInstruction(m.addInt());
	}
	public void process(StructField st) {
		TStruct t = (TStruct) st.var().type();
		st.var().processWith(this);
		int offset = 0;
		for (Map.Entry<String, DeclaredType> field : t.fields().entrySet()) {
			if (field.getKey().equals(st.field())) {
				break;
			} else {
				offset += field.getValue().size();
			}
		}
		m.addInstruction(m.pushInt(offset));
		m.addInstruction(m.addInt());
	}

}
