
package prueba;

import errores.Errors;
import maquinaP.MaquinaP;
import procesamientos.generacioncodigo.CodeGeneration;
import procesamientos.generacioncodigo.SpaceAssignment;
import procesamientos.generacioncodigo.Tagging;
import procesamientos.impresion.Impresion;
import procesamientos.typecheck.TypeCheck;
import procesamientos.typecheck.Vinculacion;
import program.Program;

public class Prueba extends Program {
	private Prog programa;

	public Prueba() {
		programa = prog(new Dec[] {
						dectype(tipoPointer(tref("miEntero")),"pMientero"),
						dectype(tInt(),"miEntero"),

						decvar(tref("miEntero"),"e"),
						decvar(tref("pMientero"),"pe"),
						decvar(tarray(tref("miEntero"), 6), "arr"),
				},
				iblock(new Inst[]{
						inew(var("pe")),
						iasig(dref(var("pe")),intct(1)),
						iasig(var("e"),dref(var("pe")))}));
	}

	public Prog root() {
		return programa;
	}

	public static final int HEAP_SIZE = 10;

	public static void main(String[] args) {
		Prueba program = new Prueba();
		Errors errores = new Errors();
		Impresion impresionSimple = new Impresion();
		program.root().processWith(impresionSimple);
		Vinculacion vinculacion = new Vinculacion(program, errores);
		program.root().processWith(vinculacion);
		if (!vinculacion.error()) {
			TypeCheck ctipos = new TypeCheck(program, errores);
			program.root().processWith(ctipos);
			if (program.root().tipo().equals(program.tOk())) {
				SpaceAssignment asignaciondirs = new SpaceAssignment();
				program.root().processWith(asignaciondirs);
				Tagging tagging = new Tagging(program);
				program.root().processWith(tagging);
				Impresion impresionCompleta = new Impresion(true);
				program.root().processWith(impresionCompleta);
				MaquinaP maquina = new MaquinaP(asignaciondirs.dataSize(), HEAP_SIZE);
				CodeGeneration generacioncod = new CodeGeneration(program, maquina);
				program.root().processWith(generacioncod);
				maquina.muestraCodigo();
				maquina.execute();
				maquina.muestraEstado();
				maquina.execute();
				maquina.muestraEstado();
			}

		}

	}
}
