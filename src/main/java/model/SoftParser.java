//Since a lot of gene expresion data is in Soft format - which is delimited by tabs i want to read and then store them in an array.
package model;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class SoftParser implements ParserStrategy{
	private String line;//what we use to read the line in.
	private String header;//to get the first line in.
	private int count =0;
	private List<String[]> data= new ArrayList<>();// this list stores a gene and it's expression vlaues.
	private List<Entry> entries = new ArrayList<>();//possibly change it here - so that we make a list of ent
	int invalidValueCount = 0;
	public ParsedData parse(File filename) {
		 
		if(!filename.getName().endsWith(".soft")) {throw new IllegalArgumentException("Invalid file type: Expected a .soft file");}
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
             
			 for (int i = 2; i < numberOfSamples; i++) {
				    String expr = entries[i].trim();
				    try {
				        double expressionValue = Double.parseDouble(expr);
				        sampleData.get(i - 2).put(gene, expressionValue);
				    } catch (NumberFormatException e) {//if we get a null value we add it to the invalid count and print it out 
				        invalidValueCount++;
				        System.err.println("Skipping invalid value: '" + expr + "' for gene " + gene + " in sample " + header_info.get(i - 2));
				    }
				}
		}
		//this stores all the lines of the file -> we want to go until the max column and any amount of rows 
		//so for each sample(i -> loop)
		//make a hashmap
		//then loop through the entries array 
		//put entries(1)[gene name] and 
		
		int max = Collections.max(header_info.keySet());//has mapping from int -> gsm sample, and we need the int for the largest one
		
		
		for (int i = 0; i < count; i++) {
		    String GSMsample = header_info.get(i);
		    Map<String, Double> geneData = sampleData.get(i);
		    if (!geneData.isEmpty()) {//this is to catch null entries
		        entries.add(new Entry(GSMsample, geneData));
		    } else {
		        System.err.println("Skipping empty sample: " + GSMsample);
		    }
		}
		
		br.close();
		} catch (FileNotFoundException e) {
		System.out.println("File not found" + filename.getName());
		e.printStackTrace();
	}catch( IOException e) {
		System.out.println("Something went wrong");
		e.printStackTrace();
	}
		//the only addition so we can use ParsedData across all our methods, which makes it cleaner.
		GeneExpressionParsedData parsedData = new GeneExpressionParsedData();
		for (Entry e : entries) {
		    parsedData.add(e);
		}
		return parsedData; 
	}
}
