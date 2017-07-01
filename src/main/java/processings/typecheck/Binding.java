package processings.typecheck;

import errores.Errors;
import processings.Processing;
import processings.typecheck.utils.CompatibilityChecker;
import program.Program;
import program.Program.*;

import java.util.HashMap;
import java.util.Map;

public class Binding extends Processing {
	private final static String ERROR_DUPLICATED_ID = "Duplicated identifier";
	private final static String ERROR_DUPLICATED_PROC_ID = "Duplicated Procedure declaration";
	private final static String ERROR_UNDECLARED_ID = "Undeclared identifier";
	private final static String ERROR_DUPLICATED_TYPE_ID = "Duplicated type ID";
	private static final String ERROR_UNDECLARED_TYPE_ID = "Undeclared type ID";
	private static final String ERROR_UNDECLARED_PROC_ID = "Undeclared Procedure";
	private Map<String,DecVar> variables;
	private Map<String,DecType> types;
	private boolean error;
	private Errors errors;
	private CompatibilityChecker compat;
	private TablaDeSimbolos symbolTable;
	private RefCompletion refCompletion;

	public Binding(Program p, Errors errors) {
		variables = new HashMap<>();
		types = new HashMap<>();
		compat = new CompatibilityChecker(p);
		this.errors = errors;
		error = false;
		symbolTable = new TablaDeSimbolos();
		refCompletion = new RefCompletion();
	}

	private class RefCompletion extends Processing {
		public void process(DecType d) {
			d.decType().accept(this);
		}
		public void process(DecVar d) {
			d.decType().accept(this);
		}
		public void process(TPointer p) {
			p.tbase().accept(this);
		}
		public void process(TRef r) {
			DecType d = types.get(r.typeId());
			if (d == null) {
				error = true;
				errors.msg(r.sourceLink()+":"+ERROR_UNDECLARED_TYPE_ID+"("+r.typeId()+")");
			}
			else {
				r.ponDeclaracion(d);
			}
		}
		public void process(TStruct st) {
			for (Map.Entry<String, DeclaredType> field : st.fields().entrySet()) {
				field.getValue().accept(this);
			}
		}
	}

	public void process(Prog p) {
		symbolTable.creaNivel();
		for (Dec d: p.decs())
			d.processWith(this);
		for (Dec d: p.decs())
			d.processWith(refCompletion);
		p.inst().processWith(this);
	}
	public void process(DecType d) {
		if(symbolTable.decTipoDuplicada(d.typeId())) {
			error = true;
			errors.msg(d.sourceLink()+":"+ERROR_DUPLICATED_TYPE_ID+"("+d.typeId()+")");
		}
		else {
			symbolTable.ponDecTipo(d.typeId(),d);
			d.decType().accept(this);
		}
	}
	public void process(DecVar d) {
		if(symbolTable.decVarDuplicada(d.var())) {
			error = true;
			errors.msg(d.sourceLink()+":"+ERROR_DUPLICATED_ID+"("+d.var()+")");
		} else {
			symbolTable.ponDecVar(d.var(), d);
			d.decType().accept(this);
		}
	}
	public void process(DecProc d) {
		if(symbolTable.decProcDuplicado(d.idproc())) {
			error = true;
			errors.msg(d.sourceLink()+":"+ERROR_DUPLICATED_PROC_ID+"("+d.idproc()+")");
		}
		else {
			symbolTable.ponDecProc(d.idproc(), d);
			symbolTable.creaNivel();
			for(FParam param: d.fparams()) {
				symbolTable.ponDecVar(param.var(),param);
				param.decType().accept(this);
			}
			d.body().processWith(this);
			symbolTable.destruyeNivel();
		}
	}
	public void process(TPointer p) {
		if (!CompatibilityChecker.isRef(p.tbase())) {
			p.tbase().accept(this);
		} else {
			processRef(CompatibilityChecker.asRef(p.tbase()));
		}
	}
	public void process(TRef r) {
		DecType d = symbolTable.decTipo(r.typeId());
		if (d == null) {
			error = true;
			errors.msg(r.sourceLink()+":"+ERROR_UNDECLARED_TYPE_ID+"("+r.typeId()+")");
		} else {
			r.ponDeclaracion(d);
		}
	}
	public void process(TStruct st) {
		for (Map.Entry<String, DeclaredType> field : st.fields().entrySet()) {
			field.getValue().accept(this);
		}
	}
	public void process(IAsig i) {
		i.mem().processWith(this);
		i.exp().processWith(this);
	}
	public void process(INew i) {
		i.mem().processWith(this);
	}
	public void process(IFree i) {
		i.mem().processWith(this);
	}
	public void process(IBlock b) {
		symbolTable.creaNivel();
		for(Dec d: b.decs())
			d.processWith(this);
		for (Dec d: b.decs())
			d.processWith(refCompletion);
		for (Inst i: b.is())
			i.processWith(this);
		symbolTable.destruyeNivel();
	}
	public void process(IRead i) {
		DecVar decVar = symbolTable.decVar(i.var());
		if (decVar == null) {
			error = true;
			errors.msg(ERROR_UNDECLARED_ID + "(" + i.var() + ")");
		} else {
			i.ponDeclaracion(decVar);
		}
	}
	public void process(IWrite i) {
		DecVar decVar = symbolTable.decVar(i.var());
		if (decVar == null) {
			error = true;
			errors.msg(ERROR_UNDECLARED_ID + "(" + i.var() + ")");
		} else {
			i.putDeclaration(decVar);
		}
	}
	public void process(IWhile wh) {
		wh.getCond().processWith(this);
		wh.getBody().processWith(this);
	}
	public void process(IDoWhile i) {
		i.getCond().processWith(this);
		i.getBody().processWith(this);
	}
	public void process(IIfThen i) {
		i.getCond().processWith(this);
		i.getThen().processWith(this);
	}
	public void process(IIfThenElse i) {
		i.getCond().processWith(this);
		i.getThen().processWith(this);
		i.getElse().processWith(this);
	}
	public void process(ISwitch i) {
		i.getCond().processWith(this);
		for (Inst c : i.getCases())
			c.processWith(this);
		if (i.hasDefault()) {
			i.getDefault().processWith(this);
		}
	}
	public void process(ICase i) {
		i.getExp().processWith(this);
		i.getBody().processWith(this);
	}
	public void process(ICall c) {
		DecProc proc = symbolTable.decProc(c.idproc());
		if (proc == null) {
			error=true;
			errors.msg(c.sourceLink()+":"+ERROR_UNDECLARED_PROC_ID+"("+c.idproc()+")");
		}
		else {
			c.putDecl(proc);
		}
		for(Exp e: c.aparams())
			e.processWith(this);
	}
	public boolean error() {
		return error;
	}
	public void process(And exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Or exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Equals exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(NotEquals exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Greater exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(GreaterEq exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Less exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(LessEq exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Not exp) {
		exp.op().processWith(this);
	}
	public void process(Addition exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Subtraction exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Multiplication exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Division exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Modulus exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(StrElem exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
	}
	public void process(Negative exp) {
		exp.op().processWith(this);
	}
	public void process(IntCast exp) {
		exp.op().processWith(this);
	}
	public void process(RealCast exp) {
		exp.op().processWith(this);
	}
	public void process(BoolCast exp) {
		exp.op().processWith(this);
	}
	public void process(UniCharCast exp) {
		exp.op().processWith(this);
	}
	public void process(UniStrCast exp) {
		exp.op().processWith(this);
	}
	public void process(Var exp) {
		DecVar decVar = symbolTable.decVar(exp.var());
		if (decVar == null) {
			error = true;
			errors.msg(exp.sourceLink() + ":" + ERROR_UNDECLARED_ID + "(" + exp.var() + ")");
		} else {
			exp.putDeclaration(decVar);
		}
	}
	public void process(DRefPtr d) {
		d.mem().processWith(this);
	}
	public void process(ArrayIndex exp) {
		exp.var().processWith(this);
		exp.index().processWith(this);
	}
	public void process(StructField exp) {
		exp.var().processWith(this);
	}

	private void processRef(TRef r) {
		DecType d = symbolTable.decTipo(r.typeId());
		if (d == null) {
			error = true;
			errors.msg(r.sourceLink()+":"+ERROR_UNDECLARED_TYPE_ID+"("+r.typeId()+")");
		} else {
			r.ponDeclaracion(d);
		}
	}
}
