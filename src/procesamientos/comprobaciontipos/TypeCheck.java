package procesamientos.comprobaciontipos;

import errores.Errores;
import procesamientos.Processing;
import procesamientos.comprobaciontipos.utils.TypeInferer;
import programa.Program;
import programa.Program.Type;
import programa.Program.CteInt;
import programa.Program.CteBool;
import programa.Program.Suma;
import programa.Program.And;
import programa.Program.IAsig;
import programa.Program.IBloque;
import programa.Program.Inst;
import programa.Program.Prog;
import programa.Program.Var;


public class TypeCheck extends Processing {
    private final static String ERROR_OPERAND_TYPES = "Los tipos de los operandos no son correctos";
    private final static String ERROR_ASSIGNMENT = "Tipos no compatibles en asignacion";
    private Program program;
    private Errores errores;
    private TypeInferer inferrer;

    public TypeCheck(Program program, Errores errores) {
        this.program = program;
        this.errores = errores;
        this.inferrer = new TypeInferer(program);
    }

    public void process(Var exp) {
        exp.ponTipo(exp.declaracion().tipoDec());
    }

    public void process(CteInt exp) {
        exp.ponTipo(program.tInt());
    }

    public void process(CteBool exp) {
        exp.ponTipo(program.tBool());
    }

    public void process(Suma exp) {
        exp.opnd1().procesaCon(this);
        exp.opnd2().procesaCon(this);

        Type inferredType = this.inferrer.inferSum(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errores.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }

    public void process(And exp) {
        exp.opnd1().procesaCon(this);
        exp.opnd2().procesaCon(this);

        Type inferredType = inferrer.inferAnd(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errores.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }

    public void process(Prog p) {
        p.inst().procesaCon(this);
        p.ponTipo(p.inst().tipo());
    }

    public void process(IAsig i) {
        i.exp().procesaCon(this);
        if (!i.declaracion().tipoDec().equals(i.exp().tipo())) {
            if (!i.exp().tipo().equals(program.tError()))
                errores.msg(i.enlaceFuente() + ":" + ERROR_ASSIGNMENT);
            i.ponTipo(program.tError());
        } else {
            i.ponTipo(program.tOk());
        }
    }

    public void process(IBloque b) {
        boolean ok = true;
        for (Inst i : b.is()) {
            i.procesaCon(this);
            ok = ok && i.tipo().equals(program.tOk());
        }
        if (ok)
            b.ponTipo(program.tOk());
        else
            b.ponTipo(program.tError());
    }

}
