package processings.typecheck;

import errores.Errors;
import processings.Processing;
import processings.typecheck.utils.CompatibilityChecker;
import processings.typecheck.utils.TypeInferer;
import program.Program;
import program.Program.*;

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
    private final static String ERROR_PARAM_NUM="The number of actual parameters is not the same as the number of formal parameters";
    private final static String ERROR_PARAM_MODE="A non-designator expression is being passed by reference";
    private final static String ERROR_PARAM_TYPE="The formal and actual parameter types are not compatible";
    private Program program;
    private Errors errors;
    private TypeInferer inferrer;

    public TypeCheck(Program program, Errors errores) {
        this.program = program;
        this.errors = errores;
        this.inferrer = new TypeInferer(program);
    }

    public void process(DRefPtr p) {
        p.mem().processWith(this);
        if(CompatibilityChecker.isPointer(p.mem().type())) {
            p.ponTipo(CompatibilityChecker.pointer(p.mem().type()).tbase());
        } else {
            if(! p.mem().type().equals(program.tError())) {
                errors.msg(p.sourcelink()+":"+ERROR_DREF);
            }
            p.ponTipo(program.tError());
        }
    }
    public void process(Var exp) {
        exp.ponTipo(exp.declaration().decType());
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
    public void process(NullCt exp) {exp.ponTipo(program.tNull());}
    public void process(Addition exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferSum(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Subtraction exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Multiplication exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Division exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferArith(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Modulus exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = this.inferrer.inferModulus(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(And exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferBoolBinExp(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Or exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferBoolBinExp(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Not exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferBoolUnExp(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Equals exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(NotEquals exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Greater exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(GreaterEq exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Less exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(LessEq exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferComparator(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(StrElem exp) {
        exp.opnd1().processWith(this);
        exp.opnd2().processWith(this);

        Type inferredType = inferrer.inferStrElem(exp.opnd1().type(), exp.opnd2().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(Negative exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferNegative(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(IntCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferIntCast(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(RealCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferRealCast(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(BoolCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferBoolCast(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(UniCharCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferUniCharCast(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(UniStrCast exp) {
        exp.op().processWith(this);

        Type inferredType = inferrer.inferUniStrCast(exp.op().type());
        if (inferredType.equals(program.tError())) {
            errors.msg(exp.sourceLink() + ":" + ERROR_OPERAND_TYPES);
        }
        exp.ponTipo(inferredType);
    }
    public void process(ArrayIndex exp) {
        exp.var().processWith(this);
        Type tarr = CompatibilityChecker.arrType(exp.var().type());
        if (tarr.equals(program.tError())) {
            errors.msg(exp.sourcelink() + ":" + ERROR_INDEX);
            exp.ponTipo(program.tError());
        } else {
            exp.ponTipo(tarr);
        }
        exp.index().processWith(this);
        if (!exp.index().type().equals(program.tInt())) {
            errors.msg(exp.sourcelink() + ":" + ERROR_INDEX_INDICE);
            exp.ponTipo(program.tError());
        }
    }
    public void process(StructField exp) {
        exp.var().processWith(this);
        TStruct t = (TStruct) exp.var().type();
        exp.ponTipo(t.fields().get(exp.field()));
    }

    public void process(Prog p) {
        p.inst().processWith(this);
        p.ponTipo(p.inst().tipo());
        boolean ok=true;
        for (Dec d: p.decs()) {
            d.processWith(this);
            if (CompatibilityChecker.isProc(d)) {
                ok = ok && (CompatibilityChecker.proc(d).body().tipo() == program.tOk());
            }
        }
        p.inst().processWith(this);
        ok = ok && (p.inst().tipo() == program.tOk());
        if (ok) {
            p.ponTipo(program.tOk());
        }
        else {
            p.ponTipo(program.tError());
        }
    }

    public void process(IAsig i) {
        i.mem().processWith(this);
        i.exp().processWith(this);
        if(CompatibilityChecker.areCompatible(i.mem().type(),i.exp().type())) {
            i.putType(program.tOk());
        }
        else {
            if (! i.mem().type().equals(program.tError()) &&
                    ! i.exp().type().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_ASSIGNMENT);
            }
            i.putType(program.tError());
        }
    }
    public void process(INew i) {
        i.mem().processWith(this);
        if (CompatibilityChecker.isPointer(i.mem().type())) {
            i.putType(program.tOk());
        } else {
            if (!i.mem().type().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_NEW);
            }
            i.putType(program.tError());
        }
    }
    public void process(IFree i) {
        i.mem().processWith(this);
        if (CompatibilityChecker.isPointer(i.mem().type())) {
            i.putType(program.tOk());
        }
        else {
            if (!i.mem().type().equals(program.tError())) {
                errors.msg(i.sourceLink()+":"+ERROR_FREE);
            }
            i.putType(program.tError());
        }
    }
    public void process(IBlock b) {
        boolean ok=true;
        for (Dec d: b.decs()) {
            d.processWith(this);
            if (CompatibilityChecker.isProc(d)) {
                ok = ok && (CompatibilityChecker.proc(d).body().tipo() == program.tOk());
            }
        }
        for (Inst i: b.is()) {
            i.processWith(this);
            ok = ok && i.tipo().equals(program.tOk());
        }
        if (ok)
            b.putType(program.tOk());
        else
            b.putType(program.tError());
    }
    public void process(IRead i) {
    	i.putType(program.tOk());
    }
    public void process(IWrite i) {
    	i.putType(program.tOk());
    }
    public void process(IWhile wh) {
    	wh.getCond().processWith(this);
    	wh.getBody().processWith(this);
    	if (!wh.getCond().type().equals(program.tBool()) ||
    			wh.getBody().tipo().equals(program.tError()))
    		wh.putType(program.tError());
    	else
    		wh.putType(program.tOk());
    }
    public void process(IDoWhile wh) {
        wh.getBody().processWith(this);
        wh.getCond().processWith(this);
        if (!wh.getCond().type().equals(program.tBool()) ||
                wh.getBody().tipo().equals(program.tError()))
            wh.putType(program.tError());
        else
            wh.putType(program.tOk());
    }
    public void process(IIfThen i) {
        i.getCond().processWith(this);
        i.getThen().processWith(this);
        if (!i.getCond().type().equals(program.tBool()) ||
                i.getThen().tipo().equals(program.tError()))
            i.putType(program.tError());
        else
            i.putType(program.tOk());
    }
    public void process(IIfThenElse i) {
        i.getCond().processWith(this);
        i.getThen().processWith(this);
        i.getElse().processWith(this);
        if (!i.getCond().type().equals(program.tBool()) ||
                i.getThen().tipo().equals(program.tError()) ||
                i.getElse().tipo().equals(program.tError()))
            i.putType(program.tError());
        else
            i.putType(program.tOk());
    }
    public void process(ISwitch i) {
        i.getCond().processWith(this);
        Type condT = i.getCond().type();
        Type blockT = program.tOk();
        for (ICase c : i.getCases()) {
            c.processWith(this);
            if (!condT.equals(c.getExp().type()) ||
                    c.tipo().equals(program.tError()))
                blockT = program.tError();
        }
        if (i.hasDefault()) {
            i.getDefault().processWith(this);
            if (i.getDefault().tipo().equals(program.tError())) {
                blockT = program.tError();
            }
        }
        i.putType(blockT);
    }
    public void process(ICase i) {
        i.getExp().processWith(this);
        i.getBody().processWith(this);
        i.putType(i.getBody().tipo());
    }
    public void process(ICall c) {
        DecProc dec = c.declaration();
        if (dec.fparams().length != c.aparams().length) {
            errors.msg(c.sourceLink()+":"+ERROR_PARAM_NUM+"("+c.idproc()+")");
            for(Exp e: c.aparams()) {
                e.processWith(this);
            }
            c.putType(program.tError());
        }
        else {
            boolean error=false;
            for (int i=0; i < dec.fparams().length; i++) {
                if (dec.fparams()[i].isByReference() && ! c.aparams()[i].isMem()) {
                    errors.msg(c.sourceLink()+":"+ERROR_PARAM_MODE+"("+c.idproc()+", param:"+(i+1)+")");
                    error=true;
                }
                c.aparams()[i].processWith(this);
                if (! CompatibilityChecker.areCompatible(dec.fparams()[i].decType(), c.aparams()[i].type())) {
                    if(! c.aparams()[i].type().equals(program.tError())) {
                        errors.msg(c.sourceLink()+":"+ERROR_PARAM_TYPE+"("+c.idproc()+", param:"+(i+1)+")");
                    }
                    error = true;
                }
            }
            if (error) c.putType(program.tError());
            else c.putType(program.tOk());
        }
    }

    public void process(DecProc d) { d.body().processWith(this); }
}
