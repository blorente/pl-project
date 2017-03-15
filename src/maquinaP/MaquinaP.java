package maquinaP;

import programa.Program;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class MaquinaP {
	private final static String W_ACCESO = "**** WARNING: Acceso a memoria sin inicializar";
	private final Valor UNKNOWN;

	private class Valor {
		public int intValue() {
			throw new UnsupportedOperationException();
		}

		public boolean boolValue() {
			throw new UnsupportedOperationException();
		}

		public double realValue() {
			throw new UnsupportedOperationException();
		}

		public char uniCharValue() {
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

		public boolean boolValue() {
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

		public char uniCharValue() {
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

	private List<Instruction> codigoP;
	private Stack<Valor> pilaEvaluacion;
	private Valor[] datos;
	private int pc;

	public interface Instruction {
		void execute();
	}

	private IAddInt IADDINT;

	private class IAddInt implements Instruction {
		public void execute() {
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

	private class IAddReal implements Instruction {
		public void execute() {
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

	private class IConcat implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new UniStringValue(opnd1.uniStringValue() + opnd2.uniStringValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "concat";
		};
	}

	private ISubInt ISUBINT;

	private class ISubInt implements Instruction {
		public void execute() {
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

	private class ISubReal implements Instruction {
		public void execute() {
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

	private class IMulInt implements Instruction {
		public void execute() {
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

	private class IMulReal implements Instruction {
		public void execute() {
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

	private class IDivInt implements Instruction {
		public void execute() {
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

	private class IDivReal implements Instruction {
		public void execute() {
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

	private class IMod implements Instruction {
		public void execute() {
			Valor resul;
			Valor op2 = pilaEvaluacion.pop();
			Valor op1 = pilaEvaluacion.pop();
			if (op1 == UNKNOWN || op2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new IntValue(op1.intValue() % op2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "mod";
		}
	}

	private IAnd IAND;

	private class IAnd implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.boolValue() && opnd2.boolValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "and";
		};
	}

	private IOr IOR;

	private class IOr implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.boolValue() || opnd2.boolValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "or";
		};
	}

	private INot INOT;

	private class INot implements Instruction {
		public void execute() {
			Valor resul;
			Valor op = pilaEvaluacion.pop();
			if (op == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(!op.boolValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "not";
		};
	}
	
	private IEqualsInt IEQUALSINT;
	private class IEqualsInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() == opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "equalsInt";
		};
	}
	
	private IEqualsReal IEQUALSREAL;
	private class IEqualsReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() == opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "equalsReal";
		};
	}
	
	private IEqualsBool IEQUALSBOOL;
	private class IEqualsBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.boolValue() == opnd2.boolValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "equalsBool";
		};
	}
	
	private IEqualsChar IEQUALSCHAR;
	private class IEqualsChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() == opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "equalsChar";
		};
	}
	
	private IEqualsString IEQUALSSTRING;
	private class IEqualsString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniStringValue().equals(opnd2.uniStringValue()));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "equalsString";
		};
	}
	
	private INotEqualsInt INOTEQUALSINT;
	private class INotEqualsInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() != opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "notEqualsInt";
		};
	}
	
	private INotEqualsReal INOTEQUALSREAL;
	private class INotEqualsReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() != opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "notEqualsReal";
		};
	}
	
	private INotEqualsBool INOTEQUALSBOOL;
	private class INotEqualsBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.boolValue() != opnd2.boolValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "notEqualsBool";
		};
	}
	
	private INotEqualsChar INOTEQUALSCHAR;
	private class INotEqualsChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() != opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "notEqualsChar";
		};
	}
	
	private INotEqualsString INOTEQUALSSTRING;
	private class INotEqualsString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(!opnd1.uniStringValue().equals(opnd2.uniStringValue()));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "notEqualsString";
		};
	}
	
	private IGreaterInt IGREATERINT;
	private class IGreaterInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() > opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterInt";
		};
	}
	
	private IGreaterReal IGREATERREAL;
	private class IGreaterReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() > opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterReal";
		};
	}
	
	private IGreaterBool IGREATERBOOL;
	private class IGreaterBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue((opnd1.boolValue() == true)
				&& (opnd2.boolValue() == false));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterBool";
		};
	}
	
	private IGreaterChar IGREATERCHAR;
	private class IGreaterChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() > opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterChar";
		};
	}
	
	private IGreaterString IGREATERSTRING;
	private class IGreaterString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniStringValue().compareTo(opnd2.uniStringValue()) > 0);
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterString";
		};
	}
	
	private IGreaterEqInt IGREATEREQINT;
	private class IGreaterEqInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() >= opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterEqsInt";
		};
	}
	
	private IGreaterEqReal IGREATEREQREAL;
	private class IGreaterEqReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() >= opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterEqReal";
		};
	}
	
	private IGreaterEqBool IGREATEREQBOOL;
	private class IGreaterEqBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(
						(opnd1.boolValue() == true) || 
						(opnd1.boolValue() == false) && (opnd2.boolValue() == false));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterEqBool";
		};
	}
	
	private IGreaterEqChar IGREATEREQCHAR;
	private class IGreaterEqChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() >= opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterEqChar";
		};
	}
	
	private IGreaterEqString IGREATEREQSTRING;
	private class IGreaterEqString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniStringValue().compareTo(opnd2.uniStringValue()) >= 0);
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "greaterEqString";
		};
	}
	
	private ILessInt ILESSINT;
	private class ILessInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() < opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessInt";
		};
	}
	
	private ILessReal ILESSREAL;
	private class ILessReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() < opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessReal";
		};
	}
	
	private ILessBool ILESSBOOL;
	private class ILessBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue((opnd1.boolValue() == false)
				&& (opnd2.boolValue() == true));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessBool";
		};
	}
	
	private ILessChar ILESSCHAR;
	private class ILessChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() < opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessChar";
		};
	}
	
	private ILessString ILESSSTRING;
	private class ILessString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniStringValue().compareTo(opnd2.uniStringValue()) < 0);
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessString";
		};
	}
	
	private ILessEqInt ILESSEQINT;
	private class ILessEqInt implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.intValue() <= opnd2.intValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessEqInt";
		};
	}
	
	private ILessEqReal ILESSEQREAL;
	private class ILessEqReal implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.realValue() <= opnd2.realValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessEqReal";
		};
	}
	
	private ILessEqBool ILESSEQBOOL;
	private class ILessEqBool implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(
						(opnd1.boolValue() == false) || 
						(opnd1.boolValue() == true) && (opnd2.boolValue() == true));
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessEqBool";
		};
	}
	
	private ILessEqChar ILESSEQCHAR;
	private class ILessEqChar implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniCharValue() <= opnd2.uniCharValue());
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessEqChar";
		};
	}
	
	private ILessEqString ILESSEQSTRING;
	private class ILessEqString implements Instruction {
		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN)
				resul = UNKNOWN;
			else
				resul = new BoolValue(opnd1.uniStringValue().compareTo(opnd2.uniStringValue()) <= 0);
			pilaEvaluacion.push(resul);
			pc++;
		}

		public String toString() {
			return "lessEqString";
		};
	}
	
	private IStrElem ISTRELEM;

	private class IStrElem implements Instruction {

		public void execute() {
			Valor opnd2 = pilaEvaluacion.pop();
			Valor opnd1 = pilaEvaluacion.pop();
			Valor resul;
			if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				int index = opnd2.intValue();
				String source = opnd1.uniStringValue();
				if (index >= source.length() || index < 0) {
					pilaEvaluacion.push(UNKNOWN);
				} else {
					char value = source.charAt(index);
					pilaEvaluacion.push(new UniCharValue(value));
				}
			}
			pc++;
		}

		public String toString() {
			return "strElem";
		}

	}

	private INegInt INEGINT;

	private class INegInt implements Instruction {
		public void execute() {
			Valor operand = pilaEvaluacion.pop();
			if (operand == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				pilaEvaluacion.push(new IntValue(-operand.intValue()));
			}
			pc++;
		}

		public String toString() {
			return "negInt";
		}
	}

	private INegReal INEGREAL;

	private class INegReal implements Instruction {
		public void execute() {
			Valor operand = pilaEvaluacion.pop();
			if (operand == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				pilaEvaluacion.push(new RealValue(-operand.realValue()));
			}
			pc++;
		}

		public String toString() {
			return "negReal";
		}
	}

	private class IPushInt implements Instruction {
		private int value;

		public IPushInt(int value) {
			this.value = value;
		}

		public void execute() {
			pilaEvaluacion.push(new IntValue(value));
			pc++;
		}

		public String toString() {
			return "pushInt(" + value + ")";
		};
	}

	private class IPushReal implements Instruction {
		private double valor;

		public IPushReal(double valor) {
			this.valor = valor;
		}

		public void execute() {
			pilaEvaluacion.push(new RealValue(valor));
			pc++;
		}

		public String toString() {
			return "pushReal(" + valor + ")";
		};
	}

	private class IPushUniChar implements Instruction {
		private char value;

		public IPushUniChar(char value) {
			this.value = value;
		}

		public void execute() {
			pilaEvaluacion.push(new UniCharValue(value));
			pc++;
		}

		public String toString() {
			return "pushUniChar(" + value + ")";
		};
	}

	private class IPushUniString implements Instruction {
		private String value;

		public IPushUniString(String value) {
			this.value = value;
		}

		public void execute() {
			pilaEvaluacion.push(new UniStringValue(value));
			pc++;
		}

		public String toString() {
			return "pushUniString(" + value + ")";
		};
	}

	private class IDesapilaDir implements Instruction {
		private int dir;

		public IDesapilaDir(int dir) {
			this.dir = dir;
		}

		public void execute() {
			datos[dir] = pilaEvaluacion.pop();
			pc++;
		}

		public String toString() {
			return "desapilaDir(" + dir + ")";
		};
	}

	private class IApilaDir implements Instruction {
		private int dir;
		private String enlaceFuente;

		public IApilaDir(int dir) {
			this(dir, null);
		}

		public IApilaDir(int dir, String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
			this.dir = dir;
		}

		public void execute() {
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

	private class IPushBool implements Instruction {
		private boolean valor;

		public IPushBool(boolean valor) {
			this.valor = valor;
		}

		public void execute() {
			pilaEvaluacion.push(new BoolValue(valor));
			pc++;
		}

		public String toString() {
			return "apilaBool(" + valor + ")";
		}
	}

	private IIntToReal IINTTOREAL;

	private class IIntToReal implements Instruction {
		private String enlaceFuente;

		public IIntToReal() {
		}

		public IIntToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new RealValue((double) source.intValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "intToReal";
		}
	}

	private IIntToBool IINTTOBOOL;

	private class IIntToBool implements Instruction {
		private String enlaceFuente;

		public IIntToBool() {
		}

		public IIntToBool(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new BoolValue(source.intValue() == 0);
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "intToReal";
		}
	}

	private ICharToReal ICHARTOREAL;

	private class ICharToReal implements Instruction {
		private String enlaceFuente;

		public ICharToReal() {
		}

		public ICharToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new RealValue((double) source.uniCharValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "charToReal";
		}
	}

	private IBoolToReal IBOOLTOREAL;

	private class IBoolToReal implements Instruction {
		private String enlaceFuente;

		public IBoolToReal() {
		}

		public IBoolToReal(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new RealValue(source.boolValue() ? 1.0 : 0.0);
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "boolToReal";
		}
	}

	private IRealToInt IREALTOINT;

	private class IRealToInt implements Instruction {
		private String enlaceFuente;

		public IRealToInt() {
		}

		public IRealToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new IntValue((int) source.realValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "realToInt";
		}
	}

	private IBoolToInt IBOOLTOINT;

	private class IBoolToInt implements Instruction {
		private String enlaceFuente;

		public IBoolToInt() {
		}

		public IBoolToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new IntValue(source.boolValue() ? 1 : 0);
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "boolToInt";
		}
	}

	private ICharToInt ICHARTOINT;

	private class ICharToInt implements Instruction {
		private String enlaceFuente;

		public ICharToInt() {
		}

		public ICharToInt(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new IntValue((int) source.uniCharValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "charToInt";
		}
	}

	private IIntToChar IINTTOCHAR;

	private class IIntToChar implements Instruction {
		private String enlaceFuente;

		public IIntToChar() {
		}

		public IIntToChar(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new UniCharValue((char) source.intValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "intToChar";
		}
	}

	private ICharToString ICHARTOSTRING;

	private class ICharToString implements Instruction {
		private String enlaceFuente;

		public ICharToString() {
		}

		public ICharToString(String enlaceFuente) {
			this.enlaceFuente = enlaceFuente;
		}

		public void execute() {
			Valor source = pilaEvaluacion.pop();
			if (source == UNKNOWN) {
				pilaEvaluacion.push(UNKNOWN);
			} else {
				Valor casted = new UniStringValue("" + source.uniCharValue());
				pilaEvaluacion.push(casted);
			}
			pc++;
		}

		public String toString() {
			return "charToString";
		}
	}
	
	private IReadInt IREADINT;
	private class IReadInt implements Instruction {
		private Scanner in;
		
		public IReadInt() {
			in = new Scanner(System.in);
		}
		
		public void execute() {
			int value = in.nextInt();
			pilaEvaluacion.push(new IntValue(value));
			pc++;
		}
		
		public String toString() {
			return "readInt";
		}
 	}
	
	private IReadReal IREADREAL;
	private class IReadReal implements Instruction {
		private Scanner in;
		
		public IReadReal() {
			in = new Scanner(System.in);
		}
		
		public void execute() {
			double value = in.nextDouble();
			pilaEvaluacion.push(new RealValue(value));
			pc++;
		}
		
		public String toString() {
			return "readDouble";
		}
 	}
	
	private IReadBool IREADBOOL;
	private class IReadBool implements Instruction {
		private Scanner in;
		
		public IReadBool() {
			in = new Scanner(System.in);
		}
		
		public void execute() {
			boolean value = in.nextBoolean();
			pilaEvaluacion.push(new BoolValue(value));
			pc++;
		}
		
		public String toString() {
			return "readBool";
		}
 	}
	
	private IReadChar IREADCHAR;
	private class IReadChar implements Instruction {
		private Scanner in;
		
		public IReadChar() {
			in = new Scanner(System.in);
		}
		
		public void execute() {
			char value = in.findInLine(".").charAt(0);
			pilaEvaluacion.push(new UniCharValue(value));
			pc++;
		}
		
		public String toString() {
			return "readIntChar";
		}
 	}
	
	private IReadString IREADSTRING;
	private class IReadString implements Instruction {
		private Scanner in;
		
		public IReadString() {
			in = new Scanner(System.in);
		}
		
		public void execute() {
			String value = in.next();
			pilaEvaluacion.push(new UniStringValue(value));
			pc++;
		}
		
		public String toString() {
			return "readString";
		}
 	}
	
	private IWriteString IWRITESTRING;
	private class IWriteString implements Instruction {
		public void execute() {
			Valor value = pilaEvaluacion.pop();
			System.out.println(value.uniStringValue());
			pc++;
		}
		
		public String toString() {
			return "writeString";
		}
	}
	
	private IWriteInt IWRITEINT;
	private class IWriteInt implements Instruction {
		public void execute() {
			Valor value = pilaEvaluacion.pop();
			System.out.println(value.intValue());
			pc++;
		}
		
		public String toString() {
			return "writeInt";
		}
	}
	
	private IWriteReal IWRITEREAL;
	private class IWriteReal implements Instruction {
		public void execute() {
			Valor value = pilaEvaluacion.pop();
			System.out.println(value.realValue());
			pc++;
		}
		
		public String toString() {
			return "writeReal";
		}
	}
	
	private IWriteBool IWRITEBOOL;
	private class IWriteBool implements Instruction {
		public void execute() {
			Valor value = pilaEvaluacion.pop();
			System.out.println(value.boolValue());
			pc++;
		}
		
		public String toString() {
			return "writeBool";
		}
	}
	
	private IWriteChar IWRITECHAR;
	private class IWriteChar implements Instruction {
		public void execute() {
			Valor value = pilaEvaluacion.pop();
			System.out.println(value.uniCharValue());
			pc++;
		}
		
		public String toString() {
			return "writeChar";
		}
	}

	private class IBranchIfFalse implements Instruction {

		int target;

		public IBranchIfFalse(int target) {
			this.target = target;
		}

		public void execute() {
			Valor value = pilaEvaluacion.pop();
			if (!value.boolValue()) {
				pc = target;
			} else {
				pc++;
			}
		}

		public String toString() {
			return "branchIfFalse(" + target + ")";
		}
	}

	private class IBranch implements Instruction {

		int target;

		public IBranch(int target) {
			this.target = target;
		}

		public void execute() {
			pc = target;
		}

		public String toString() {
			return "branch(" + target + ")";
		}
	}


	private IDup IDUP;
	private class IDup implements Instruction {
		@Override
		public void execute() {
			Valor v = pilaEvaluacion.pop();
			pilaEvaluacion.push(v);
			pilaEvaluacion.push(v);
			pc++;
		}

		@Override
		public String toString() {
			return "dup";
		}
	}

	private IPop IPOP;
	private class IPop implements Instruction {
		@Override
		public void execute() {
			pilaEvaluacion.pop();
			pc++;
		}

		@Override
		public String toString() {
			return "pop";
		}
	}

	public Instruction addInt() {
		return IADDINT;
	}

	public Instruction addReal() {
		return IADDREAL;
	}

	public Instruction concat() {
		return ICONCAT;
	}

	public Instruction subInt() {
		return ISUBINT;
	}

	public Instruction subReal() {
		return ISUBREAL;
	}

	public Instruction mulInt() {
		return IMULINT;
	}

	public Instruction mulReal() {
		return IMULREAL;
	}

	public Instruction divInt() {
		return IDIVINT;
	}

	public Instruction divReal() {
		return IDIVREAL;
	}

	public Instruction mod() {
		return IMOD;
	}

	public Instruction negInt() {
		return INEGINT;
	}

	public Instruction negReal() {
		return INEGREAL;
	}

	public Instruction and() {
		return IAND;
	}

	public Instruction or() {
		return IOR;
	}

	public Instruction not() {
		return INOT;
	}
	
	public Instruction equalsInt() {
		return IEQUALSINT;
	}
	public Instruction equalsReal() {
		return IEQUALSREAL;
	}
	public Instruction equalsBool() {
		return IEQUALSBOOL;
	}
	public Instruction equalsChar() {
		return IEQUALSCHAR;
	}
	public Instruction equalsString() {
		return IEQUALSSTRING;
	}
	
	public Instruction notEqualsInt() {
		return INOTEQUALSINT;
	}
	public Instruction notEqualsReal() {
		return INOTEQUALSREAL;
	}
	public Instruction notEqualsBool() {
		return INOTEQUALSBOOL;
	}
	public Instruction notEqualsChar() {
		return INOTEQUALSCHAR;
	}
	public Instruction notEqualsString() {
		return INOTEQUALSSTRING;
	}
	
	public Instruction greaterInt() {
		return IGREATERINT;
	}
	public Instruction greaterReal() {
		return IGREATERREAL;
	}
	public Instruction greaterBool() {
		return IGREATERBOOL;
	}
	public Instruction greaterChar() {
		return IGREATERCHAR;
	}
	public Instruction greaterString() {
		return IGREATERSTRING;
	}
	
	public Instruction greaterEqInt() {
		return IGREATEREQINT;
	}
	public Instruction greaterEqReal() {
		return IGREATEREQREAL;
	}
	public Instruction greaterEqBool() {
		return IGREATEREQBOOL;
	}
	public Instruction greaterEqChar() {
		return IGREATEREQCHAR;
	}
	public Instruction greaterEqString() {
		return IGREATEREQSTRING;
	}
	
	public Instruction lessInt() {
		return ILESSINT;
	}
	public Instruction lessReal() {
		return ILESSREAL;
	}
	public Instruction lessBool() {
		return ILESSBOOL;
	}
	public Instruction lessChar() {
		return ILESSCHAR;
	}
	public Instruction lessString() {
		return ILESSSTRING;
	}
	
	public Instruction lessEqInt() {
		return ILESSEQINT;
	}	
	public Instruction lessEqReal() {
		return ILESSEQREAL;
	}
	public Instruction lessEqBool() {
		return ILESSEQBOOL;
	}
	public Instruction lessEqChar() {
		return ILESSEQCHAR;
	}
	public Instruction lessEqString() {
		return ILESSEQSTRING;
	}

	public Instruction strElem() {
		return ISTRELEM;
	}

	public Instruction pushInt(int val) {
		return new IPushInt(val);
	}

	public Instruction pushReal(double val) {
		return new IPushReal(val);
	}

	public Instruction pushUniChar(char val) {
		return new IPushUniChar(val);
	}

	public Instruction pushUniString(String val) {
		return new IPushUniString(val);
	}

	public Instruction pushBool(boolean val) {
		return new IPushBool(val);
	}

	public Instruction desapilaDir(int dir) {
		return new IDesapilaDir(dir);
	}

	public Instruction apilaDir(int dir) {
		return new IApilaDir(dir);
	}

	public Instruction apilaDir(int dir, String dinfo) {
		return new IApilaDir(dir, dinfo);
	}

	public Instruction intToReal() {
		return IINTTOREAL;
	}

	public Instruction intToBool() {
		return IINTTOBOOL;
	}

	public Instruction intToChar() {
		return IINTTOCHAR;
	}

	public Instruction realToInt() {
		return IREALTOINT;
	}

	public Instruction boolToInt() {
		return IBOOLTOINT;
	}

	public Instruction boolToReal() {
		return IBOOLTOREAL;
	}

	public Instruction charToInt() {
		return ICHARTOINT;
	}

	public Instruction charToReal() {
		return ICHARTOREAL;
	}

	public Instruction charToString() {
		return ICHARTOSTRING;
	}
	
	public Instruction readInt() {
		return IREADINT;
	}
	
	public Instruction readReal() {
		return IREADREAL;
	}
	
	public Instruction readBool() {
		return IREADBOOL;
	}
	
	public Instruction readChar() {
		return IREADCHAR;
	}
	
	public Instruction readString() {
		return IREADSTRING;
	}
	
	public Instruction writeInt() {
		return IWRITEINT;
	}
	
	public Instruction writeReal() {
		return IWRITEREAL;
	}

	public Instruction writeBool() {
		return IWRITEBOOL;
	}
		
	public Instruction writeChar() {
		return IWRITECHAR;
	}
	
	public Instruction writeString() {
		return IWRITESTRING;
	}

	public Instruction branchIfFalse(int dir) {
		return new IBranchIfFalse(dir);
	}

	public Instruction branch(int dir) {
		return new IBranch(dir);
	}

	public Instruction dup() {
		return IDUP;
	}

	public Instruction pop() {
		return IPOP;
	}

	public void addInstruction(Instruction i) {
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
		IOR = new IOr();
		INOT = new INot();
		
		IEQUALSINT = new IEqualsInt();
		IEQUALSREAL = new IEqualsReal();
		IEQUALSBOOL = new IEqualsBool();
		IEQUALSCHAR = new IEqualsChar();
		IEQUALSSTRING = new IEqualsString();
		INOTEQUALSINT = new INotEqualsInt();
		INOTEQUALSREAL = new INotEqualsReal();
		INOTEQUALSBOOL = new INotEqualsBool();
		INOTEQUALSCHAR = new INotEqualsChar();
		INOTEQUALSSTRING = new INotEqualsString();
		IGREATERINT = new IGreaterInt();
		IGREATERREAL = new IGreaterReal();
		IGREATERBOOL = new IGreaterBool();
		IGREATERCHAR = new IGreaterChar();
		IGREATERSTRING = new IGreaterString();
		IGREATEREQINT = new IGreaterEqInt();
		IGREATEREQREAL = new IGreaterEqReal();
		IGREATEREQBOOL = new IGreaterEqBool();
		IGREATEREQCHAR = new IGreaterEqChar();
		IGREATEREQSTRING = new IGreaterEqString();
		ILESSINT = new ILessInt();
		ILESSREAL = new ILessReal();
		ILESSBOOL = new ILessBool();
		ILESSCHAR = new ILessChar();
		ILESSSTRING = new ILessString();
		ILESSEQINT = new ILessEqInt();
		ILESSEQREAL = new ILessEqReal();
		ILESSEQBOOL = new ILessEqBool();
		ILESSEQCHAR = new ILessEqChar();
		ILESSEQSTRING = new ILessEqString();		

		ISTRELEM = new IStrElem();

		INEGINT = new INegInt();
		INEGREAL = new INegReal();

		IINTTOREAL = new IIntToReal();
		IINTTOBOOL = new IIntToBool();
		IINTTOCHAR = new IIntToChar();
		IREALTOINT = new IRealToInt();
		IBOOLTOINT = new IBoolToInt();
		IBOOLTOREAL = new IBoolToReal();
		ICHARTOINT = new ICharToInt();
		ICHARTOREAL = new ICharToReal();
		ICHARTOSTRING = new ICharToString();
		
		IREADINT = new IReadInt();
		IREADREAL = new IReadReal();
		IREADBOOL = new IReadBool();
		IREADCHAR = new IReadChar();
		IREADSTRING = new IReadString();
		IWRITEINT = new IWriteInt();
		IWRITEREAL = new IWriteReal();
		IWRITEBOOL = new IWriteBool();
		IWRITECHAR = new IWriteChar();
		IWRITESTRING = new IWriteString();

		IDUP = new IDup();
		IPOP = new IPop();

		UNKNOWN = new UnknownValue();
	}

	public void ejecuta() {
		while (pc != codigoP.size()) {
			codigoP.get(pc).execute();
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
