package hacking.files;

import hacking.Game;
import hacking.files.programs.Tedit;
import hacking.files.programs.TestPro;

public class FileSystem{
	private Folder home;
	private Folder programs;

	private TextFile log;
	
	private Game game;
	private String driveName;
	private Folder parentDir;
	
	public FileSystem(Game g, String driveName){
		this.game = g;
		this.driveName = driveName+":";
		parentDir = new Folder(g, driveName);
		home = new Folder(g, "Home");
		programs = new Folder(g, "Programs");
		Folder testFolder = new Folder(g, "Test");
		home.addFolder(testFolder);
		log = new TextFile(g, "log");
		TextFile stuff = new TextFile(g, "stuff");
		TextFile testFile = new TextFile(g, "test");
		parentDir.addFolder(home).addFile(testFile);
		parentDir.addFile(log);
		
		home.addFolder(programs).addFile(new Tedit(g));
		programs.addFile(new TestPro(g));
		home.addFolder("Documents").addFile(stuff);
	}
	
	public File getFileFromPath(Folder start, String path){
		String[] split = path.split("\\\\|/");
		
		if(split.length <= 0) return null;
		if(split[0].equals(driveName)) start = parentDir;
		Folder cur = start;
		
		for(String s : split){
			s = s.split("\\.",2)[0];
			if(cur.hasFile(s)){
				File next = cur.getFile(s);
				if(next instanceof Folder){
					cur = (Folder)next;
				}else{
					return next;
				}
			}else{
				return null;
			}
		}
		
		if(cur.equals(start)) return null;
		return cur;
	}

	public TextFile getLog(){
		return log;
	}

	public Folder getHome(){
		return home;
	}
	
	public Folder getPrograms(){
		return programs;
	}

	public String getDriveName(){
		return driveName;
	}

	public Folder getParentDir(){
		return parentDir;
	}
}
