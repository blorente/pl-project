package maquinaP;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class MaquinaP {
   private final static String W_ACCESO="**** WARNING: Acceso a memoria sin inicializar"; 
   private final Valor UNKNOWN; 
   private class Valor {
      public int valorInt() {throw new UnsupportedOperationException();}  
      public boolean valorBool() {throw new UnsupportedOperationException();} 
   } 
   private class ValorInt extends Valor {
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorBool extends Valor {
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
   }
   private class ValorUnknown extends Valor {
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
   private ISuma ISUMA;
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         Valor resul;
         if (opnd1 == UNKNOWN || opnd2 == UNKNOWN) 
             resul = UNKNOWN;
         else 
             resul = new ValorInt(opnd1.valorInt()+opnd2.valorInt());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "suma";};
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
             resul = new ValorBool(opnd1.valorBool()&&opnd2.valorBool());
         pilaEvaluacion.push(resul);
         pc++;
      } 
      public String toString() {return "and";};
   }
   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apilaInt("+valor+")";};
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
      public String toString() {return "desapilaDir("+dir+")";};
   }
   private class IApilaDir implements Instruccion {
      private int dir;
      private String enlaceFuente;
      public IApilaDir(int dir) {
        this(dir,null);  
      }
      public IApilaDir(int dir, String enlaceFuente) {
        this.enlaceFuente = enlaceFuente;  
        this.dir = dir;  
      }
      public void ejecuta() {
         if(datos[dir] == null) { 
             System.err.println(enlaceFuente+":"+W_ACCESO); 
             pilaEvaluacion.push(UNKNOWN); 
         }     
         else 
              pilaEvaluacion.push(datos[dir]);
         pc++;
      } 
      public String toString() {return "apilaDir("+dir+","+enlaceFuente+")";};
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apilaBool("+valor+")";};
   }
   
   public Instruccion suma() {return ISUMA;}
   public Instruccion and() {return IAND;}
   public Instruccion apilaInt(int val) {return new IApilaInt(val);}
   public Instruccion apilaBool(boolean val) {return new IApilaBool(val);}
   public Instruccion desapilaDir(int dir) {return new IDesapilaDir(dir);}
   public Instruccion apilaDir(int dir) {return new IApilaDir(dir);}
   public Instruccion apilaDir(int dir,String dinfo) {return new IApilaDir(dir,dinfo);}
   
   public void addInstruccion(Instruccion i) {
      codigoP.add(i); 
   }

   public MaquinaP(int tamdatos) {
      this.codigoP = new ArrayList<>();  
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos];
      this.pc = 0;
      ISUMA = new ISuma();
      IAND = new IAnd();
      UNKNOWN = new ValorUnknown();
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
          codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
}
