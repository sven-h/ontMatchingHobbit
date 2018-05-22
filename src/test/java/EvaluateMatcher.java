import de.uni_mannheim.informatik.dws.ontmatching.matchingeval.Evaluation;
import de.uni_mannheim.informatik.dws.ontmatching.matchingeval.benchmarks.BenchmarkRepository;
import de.uni_mannheim.informatik.dws.ontmatching.matchingeval.resultswriter.OnlyCsvWriter;
import de.uni_mannheim.informatik.dws.ontmatching.demomatcher.DemoMatcher;
import java.net.MalformedURLException;


public class EvaluateMatcher {
    
    public static void main(String[] args) throws MalformedURLException{
        Evaluation.setResultsWriter(new OnlyCsvWriter());
        Evaluation.run(BenchmarkRepository.Conference.V1, new DemoMatcher());
    }
    
    
}
