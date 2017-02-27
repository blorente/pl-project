package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MaquinaP {
	private final static String W_ACCESO = "**** WARNING: Acceso a memoria sin inicializar";
	private final Valor UNKNOWN;

	private class Valor {
		public int intValue() {
			throw new UnsupportedOperationException();
		}

		public boolean valorBool() {
			throw new UnsupportedOperationException();
		}

		public double realValue() {
			throw new UnsupportedOperationException();
		}

		public char valorUniChar() {
			throw new UnsupportedOperationException();
		}

		public String uniStringValue() {
			throw new UnsupportedOperationException();
		}
	}

	private class IntValue extends Valor {
		private int valor;

		public IntValue(int valor) {
			this.valor = valor;
		}

		public int intValue() {
			return valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class BoolValue extends Valor {
		private boolean valor;

		public BoolValue(boolean valor) {
			this.valor = valor;
		}

		public boolean valorBool() {
			return valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class RealValue extends Valor {
		private double valor;

		public RealValue(double valor) {
			this.valor = valor;
		}

		public double realValue() {
			return valor;
		}

		public String toString() {
			return String.valueOf(valor);
		}
	}

	private class UniCharValue extends Valor {
		private char valor;

		public UniCharValue(char valor) {
			this.valor = valor;
		}

		public char valorUniChar() {
			return valor;
		}

		public String toString() {
			return "" + valor;
		}
	}

	private class UniStringValue extends Valor {
		private String valor;

		public UniStringValue(String valor) {
			this.valor = valor;
		}

		public String valorUniString() {
			return valor;
		}

		public String toString() {
			return valor;
		}
	}

	private class UnknownValue extends Valor {
		public String toString() {
			return "?";
		}
	}

	private List<Instruccion> codigoP;
	private Stack<Valor> pilaEvaluacion;
	private Valor[] datos;
	private int pc;

	public interface Instruccion {
		void ejecuta();
	}

	private IAddInt IADDINT;

	private class IAddInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new IntValue(opnd1.intValue() + opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "addInt";
		};
	}

	private IAddReal IADDREAL;

	private class IAddReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new RealValue(opnd1.realValue() + opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "addReal";
		};
	}

	private IConcat ICONCAT;

	private class IConcat implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new UniStringValue(opnd1.uniStringValue()
						+ opnd2.uniStringValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "concat";
		};
	}

	private ISubInt ISUBINT;

	private class ISubInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new IntValue(opnd1.intValue() - opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "subInt";
		};
	}

	private ISubReal ISUBREAL;

	private class ISubReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new RealValue(opnd1.realValue() - opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "subReal";
		};
	}

	private IMulInt IMULINT;

	private class IMulInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new IntValue(opnd1.intValue() * opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "mulInt";
		};
	}

	private IMulReal IMULREAL;

	private class IMulReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new RealValue(opnd1.realValue() * opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "mulReal";
		};
	}

	private IDivInt IDIVINT;

	private class IDivInt implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new IntValue(opnd1.intValue() / opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "divInt";
		};
	}

	private IDivReal IDIVREAL;

	private class IDivReal implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new RealValue(opnd1.realValue() / opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "divReal";
		};
	}

	private IAnd IAND;

	private class IAnd implements Instruccion {
		public void ejecuta() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.valorBool() && opnd2.valorBool());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "and";
		};
	}

	private class IPushInt implements Instruccion {
		private int value;

		public IPushInt(int value) {
			this.value = value;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new IntValue(value));
			pc++;
		}

		public String toString() {
			return "pushInt(" + value + ")";
		};
	}

	private class IPushReal implements Instruccion {
		private double valor;

		public IPushReal(double valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new RealValue(valor));
			pc++;
		}

		public String toString() {
			return "pushReal(" + valor + ")";
		};
	}

	private class IPushUniChar implements Instruccion {
		private char value;

		public IPushUniChar(char value) {
			this.value = value;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new UniCharValue(value));
			pc++;
		}

		public String toString() {
			return "pushUniChar(" + value + ")";
		};
	}

	private class IPushUniString implements Instruccion {
		private String value;

		public IPushUniString(String value) {
			this.value = value;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new UniStringValue(value));
			pc++;
		}

		public String toString() {
			return "pushUniString(" + value + ")";
		};
	}

	private class IDesapilaDir implements Instruccion {
		private int dir;

		public IDesapilaDir(int dir) {
			this.dir = dir;
		}

		public void ejecuta() {
			datos[dir] = pilaEvaluacion.pop();
			pc++;
		}

		public String toString() {
			return "desapilaDir(" + dir + ")";
		};
	}

	private class IApilaDir implements Instruccion {
		private int dir;
		private String enlaceFuente;

		public IApilaDir(int dir) {
			this(dir, null);
		}

		public IApilaDir(int dir, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
			this.dir = dir;
		}

		public void ejecuta() {
			if (datos[dir] == null) {
				System.err.println(enlaceFuente + ":" + W_ACCESO);
				pilaEvaluacion.push(UNKNOWN);
			} else
				pilaEvaluacion.push(datos[dir]);
			pc++;
		}

		public String toString() {
			return "apilaDir(" + dir + "," + enlaceFuente + ")";
		};
	}

	private class IPushBool implements Instruccion {
		private boolean valor;

		public IPushBool(boolean valor) {
			this.valor = valor;
		}

		public void ejecuta() {
			pilaEvaluacion.push(new BoolValue(valor));
			pc++;
		}

		public String toString() {
			return "apilaBool(" + valor + ")";
		};
	}

	public Instruccion addInt() {
		return IADDINT;
	}

	public Instruccion addReal() {
		return IADDREAL;
	}

	public Instruccion concat() {
		return ICONCAT;
	}

	public Instruccion subInt() {
		return ISUBINT;
	}

	public Instruccion subReal() {
		return ISUBREAL;
	}

	public Instruccion mulInt() {
		return IMULINT;
	}

	public Instruccion mulReal() {
		return IMULREAL;
	}

	public Instruccion divInt() {
		return IDIVINT;
	}

	public Instruccion divReal() {
		return IDIVREAL;
	}

	public Instruccion and() {
		return IAND;
	}

	public Instruccion apilaInt(int val) {
		return new IPushInt(val);
	}

	public Instruccion pushReal(double val) {
		return new IPushReal(val);
	}

	public Instruccion pushUniChar(char val) {
		return new IPushUniChar(val);
	}

	public Instruccion pushUniString(String val) {
		return new IPushUniString(val);
	}

	public Instruccion pushBool(boolean val) {
		return new IPushBool(val);
	}

	public Instruccion desapilaDir(int dir) {
		return new IDesapilaDir(dir);
	}

	public Instruccion apilaDir(int dir) {
		return new IApilaDir(dir);
	}

	public Instruccion apilaDir(int dir, String dinfo) {
		return new IApilaDir(dir, dinfo);
	}

	public void addInstruccion(Instruccion i) {
		codigoP.add(i);
	}

	public MaquinaP(int tamdatos) {
		this.codigoP = new ArrayList<>();
		pilaEvaluacion = new Stack<>();
		datos = new Valor[tamdatos];
		this.pc = 0;
		IADDINT = new IAddInt();
		IAND = new IAnd();
		UNKNOWN = new UnknownValue();
	}

	public void ejecuta() {
		while (pc != codigoP.size()) {
			codigoP.get(pc).ejecuta();
		}
	}

	public void muestraCodigo() {
		System.out.println("CodigoP");
		for (int i = 0; i < codigoP.size(); i++) {
			System.out.println(" " + i + ":" + codigoP.get(i));
		}
	}

	public void muestraEstado() {
		System.out.println("Pila de evaluacion");
		for (int i = 0; i < pilaEvaluacion.size(); i++) {
			System.out.println(" " + i + ":" + pilaEvaluacion.get(i));
		}
		System.out.println("Datos");
		for (int i = 0; i < datos.length; i++) {
			System.out.println(" " + i + ":" + datos[i]);
		}
		System.out.println("PC:" + pc);
	}
}
