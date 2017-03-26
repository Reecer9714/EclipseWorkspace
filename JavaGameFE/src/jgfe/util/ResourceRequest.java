package jgfe.util;

public class ResourceRequest{
    public String name;
    public String file;
    private int resourceType;
    
    public ResourceRequest(String name, String file, int resourceType){
	this.name = name;
	this.file = file;
	this.resourceType = resourceType;
    }
    
    public int getType(){
	return resourceType;
    }
}
