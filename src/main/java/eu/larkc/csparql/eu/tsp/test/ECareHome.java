package eu.larkc.csparql.eu.tsp.test;

import eu.larkc.csparql.cep.api.RdfStream;
import eu.larkc.csparql.common.utils.CsparqlUtils;
import eu.larkc.csparql.common.utils.ReasonerChainingType;
import eu.larkc.csparql.core.engine.ConsoleFormatter;
import eu.larkc.csparql.core.engine.CsparqlEngineImpl;
import eu.larkc.csparql.core.engine.CsparqlQueryResultProxy;
import eu.larkc.csparql.core.engine.RDFStreamFormatter;
import eu.larkc.csparql.eu.tsp.test.streamer.AdditionStreamer;
import eu.larkc.csparql.eu.tsp.test.streamer.CouchStreamer;
import eu.larkc.csparql.eu.tsp.test.streamer.TvStreamer;
import eu.larkc.csparql.sr4ld2014.SR4LD2014_Ex6;
import eu.larkc.csparql.sr4ld2014.streamer.FacebookStreamer;
import eu.larkc.csparql.sr4ld2014.streamer.SensorsStreamer;
import org.apache.log4j.PropertyConfigurator;
import org.lorislab.clingo4j.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ECareHome {
    private static Logger logger = LoggerFactory.getLogger(ECareHome.class);

   /*
     * ECARE-HOME Reasoning example
     */

    public static void main(String[] args) {

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
                    "PREFIX sosa: <http://www.w3.org/ns/sosa/> " +
                    "CONSTRUCT " +
                    "{ " +
                    "_:c0 rdf:type event:ComplexEvent . " +
                    "_:c0 dul:isObservableAt _:c1 . " +
                    "_:c0 sosa:isObservedBy ?sensor . " +
                    "_:c0 sosa:hasSimpleResult ?value . " +
                    "_:c0 sosa:madeBySensor ?sensorLibelle ." +
                    "_:c0 dul:hasLocation ?location ." +
                    "_:c1 rdf:type dul:TimeInterval . " +
                    "_:c1 time:hasUpperTimeStampValue ?maxTime . " +
                    "_:c1 time:hasLowerTimeStampValue ?minTime . " +
                    "} " +
                    "FROM STREAM <http://ecareathome.org/stream#tv> [ RANGE 10s STEP 10s] " +
                    //"FROM <http://www.w3.org/ns/sosa> " +
                    "WHERE " +
                    "{ { SELECT ?sensor ?sensorLibelle ?location ( AVG (?value ) AS ?avg ) ( MAX (?upper ) AS ?maxTime ) " +
                    " ( MIN (?lower ) AS ?minTime ) " +
                    "WHERE { " +
                    "_:b0 sosa:isObservedBy ?sensor ; " +
                    "sosa:hasSimpleResult ?value ; " +
                    "sosa:madeBySensor ?sensorLibelle ; " +
                    "dul:hasLocation ?location ; " +
                    "dul:isObservableAt _:b1 . " +
                    "_:b1 time:hasUpperTimeStampValue ?upper ; " +
                    "time:hasLowerTimeStampValue ?lower . " +
                    "} " +
                    "GROUP BY ?sensor  ?sensorLibelle ?location" +
                    "} " +
                    //"BIND (if (( ?avg <20 ) , false , true ) AS ?value ) " +
                    "BIND ( ?avg AS ?value ) " +
                    "BIND ( now() AS ?time ) " +
                    "FILTER bound (?sensor ) " +
                    "}";

            String streamCleanCouch = "REGISTER STREAM cleancouch AS " +
                    "PREFIX :<http://ecareathome.org/stream#> " +
                    "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                    "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> " +
                    "PREFIX dul:<http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#> " +
                    "PREFIX time:<http://w3id.org/ecareathome/patterns/timeinterval.owl#> " +
                    "PREFIX event:<http://w3id.org/ecareathome/patterns/event.owl#> " +
                    "PREFIX sosa:<http://www.w3.org/ns/sosa/> " +
                    "CONSTRUCT " +
                    "{ " +
                    "_:c0 rdf:type event:ComplexEvent . " +
                    "_:c0 dul:isObservableAt _:c1 . " +
                    "_:c0 sosa:isObservedBy ?sensor . " +
                    "_:c0 sosa:hasSimpleResult ?value . " +
                    "_:c0 sosa:madeBySensor ?sensorLibelle ." +
                    "_:c0 dul:hasLocation ?location ." +
                    "_:c1 rdf:type dul:TimeInterval . " +
                    "_:c1 time:hasUpperTimeStampValue ?maxTime . " +
                    "_:c1 time:hasLowerTimeStampValue ?minTime . " +
                    "} " +
                    "FROM STREAM <http://ecareathome.org/stream#couch> [ RANGE 10s STEP 10s] " +
                    //"FROM <http://www.w3.org/ns/sosa> " +
                    "WHERE " +
                    "{ { SELECT ?sensor ?sensorLibelle ?location ( MAX (?upper ) AS ?maxTime ) ( MIN (?lower ) AS ?minTime ) " +
                    "WHERE " +
                    "{ " +
                    "_:b0 sosa:isObservedBy ?sensor ; " +
                    "sosa:madeBySensor ?sensorLibelle ; " +
                    "dul:hasLocation ?location ; " +
                    "dul:isObservableAt _:b1 . " +
                    "_:b1 time:hasUpperTimeStampValue ?upper ; " +
                    "time:hasLowerTimeStampValue ?lower . " +
                    "} " +
                    "GROUP BY ?sensor ?sensorLibelle ?location" +
                    "} " +
                    "{ SELECT ?sensor ( COUNT(?sensor ) AS ?isTrue ) " +
                    "WHERE " +
                    "{ " +
                    "_:b2 sosa:isObservedBy ?sensor ; " +
                    "sosa:hasSimpleResult true . " +
                    "} " +
                    "GROUP BY ?sensor ?sensorLibelle ?location" +
                    "} " +
                    "{ SELECT ?sensor ( COUNT(?sensor ) AS ?isFalse ) " +
                    "WHERE " +
                    "{ " +
                    "_:b3 sosa:isObservedBy ?sensor ; " +
                    "sosa:hasSimpleResult false . " +
                    "} " +
                    "GROUP BY ?sensor  ?sensorLibelle ?location" +
                    "} " +
                    "BIND (( ?isTrue/( ?isTrue + ?isFalse ) ) AS ?avg ) " +
                    //"BIND (( ?isTrue/( ?isTrue + ?isFalse ) ) AS ?avg ) " +
                    //"BIND (if (( ?avg >= 0.9 ) , true , if (( ?avg <0.1 ) , false , ?avg )) AS " +
                    //"?value ) " +
                    /** si value == 100, cela veut dire que la valeur est true */
                    "BIND ( ?avg * 100 AS ?value)"+
                    "BIND ( now() AS ?time ) " +
                    "} ";


            String streamPartitionedOld = "REGISTER STREAM partitioned AS " +
                    "PREFIX :<http://ecareathome.org/stream#> " +
                    "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> " +
                    "PREFIX event:<http://w3id.org/ecareathome/patterns/event.owl#> " +
                    "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                    "PREFIX sosa:<http://www.w3.org/ns/sosa/> " +
                    "CONSTRUCT " +
                    "{ " +
                    "?subject ?predicate ?object . " +
                    "} " +
                    "FROM STREAM <http://ecareathome.org/stream#cleantv> [ RANGE 10s STEP 10s] " +
                    "FROM STREAM <http://ecareathome.org/stream#cleancouch> [ RANGE 10s STEP 10s] " +
                    "WHERE " +
                    "{ ?event rdf:type event:ComplexEvent . " +
                    "?event (! <>) * ?subject . " +
                    "?subject ?predicate ?object " +
                    "}";

            String streamPartitioned = "REGISTER STREAM partitioned AS " +
                    "PREFIX :<http://ecareathome.org/stream#> " +
                    "PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
                    "PREFIX xsd:<http://www.w3.org/2001/XMLSchema#> " +
                    "PREFIX dul:<http://www.ontologydesignpatterns.org/ont/dul/DUL.owl#> " +
                    "PREFIX time:<http://w3id.org/ecareathome/patterns/timeinterval.owl#> " +
                    "PREFIX event:<http://w3id.org/ecareathome/patterns/event.owl#> " +
                    "PREFIX sosa:<http://www.w3.org/ns/sosa/> " +
                    "CONSTRUCT " +
                    "{ " +
                    "?subject ?predicate ?object . " +
                    "} " +
                    "FROM STREAM <http://ecareathome.org/stream#cleantv> [ RANGE 10s STEP 10s] " +
                    "FROM STREAM <http://ecareathome.org/stream#cleancouch> [ RANGE 10s STEP 10s] " +
                    "WHERE " +
                    "{ ?event rdf:type event:ComplexEvent . " +
                    "?event (! <>) * ?subject . " +
                    "?subject ?predicate ?object " +
                    "}";

            CouchStreamer streamCouch = new CouchStreamer("http://ecareathome.org/stream#couch", "", 500L);
            TvStreamer streamTV = new TvStreamer("http://ecareathome.org/stream#tv", "", 500L);

            //Register new streams in the engine
            engine.registerStream(streamCouch);
            engine.registerStream(streamTV);

            Thread couchThread = new Thread(streamCouch);
            Thread TvThread = new Thread(streamTV);

            CsparqlQueryResultProxy streamQ1 = engine.registerQuery(streamCleanCouch, false);
            CsparqlQueryResultProxy streamQ2 = engine.registerQuery(streamCleanTv, false);

            //Attach a result consumer to the query result proxy to generate a new streams
            RdfStream cleanCouch = new RDFStreamFormatter("http://ecareathome.org/stream#cleancouch");
            RdfStream cleanTv = new RDFStreamFormatter("http://ecareathome.org/stream#cleantv");

            //streamQ1.addObserver(new ConsoleFormatter());
            streamQ1.addObserver((RDFStreamFormatter) cleanCouch);
            streamQ2.addObserver((RDFStreamFormatter) cleanTv);


            //streamQ1.addObserver(new ConsoleFormatter());
            //streamQ2.addObserver(new ConsoleFormatter());

            engine.registerStream(cleanCouch);
            engine.registerStream(cleanTv);

            //Start streaming data
            couchThread.start();
            TvThread.start();

            //Register new query in the engine
            CsparqlQueryResultProxy streamPartition = engine.registerQuery(streamPartitioned, false);

            streamPartition.addObserver(new CustomFormatter());
            //streamPartition.addObserver(new ConsoleFormatter());
            //clingoCall();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    static void clingoCall(){
        Clingo.init("D:\\clingo4j\\src\\main\\clingo");

        try (Clingo control = Clingo.create()) {
            System.out.println(control.getVersion());
            //control.add("base", "b :- not a. a :- not b.");
            //control.ground("base");
            control.load("D:\\clingo4j\\src\\main\\clingo\\clingoRules.lp");
            control.ground("base");

            try (SolveHandle handle = control.solve()) {
                for (Model model : handle)  {
                    System.out.println("Model type: " + model.getType());
                    for (Symbol atom : model.getSymbols()) {
                        System.out.println(atom);
                    }
                }
            }
        } catch (ClingoException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
