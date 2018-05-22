package de.uni_mannheim.informatik.dws.ontmatching.demomatcher;

import de.uni_mannheim.informatik.dws.ontmatching.yetanotheralignmentapi.Mapping;
import de.uni_mannheim.informatik.dws.ontmatching.matchingjena.MatcherJena;
import java.util.HashMap;
import java.util.Properties;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntResource;
import org.apache.jena.util.iterator.ExtendedIterator;


public class DemoMatcher extends MatcherJena{
    
    @Override
    public Mapping match(OntModel ont1, OntModel ont2, Mapping mapping, Properties p) {
        matchResources(ont1.listClasses(), ont2.listClasses(), mapping);
        matchResources(ont1.listDatatypeProperties(), ont2.listDatatypeProperties(), mapping);
        matchResources(ont1.listObjectProperties(), ont2.listObjectProperties(), mapping);
        return mapping;
    }
    
    public void matchResources(ExtendedIterator<? extends OntResource> resourceIterOnt1,ExtendedIterator<? extends OntResource> resourceIterOnt2, Mapping mapping) {
        HashMap<String, String> label2URI = new HashMap<>();
        while (resourceIterOnt1.hasNext()) {
            OntResource r = resourceIterOnt1.next();
            label2URI.put(r.getLocalName(), r.getURI());
            //label2URI.put(r.getLabel("en"), r.getURI());
        }
        while (resourceIterOnt2.hasNext()) {
            OntResource resourceOnto2 = resourceIterOnt2.next();
            String uriOnto1 = label2URI.get(resourceOnto2.getLocalName());//resourceOnto2.getLabel("en"));
            if(uriOnto1 != null){
                mapping.add(uriOnto1, resourceOnto2.getURI());
            }
        }
    }
}
