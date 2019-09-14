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
import java.util.concurrent.CompletableFuture;

public class ClingoService {


    private static Logger logger = LoggerFactory.getLogger(ClingoService.class);

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
            control.add("base",listeFaitString);
            control.load("D:\\CSPARQL-ReadyToGoPack\\clingo\\clingoRules.lp");
            //control.add("base", "hasSimpleResult(mmmm,true). \nisObservedBy(mmmm,tv_sensor).");
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

    public void rdfToList(String resAggrega){

        String[] listeTriple = resAggrega.split("\n");
        String[] predicatExcluded= {"hasUpperTimeStampValue","hasLowerTimeStampValue"};
        List<String> predicatToKeep= Arrays.asList("hasSimpleResult", "isObservedBy","hasLocation","madeBySensor");

        //System.out.println("nombre de triplets reçu " + listeTriple.length );
        List listeFait = new ArrayList();
        for (String triple: listeTriple ) {
            RDFTuple t= new RDFTuple();
            t.addFields(triple.split("\t"));

            //System.out.println("Triple ==> "+ t.toString());

            String[] tripleSplit = triple.split("\t");
            String predicat = tripleSplit[1];
            String[] suffixPredicat = predicat.replace("#","/").split("/");
            if (suffixPredicat.length>1 && predicatToKeep.indexOf(suffixPredicat[suffixPredicat.length-1]) != -1){

                String obj = tripleSplit[2].replace("^^http://www.w3.org/2001/XMLSchema#boolean","").replace("^^http://www.w3.org/2001/XMLSchema#decimal","").replace("\"","");
                obj = obj.substring(0, obj.indexOf(".") != -1 ? obj.indexOf(".") : obj.length());
                if(suffixPredicat[suffixPredicat.length-1].equals("isObservedBy") || suffixPredicat[suffixPredicat.length-1].equals("hasLocation")){
                    listeFait.add(suffixPredicat[suffixPredicat.length-1]+"(\""+tripleSplit[0].concat("\"") +",\"" + obj +"\").");
                }
                else {
                    listeFait.add(suffixPredicat[suffixPredicat.length-1]+"(\""+tripleSplit[0].concat("\"") +"," + obj +").");
                }
            }
        }
        CompletableFuture.runAsync(() -> {
            long startTime=System.currentTimeMillis();
            logger.info("Clingo Start traitement {}", startTime);
            logger.info("nb de fait {}", listeFait.size());
            clingoCall(listeFait);
            long endTime=System.currentTimeMillis();
            logger.info("Clingo End traitement {}", endTime);
            logger.info("duration traitement {}", (endTime-startTime));

        });
    }

}
