package eu.larkc.csparql.eu.tsp.test;

import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.ResultFormatter;
import org.lorislab.clingo4j.api.Clingo;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Observable;

public class CustomFormatter  extends ResultFormatter {
    public CustomFormatter() {
    }

    public void update(Observable o, Object arg) {
        RDFTable q = (RDFTable)arg;
        System.out.println();
        System.out.println("-------" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
        Iterator var4 = q.iterator();
        ClingoService clingo = new ClingoService();
        clingo.rdfToList(q.toString());
//        while(var4.hasNext()) {
//            RDFTuple t = (RDFTuple)var4.next();
//            System.out.println(t.toString().replaceAll("\t", ",").substring(0, t.toString().length()-1));
//        }

        System.out.println();
    }
}