package frontend;
import errores.Errors;
import java.io.FileReader;
import maquinaP.MaquinaP;
import procesamientos.typecheck.TypeCheck;
import procesamientos.typecheck.Vinculacion;
import procesamientos.generacioncodigo.SpaceAssignment;
import procesamientos.generacioncodigo.Tagging;
import procesamientos.generacioncodigo.CodeGeneration;
import procesamientos.impresion.Impresion;
public class Main{
   public static void main(String[] args) throws Exception {
     try{   
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(new FileReader(args[0]));
      Errors errors = new Errors();
      ASTOps ops = new ASTOps(errors);
      asint.setOps(ops);
      asint.inicio();
      //new Impresion().procesa(ops.raiz());
      Vinculacion vinculacion = new Vinculacion(ops, errors);
      ops.root().processWith(vinculacion);
      if (vinculacion.error()) System.exit(1);
      TypeCheck tipado = new TypeCheck(ops,errors);
      ops.root().processWith(tipado);
      if (ops.root().tipo() != ops.tError()) {
          SpaceAssignment asignacionEspacio = new SpaceAssignment();
          ops.root().processWith(asignacionEspacio);
          Tagging etiquetado = new Tagging(ops);
          ops.root().processWith(etiquetado);
          MaquinaP machine = new MaquinaP(asignacionEspacio.dataSize(),50);//,10,asignacionEspacio.numDisplays());
          CodeGeneration generacioncod = new CodeGeneration(ops, machine);
          ops.root().processWith(generacioncod);
          //maquina.muestraCodigo(); 
          machine.execute();
          machine.muestraEstado();
      }
     }
     catch(TokenMgrError err) {
         System.err.println(err.getMessage());
     }     
     catch(ParseException err) {
         System.err.println(err.getMessage());
     }     
   }
}