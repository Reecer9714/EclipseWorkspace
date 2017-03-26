package jgfe.util;

public abstract class Resource{
    public String name;
    
    public abstract Resource load(String path);
}
