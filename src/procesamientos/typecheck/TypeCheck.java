package procesamientos.typecheck;

import errores.Errors;
import procesamientos.Processing;
import procesamientos.typecheck.utils.CompatibilityChecker;
import procesamientos.typecheck.utils.TypeInferer;
import programa.Program;
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
import programa.Program.Subtraction;
import programa.Program.Type;
import programa.Program.UniCharCast;
import programa.Program.IntCt;
import programa.Program.Less;
import programa.Program.LessEq;
import programa.Program.Modulus;
import programa.Program.BoolCt;
import programa.Program.RealCt;
import programa.Program.StrElem;
import programa.Program.UniCharCt;
import programa.Program.UniStrCast;
import programa.Program.UniStringCt;
import programa.Program.Addition;
import programa.Program.And;
import programa.Program.BoolCast;
import programa.Program.IAsig;
import programa.Program.IBlock;
import programa.Program.IRead;
import programa.Program.IWrite;
import programa.Program.Inst;
import programa.Program.IntCast;
import programa.Program.Prog;
import programa.Program.RealCast;
import programa.Program.Var;
import programa.Program.ISwitch;
import programa.Program.ICase;
import programa.Program.DecRef;
import programa.Program.INew;
import programa.Program.IFree;

public class TypeCheck extends Processing {
    private final static String ERROR_OPERAND_TYPES = "Incorrect operand types";
    private final static String ERROR_ASSIGNMENT = "Incompatible types for assignment";
    private final static String ERROR_DREF="A pointer type was expected";
    private final static String ERROR_INDEX="An array object was expected";
    private final static String ERROR_INDEX_INDICE="The index expression must be an integer";
    private final static String ERROR_SELECT="A struct type was expected";
    private final static String ERROR_SELECT_CAMPO="The desired field could not be found in the struct";
    private final static String ERROR_COND="Erroneous condition type";
    private final static String ERROR_NEW="The operand of New must be a pointer";
    private final static String ERROR_FREE="The operand of Free must be a pointer";
    private Program program;
    private Errors errors;
    private TypeInferer inferrer;

    public TypeCheck(Program program, Errors errores) {
        this.program = program;
        this.errors = errores;
        this.inferrer = new TypeInferer(program);
    }

    public void process(DecRef p) {
        p.mem().processWith(this);
        if(CompatibilityChecker.isPointer(p.mem().tipo())) {
            p.ponTipo(CompatibilityChecker.pointer(p.mem().tipo()).tbase());
        } else {
            if(! p.mem().tipo().equals(program.tError())) {
                errors.msg(p.sourcelink()+":"+ERROR_DREF);
            }
            p.ponTipo(program.tError());
        }
    }
    public void process(Var exp) {
        exp.ponTipo(exp.declaration().tipoDec());
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
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Subtraction exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Multiplication exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Division exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Modulus exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferModulus(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(And exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferBoolBinExp(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Or exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferBoolBinExp(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Not exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferBoolUnExp(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Equals exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(NotEquals exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Greater exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(GreaterEq exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Less exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(LessEq exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(StrElem exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferStrElem(exp.opnd1().tipo(), exp.opnd2().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Negative exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferNegative(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(IntCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferIntCast(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(RealCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferRealCast(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(BoolCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferBoolCast(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(UniCharCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferUniCharCast(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(UniStrCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferUniStrCast(exp.op().tipo());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }

    public void process(Prog p) {
        p.inst().processWith(this);
        p.ponTipo(p.inst().tipo());
    }
    public void process(IAsig i) {
        i.mem().processWith(this);
        i.exp().processWith(this);
        if(CompatibilityChecker.areCompatible(i.mem().tipo(),i.exp().tipo())) {
            i.ponTipo(program.tOk());
        }
        else {
            if (! i.mem().tipo().equals(program.tError()) &&
                    ! i.exp().tipo().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_ASSIGNMENT);
            }
            i.ponTipo(program.tError());
        }
    }
    public void process(INew i) {
        i.mem().processWith(this);
        if (CompatibilityChecker.isPointer(i.mem().tipo())) {
            i.ponTipo(program.tOk());
        }
        else {
            if (! i.mem().tipo().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_NEW);
            }
            i.ponTipo(program.tError());
        }
    }
    public void process(IFree i) {
        i.mem().processWith(this);
        if (CompatibilityChecker.isPointer(i.mem().tipo())) {
            i.ponTipo(program.tOk());
        }
        else {
            if (!i.mem().tipo().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_FREE);
            }
            i.ponTipo(program.tError());
        }
    }
    public void process(IBlock b) {
        boolean ok = true;
        for (Inst i : b.is()) {
            i.processWith(this);
            ok = ok && i.tipo().equals(program.tOk());
        }
        if (ok)
            b.ponTipo(program.tOk());
        else
            b.ponTipo(program.tError());
    }
    public void process(IRead i) {
    	i.ponTipo(program.tOk());
    }
    public void process(IWrite i) {
    	i.ponTipo(program.tOk());
    }
    public void process(IWhile wh) {
    	wh.getCond().processWith(this);
    	wh.getBody().processWith(this);
    	if (!wh.getCond().tipo().equals(program.tBool()) ||
    			wh.getBody().tipo().equals(program.tError()))
    		wh.ponTipo(program.tError());
    	else
    		wh.ponTipo(program.tOk());
    }
    public void process(IDoWhile wh) {
        wh.getBody().processWith(this);
        wh.getCond().processWith(this);
        if (!wh.getCond().tipo().equals(program.tBool()) ||
                wh.getBody().tipo().equals(program.tError()))
            wh.ponTipo(program.tError());
        else
            wh.ponTipo(program.tOk());
    }
    public void process(IIfThen i) {
        i.getCond().processWith(this);
        i.getThen().processWith(this);
        if (!i.getCond().tipo().equals(program.tBool()) ||
                i.getThen().tipo().equals(program.tError()))
            i.ponTipo(program.tError());
        else
            i.ponTipo(program.tOk());
    }
    public void process(IIfThenElse i) {
        i.getCond().processWith(this);
        i.getThen().processWith(this);
        i.getElse().processWith(this);
        if (!i.getCond().tipo().equals(program.tBool()) ||
                i.getThen().tipo().equals(program.tError()) ||
                i.getElse().tipo().equals(program.tError()))
            i.ponTipo(program.tError());
        else
            i.ponTipo(program.tOk());
    }
    public void process(ISwitch i) {
        i.getCond().processWith(this);
        Type condT = i.getCond().tipo();
        Type blockT = program.tOk();
        for (ICase c : i.getCases()) {
            c.processWith(this);
            if (!condT.equals(c.getExp().tipo()) ||
                    c.tipo().equals(program.tError()))
                blockT = program.tError();
        }
        if (i.hasDefault()) {
            i.getDefault().processWith(this);
            if (i.getDefault().tipo().equals(program.tError())) {
                blockT = program.tError();
            }
        }
        i.ponTipo(blockT);
    }
    public void process(ICase i) {
        i.getExp().processWith(this);
        i.getBody().processWith(this);
        i.ponTipo(i.getBody().tipo());
    }
}
