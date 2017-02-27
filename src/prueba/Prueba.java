
package prueba;

import errores.Errors;
import maquinaP.MaquinaP;
import procesamientos.generacioncodigo.AsignacionDirecciones;
import procesamientos.generacioncodigo.GeneracionDeCodigo;
import procesamientos.impresion.Impresion;
import procesamientos.typecheck.TypeCheck;
import procesamientos.typecheck.Vinculacion;
import programa.Program;


public class Prueba extends Program {
    private Prog programa;

    public Prueba() {
        programa = prog(new Dec[]{decvar(tReal(), "x", "linea 1"),
                        decvar(tUniString(), "y", "linea 2"),
                        decvar(tBool(), "z", "linea 3"),
                        decvar(tReal(), "real", "linea 4"),
                        decvar(tUniChar(), "char", "linea 5"),
                        decvar(tUniString(), "string1", "linea 6"),
                        decvar(tUniString(), "string2", "linea 6"),
                },
                ibloque(
                        new Inst[]{
                                iasig("x",
                                        add(add(intct(5), intct(6), "linea 7"),
                                                realct(25), "linea 7"), "linea 7"),
                                iasig("string1", unistringct("Hello ")),
                                iasig("string2", unistringct("World!")),
                                iasig("x",
                                		divide(var("x", "linea 11"), var("real", "linea 11"))),
                                iasig("y",
                                        add(var("string1", "linea 8"), var("string2", "linea 8"), "linea 8"))
                        }));
    }

    public Prog root() {
        return programa;
    }

    public static void main(String[] args) {
        Prueba programa = new Prueba();
        Impresion impresion = new Impresion();
        programa.root().procesaCon(impresion);
        Errors errores = new Errors();
        Vinculacion vinculacion = new Vinculacion(errores);
        programa.root().procesaCon(vinculacion);
        if (!vinculacion.error()) {
            TypeCheck ctipos = new TypeCheck(programa, errores);
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
  