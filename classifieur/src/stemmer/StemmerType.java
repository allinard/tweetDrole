package stemmer;

import java.util.List;

public interface StemmerType {

    public List<String> getStems(List<String> inputList);
    
}
