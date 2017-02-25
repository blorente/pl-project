package procesamientos.impresion;

import procesamientos.Processing;

import programa.Program.CteInt;
import programa.Program.CteBool;
import programa.Program.Suma;
import programa.Program.And;
import programa.Program.Dec;
import programa.Program.DecVar;
import programa.Program.Exp;
import programa.Program.IAsig;
import programa.Program.IBloque;
import programa.Program.Inst;
import programa.Program.Prog;
import programa.Program.Var;


public class Impresion extends Processing {
   private boolean attributes;
   private int indentation;
   public Impresion(boolean atributos) {
     this.attributes = atributos;
     indentation = 0;
   }
   public Impresion() {
     this(false);
   }
    
   private void printAttributes(Exp exp) {
       if(attributes) {
          System.out.print("@{t:"+exp.tipo()+"}"); 
       }
   }
   private void printAttributes(Prog prog) {
       if(attributes) {
          System.out.print("@{t:"+prog.tipo()+"}"); 
       }
   }
   private void printAttributes(Inst i) {
       if(attributes) {
          System.out.print("@{t:"+i.tipo()+"}"); 
       }
   }
   
   private void indent() {
      for (int i = 0; i < indentation; i++)
          System.out.print(" ");
   }
     
   public void process(CteInt exp) {
     System.out.print(exp.intVal());
     printAttributes(exp);
   } 
   public void process(CteBool exp) {
     System.out.print(exp.boolVal());
     printAttributes(exp);
   } 
   public void process(Var exp) {
     System.out.print(exp.var());
     printAttributes(exp);
   } 
   public void process(Suma exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print('+');
     printAttributes(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   } 
   public void process(And exp) {
     System.out.print('('); 
     exp.opnd1().procesaCon(this);
     System.out.print("&&");
     printAttributes(exp);
     exp.opnd2().procesaCon(this);
     System.out.print(')'); 
   }
   public void process(Prog p) {
      for(Dec d: p.decs()) 
          d.procesaCon(this);
      p.inst().procesaCon(this);
      printAttributes(p);
      System.out.println();
   }     
   public void process(DecVar t) {
      System.out.print(t.tipoDec()+" "+t.var());    
      System.out.println();
   }     
   public void process(IAsig i) {
      indent();
      System.out.print(i.var()+"=");
      i.exp().procesaCon(this);
      printAttributes(i);
      System.out.println(); 
   }     
   public void process(IBloque b) {
      indent();
      System.out.println("{");
      indentation += 3;
      for(Inst i: b.is())
          i.procesaCon(this);
      indentation -=3;
      indent();
      System.out.print("}");
      printAttributes(b);
      System.out.println();
   }     
}   