
//Since a lot of gene expresion data is in Soft format - which is delimited by tabs i want to read and then store them in an array.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class parser{
	private String filename;
	
	public parser(String filename) {
		this.filename= filename; 
	}
	//lets try to first print the gene and it's id out 
	public List<String[]> softparser() throws IOException {
		String line;//what we use to read the line in.
		List<String[]> data= new ArrayList<>();// this list stores a gene and it's expression vlaues.
		
		try {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while((line=br.readLine())!=null) {
			if(line.startsWith("^")|| line.startsWith("!") ) {
				continue;//we'll skip the meta data lines.
			}
			String[] entries = line.split("\t");//splits the entries that we get, since they are tab delimited.
			if(entries.length >=2) {//checks that the gene has expression data.
				data.add(entries);
			}	
		}
		br.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return data;
	}
}
