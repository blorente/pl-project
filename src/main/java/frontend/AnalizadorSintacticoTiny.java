/* Generated By:JavaCC: Do not edit this line. AnalizadorSintacticoTiny.java */
package frontend;

import program.Program.Prog;
import program.Program.Dec;
import program.Program.Inst;
import program.Program.DeclaredType;
import program.Program.FParam;
import program.Program.Exp;


import frontend.ASTOps.Lista;

class CabezaProcAs {
   public String proc;
   public Lista<FParam> fparams;
   public CabezaProcAs(String proc, Lista<FParam> fparams) {
      this.proc = proc;
      this.fparams = fparams;
   }
}

public class AnalizadorSintacticoTiny implements AnalizadorSintacticoTinyConstants {
   private ASTOps ops;
   public void setOps(ASTOps ops) {
     this.ops = ops;
   }
   String enlaceFuente(Token token) {
    return "fila "+token.beginLine+" columna "+token.beginColumn;
   }

  final public void inicio() throws ParseException {
    program();
    jj_consume_token(0);
  }

  final public Prog program() throws ParseException {
     Lista<Dec> dec; Inst ins;
    dec = declaraciones();
    ins = instruccion();
         {if (true) return ops.prog(dec,ins);}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Dec> declaraciones() throws ParseException {
     Lista<Dec> decs;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TYPE:
    case VAR:
    case PROC:
      decs = listaDeclaraciones();
         {if (true) return decs;}
      break;
    default:
      jj_la1[0] = jj_gen;
         {if (true) return ops.nodecs();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Lista<Dec> listaDeclaraciones() throws ParseException {
     Dec dec; Lista<Dec> decs;
    dec = declaracion();
    decs = rListaDeclaraciones(ops.decs(ops.nodecs(),dec));
           {if (true) return decs;}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Dec> rListaDeclaraciones(Lista<Dec> decsh) throws ParseException {
      Dec dec; Lista<Dec> decs;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TYPE:
    case VAR:
    case PROC:
      dec = declaracion();
      decs = rListaDeclaraciones(ops.decs(decsh,dec));
            {if (true) return decs;}
      break;
    default:
      jj_la1[1] = jj_gen;
            {if (true) return decsh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Dec declaracion() throws ParseException {
      DeclaredType tipo;  Token id; Token proc;
      CabezaProcAs cabezaProc$as; Inst ins;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TYPE:
      jj_consume_token(TYPE);
      tipo = tipo0();
      id = jj_consume_token(identificador);
      jj_consume_token(43);
           {if (true) return ops.dectype(tipo,id.image,enlaceFuente(id));}
      break;
    case VAR:
      jj_consume_token(VAR);
      tipo = tipo0();
      id = jj_consume_token(identificador);
      jj_consume_token(43);
           {if (true) return ops.decvar(tipo,id.image,enlaceFuente(id));}
      break;
    case PROC:
      proc = jj_consume_token(PROC);
      cabezaProc$as = cabezaProc();
      ins = bloque();
      pyComa();
           {if (true) return ops.decProc(cabezaProc$as.proc, cabezaProc$as.fparams, ins,
                              enlaceFuente(proc));}
      break;
    default:
      jj_la1[2] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public CabezaProcAs cabezaProc() throws ParseException {
      Lista<FParam> fparams; Token id;
    id = jj_consume_token(identificador);
    jj_consume_token(44);
    fparams = parametrosFormales();
    jj_consume_token(45);
           {if (true) return new CabezaProcAs(id.image,fparams);}
    throw new Error("Missing return statement in function");
  }

  final public Lista<FParam> parametrosFormales() throws ParseException {
      Lista<FParam> fparams;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
    case BOOL:
    case CHAR:
    case STRING:
    case DOUBLE:
    case identificador:
      fparams = listaParametrosFormales();
          {if (true) return fparams;}
      break;
    default:
      jj_la1[3] = jj_gen;
          {if (true) return ops.nofparams();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Lista<FParam> listaParametrosFormales() throws ParseException {
      FParam fparam;
      Lista<FParam> fparams;
    fparam = parametroFormal();
    fparams = rListaParametrosFormales(ops.fparams(ops.nofparams(),fparam));
           {if (true) return fparams;}
    throw new Error("Missing return statement in function");
  }

  final public Lista<FParam> rListaParametrosFormales(Lista<FParam> fparamsh) throws ParseException {
      FParam fparam;
      Lista<FParam> fparams;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 46:
      jj_consume_token(46);
      fparam = parametroFormal();
      fparams = rListaParametrosFormales(ops.fparams(fparamsh,fparam));
       {if (true) return fparams;}
      break;
    default:
      jj_la1[4] = jj_gen;
       {if (true) return fparamsh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public FParam parametroFormal() throws ParseException {
      DeclaredType tipo; boolean porRef; Token id;
    tipo = tipo0();
    porRef = modo();
    id = jj_consume_token(identificador);
         {if (true) return ops.fparam(tipo,porRef,id.image,enlaceFuente(id));}
    throw new Error("Missing return statement in function");
  }

  final public boolean modo() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 47:
      jj_consume_token(47);
        {if (true) return true;}
      break;
    default:
      jj_la1[5] = jj_gen;
        {if (true) return false;}
    }
    throw new Error("Missing return statement in function");
  }

  final public DeclaredType tipo0() throws ParseException {
     DeclaredType tipo$tipo1;
     DeclaredType tipo$rTipo0;
    tipo$tipo1 = tipo1();
    tipo$rTipo0 = rTipo0(tipo$tipo1);
         {if (true) return tipo$rTipo0;}
    throw new Error("Missing return statement in function");
  }

  final public DeclaredType rTipo0(DeclaredType tipoh) throws ParseException {
     DeclaredType tipo;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 48:
      jj_consume_token(48);
      tipo = rTipo0(ops.tipoPointer(tipoh));
         {if (true) return tipo;}
      break;
    default:
      jj_la1[6] = jj_gen;
         {if (true) return tipoh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public DeclaredType tipo1() throws ParseException {
     Token id;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
      jj_consume_token(INT);
         {if (true) return ops.tInt();}
      break;
    case BOOL:
      jj_consume_token(BOOL);
         {if (true) return ops.tBool();}
      break;
    case DOUBLE:
      jj_consume_token(DOUBLE);
         {if (true) return ops.tReal();}
      break;
    case STRING:
      jj_consume_token(STRING);
         {if (true) return ops.tUniString();}
      break;
    case CHAR:
      jj_consume_token(CHAR);
        {if (true) return ops.tUniChar();}
      break;
    case identificador:
      id = jj_consume_token(identificador);
         {if (true) return ops.tref(id.image,enlaceFuente(id));}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Inst instruccion() throws ParseException {
     Inst ins;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case identificador:
    case numeroEntero:
    case realNumber:
    case stringLiteral:
    case charLiteral:
    case 44:
    case 48:
      ins = iasig();
       {if (true) return ins;}
      break;
    case 50:
      ins = iblock();
       {if (true) return ins;}
      break;
    case WHILE:
      ins = iwhile();
       {if (true) return ins;}
      break;
    case CALL:
      ins = icall();
       {if (true) return ins;}
      break;
    case NEW:
      ins = inew();
       {if (true) return ins;}
      break;
    case DELETE:
      ins = idelete();
       {if (true) return ins;}
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Inst iasig() throws ParseException {
     Exp el,er; Token asig;
    el = exp0();
    asig = jj_consume_token(49);
    er = exp0();
    jj_consume_token(43);
       {if (true) return ops.iasig(el,er,enlaceFuente(asig));}
    throw new Error("Missing return statement in function");
  }

  final public Inst iblock() throws ParseException {
    Inst ins;
    ins = bloque();
    pyComa();
       {if (true) return ins;}
    throw new Error("Missing return statement in function");
  }

  final public Inst iwhile() throws ParseException {
    Exp exp; Inst i; Token While;
    While = jj_consume_token(WHILE);
    exp = exp0();
    i = bloque();
    pyComa();
       {if (true) return ops.iwhile(exp,i,enlaceFuente(While));}
    throw new Error("Missing return statement in function");
  }

  final public Inst icall() throws ParseException {
      Token id;  Lista<Exp> rparams;
    jj_consume_token(CALL);
    id = jj_consume_token(identificador);
    rparams = parametrosReales();
    jj_consume_token(43);
          {if (true) return ops.icall(id.image,rparams,enlaceFuente(id));}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Exp> parametrosReales() throws ParseException {
     Lista<Exp> rparams;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case WITH:
      jj_consume_token(WITH);
      rparams = listaParametrosReales();
          {if (true) return rparams;}
      break;
    default:
      jj_la1[9] = jj_gen;
          {if (true) return ops.norparams();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Lista<Exp> listaParametrosReales() throws ParseException {
    Exp rparam; Lista<Exp> rparams;
    rparam = exp0();
    rparams = rListaParametrosReales(ops.rparams(ops.norparams(),rparam));
      {if (true) return rparams;}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Exp> rListaParametrosReales(Lista<Exp> rparamsh) throws ParseException {
     Exp rparam; Lista<Exp> rparams;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 46:
      jj_consume_token(46);
      rparam = exp0();
      rparams = rListaParametrosReales(ops.rparams(rparamsh,rparam));
        {if (true) return rparams;}
      break;
    default:
      jj_la1[10] = jj_gen;
     {if (true) return rparamsh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Inst inew() throws ParseException {
     Token cnew; Exp desig;
    cnew = jj_consume_token(NEW);
    desig = exp0();
    jj_consume_token(43);
         {if (true) return ops.inew(desig,enlaceFuente(cnew));}
    throw new Error("Missing return statement in function");
  }

  final public Inst idelete() throws ParseException {
     Token cdelete; Exp desig;
    cdelete = jj_consume_token(DELETE);
    desig = exp0();
    jj_consume_token(43);
          {if (true) return ops.idelete(desig,enlaceFuente(cdelete));}
    throw new Error("Missing return statement in function");
  }

  final public Inst bloque() throws ParseException {
     Lista<Dec> decs; Lista<Inst> is;
    jj_consume_token(50);
    decs = declaraciones();
    is = instrucciones();
    jj_consume_token(51);
          {if (true) return ops.iblock(decs,is);}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Inst> instrucciones() throws ParseException {
     Lista<Inst> insts;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case WHILE:
    case CALL:
    case NEW:
    case DELETE:
    case identificador:
    case numeroEntero:
    case realNumber:
    case stringLiteral:
    case charLiteral:
    case 44:
    case 48:
    case 50:
      insts = listaInstrucciones();
        {if (true) return insts;}
      break;
    default:
      jj_la1[11] = jj_gen;
        {if (true) return ops.noInsts();}
    }
    throw new Error("Missing return statement in function");
  }

  final public Lista<Inst> listaInstrucciones() throws ParseException {
     Inst ins; Lista<Inst> insts;
    ins = instruccion();
    insts = rListaInstrucciones(ops.insts(ops.noInsts(),ins));
         {if (true) return insts;}
    throw new Error("Missing return statement in function");
  }

  final public Lista<Inst> rListaInstrucciones(Lista<Inst> instsh) throws ParseException {
    Inst ins; Lista<Inst> insts;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case TRUE:
    case FALSE:
    case WHILE:
    case CALL:
    case NEW:
    case DELETE:
    case identificador:
    case numeroEntero:
    case realNumber:
    case stringLiteral:
    case charLiteral:
    case 44:
    case 48:
    case 50:
      ins = instruccion();
      insts = rListaInstrucciones(ops.insts(instsh,ins));
         {if (true) return insts;}
      break;
    default:
      jj_la1[12] = jj_gen;
      {if (true) return instsh;}
    }
    throw new Error("Missing return statement in function");
  }

  final public void pyComa() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 43:
      jj_consume_token(43);
      break;
    default:
      jj_la1[13] = jj_gen;

    }
  }

  final public Exp exp0() throws ParseException {
    Exp exp;
    exp = exp1();
      {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  final public Exp exp1() throws ParseException {
    Exp exp$exp2, exp$rexp1;
    exp$exp2 = exp2();
    exp$rexp1 = rexp1(exp$exp2);
        {if (true) return exp$rexp1;}
    throw new Error("Missing return statement in function");
  }

  final public Exp rexp1(Exp exph) throws ParseException {
     Token op; Exp exp$exp2, exp$rexp1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 52:
      op = opAditivo();
      exp$exp2 = exp2();
      exp$rexp1 = rexp1(ops.opBin(op.image,exph,exp$exp2,enlaceFuente(op)));
          {if (true) return exp$rexp1;}
      break;
    default:
      jj_la1[14] = jj_gen;
       {if (true) return exph;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Token opAditivo() throws ParseException {
     Token t;
    t = jj_consume_token(52);
       {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public Exp exp2() throws ParseException {
     Exp exp$exp3, exp$rexp2;
    exp$exp3 = exp3();
    exp$rexp2 = rexp2(exp$exp3);
         {if (true) return exp$rexp2;}
    throw new Error("Missing return statement in function");
  }

  final public Exp rexp2(Exp exph) throws ParseException {
     Exp exp; Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 53:
      t = jj_consume_token(53);
      exp = exp3();
        {if (true) return ops.opBin(t.image,exph,exp,enlaceFuente(t));}
      break;
    default:
      jj_la1[15] = jj_gen;
        {if (true) return exph;}
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp exp3() throws ParseException {
     Exp exp;
    exp = exp4();
       {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  final public Exp exp4() throws ParseException {
     Exp exp;
    exp = exp5();
         {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  final public Exp exp5() throws ParseException {
     Exp exp;
    exp = exp6();
        {if (true) return exp;}
    throw new Error("Missing return statement in function");
  }

  final public Exp exp6() throws ParseException {
     Exp exp; Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case 48:
      t = jj_consume_token(48);
      exp = exp6();
        {if (true) return ops.opUn(t.image,exp,enlaceFuente(t));}
      break;
    case TRUE:
    case FALSE:
    case identificador:
    case numeroEntero:
    case realNumber:
    case stringLiteral:
    case charLiteral:
    case 44:
      exp = exp7();
        {if (true) return exp;}
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp exp7() throws ParseException {
      Token t; Exp e;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case identificador:
      t = jj_consume_token(identificador);
        {if (true) return ops.var(t.image,enlaceFuente(t));}
      break;
    case TRUE:
    case FALSE:
    case numeroEntero:
    case realNumber:
    case stringLiteral:
    case charLiteral:
      e = literal();
         {if (true) return e;}
      break;
    case 44:
      jj_consume_token(44);
      e = exp0();
      jj_consume_token(45);
         {if (true) return e;}
      break;
    default:
      jj_la1[17] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public Exp literal() throws ParseException {
     Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case numeroEntero:
      t = jj_consume_token(numeroEntero);
        {if (true) return ops.intFromString(t.image);}
      break;
    case realNumber:
      t = jj_consume_token(realNumber);
       {if (true) return ops.realFromString(t.image);}
      break;
    case charLiteral:
      t = jj_consume_token(charLiteral);
       System.out.println("Char literal triggered");
      {if (true) return ops.charLiteral(t.image);}
      break;
    case stringLiteral:
      t = jj_consume_token(stringLiteral);
       System.out.println("String literal triggered");
      {if (true) return ops.stringLiteral(t.image);}
      break;
    case TRUE:
      jj_consume_token(TRUE);
        {if (true) return ops.boolct(true);}
      break;
    case FALSE:
      jj_consume_token(FALSE);
        {if (true) return ops.boolct(false);}
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public AnalizadorSintacticoTinyTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[19];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x160000,0x160000,0x160000,0x1f000,0x0,0x0,0x0,0x1f000,0x1a80c00,0x400000,0x0,0x1a80c00,0x1a80c00,0x0,0x0,0x0,0xc00,0xc00,0xc00,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x0,0x10,0x4000,0x8000,0x10000,0x10,0x51670,0x0,0x4000,0x51670,0x51670,0x800,0x100000,0x200000,0x11670,0x1670,0x660,};
   }

  /** Constructor with InputStream. */
  public AnalizadorSintacticoTiny(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AnalizadorSintacticoTiny(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public AnalizadorSintacticoTiny(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AnalizadorSintacticoTinyTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public AnalizadorSintacticoTiny(AnalizadorSintacticoTinyTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AnalizadorSintacticoTinyTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[54];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 19; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 54; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
