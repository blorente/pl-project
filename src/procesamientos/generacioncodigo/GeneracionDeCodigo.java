package procesamientos.generacioncodigo;

import maquinaP.MaquinaP;
import procesamientos.Processing;
import programa.Program;
import programa.Program.IntCt;
import programa.Program.BoolCt;
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
   public void process(IntCt exp) {
       maquina.addInstruccion(maquina.apilaInt(exp.intVal()));
   } 
   public void process(BoolCt exp) {
       maquina.addInstruccion(maquina.apilaBool(exp.boolVal()));
   } 
   public void process(Suma exp) {
       exp.opnd1().processWith(this);
       exp.opnd2().processWith(this);
       maquina.addInstruccion(maquina.suma());         
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
