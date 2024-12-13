// A temp class made to test the SoftParser

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempMain {
	public static void main(String args[]) {
		parser parser = new parser();
		List<Entry> temp = new ArrayList<>();
		EucledianDistance length = new EucledianDistance();
		
		
		
		try {
			temp = parser.softparser("src/main/resources/TestFile.soft");
			
			KmeansClustering.fit(temp,3,length,3);
			for (Entry row : temp) {
                System.out.println(row);
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

