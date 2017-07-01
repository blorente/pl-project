package procesamientos.generacioncodigo;

import procesamientos.Processing;
import program.Program;
import program.Program.*;

import java.util.Map;

public class SpaceAssignment extends Processing {
    private int dir;

    public SpaceAssignment() {
        dir = 0;
    }
    public int numDisplays() {return 10;}

    public void process(Prog p) {
        for (Program.Dec d : p.decs())
            d.processWith(this);
    }

    public int dataSize() {
        return dir;
    }

    public void process(DecVar d) {
        d.decType().accept(this);
        d.assignAddr(dir);
        dir += d.decType().size();
    }
    public void process(DecType d) {
        if (d.decType().size() == 0) {
            d.decType().accept(this);
        }
    }

    public void process(Int t) {
        if (t.size() == 0)
            t.putSize(1);
    }
    public void process(Real t) {
        if (t.size() == 0)
            t.putSize(1);
    }
    public void process(Bool t) {
        if (t.size() == 0)
            t.putSize(1);
    }
    public void process(UniString t) {
        if (t.size() == 0)
            t.putSize(1);
    }
    public void process(UniChar t) {
        if (t.size() == 0)
            t.putSize(1);
    }
    public void process(TPointer t) {
        if (t.size() == 0) {
            t.putSize(1);
            t.tbase().accept(this);
        }
    }
    public void process(TRef r) {
        if (r.size() == 0) {
            r.declaration().decType().accept(this);
            r.putSize(r.declaration().decType().size());
        }
    }
    public void process(TArray arr) {
        if (arr.size() == 0) {
            arr.putSize(arr.tbase().size());
        }
    }
    public void process(TStruct str) {
        int size = 0;
        for (Map.Entry<String, DeclaredType> field : str.fields().entrySet()) {
            field.getValue().accept(this);
            size += field.getValue().size();
        }
        str.putSize(size);
    }
}
