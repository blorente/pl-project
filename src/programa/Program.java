package programa;

import procesamientos.Processing;

public abstract class Program {
   private final Type TINT;
   private final Type TBOOL;
   private final Type TREAL;
   private final Type TUNICHAR;
   private final Type TUNISTRING;
   private final Type TOK;
   private final Type TERROR;
   
   public Program() {
      TINT = new Int();
      TBOOL = new Bool();
      TREAL = new Real();
      TUNICHAR = new UniChar();
      TUNISTRING = new UniString();
      TOK = new Ok();
      TERROR = new Error();
   }
   
   public interface Type {
     void accept(Processing p);
   }
   
   public class Int implements Type {
       public void accept(Processing p) {
          p.process(this);
       }     
       public String toString() {return "INT";}
   }

   public class Bool implements Type {
       public void accept(Processing p) {
          p.process(this);
       }         
       public String toString() {return "BOOL";}
   }

   public class Real implements Type {
       public void accept(Processing p) {
           p.process(this);
       }
       public String toString() {return "REAL";}
   }

   public class UniChar implements Type {
       public void accept(Processing p) {
           p.process(this);
       }
       public String toString() {return "UNICHAR";}
   }

   public class UniString implements Type {
       public void accept(Processing p) {
           p.process(this);
       }
       public String toString() {return "UNISTRING";}
   }

   public class Ok implements Type {
       public void accept(Processing p) {
          p.process(this);
       }         
       public String toString() {return "OK";}
   }
   public class Error implements Type {
       public void accept(Processing p) {
          p.process(this);
       }         
       public String toString() {return "ERROR";}
   }
   
   public class Prog {
     private Dec[] decs;
     private Inst i;
     private Type tipo;
     public Prog(Dec[] decs, Inst i) {
       this.decs = decs;
       this.i = i;
       this.tipo = null;
     }
     public Dec[] decs() {return decs;}
     public Inst inst() {return i;}
     public Type tipo() {return tipo;}
     public void ponTipo(Type tipo) {this.tipo = tipo;}
     public void procesaCon(Processing p) {
       p.process(this);
     }
   }

      
   public abstract class Dec {
     public abstract void procesaCon(Processing p);
   }
   
   public class DecVar extends Dec {
     private String enlaceFuente;
     private String var;
     private Type tipoDec;
     private int dir;
     public DecVar(Type tipo, String var) {
       this(tipo,var,null);
     }
     public DecVar(Type tipo, String var, String enlaceFuente) {
       this.tipoDec = tipo;  
       this.enlaceFuente = enlaceFuente;
       this.var= var;
     }
     public Type tipoDec() {return tipoDec;}
     public String var() {
         return var;
     }
     public int dir() {return dir;}
     public void ponDir(int dir) {this.dir = dir;}
     public String enlaceFuente() {
         return enlaceFuente;  
     }
     public void procesaCon(Processing p) {
         p.process(this);
     }
   }
   
   public abstract class Inst  {
      private Type tipo;
      public Inst() {
       tipo = null;   
      }
     public Type tipo() {return tipo;}
     public void ponTipo(Type tipo) {this.tipo = tipo;}
     public abstract void procesaCon(Processing p);
   }

   
   public class IAsig extends Inst {
       private String var;
       private Exp exp;
       private String enlaceFuente;
       private DecVar declaracion;
       public IAsig(String var, Exp exp, String enlaceFuente) {
          this.var = var;
          this.exp = exp;
          this.declaracion = null;
          this.enlaceFuente = enlaceFuente;
       }
       public IAsig(String var, Exp exp) {
          this(var,exp,null); 
       }
       public String var() {return var;}
       public Exp exp() {return exp;}
       public DecVar declaracion() {
           return declaracion;
       }
       public String enlaceFuente() {
           return enlaceFuente;
       }
       public void ponDeclaracion(DecVar d) {
          declaracion = d; 
       }
       public void procesaCon(Processing p) {
         p.process(this);
       }
   }   

   public class IBloque extends Inst {
       private Inst[] is;
       public IBloque(Inst[] is) {
          this.is = is;
       }
       public Inst[] is() {return is;}
       
       public void procesaCon(Processing p) {
         p.process(this);
       }
   }   
   

   public abstract class Exp {
     private Type tipo;
     public Exp() {
       tipo = null;
     }
     public void ponTipo(Type tipo) {
        this.tipo = tipo;  
     }
     public Type tipo() {
         return tipo;  
     }
     public abstract void procesaCon(Processing p);
   }

   public class Var extends Exp {
       private String var;
       private DecVar declaracion;
       private String enlaceFuente;
       public Var(String var) {
         this(var,null);  
       }
       public Var(String var, String enlaceFuente) {
         this.var = var;
         declaracion = null;
         this.enlaceFuente = enlaceFuente;
       }
       public String var() {return var;}
       public DecVar declaracion() {return declaracion;}
       public void ponDeclaracion(DecVar dec) {
           declaracion = dec;
       }
       public String enlaceFuente() {return enlaceFuente;}
       public void procesaCon(Processing p) {
          p.process(this);
       }
   }
   public class CteInt extends Exp {
       private int val;
       public CteInt(int val) {
         this.val = val;
       }
       public int intVal() {return val;}
       public void procesaCon(Processing p) {
          p.process(this);
       }
   }
   public class CteBool extends Exp {
       private boolean val;
       public CteBool(boolean val) {
         this.val = val;
       }
       public boolean boolVal() {return val;}
       public void procesaCon(Processing p) {
          p.process(this);
       }
   }
   private abstract class ExpBin extends Exp {
       private Exp opnd1;
       private Exp opnd2;
       private String enlaceFuente;
       public ExpBin(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public ExpBin(Exp opnd1, Exp opnd2,String enlaceFuente) {
         this.enlaceFuente = enlaceFuente; 
         this.opnd1 = opnd1;
         this.opnd2 = opnd2;
       }
     public Exp opnd1() {return opnd1;}
     public Exp opnd2() {return opnd2;}
     public String enlaceFuente() {return enlaceFuente;}
   }

   public class Suma extends ExpBin {
       public Suma(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public Suma(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
      public void procesaCon(Processing p) {
          p.process(this);
       }
   }
   public class And extends ExpBin {
       public And(Exp opnd1, Exp opnd2) {
         this(opnd1,opnd2,null);
       }
       public And(Exp opnd1, Exp opnd2,String enlaceFuente) {
         super(opnd1,opnd2,enlaceFuente);  
       }
       public void procesaCon(Processing p) {
          p.process(this);
       }
   }

   public Prog prog(Dec[] decs, Inst i) {
      return new Prog(decs,i);  
   }
   
   public Dec decvar(Type t, String v) {
      return new DecVar(t,v);  
   }
   
   public Dec decvar(Type t, String v, String enlaceFuente) {
      return new DecVar(t,v,enlaceFuente);  
   }
   
   public Inst iasig(String v, Exp e) {
      return new IAsig(v,e);  
   }
   
   public Inst iasig(String v, Exp e, String enlaceFuente) {
      return new IAsig(v,e,enlaceFuente);  
   }

   public Inst ibloque(Inst[] is) {
      return new IBloque(is);  
   }
   public Exp var(String id) {
      return new Var(id);  
   }
   public Exp var(String id, String enlaceFuente) {
      return new Var(id,enlaceFuente);  
   }
   public Exp cteint(int val) {
      return new CteInt(val);  
   }
   public Exp ctebool(boolean val) {
      return new CteBool(val);  
   }
   public Exp suma(Exp exp1, Exp exp2) {
      return new Suma(exp1, exp2);  
   }
   public Exp and(Exp exp1, Exp exp2) {
      return new And(exp1, exp2);  
   }
   public Exp suma(Exp exp1, Exp exp2, String enlaceFuente) {
      return new Suma(exp1, exp2, enlaceFuente);  
   }
   public Exp and(Exp exp1, Exp exp2, String enlaceFuente) {
      return new And(exp1, exp2, enlaceFuente);  
   }
   public Type tInt() {return TINT;}
   public Type tBool() {return TBOOL;}
   public Type tReal() {return TREAL;}
   public Type tUniChar() {return TUNICHAR;}
   public Type tUniString() {return TUNISTRING;}
   public Type tOk() {return TOK;}
   public Type tError() {return TERROR;}
   
   public abstract Prog root();
   
}