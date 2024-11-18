
//Since a lot of gene expresion data is in Soft format - which is delimited by tabs i want to read and then store them in an array.

import java.io.BufferedReader;
import java.io.FileReader;

class parser{
	private String filename;
	
	public parser(String filename) {
		this.filename= filename; 
	}
	//lets try to first print the gene and it's id out 
	public String softparser() {
	BufferedReader br = new BufferedReader(new FileReader(file));
	}
}
