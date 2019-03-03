package hacking.terminal;

public class StringStream{
	
	public String stream;
	
	public StringStream(){
		stream = "";
	}
	
	public void add(String s){
		stream.concat(s);
	}
	
	public void add(char c){
		stream += c;
	}
	
	public String flush(){
		String r = stream;
		stream = "";
		return r;
	}
	
	public static void main(String[] args){
		StringStream ss = new StringStream();
		
		ss.add('T');
		ss.add('e');
		ss.add('s');
		ss.add('t');
		ss.add('\n');
		ss.add('L');
		ss.add('i');
		ss.add('n');
		ss.add('e');
		
		String out = ss.flush();
		System.out.print(out);
	}

	public void remove(){
		stream = stream.substring(0,stream.length()-1);
	}
	
}
