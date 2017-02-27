package procesamientos;

import programa.Program;
import programa.Program.Division;
import programa.Program.IntCt;
import programa.Program.BoolCt;
import programa.Program.Multiplication;
import programa.Program.RealCt;
import programa.Program.Subtraction;
import programa.Program.UniCharCt;
import programa.Program.UniStringCt;
import programa.Program.Var;
import programa.Program.Addition;
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
   public void process(IntCt exp) {}
   public void process(BoolCt exp) {}
   public void process(RealCt exp) {}
   public void process(UniCharCt exp) {}
   public void process(UniStringCt exp) {}

   public void process(Var exp) {}
   
   public void process(Addition exp) {}
   public void process(Subtraction exp) {}
   public void process(Multiplication exp) {}
   public void process(Division exp) {}
   
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
