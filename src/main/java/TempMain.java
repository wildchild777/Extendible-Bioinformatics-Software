// A temp class made to test the SoftParser

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempMain {
	public static void main(String args[]) {
		parser parser = new parser();
		List<String[]> temp = new ArrayList<>();
		try {
			temp = parser.softparser("src/main/resources/GDS4794_full.soft");
			for (int i = 0; i < 5; i++) {
                System.out.println(Arrays.toString(temp.get(i)));
            }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
