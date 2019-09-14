package eu.larkc.csparql.eu.tsp.test;


import eu.larkc.csparql.common.RDFTuple;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.lorislab.clingo4j.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class clingoAgent extends Agent {

    public void setup(){
        System.out.println("Hello World! My name is clingo and my alias is " + this.getLocalName());

        /** Déclaration d'un service + lien avec l'agent et l'enregistrer dans DF */
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("clingo-service");
        sd.setName("JADE-clingo-Agent");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        /** comportement cyclique ==> attend la reception des messages */
        addBehaviour(new jade.core.behaviours.CyclicBehaviour() {
            public void action() {
                System.out.println(" Wainting... ");
                ACLMessage m = receive();
                if(m != null) {

                    String[] listeTriple = m.getContent().split("\n");
                    String[] predicatExcluded= {"hasUpperTimeStampValue","hasLowerTimeStampValue"};

                    System.out.println("nombre de triplets reçu " + listeTriple.length );
                    List listeFait = new ArrayList();
                    for (String triple: listeTriple ) {
                        RDFTuple t= new RDFTuple();
                        t.addFields(triple.split("\t"));

                        System.out.println("Triple ==> "+ t.toString());

                        String[] tripleSplit = triple.split("\t");
                        String predicat = tripleSplit[1];
                        String[] suffixPredicat = predicat.split("#");
                        if (suffixPredicat.length>1 && ("hasSimpleResult".equals(suffixPredicat[1]) || "isObservedBy".equals(suffixPredicat[1]))){

                            String obj = tripleSplit[2].replace("^^http://www.w3.org/2001/XMLSchema#boolean","").replace("\"","");
                            listeFait.add(suffixPredicat[1]+"("+tripleSplit[0].replace(":","").replace("-","") +"," + obj +").");
                        }

                    }
                    /** Appel Clingo */
                    clingoCall(listeFait);
                } else {
                    block();
                }
            }
        });

    }

    private static Logger logger = LoggerFactory.getLogger(clingoAgent.class);

    static void clingoCall(List<String> listeFait){
        Clingo.init("D:\\clingo4j\\src\\main\\clingo");

        try (Clingo control = Clingo.create()) {
            System.out.println(control.getVersion());
            //control.add("base", "b :- not a. a :- not b.");
            //control.ground("base");
            String listeFaitString = "";
            for(String str: listeFait) {
                //System.out.println("fait : "+str);
                listeFaitString += str +"\n";
            }
//            control.add("base",listeFaitString);
            control.load("D:\\clingo4j\\src\\main\\clingo\\clingoRules.lp");
            control.add("base", "hasSimpleResult(mmmm,true). \nisObservedBy(mmmm,tv_sensor).");
            control.ground("base");

            try (SolveHandle handle = control.solve()) {
                for (Model model : handle)  {
                    System.out.println("Model type: " + model.getType());
                    for (Symbol atom : model.getSymbols()) {
                        /** Resultat */
                        System.out.println(atom);
                    }
                }
            }
        } catch (ClingoException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
