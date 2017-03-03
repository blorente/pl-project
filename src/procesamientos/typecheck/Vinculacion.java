package procesamientos.typecheck;

import errores.Errors;

import java.util.HashMap;
import java.util.Map;

import procesamientos.Processing;
import programa.Program.Addition;
import programa.Program.Division;
import programa.Program.Multiplication;
import programa.Program.Negative;
import programa.Program.Prog;
import programa.Program.StrElem;
import programa.Program.DecVar;
import programa.Program.IAsig;
import programa.Program.IBloque;
import programa.Program.And;
import programa.Program.Dec;
import programa.Program.Inst;
import programa.Program.Modulus;
import programa.Program.Subtraction;
import programa.Program.Var;

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
			d.procesaCon(this);
		p.inst().procesaCon(this);
	}

	public void process(DecVar d) {
		if (tablaDeSimbolos.containsKey(d.var())) {
			error = true;
			errores.msg(d.enlaceFuente() + ":" + ERROR_ID_DUPLICADO + "("
					+ d.var() + ")");
		} else {
			tablaDeSimbolos.put(d.var(), d);
		}
	}

	public void process(IAsig i) {
		DecVar decVar = tablaDeSimbolos.get(i.var());
		if (decVar == null) {
			error = true;
			errores.msg(i.enlaceFuente() + ":" + ERROR_ID_NO_DECLARADO + "("
					+ i.var() + ")");
		} else {
			i.ponDeclaracion(decVar);
		}
		i.exp().processWith(this);
	}

	public void process(IBloque b) {
		for (Inst i : b.is())
			i.procesaCon(this);
	}

	public boolean error() {
		return error;
	}

	public void process(And exp) {
		exp.opnd1().processWith(this);
		exp.opnd2().processWith(this);
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
	
	public void process(Var exp) {
		DecVar decVar = tablaDeSimbolos.get(exp.var());
		if (decVar == null) {
			error = true;
			errores.msg(exp.enlaceFuente() + ":" + ERROR_ID_NO_DECLARADO + "("
					+ exp.var() + ")");
		} else {
			exp.ponDeclaracion(decVar);
		}

	}

}
