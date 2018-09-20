package de.uni_mannheim.informatik.dws.ontmatching.demoexternalmatcher;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) throws MalformedURLException, IOException{
        switch (args.length) {
            case 2:
                System.out.println(matchURL(new URL(args[0]), new URL(args[1]), null));
                break;
            case 3:
                System.out.println(matchURL(new URL(args[0]), new URL(args[1]), new URL(args[2])));
                break;
            default:
                System.err.println("Did not get 2 or 3 input URLs.");
                break;
        }
    }
    
    /* This method should return an URL representing the alignment file */
    private static URL matchURL(URL left, URL right, URL inputAlignment) throws IOException{
        return getUrlOfTempFileWithContent(matchString(left, right, inputAlignment));
    }
    
    /* This method should return the serialized alignment as a string.*/
    private static String matchString(URL left, URL right, URL inputAlignment){
        return JenaURLMatcher.match(left, right, inputAlignment);
    }
    
    
    public static URL getUrlOfTempFileWithContent(String content) throws IOException{
        File alignmentFile = File.createTempFile("alignment", ".rdf");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(alignmentFile))) {
            out.write(content);
        }
        return alignmentFile.toURI().toURL();
    }
}
