
package prueba;

import errores.Errores;
import maquinaP.MaquinaP;
import procesamientos.comprobaciontipos.TypeCheck;
import procesamientos.comprobaciontipos.Vinculacion;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
import programa.Program;


public class Prueba extends Program {
   private Prog programa;
   public Prueba() {
     programa = prog(new Dec[]{decvar(tInt(),"x","linea1"),
                               decvar(tInt(),"y","linea2"),
                               decvar(tBool(),"z","linea3")
                              }, 
                     ibloque(
                          new Inst[]{
                                     iasig("x",  
                                         suma(suma(cteint(5),cteint(6),"linea 5"),
                                              cteint(25),"linea 5"),"linea 5"),
                                     iasig("y",  
                                         suma(suma(var("y","linea 6"),cteint(6),"linea 6"),
                                              cteint(25),"linea 6"), "linea 6")
                                   }));  
   }
   public Prog root() {
      return programa; 
   } 
   public static void main(String[] args) {
      Prueba programa = new Prueba();
      Impresion impresion = new Impresion();
      programa.root().procesaCon(impresion);
      Errores errores = new Errores();
      Vinculacion vinculacion = new Vinculacion(errores);
      programa.root().procesaCon(vinculacion);
      if (! vinculacion.error()) {
        TypeCheck ctipos = new TypeCheck(programa,errores);
        programa.root().procesaCon(ctipos);
        if (programa.root().tipo().equals(programa.tOk())) {
           AsignacionDirecciones asignaciondirs = new AsignacionDirecciones();
           programa.root().procesaCon(asignaciondirs);
           MaquinaP maquina = new MaquinaP(asignaciondirs.tamanioDatos());
           GeneracionDeCodigo generacioncod = new GeneracionDeCodigo(maquina);
           programa.root().procesaCon(generacioncod);
           maquina.muestraCodigo(); 
           maquina.ejecuta();
           maquina.muestraEstado();
           maquina.ejecuta();
        }
        
      }
      
   }
}
  