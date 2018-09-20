package de.uni_mannheim.informatik.dws.ontmatching.demomatcher;

import de.uni_mannheim.informatik.dws.ontmatching.matchingbase.MatcherURL;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoMatcherURL extends MatcherURL{
    private static final Logger logger = LoggerFactory.getLogger(DemoMatcherURL.class);
    
    @Override
    public URL match(URL source, URL target, URL inputAlignment) throws Exception {
        //TODO: read the source and target URL and produce an alignment in alignment format ( http://alignapi.gforge.inria.fr/format.html )
        return null;        
    }
    
}
