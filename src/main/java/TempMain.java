// A temp class made to test the SoftParser

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempMain {
	public static void main(String args[]) {
		parser parser = new parser();
		List<Entry> temp = new ArrayList<>();
		try {
			temp = parser.softparser("src/main/resources/GDS3310_full.soft");
			
			for (Entry row : temp) {
                System.out.println(row);
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

