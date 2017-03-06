package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Processing;
import programa.Program;
import programa.Program.Type;
import programa.Program.UniCharCast;
import programa.Program.IntCt;
import programa.Program.Less;
import programa.Program.LessEq;
import programa.Program.Modulus;
import programa.Program.BoolCt;
import programa.Program.Addition;
import programa.Program.Subtraction;
import programa.Program.Multiplication;
import programa.Program.Negative;
import programa.Program.Not;
import programa.Program.NotEquals;
import programa.Program.Or;
import programa.Program.Division;
import programa.Program.Equals;
import programa.Program.Greater;
import programa.Program.GreaterEq;
import programa.Program.And;
import programa.Program.BoolCast;
import programa.Program.Prog;
import programa.Program.RealCast;
import programa.Program.IBlock;
import programa.Program.IRead;
import programa.Program.IWrite;
import programa.Program.IntCast;
import programa.Program.IAsig;
import programa.Program.RealCt;
import programa.Program.StrElem;
import programa.Program.UniCharCt;
import programa.Program.UniStrCast;
import programa.Program.UniStringCt;
import programa.Program.Var;

public class GeneracionDeCodigo extends Processing {
	private MaquinaP maquina;
	private Program program;

	public GeneracionDeCodigo(Program program, MaquinaP maquina) {
		this.program = program;
		this.maquina = maquina;
	}

	public void process(Var exp) {
		maquina.addInstruction(maquina.apilaDir(exp.declaracion().dir(), exp.enlaceFuente()));
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.addInt());
		}
		if (exp.tipo().equals(program.tReal())) {
			maquina.addInstruction(maquina.addReal());
		}
		if (exp.tipo().equals(program.tUniString())) {
			maquina.addInstruction(maquina.concat());
		}
	}

	public void process(Subtraction exp) {
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.subInt());
		}
		if (exp.tipo().equals(program.tReal())) {
			maquina.addInstruction(maquina.subReal());
		}
	}

	public void process(Multiplication exp) {
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.mulInt());
		}
		if (exp.tipo().equals(program.tReal())) {
			maquina.addInstruction(maquina.mulReal());
		}
	}

	public void process(Division exp) {
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
		exp.opnd1().processWith(this);
		if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
			maquina.addInstruction(maquina.intToReal());
		}
		exp.opnd2().processWith(this);
		if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		}

		if (exp.tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.divInt());
		}
		if (exp.tipo().equals(program.tReal())) {
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		Type t1 = exp.opnd1().tipo();
		Type t2 = exp.opnd2().tipo();
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
		if (exp.tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.negInt());
		} else if (exp.tipo().equals(program.tReal())) {
			maquina.addInstruction(maquina.negReal());
		}
	}

	public void process(IntCast exp) {
		exp.op().processWith(this);
		if (exp.op().tipo().equals(program.tReal())) {
			maquina.addInstruction(maquina.realToInt());
		} else if (exp.op().tipo().equals(program.tBool())) {
			maquina.addInstruction(maquina.boolToInt());
		} else if (exp.op().tipo().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToInt());
		}
	}

	public void process(RealCast exp) {
		exp.op().processWith(this);
		if (exp.op().tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToReal());
		} else if (exp.op().tipo().equals(program.tBool())) {
			maquina.addInstruction(maquina.boolToReal());
		} else if (exp.op().tipo().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToReal());
		}
	}

	public void process(BoolCast exp) {
		exp.op().processWith(this);
		if (exp.op().tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToBool());
		}
	}

	public void process(UniCharCast exp) {
		exp.op().processWith(this);
		if (exp.op().tipo().equals(program.tInt())) {
			maquina.addInstruction(maquina.intToChar());
		}
	}

	public void process(UniStrCast exp) {
		exp.op().processWith(this);
		if (exp.op().tipo().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.charToString());
		}
	}

	public void process(Prog p) {
		p.inst().processWith(this);
	}

	public void process(IAsig i) {
		i.exp().processWith(this);
		maquina.addInstruction(maquina.desapilaDir(i.declaracion().dir()));
	}

	public void process(IBlock b) {
		for (Program.Inst i : b.is())
			i.processWith(this);
	}
	
	public void process(IRead r) {
		if (r.declaracion().tipoDec().equals(program.tInt())) {
			maquina.addInstruction(maquina.readInt());
		} else if (r.declaracion().tipoDec().equals(program.tReal())) {
			maquina.addInstruction(maquina.readReal());
		}  else if (r.declaracion().tipoDec().equals(program.tBool())) {
			maquina.addInstruction(maquina.readBool());
		}  else if (r.declaracion().tipoDec().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.readChar());
		}  else if (r.declaracion().tipoDec().equals(program.tUniString())) {
			maquina.addInstruction(maquina.readString());
		} 
		maquina.addInstruction(maquina.desapilaDir(r.declaracion().dir()));
	}
	
	public void process(IWrite w) {
		maquina.addInstruction(maquina.apilaDir(w.declaration().dir()));
		if (w.declaration().tipoDec().equals(program.tInt())) {
			maquina.addInstruction(maquina.writeInt());
		} else if (w.declaration().tipoDec().equals(program.tReal())) {
			maquina.addInstruction(maquina.writeReal());
		}  else if (w.declaration().tipoDec().equals(program.tBool())) {
			maquina.addInstruction(maquina.writeBool());
		}  else if (w.declaration().tipoDec().equals(program.tUniChar())) {
			maquina.addInstruction(maquina.writeChar());
		}  else if (w.declaration().tipoDec().equals(program.tUniString())) {
			maquina.addInstruction(maquina.writeString());
		}
	}
}
