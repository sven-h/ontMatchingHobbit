package de.uni_mannheim.informatik.dws.ontmatching.demomatcher;

import de.uni_mannheim.informatik.dws.ontmatching.matchingbase.MatcherString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoMatcherString extends MatcherString{
    private static final Logger logger = LoggerFactory.getLogger(DemoMatcherString.class);
    
    @Override
    public String match(String source, String target, String inputAlignment) throws Exception {
        //TODO: read the source and target (usually in rdf/xml) and produce an alignment in alignment format ( http://alignapi.gforge.inria.fr/format.html )
        return "";
    }
    
}
