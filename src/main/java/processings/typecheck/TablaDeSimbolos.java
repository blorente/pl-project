package processings.typecheck;

import program.Program.*;

import java.util.*;

public class TablaDeSimbolos {
    private static class Nivel {
       public Map<String, DecType> tipos;
       public Map<String, DecVar> variables;
       public Map<String, DecProc> procedimientos;
       public Nivel() {
         tipos = new HashMap<>();  
         variables = new HashMap<>();
         procedimientos = new HashMap<>();
       }
    }
    private List<Nivel> niveles; 
    
    public TablaDeSimbolos() {
        niveles =new ArrayList<Nivel>();
    }
   
    public void ponDecTipo(String tipo, DecType dec) {
        niveles.get(0).tipos.put(tipo,dec);
    }  
    public void ponDecVar(String var, DecVar dec) {
        niveles.get(0).variables.put(var, dec);
    } 
    public void ponDecProc(String proc, DecProc dec) {
        niveles.get(0).procedimientos.put(proc,dec);
    }
    public boolean decTipoDuplicada(String tipo) {
        return niveles.get(0).tipos.containsKey(tipo);
    } 
    public boolean decVarDuplicada(String var) {
        return niveles.get(0).variables.containsKey(var);
    } 
    public boolean decProcDuplicado(String proc) {
        return niveles.get(0).variables.containsKey(proc);
    } 
    public DecType decTipo(String tipo) {
        Iterator<Nivel> iniveles = niveles.iterator();
        DecType dec = null;
        while(dec == null && iniveles.hasNext()) {
           Nivel nivel = iniveles.next();
           dec = nivel.tipos.get(tipo);
        }
        return dec;
    }
    public DecVar decVar(String var) {
        Iterator<Nivel> iniveles = niveles.iterator();
        DecVar dec = null;
        while(dec == null && iniveles.hasNext()) {
           Nivel nivel = iniveles.next();
           dec = nivel.variables.get(var);
        }
        return dec;
    }
    public DecProc decProc(String proc) {
        Iterator<Nivel> iniveles = niveles.iterator();
        DecProc dec = null;
        while(dec == null && iniveles.hasNext()) {
           Nivel nivel = iniveles.next();
           dec = nivel.procedimientos.get(proc);
        }
        return dec;
    }
    public void creaNivel() {
        niveles.add(0,new Nivel());
    }
    public void destruyeNivel() {
        niveles.remove(0);
    }
}
