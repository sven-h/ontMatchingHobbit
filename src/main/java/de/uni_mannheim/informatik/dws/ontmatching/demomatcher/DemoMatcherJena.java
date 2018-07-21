package de.uni_mannheim.informatik.dws.ontmatching.demomatcher;

import de.uni_mannheim.informatik.dws.ontmatching.matchingbase.OaeiOptions;
import de.uni_mannheim.informatik.dws.ontmatching.yetanotheralignmentapi.Mapping;
import de.uni_mannheim.informatik.dws.ontmatching.matchingjena.MatcherJena;
import java.util.HashMap;
import java.util.Properties;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DemoMatcherJena extends MatcherJena{
    private static final Logger logger = LoggerFactory.getLogger(DemoMatcherJena.class);
    /*
    Accessing the resources:
    - all files in "src/main/oaei-resources" folder are stored in the current working directory and can be accessed with 
      Files.readAllLines(Paths.get("configuration_oaei.txt"));    
    - all files in "src/main/resources" folder are compiled to the resulting jat and can be accessed with
    getClass().getClassLoader().getResourceAsStream("configuration_jar.txt");
    Accessing :
    */
    
    @Override
    public Mapping match(OntModel ont1, OntModel ont2, Mapping mapping, Properties p) {
        logger.info("Start matching");
        
        if(OaeiOptions.isMatchingClassesRequired())
            matchResources(ont1.listClasses(), ont2.listClasses(), mapping);
        
        if(OaeiOptions.isMatchingDataPropertiesRequired())
            matchResources(ont1.listDatatypeProperties(), ont2.listDatatypeProperties(), mapping);
        
        if(OaeiOptions.isMatchingObjectPropertiesRequired())
            matchResources(ont1.listObjectProperties(), ont2.listObjectProperties(), mapping);
        
        if(OaeiOptions.isMatchingInstancesRequired())
            matchResources(ont1.listIndividuals(), ont2.listIndividuals(), mapping);
        
        logger.info("Finished matching");
        return mapping;
    }
    
    public void matchResources(ExtendedIterator<? extends OntResource> resourceIterOnt1,ExtendedIterator<? extends OntResource> resourceIterOnt2, Mapping mapping) {
        HashMap<String, String> label2URI = new HashMap<>();
        while (resourceIterOnt1.hasNext()) {
            OntResource r = resourceIterOnt1.next();
            label2URI.put(r.getLabel(null), r.getURI());
        }
        while (resourceIterOnt2.hasNext()) {
            OntResource resourceOnto2 = resourceIterOnt2.next();
            String uriOnto1 = label2URI.get(resourceOnto2.getLabel(null));
            if(uriOnto1 != null){
                mapping.add(uriOnto1, resourceOnto2.getURI());
            }
        }
    }
}
