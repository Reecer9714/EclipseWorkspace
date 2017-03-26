package twitchbot;

import java.io.*;
import java.util.ArrayList;

public class SpamFilter{
    private ArrayList<String> spam = new ArrayList<String>();
    private ArrayList<String> ham = new ArrayList<String>();
    boolean atSpam = false;
    private int[] ratio = new int[2];
    
    public SpamFilter(String sample) throws IOException{
	BufferedReader br = new BufferedReader(new FileReader(sample));
	try {
	    String line = br.readLine();

	    while (line != null) {
		if(line.equals("%spam")){
		    atSpam = true;
		    continue;
		}
		if(atSpam)
		    spam.add(line);
		else ham.add(line);
	        line = br.readLine();
	    }
	}finally {
	    br.close();
	}
    }
    
    boolean testMessage(String msg){
	msg.length();
	return false;
    }
    
    public ArrayList<String> getSpam(){
	return spam;
    }
    public ArrayList<String> getHam(){
	return ham;
    }
    
    public static void main(String[] args){
	try{
	    SpamFilter sf = new SpamFilter(SpamFilter.class.getResource("/TwitchBot/src/resources/TwitchSpam.txt").getPath());
	    for(String s: sf.getHam()){
		System.out.println(s);
	    }
	    for(String s: sf.getSpam()){
		System.out.println(s);
	    }
	}
	catch(IOException e){
	    e.printStackTrace();
	}
    }
}
