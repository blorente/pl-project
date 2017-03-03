package procesamientos.typecheck;

import errores.Errors;
import procesamientos.Processing;
import procesamientos.typecheck.utils.TypeInferer;
import programa.Program;
import programa.Program.Division;
import programa.Program.Multiplication;
import programa.Program.Negative;
import programa.Program.Subtraction;
import programa.Program.Type;
import programa.Program.IntCt;
import programa.Program.Modulus;
import programa.Program.BoolCt;
import programa.Program.RealCt;
import programa.Program.StrElem;
import programa.Program.UniCharCt;
import programa.Program.UniStringCt;
import programa.Program.Addition;
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
    private Errors errors;
    private TypeInferer inferrer;

    public TypeCheck(Program program, Errors errores) {
        this.program = program;
        this.errors = errores;
        this.inferrer = new TypeInferer(program);
    }

    public void process(Var exp) {
        exp.ponTipo(exp.declaracion().tipoDec());
    }

    public void process(IntCt exp) {
        exp.ponTipo(program.tInt());
    }

    public void process(BoolCt exp) {
        exp.ponTipo(program.tBool());
    }

    public void process(RealCt exp) {
        exp.ponTipo(program.tReal());
    }

    public void process(UniCharCt exp) {
        exp.ponTipo(program.tUniChar());
    }

    public void process(UniStringCt exp) {
        exp.ponTipo(program.tUniString());
    }

    public void process(Addition exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferSum(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    
    public void process(Subtraction exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    
    public void process(Multiplication exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    
    public void process(Division exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    

    public void process(Modulus exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferModulus(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }

    public void process(And exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferAnd(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    
    public void process(StrElem exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferStrElem(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    
    public void process(Negative exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferNegative(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.enlaceFuente() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }

    public void process(Prog p) {
        p.inst().procesaCon(this);
        p.ponTipo(p.inst().tipo());
    }

    public void process(IAsig i) {
        i.exp().processWith(this);
        if (!i.declaracion().tipoDec().equals(i.exp().tipo())) {
            if (!i.exp().tipo().equals(program.tError()))
                errors.msg(i.enlaceFuente() + ":" + ERROR_ASSIGNMENT);
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
