package net.oemig.scta.tracer.jfreechart.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//practically speaking: this is a trace
public class SCTADatasetSeries {
	
	//the trace name
	private String name;
	
	//the sessions
	private List<SCTADatasetItem> items;
	
	public SCTADatasetSeries(String newName){
		this.items=new ArrayList<SCTADatasetItem>();
		this.name=newName;
	}
	
	public void add(SCTADatasetItem item){
		this.items.add(item);
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public SCTADatasetItem[] getItems() {
		return items.toArray(new SCTADatasetItem[items.size()]);
	}
	public void setItems(SCTADatasetItem[] items) {
		this.items = Arrays.asList(items);
	}

}
