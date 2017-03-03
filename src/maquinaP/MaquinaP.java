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

		public String uniStringValue() {
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
	
	private IMod IMOD;
	
	private class IMod implements Instruccion {
		public void ejecuta() {
			Valor op2 = pilaEvaluacion.pop();
			Valor op1 = pilaEvaluacion.pop();
			pilaEvaluacion.push(new IntValue(op1.intValue() % op2.intValue()));
			pc++;
		}
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
			return "pushDir(" + dir + ")";
		}
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
		}
	}

	private IIntToReal IINTTOREAL;

	private class IIntToReal implements Instruccion {
		private String enlaceFuente;

		public IIntToReal() {}
		public IIntToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new RealValue((double) source.intValue());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "intToReal";
		}
	}

	private ICharToReal ICHARTOREAL;

	private class ICharToReal implements Instruccion {
		private String enlaceFuente;

		public ICharToReal() {}
		public ICharToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new RealValue((double) source.valorUniChar());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "charToReal";
		}
	}

	private IBoolToReal IBOOLTOREAL;

	private class IBoolToReal implements Instruccion {
		private String enlaceFuente;

		public IBoolToReal() {}
		public IBoolToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new RealValue(source.valorBool() ? 1.0 : 0.0);
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "boolToReal";
		}
	}

	private IRealToInt IREALTOINT;

	private class IRealToInt implements Instruccion {
		private String enlaceFuente;

		public IRealToInt() {}
		public IRealToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new IntValue((int) source.realValue());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "realToInt";
		}
	}

	private IBoolToInt IBOOLTOINT;

	private class IBoolToInt implements Instruccion {
		private String enlaceFuente;

		public IBoolToInt() {}
		public IBoolToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new IntValue(source.valorBool() ? 1 : 0);
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "boolToInt";
		}
	}

	private ICharToInt ICHARTOINT;

	private class ICharToInt implements Instruccion {
		private String enlaceFuente;

		public ICharToInt() {}
		public ICharToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new IntValue((int) source.valorUniChar());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "charToInt";
		}
	}

	private IIntToChar IINTTOCHAR;

	private class IIntToChar implements Instruccion {
		private String enlaceFuente;

		public IIntToChar() {}
		public IIntToChar(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new UniCharValue((char) source.intValue());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "intToChar";
		}
	}

	private ICharToString ICHARTOSTRING;

	private class ICharToString implements Instruccion {
		private String enlaceFuente;

		public ICharToString() {}
		public ICharToString(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void ejecuta() {
			Valor source = pilaEvaluacion.pop();
			Valor casted = new UniStringValue("" + source.valorUniChar());
			pilaEvaluacion.push(casted);
			pc++;
		}

		public String toString() {
			return "charToString";
		}
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
	
	public Instruccion mod() {
		return IMOD;
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

	public Instruccion intToReal() {
		return IINTTOREAL;
	}

	public Instruccion intToChar() {
		return IINTTOCHAR;
	}

	public Instruccion boolToInt() {
		return IBOOLTOINT;
	}

	public Instruccion boolToReal() {
		return IBOOLTOREAL;
	}

	public Instruccion charToInt() {
		return ICHARTOINT;
	}

	public Instruccion charToReal() {
		return ICHARTOREAL;
	}

	public Instruccion charToString() {
		return ICHARTOSTRING;
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
		IADDREAL = new IAddReal();
		ISUBINT = new ISubInt();
		ISUBREAL = new ISubReal();
		IMULINT = new IMulInt();
		IMULREAL = new IMulReal();
		IDIVINT = new IDivInt();
		IDIVREAL = new IDivReal();
		ICONCAT = new IConcat();
		IMOD = new IMod();
		IAND = new IAnd();

		IINTTOREAL = new IIntToReal();
		IINTTOCHAR = new IIntToChar();
		IREALTOINT = new IRealToInt();
		IBOOLTOINT = new IBoolToInt();
		IBOOLTOREAL = new IBoolToReal();
		ICHARTOINT = new ICharToInt();
		ICHARTOREAL = new ICharToReal();
		ICHARTOSTRING = new ICharToString();

		UNKNOWN = new UnknownValue();
	}

	public void ejecuta() {
		while (pc != codigoP.size()) {
			muestraEstado();
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
