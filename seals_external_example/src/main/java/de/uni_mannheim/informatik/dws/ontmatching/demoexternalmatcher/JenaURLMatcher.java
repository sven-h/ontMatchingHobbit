package de.uni_mannheim.informatik.dws.ontmatching.demoexternalmatcher;

import de.uni_mannheim.informatik.dws.ontmatching.yetanotheralignmentapi.AlignmentSerializer;
import de.uni_mannheim.informatik.dws.ontmatching.yetanotheralignmentapi.Mapping;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JenaURLMatcher {
    private static final Logger logger = LoggerFactory.getLogger(JenaURLMatcher.class);
        
    
    public static String match(URL left_url, URL right_url, URL input){
        OntModel ont1 = readOntology(left_url);
        OntModel ont2 = readOntology(right_url);
        
        Mapping mapping = new Mapping();
        logger.info("Start matching");
        matchResourcesWithLabel(ont1.listClasses(), ont2.listClasses(), mapping);
        matchResourcesWithLabel(ont1.listDatatypeProperties(), ont2.listDatatypeProperties(), mapping);
        matchResourcesWithLabel(ont1.listObjectProperties(), ont2.listObjectProperties(), mapping);
        matchResourcesWithLabel(ont1.listIndividuals(), ont2.listIndividuals(), mapping);      
        logger.info("Finished matching");
        return AlignmentSerializer.serialize(mapping);
    }
    
    private static void matchResourcesWithLabel(ExtendedIterator<? extends OntResource> resourceIterOnt1,ExtendedIterator<? extends OntResource> resourceIterOnt2, Mapping mapping) {
        HashMap<String, String> label2URI = new HashMap<>();
        while (resourceIterOnt1.hasNext()) {
            OntResource r = resourceIterOnt1.next();
            if(r.getLabel(null) != null)
                label2URI.put(normalizeString(r.getLabel(null)), r.getURI());
        }
        while (resourceIterOnt2.hasNext()) {
            OntResource resourceOnto2 = resourceIterOnt2.next();
            if(resourceOnto2.getLabel(null) != null){
                String uriOnto1 = label2URI.get(normalizeString(resourceOnto2.getLabel(null)));
                if(uriOnto1 != null){
                    mapping.add(uriOnto1, resourceOnto2.getURI());
                }
            }
        }
    }
    
    private static OntModel readOntology(URL url){
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);    
        model.read(url.toString());
        return model;
    }
    
    
    private static String myFormat = String.format("%s|%s|%s", "(?<=[\\p{Lu}])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[\\p{Lu}])", "(?<=[A-Za-z])(?=[^\\p{L}])");
    private static List<String> replaceChars = Arrays.asList(",", ";", ":", "(", ")", "?", "!", ".", "_", "-");
    private static String normalizeString(String s){
        String ret = s;
        for(String replace : replaceChars){
            ret = ret.replace(replace, " ");
        }
        return ret.replaceAll(myFormat, " ").toLowerCase();
    }
    
}
