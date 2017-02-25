package procesamientos;

import programa.Program.CteInt;
import programa.Program.CteBool;
import programa.Program.Var;
import programa.Program.Suma;
import programa.Program.Prog;
import programa.Program.DecVar;
import programa.Program.IAsig;
import programa.Program.IBloque;
import programa.Program.And;
import programa.Program.Int;
import programa.Program.Bool;
import programa.Program.Real;
import programa.Program.UniChar;
import programa.Program.UniString;
import programa.Program.Error;
import programa.Program.Ok;

public class Processing {
   public void process(CteInt exp) {}
   public void process(CteBool exp) {}
   public void process(Var exp) {}
   public void process(Suma exp) {}
   public void process(And exp) {}

   public void process(Int t) {}
   public void process(Bool t) {}
   public void process(Real t) {}
   public void process(UniChar t) {}
   public void process(UniString t) {}
   public void process(Ok t) {}
   public void process(Error t) {}

   public void process(Prog p) {}
   public void process(DecVar d) {}

   public void process(IAsig i) {}
   public void process(IBloque i) {}
}
