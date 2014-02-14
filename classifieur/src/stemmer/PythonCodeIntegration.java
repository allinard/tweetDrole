package stemmer;

import java.util.ArrayList;
import java.util.List;

public class PythonCodeIntegration {

	public static void main(String[] args) {
		List<String> liste = new ArrayList<String>();
		liste.add("mangeront");
		liste.add("mangeras");
		liste.add("mangeait");
		liste.add("mangue");
		liste.add("trouver");
		liste.add("trouvaille");
		liste.add("trouvailles");
		liste.add("trouveras");
		liste.add("trouverait");
		liste.add("trouvait");


		// Create factory and coerce Jython calculator object
        JythonObjectFactory factory = JythonObjectFactory.getInstance();
        StemmerType costCalc = (StemmerType)
            factory.createObject(StemmerType.class, "stemmer");
        System.out.println(costCalc.getStems(liste));
	}

}
