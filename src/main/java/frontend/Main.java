package frontend;

import errores.Errors;
import maquinaP.MaquinaP;
import procesamientos.generacioncodigo.CodeGeneration;
import procesamientos.generacioncodigo.SpaceAssignment;
import procesamientos.generacioncodigo.Tagging;
import procesamientos.impresion.Printing;
import procesamientos.typecheck.TypeCheck;
import procesamientos.typecheck.Vinculacion;

import java.io.FileReader;
public class Main{
   public static void main(String[] args) throws Exception {
     try{   
      AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTiny(new FileReader(args[0]));
      Errors errors = new Errors();
      ASTOps ops = new ASTOps(errors);
      asint.setOps(ops);
      asint.inicio();
      Printing printer = new Printing(false);
      Printing fullPrinter = new Printing(true);
      printer.process(ops.root());
      Vinculacion vinculacion = new Vinculacion(ops, errors);
      ops.root().processWith(vinculacion);
      if (vinculacion.error()) System.exit(1);
      TypeCheck tipado = new TypeCheck(ops,errors);
      ops.root().processWith(tipado);
      fullPrinter.process(ops.root());
      if (ops.root().tipo() != ops.tError()) {
          SpaceAssignment spaceAssig = new SpaceAssignment();
          ops.root().processWith(spaceAssig);
          Tagging tagging = new Tagging(ops);
          ops.root().processWith(tagging);
          fullPrinter.process(ops.root());
          MaquinaP machine = new MaquinaP(spaceAssig.dataSize(),50);//,10,asignacionEspacio.numDisplays());
          CodeGeneration codegen = new CodeGeneration(ops, machine);
          ops.root().processWith(codegen);
          machine.showCode();
          machine.execute();
          machine.showState();
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