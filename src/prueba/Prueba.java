
package prueba;

import errores.Errors;
import maquinaP.MaquinaP;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.CodeGeneration;
import procesamientos.generacioncodigo.Tagging;
import procesamientos.impresion.Impresion;
import procesamientos.typecheck.TypeCheck;
import procesamientos.typecheck.Vinculacion;
import programa.Program;

public class Prueba extends Program {
	private Prog programa;

	public Prueba() {
		programa = prog(
				new Dec[] { decvar(tReal(), "x", "linea 1"),
						decvar(tUniString(), "y", "linea 2"),
						decvar(tBool(), "z", "linea 3"),
						decvar(tReal(), "real", "linea 4"),
						decvar(tUniChar(), "char", "linea 5"),
						decvar(tUniString(), "string1", "linea 6"),
						decvar(tUniString(), "string2", "linea 6"),
						decvar(tInt(), "resto", "linea 7"),
						decvar(tUniChar(), "strElem", "linea 8"),
						decvar(tInt(), "ichar", "linea 8"),
						decvar(tBool(), "myand", "linea 9"),
						decvar(tBool(), "myor", "linea 9"),
						decvar(tBool(), "mynot", "linea 9"),
						decvar(tBool(), "myeq", "linea 10"),
						decvar(tBool(), "myneq", "linea 10"),
						decvar(tBool(), "mygreater", "linea 10"),
						decvar(tBool(), "mygteq", "linea 10"),
						decvar(tBool(), "myless", "linea 10"),
						decvar(tBool(), "mylteq", "linea 10"),
						decvar(tInt(), "mynum"),
						},
				iblock(new Inst[] { iasig("real", add(intct(1),realct(10))),
						iasig("resto", modulus(intct(3), intct(2), "linea 7"), "linea 7"),
						iasig("char", unicharct('b')),
						iasig("x", 
								add(add(intct(5), intct(6), "linea 7"), 
										realct(25), "linea 7"), "linea 7"),
						iasig("string1", unistringct("Hello ")), 
						iasig("string2", unistringct("World!")),
						iasig("x", divide(var("x", "linea 11"), var("real", "linea 11"))),
						iasig("y", 
								add(var("string1", "linea 8"), var("string2", "linea 8"), "linea 8")),
						iasig("resto", negative(var("resto", "linea 11"), "linea 13")),
						iasig("strElem", 
								strElem(var("y", "linea 12"), 
										negative(var("resto", "linea 13"), "linea 13"), "linea 14")),
						iasig("ichar", intct((int)'a')),
						iasig("resto", intcast(var("real"))),
						iasig("real", realcast(var("char"))),
						iasig("char", charcast(var("ichar"))),
						iasig("z", boolcast(var("ichar"))),
						iasig("string2", strcast(var("char"))),
						iasig("myand", and(boolct(true), boolct(false))),
						iasig("myor", or(boolct(true), boolct(false))),
						iasig("mynot", not(boolct(true))),
						iasig("myeq", equals(intct(9), intct(4))),
						iasig("myneq", notequals(intct(9), realct(4.0))),
						iasig("mygreater", greater(boolct(true), boolct(false))),
						iasig("mygteq", greatereq(unicharct('a'), unicharct('b'))),
						iasig("myless", less(unistringct("aaa"), unistringct("ab"))),
						iasig("mylteq", lesseq(realct(3), intct(4))),
						iwrite("ichar"),
						iasig("mynum", intct(0)),
						iwhile(
								less(var("mynum"), intct(5)),
								iblock(new Inst[] {
										iasig("mynum", add(var("mynum"), intct(1)))
												})
								),
						iifthen(
								less(var("mynum"), intct(6)),
								iblock(new Inst[] {
										iasig("mynum", add(var("mynum"), intct(1)))
								})
						),
						iifthenelse(
								less(var("mynum"), intct(6)),
								iblock(new Inst[] {
										iasig("mynum", add(var("mynum"), intct(1)))
								}),
								iblock(new Inst[] {
										iasig("mynum", subtract(var("mynum"), intct(1)))
								})
						),
						dowhile(
								iblock(new Inst[] {
										iasig("mynum", add(var("mynum"), intct(1)))
								}),
								less(var("mynum"), intct(10))
						)
						}));
	}

	public Prog root() {
		return programa;
	}

	public static void main(String[] args) {
		Prueba program = new Prueba();
		Errors errores = new Errors();
		Impresion impresionSimple = new Impresion();
		program.root().processWith(impresionSimple);
		Vinculacion vinculacion = new Vinculacion(errores);
		program.root().processWith(vinculacion);
		if (!vinculacion.error()) {
			TypeCheck ctipos = new TypeCheck(program, errores);
			program.root().processWith(ctipos);
			if (program.root().tipo().equals(program.tOk())) {
				AsignacionDirecciones asignaciondirs = new AsignacionDirecciones();
				program.root().processWith(asignaciondirs);
				Tagging tagging = new Tagging(program);
				program.root().processWith(tagging);
				Impresion impresionCompleta = new Impresion(true);
				program.root().processWith(impresionCompleta);
				MaquinaP maquina = new MaquinaP(asignaciondirs.tamanioDatos());
				CodeGeneration generacioncod = new CodeGeneration(program, maquina);
				program.root().processWith(generacioncod);
				maquina.muestraCodigo();
				maquina.ejecuta();
				maquina.muestraEstado();
				maquina.ejecuta();
				maquina.muestraEstado();
			}

		}

	}
}
