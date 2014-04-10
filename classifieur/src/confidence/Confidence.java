package confidence;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Confidence {

	public static double PREDICT = 0.7;
	
	public static void main(String args[]) {
		try {
			List<String> lignes = Files.readAllLines(
					Paths.get("/home/alexis/Desktop/scripts4LINA5/scripts4LINA5/outputCorpusTweetWithStemmjRFDesequilibre.txt"),
					Charset.forName("UTF-8"));

			int truePredictedTrue = 0;
			int truePredictedFalse = 0;
			int falsePredictedTrue = 0;
			int falsePredictedFalse = 0;
			int total = 0;
			
			float predictionScore;

			for (String ligne : lignes) {
				ligne = ligne.replaceAll("\\s+", " ").trim();
				String[] elements = ligne.split(" ");

				System.out.println("--> "+total);
				
				if(elements[3].contains("+")){
					predictionScore = Float.valueOf(elements[4]);
				}
				else{
					predictionScore = Float.valueOf(elements[3]);
				}
				
				
				if (elements[1].contains("1:true")) {
					if(elements[2].contains("1:true")){
						if(predictionScore>PREDICT){
							truePredictedTrue++;
							total++;
						}
					} else if(elements[2].contains("2:false")){
						truePredictedFalse++;
						total++;
					}

				} else if (elements[1].contains("2:false")) {
					if(elements[2].contains("1:true")){
						if(predictionScore>PREDICT){
							falsePredictedTrue++;
							total++;
						}
					} else if(elements[2].contains("2:false")){
						falsePredictedFalse++;
						total++;
					}
				}

			}
			
			System.out.println("total "+ total);
			System.out.println("truePredictedTrue"+ truePredictedTrue);
			System.out.println("truePredictedFalse "+ truePredictedFalse);
			System.out.println("falsePredictedTrue "+ falsePredictedTrue);
			System.out.println("falsePredictedFalse "+ falsePredictedFalse);

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
