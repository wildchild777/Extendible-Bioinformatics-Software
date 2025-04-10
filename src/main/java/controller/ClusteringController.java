package controller;
import java.util.List;

import model.*;

public class ClusteringController {
	
	private ParserContext parsingContext;
	private ClusterContext clusterContext;
	
	public ClusteringController(ParserContext parsingContext,ClusterContext clusterContext) {
		this.parsingContext = parsingContext;
		this.clusterContext = clusterContext;
	}
	
	//later we have to change this from List<Entry> to ParsedData
	private ParsedData loadData(String filename) {
		if(filename.endsWith(".soft")) {
			parsingContext.setParser(new SoftParser());
		}
		else {
			throw new IllegalArgumentException("Unsupported file type");
		}
		
		return parsingContext.executeParse(filename);
	}
	
	

}
