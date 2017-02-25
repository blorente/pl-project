package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Processing;
import programa.Program;
import programa.Program.CteInt;
import programa.Program.CteBool;
import programa.Program.Suma;
import programa.Program.And;
import programa.Program.Prog;
import programa.Program.IBloque;
import programa.Program.IAsig;
import programa.Program.Var;


public class GeneracionDeCodigo extends Processing {
   private MaquinaP maquina; 
   public GeneracionDeCodigo(MaquinaP maquina) {
      this.maquina = maquina; 
   }
   public void process(Var exp) {
      maquina.addInstruccion(maquina.apilaDir(exp.declaracion().dir(),exp.enlaceFuente()));         
   } 
   public void process(CteInt exp) {
       maquina.addInstruccion(maquina.apilaInt(exp.intVal()));
   } 
   public void process(CteBool exp) {
       maquina.addInstruccion(maquina.apilaBool(exp.boolVal()));
   } 
   public void process(Suma exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.suma());         
   } 
   public void process(And exp) {
       exp.opnd1().procesaCon(this);
       exp.opnd2().procesaCon(this);
       maquina.addInstruccion(maquina.and());         
   }   
   public void process(Prog p) {
      p.inst().procesaCon(this);
   }     
   public void process(IAsig i) {
      i.exp().procesaCon(this);
      maquina.addInstruccion(maquina.desapilaDir(i.declaracion().dir()));
   }     
   public void process(IBloque b) {
      for(Program.Inst i: b.is())
          i.procesaCon(this);
   }     
}
