package eu.larkc.csparql.eu.tsp.test;

import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.RDFTable;
import eu.larkc.csparql.common.RDFTuple;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import eu.larkc.csparql.core.engine.RDFStreamFormatter;
import eu.larkc.csparql.eu.tsp.test.streamer.CouchStreamer;
import eu.larkc.csparql.eu.tsp.test.streamer.TvStreamer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.apache.log4j.PropertyConfigurator;
import org.lorislab.clingo4j.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

public class tvAgent extends Agent {

    AID agentClingo;

    public void setup(){
        System.out.println("Hello World! My name is " + this.getLocalName());

        /** Chercher le service Clingo */
        addBehaviour(new jade.core.behaviours.OneShotBehaviour() {
             public void action() {
                 agentClingo = searchClingo();
             }
         });

        /** Lancer le traitement => Simulation Stream et execution requete*/
        addBehaviour(new jade.core.behaviours.OneShotBehaviour(){
            public void action(){
                startTraitement();
            }
        });


    }

    private static Logger logger = LoggerFactory.getLogger(tvAgent.class);

   /*
     * ECARE-HOME Reasoning example
     */

    //public static void main(String[] args) {
    public void startTraitement(){
        try{

           //Configure log4j logger for the csparql engine
            PropertyConfigurator.configure("log4j_configuration/csparql_readyToGoPack_log4j.properties");

           //Create csparql engine instance
            final CsparqlEngineImpl engine = new CsparqlEngineImpl();
           //Initialize the engine instance
           //The initialization creates the static engine (SPARQL) and the stream engine (CEP)
            engine.initialize();

            String streamCleanTv = "REGISTER STREAM cleantv AS " +
                    "PREFIX :<http://ecareathome.org/stream#> " +
                    "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                    "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> " +
                    "PREFIX dul:<http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#> " +
                    "PREFIX time:<http://w3id.org/ecareathome/patterns/timeinterval.owl#> " +
                    "PREFIX event:<http://w3id.org/ecareathome/patterns/event.owl#> " +
                    "PREFIX sosa:<http://www.w3.org/ns/sosa#> " +
                    "CONSTRUCT " +
                    "{ " +
                    "_:c0 rdf:type event:ComplexEvent . " +
                    "_:c0 dul:isObservableAt _:c1 . " +
                    "_:c0 sosa:isObservedBy ?sensor . " +
                    "_:c0 sosa:hasSimpleResult ?value . " +
                    "_:c1 rdf:type dul:TimeInterval . " +
                    "_:c1 time:hasUpperTimeStampValue ?maxTime . " +
                    "_:c1 time:hasLowerTimeStampValue ?minTime . " +
                    "} " +
                    "FROM STREAM <http://ecareathome.org/stream#tv> [ RANGE 10s STEP 10s] " +
                    "WHERE " +
                    "{ { SELECT ?sensor ( AVG (?value ) AS ?avg ) ( MAX (?upper ) AS ?maxTime ) " +
                    " ( MIN (?lower ) AS ?minTime ) " +
                    "WHERE { " +
                    "_:b0 sosa:isObservedBy ?sensor ; " +
                    "sosa:hasSimpleResult ?value ; " +
                    "dul:isObservableAt _:b1 . " +
                    "_:b1 time:hasUpperTimeStampValue ?upper ; " +
                    "time:hasLowerTimeStampValue ?lower . " +
                    "} " +
                    "GROUP BY ?sensor " +
                    "} " +
                    "BIND (if (( ?avg <20 ) , false , true ) AS ?value ) " +
                    "BIND ( now() AS ?time ) " +
                    "FILTER bound (?sensor ) " +
                    "}";

            TvStreamer streamTV = new TvStreamer("http://ecareathome.org/stream#tv", "", 1000L);

            //Register new streams in the engine
            engine.registerStream(streamTV);

            Thread TvThread = new Thread(streamTV);

            CsparqlQueryResultProxy streamQ2 = engine.registerQuery(streamCleanTv, false);

            //Attach a result consumer to the query result proxy to generate a new streams
            RdfStream cleanTv = new RDFStreamFormatter("http://ecareathome.org/stream#cleantv");

            //streamQ1.addObserver(new ConsoleFormatter());
            //streamQ2.addObserver((RDFStreamFormatter) cleanTv);

            CustomFormatter3 co = new CustomFormatter3(agentClingo);
            streamQ2.addObserver((CustomFormatter3) co);

            //engine.registerStream(cleanTv);

            //Start streaming data
            TvThread.start();


        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    public AID searchClingo(){
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType( "clingo-service" );
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = new DFAgentDescription[0];
            result = DFService.search(this, dfd);

            System.out.println(result.length + " results recherche Clingo" );
            if (result.length>0) {
                System.out.println(" " + result[0].getName());
                return result[0].getName();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class CustomFormatter3 extends Observable implements Observer {
        AID currentAgent = null;
        public CustomFormatter3(AID agent) {
            super();
            this.currentAgent = agent;
        }

        public void update(Observable o, Object arg) {
            RDFTable q = (RDFTable)arg;
            System.out.println();
            System.out.println("-------" + q.toString() +"\n" + q.size() + " results at SystemTime=[" + System.currentTimeMillis() + "]--------");
            Iterator var4 = q.iterator();

            ACLMessage message = new ACLMessage(ACLMessage.INFORM);
            message.addReceiver(currentAgent);
            message.setContent(q.toString());
            send(message);

            while(var4.hasNext()) {
                RDFTuple t = (RDFTuple)var4.next();
                //System.out.println(t.toString().replaceAll("\t", ",").substring(0, t.toString().length()-1));
                //this.put(t.toString().replaceAll("\t", ",").substring(0, t.toString().length()-1));
                //System.out.println("agentClingo" + currentAgent.getLocalName() + "\n" + t.toString());

            }

            System.out.println();
        }
    }
}
