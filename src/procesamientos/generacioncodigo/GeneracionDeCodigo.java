package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Processing;
import programa.Program;
import programa.Program.Type;
import programa.Program.IntCt;
import programa.Program.Modulus;
import programa.Program.BoolCt;
import programa.Program.Addition;
import programa.Program.Subtraction;
import programa.Program.Multiplication;
import programa.Program.Division;
import programa.Program.And;
import programa.Program.Prog;
import programa.Program.IBloque;
import programa.Program.IAsig;
import programa.Program.RealCt;
import programa.Program.UniCharCt;
import programa.Program.UniStringCt;
import programa.Program.Var;


public class GeneracionDeCodigo extends Processing {
   private MaquinaP maquina;
   private Program program;
   public GeneracionDeCodigo(Program program, MaquinaP maquina) {
     this.program = program;
      this.maquina = maquina;
   }
   public void process(Var exp) {
      maquina.addInstruccion(maquina.apilaDir(exp.declaracion().dir(),exp.enlaceFuente()));
   }
   public void process(IntCt exp) {
       maquina.addInstruccion(maquina.apilaInt(exp.intVal()));
   }
   public void process(RealCt exp) {
       maquina.addInstruccion(maquina.pushReal(exp.realVal()));
   }
   public void process(UniCharCt exp) {
       maquina.addInstruccion(maquina.pushUniChar(exp.charVal()));
   }
   public void process(UniStringCt exp) {
       maquina.addInstruccion(maquina.pushUniString(exp.stringVal()));
   }
   public void process(BoolCt exp) {
       maquina.addInstruccion(maquina.pushBool(exp.boolVal()));
   }

   public void process(Addition exp) {
       Type t1 = exp.opnd1().tipo();
       Type t2 = exp.opnd2().tipo();
       exp.opnd1().processWith(this);
       if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
         maquina.addInstruccion(maquina.intToReal());
       }
       exp.opnd2().processWith(this);
       if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
         maquina.addInstruccion(maquina.intToReal());
       }

       if (exp.tipo().equals(program.tInt())) {
         maquina.addInstruccion(maquina.addInt());
       }
       if (exp.tipo().equals(program.tReal())) {
         maquina.addInstruccion(maquina.addReal());
       }
       if (exp.tipo().equals(program.tUniString())) {
         maquina.addInstruccion(maquina.concat());
       }
   }

   public void process(Subtraction exp) {
     Type t1 = exp.opnd1().tipo();
     Type t2 = exp.opnd2().tipo();
     exp.opnd1().processWith(this);
     if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
       maquina.addInstruccion(maquina.intToReal());
     }
     exp.opnd2().processWith(this);
     if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
       maquina.addInstruccion(maquina.intToReal());
     }

     if (exp.tipo().equals(program.tInt())) {
       maquina.addInstruccion(maquina.subInt());
     }
     if (exp.tipo().equals(program.tReal())) {
       maquina.addInstruccion(maquina.subReal());
     }
   }
   public void process(Multiplication exp) {
     Type t1 = exp.opnd1().tipo();
     Type t2 = exp.opnd2().tipo();
     exp.opnd1().processWith(this);
     if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
       maquina.addInstruccion(maquina.intToReal());
     }
     exp.opnd2().processWith(this);
     if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
       maquina.addInstruccion(maquina.intToReal());
     }

     if (exp.tipo().equals(program.tInt())) {
       maquina.addInstruccion(maquina.mulInt());
     }
     if (exp.tipo().equals(program.tReal())) {
       maquina.addInstruccion(maquina.mulReal());
     }
   }
   public void process(Division exp) {
     Type t1 = exp.opnd1().tipo();
     Type t2 = exp.opnd2().tipo();
     exp.opnd1().processWith(this);
     if (t1.equals(program.tInt()) && t2.equals(program.tReal())) {
       maquina.addInstruccion(maquina.intToReal());
     }
     exp.opnd2().processWith(this);
     if (t1.equals(program.tReal()) && t2.equals(program.tInt())) {
       maquina.addInstruccion(maquina.intToReal());
     }

     if (exp.tipo().equals(program.tInt())) {
       maquina.addInstruccion(maquina.divInt());
     }
     if (exp.tipo().equals(program.tReal())) {
       maquina.addInstruccion(maquina.divReal());
     }
   }
   
   public void process(Modulus exp) {
       exp.opnd1().processWith(this);
       exp.opnd2().processWith(this);
       maquina.addInstruccion(maquina.mod());
   }

   public void process(And exp) {
       exp.opnd1().processWith(this);
       exp.opnd2().processWith(this);
       maquina.addInstruccion(maquina.and());
   }
   public void process(Prog p) {
      p.inst().procesaCon(this);
   }
   public void process(IAsig i) {
      i.exp().processWith(this);
      maquina.addInstruccion(maquina.desapilaDir(i.declaracion().dir()));
   }
   public void process(IBloque b) {
      for(Program.Inst i: b.is())
          i.procesaCon(this);
   }
}
