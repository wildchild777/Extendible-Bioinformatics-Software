
//Since a lot of gene expresion data is in Soft format - which is delimited by tabs i want to read and then store them in an array.

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class SoftParser implements ParserStrategy{
	private String line;//what we use to read the line in.
	private String header;//to get the first line in.
	private int count =0;
	private List<String[]> data= new ArrayList<>();// this list stores a gene and it's expression vlaues.
	private List<Entry> entries = new ArrayList<>();
	
	public List<Entry> parse(String filename) {
		try {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		while((header = br.readLine())!= null) {
			if(!header.startsWith("^") && !header.startsWith("!") && !header.startsWith("#") ) {
				break;//we'll skip the meta data lines.
			}
		}	
		 String[] columns= header.split("\t");//splits the entries that we get, since they are tab delimited.
		
		 for(String temp : columns) {
			 if(temp.startsWith("GSM")) {count++;}//only has the column names with the GSM samples
		 }
		int numberOfSamples = count +2;//takes in first and second column - for ID_prope and gene name
		
		Map<Integer, String> header_info= new HashMap<>();
		for(int i =2; i<numberOfSamples;i++) {//this is to get only the gsm sample names
			header_info.put(i-2, columns[i]);//this works
		}
		//now we have the int -> header spec
		//we want to have a gene -> expression for all of these header values
		// the list of gene -> expression will be our entry and we want to do it for all the 
		
		//for each sample we make a hashmap
		//iterate through that hashmap put column number 1()gene name -> the expression value.
		// and then go to the next line 
		 
		List<Map<String, Double>> sampleData = new ArrayList<>(count);//for the number of gsm samples 
         for (int i = 0; i <count; i++) {//we dont need to since count has skipped alreday
             sampleData.add(new HashMap<>());
         }//this creates gsm number of hashmap for us 
         
         while((line=br.readLine())!=null) {
			if(line.startsWith("^")|| line.startsWith("!") ||line.startsWith("#") ) {
				continue;//we'll skip the meta data lines.
			}
			String[] entries = line.split("\t");//splits the entries that we get, since they are tab delimited.
			 String gene = entries[1]; // Gene name (second column)
             for (int i = 2; i < numberOfSamples; i++) { // Start from the third column
                 double expressionValue = Double.parseDouble(entries[i]);
                 sampleData.get(i - 2).put(gene, expressionValue);
             }
		}
		//this stores all the lines of the file -> we want to go until the max column and any amount of rows 
		//so for each sample(i -> loop)
		//make a hashmap
		//then loop through the entries array 
		//put entries(1)[gene name] and 
		
		int max = Collections.max(header_info.keySet());//has mapping from int -> gsm sample, and we need the int for the largest one
		
		for(int i=0; i<count;i++){//go through all the GSM samples
			String GSMsample = header_info.get(i);
			Map<String,Double> geneData = sampleData.get(i);
			entries.add(new Entry(GSMsample,geneData));
		}
		
		/*
		while((line=br.readLine())!=null) {
			if(line.startsWith("^")|| line.startsWith("!") ||line.startsWith("#") ) {
				continue;//we'll skip the meta data lines.
			}
			String[] entries = line.split("\t");//splits the entries that we get, since they are tab delimited.
			
			
			
			
			
			String[] geneData = new String[numberOfSamples];
		    for (int i = 0; i < numberOfSamples; i++) {
		        geneData[i] = entries[i]; // Copy only necessary columns
		    }
		    data.add(geneData); // Add the parsed row to the data list
			}*/
		
		br.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch( IOException f) {
		f.printStackTrace();
	}
	return entries;
	}
}
