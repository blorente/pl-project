package procesamientos.generacioncodigo;

import procesamientos.Processing;
import programa.Program;
import programa.Program.Prog;
import programa.Program.DecVar;


public class AsignacionDirecciones extends Processing {
   private int dir; 
   public AsignacionDirecciones() {
       dir = 0;
   }
   public void process(Prog p) {
      for(Program.Dec d: p.decs())
          d.procesaCon(this);
   }     
   public void process(DecVar d) {
       d.ponDir(dir++);
   }     
   public int tamanioDatos() {return dir;}
}
