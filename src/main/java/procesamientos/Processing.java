package procesamientos;

import program.Program.*;
import program.Program.Error;

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
   public void process(Modulus exp) {}

   public void process(And exp) {}
   public void process(Or exp) {}
   public void process(Not exp) {}
   public void process(Equals exp) {}
   public void process(NotEquals exp) {}
   public void process(Greater exp) {}
   public void process(GreaterEq exp) {}
   public void process(Less exp) {}
   public void process(LessEq exp) {}
   
   public void process(StrElem exp) {}
   
   public void process(Negative exp) {}
   public void process(IntCast exp) {}
   public void process(RealCast exp) {}
   public void process(BoolCast exp) {}
   public void process(UniCharCast exp) {}
   public void process(UniStrCast exp) {}

   public void process(Int t) {}
   public void process(Bool t) {}
   public void process(Real t) {}
   public void process(UniChar t) {}
   public void process(UniString t) {}
   public void process(TRef t) {}
   public void process(TPointer t) {}
   public void process(TArray t) {}
   public void process(Ok t) {}
   public void process(Error t) {}

   public void process(Prog p) {}
   public void process(DecRef d) {}
   public void process(DecType d) {}
   public void process(DecVar d) {}

   public void process(ArrayIndex a) {}

   public void process(IAsig i) {}
   public void process(IBlock i) {}
   public void process(IRead i) {}
   public void process(IWrite i) {}
   public void process(IWhile i) {}
   public void process(IDoWhile i) {}
   public void process(IIfThen i) {}
   public void process(IIfThenElse i) {}
   public void process(ISwitch i) {}
   public void process(ICase i) {}
   public void process(IFree i) {}

   public void process(INew i) {}
}
