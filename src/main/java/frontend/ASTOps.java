package frontend;

import errores.Errors;
import org.apache.commons.lang3.StringEscapeUtils;
import program.Program;

import java.lang.reflect.Array;

public class ASTOps extends Program {
    private Errors errors;
    private final String ERROR_DESIG=" Se esperada un designador";
    public ASTOps(Errors errors) {
       this.errors = errors;
    }
    public abstract static class Lista<T> {
        public Lista<T> todosMenosElUltimo() {throw new UnsupportedOperationException();}
        public T elUltimo() {throw new UnsupportedOperationException();}
        public abstract boolean esVacia();
        public abstract int numElems();
    }
    private static class ListaVacia<T> extends Lista<T> {
        public boolean esVacia() {return true;}
        public int numElems() {return 0;}
        
    }
    private static class ListaNoVacia<T> extends Lista<T> {
        private Lista<T> todosMenosElUltimo;
        private T elUltimo;
        private int nElems;
        public ListaNoVacia(Lista<T> todosMenosElUltimo, T elUltimo) {
            this.todosMenosElUltimo = todosMenosElUltimo;
            this.elUltimo = elUltimo;
            this.nElems = todosMenosElUltimo.numElems()+1;
        }
        public Lista<T> todosMenosElUltimo() {return todosMenosElUltimo;}
        public T elUltimo() {return elUltimo;}
        public boolean esVacia() {return false;}
        public int numElems() {
            return nElems;
        }
    }
    private <T> T[] toArray(Class<T> c, Lista<T> l) {
       T[] a = (T[]) Array.newInstance(c,l.numElems());
       for (int i=l.numElems()-1; i >=0; i--) {
           a[i] = l.elUltimo();
           l = l.todosMenosElUltimo();
       }
       return a;
    }
    private void chequeaDesig(Exp d, String sourceLink) {
        if (! (d.isMem())) {
            errors.msg(sourceLink + ":" + ERROR_DESIG);
            System.exit(1);
        }
    }
    
    private Prog root;
    public Prog prog(Dec[] decs, Inst i) {
        root = super.prog(decs, i);
        return root;
    }
    @Override
    public Prog root() {
        return root;
    }

    public Prog prog(Lista<Dec> decs, Inst i) {
       return prog(toArray(Dec.class, decs),i); 
    }
    public Lista<Dec> nodecs() {
       return new ListaVacia<Dec>();        
    } 
    public Lista<Dec> decs(Lista<Dec> todasMenosLaUltima, Dec laUltima) {
       return new ListaNoVacia<Dec>(todasMenosLaUltima, laUltima);        
    } 
    public Dec decProc(String nombre, Lista<FParam> fparams, Inst cuerpo, 
                       String sourceLink) {
        return decProc(nombre, toArray(FParam.class, fparams), cuerpo, 
                       sourceLink); 
    }
    public Lista<FParam> nofparams() {
        return new ListaVacia<FParam>(); 
    }
    public Lista<FParam> fparams(Lista<FParam> todosMenosElUltimo, FParam elUltimo) {
        return new ListaNoVacia<FParam>(todosMenosElUltimo,elUltimo); 
    }    
    public Inst iblock(Lista<Dec> decs, Lista<Inst> is) {
        return iblock(toArray(Dec.class,decs), toArray(Inst.class, is));
    }
    public Lista<Inst> noInsts() {
        return new ListaVacia<Inst>(); 
    }
    public Lista<Inst> insts(Lista<Inst> todosMenosLaUltima, Inst laUltima) {
        return new ListaNoVacia<Inst>(todosMenosLaUltima, laUltima); 
    }
    
    public Inst iasig(Exp l, Exp r,String sourceLink) {
        chequeaDesig(l,sourceLink);
        return iasig((Mem)l,r,sourceLink);
    }
    public Inst inew(Exp d, String sourceLink) {
        chequeaDesig(d,sourceLink);
        return inew((Mem)d,sourceLink);
    }
    public Inst idelete(Exp d,String sourceLink) {
        chequeaDesig(d,sourceLink);
        return ifree((Mem)d,sourceLink);
    }
    public Inst icall(String nombre, Lista<Exp> params, String sourceLink) {
        return icall(nombre,toArray(Exp.class,params),sourceLink);
    }
    public Lista<Exp> norparams() {
        return new ListaVacia<Exp>();
    }
    public Lista<Exp> rparams(Lista<Exp> allButTheLast, Exp lastOne) {
        return new ListaNoVacia(allButTheLast,lastOne);
    }
    public Exp opUn(String op, Exp exp, String sourceLink) {
        switch(op) {
            case "*": {
                       chequeaDesig(exp,sourceLink);
                       return dref((Mem)exp,sourceLink);
            } 
        }
        throw new UnsupportedOperationException("opUn("+op+"...)");
    }
    public int parseInt(String i) {return Integer.valueOf(i);}
    public Exp intFromString(String i) {
        return intct(parseInt(i));
    }
    public Exp realFromString(String i) {
        return realct(Double.valueOf(i));
    }

    public Exp charLiteral(String i) {
        if (i.charAt(1) == '\\') {
            String escaped = i.substring(1, i.length()-1);
            switch (escaped) {
                case "\\n":
                    return unicharct('\n');
                case "\\'":
                    return unicharct('\'');
                case "\\\\":
                    return unicharct('\\');
                default:
                    String code = escaped.substring(2);
                    int hexToInt = Integer.parseInt(code, 16);
                    char res = (char)hexToInt;
                    return unicharct(res);
            }
        } else {
            return unicharct(i.charAt(1));
        }
    }
    public Exp stringLiteral(String i) {
        String body = i.substring(1, i.length()-1);
        body = StringEscapeUtils.unescapeJava(body);
        return unistringct(body);
    }
}
