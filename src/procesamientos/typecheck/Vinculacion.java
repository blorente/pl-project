package procesamientos.typecheck;

import errores.Errors;

import java.util.HashMap;
import java.util.Map;

import procesamientos.Processing;
import programa.Program.Addition;
import programa.Program.Division;
import programa.Program.Equals;
import programa.Program.Greater;
import programa.Program.GreaterEq;
import programa.Program.IWhile;
import programa.Program.IDoWhile;
import programa.Program.IIfThen;
import programa.Program.IIfThenElse;
import programa.Program.Multiplication;
import programa.Program.Negative;
import programa.Program.Not;
import programa.Program.NotEquals;
import programa.Program.Or;
import programa.Program.Prog;
import programa.Program.RealCast;
import programa.Program.StrElem;
import programa.Program.DecVar;
import programa.Program.IAsig;
import programa.Program.IBlock;
import programa.Program.IRead;
import programa.Program.IWrite;
import programa.Program.And;
import programa.Program.BoolCast;
import programa.Program.Dec;
import programa.Program.Inst;
import programa.Program.IntCast;
import programa.Program.Less;
import programa.Program.LessEq;
import programa.Program.Modulus;
import programa.Program.Subtraction;
import programa.Program.UniCharCast;
import programa.Program.UniStrCast;
import programa.Program.Var;
import programa.Program.ISwitch;
import programa.Program.ICase;

public class Vinculacion extends Processing {
	private final static String ERROR_ID_DUPLICADO = "Identificador ya declarado";
	private final static String ERROR_ID_NO_DECLARADO = "Identificador no declarado";
	private Map<String, DecVar> tablaDeSimbolos;
	private boolean error;
	private Errors errores;

	public Vinculacion(Errors errores) {
		tablaDeSimbolos = new HashMap<>();
		this.errores = errores;
		error = false;
	}

	public void process(Prog p) {
		for (Dec d : p.decs())
			d.processWith(this);
		p.inst().processWith(this);
	}

	public void process(DecVar d) {
		if (tablaDeSimbolos.containsKey(d.var())) {
			error = true;
			errores.msg(d.sourceLink() + ":" + ERROR_ID_DUPLICADO + "(" + d.var() + ")");
		} else {
			tablaDeSimbolos.put(d.var(), d);
		}
	}

	public void process(IAsig i) {
		DecVar decVar = tablaDeSimbolos.get(i.var());
		if (decVar == null) {
			error = true;
			errores.msg(i.sourceLink() + ":" + ERROR_ID_NO_DECLARADO + "(" + i.var() + ")");
		} else {
			i.ponDeclaracion(decVar);
		}
		i.exp().processWith(this);
	}

	public void process(IBlock b) {
		for (Inst i : b.is())
			i.processWith(this);
	}
	
	public void process(IRead i) {
		DecVar decVar = tablaDeSimbolos.get(i.var());
		if (decVar == null) {
			error = true;
			errores.msg(ERROR_ID_NO_DECLARADO + "(" + i.var() + ")");
		} else {
			i.ponDeclaracion(decVar);
		}
	}
	
	public void process(IWrite i) {
		DecVar decVar = tablaDeSimbolos.get(i.var());
		if (decVar == null) {
			error = true;
			errores.msg(ERROR_ID_NO_DECLARADO + "(" + i.var() + ")");
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
		DecVar decVar = tablaDeSimbolos.get(exp.var());
		if (decVar == null) {
			error = true;
			errores.msg(exp.sourceLink() + ":" + ERROR_ID_NO_DECLARADO + "(" + exp.var() + ")");
		} else {
			exp.putDeclaration(decVar);
		}

	}

}
