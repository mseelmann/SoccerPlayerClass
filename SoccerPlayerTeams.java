import java.util.*;
import java.io.*;
class SoccerPlayerTeams {
    public static void main(String[] args) throws IOException {
        
    File f = new File(args[0]); //data.csv
    ArrayList<SoccerPlayer> players = data(f);

    ArrayList<SoccerPlayer> teamPlayers = tPlayers(players,"arsenal");
    System.out.println(teamPlayers);

    }
    //creates an ArrayList of SoccerPlayer objects
    public static ArrayList<SoccerPlayer> data(File f) throws IOException {
        ArrayList<SoccerPlayer> players = new ArrayList<>();
        Scanner in = new Scanner(f);
        String header = in.nextLine();
        while(in.hasNextLine()) {
            String line = in.nextLine();
            String[] data = line.split(",");
            int j = Integer.parseInt(data[2]);
            boolean b = Boolean.parseBoolean(data[5]);
            SoccerPlayer r = new SoccerPlayer(new Name(data[0],data[1]),j,data[3],data[4],b);
            System.out.println(r);
            players.add(r);
        }
        return players;
    }
    //collects players from a team by ascending jersey number
    public static ArrayList<SoccerPlayer> tPlayers(ArrayList<SoccerPlayer> p,String t) {
        ArrayList<SoccerPlayer> s = new ArrayList<>();
        for(int i=0; i<p.size(); i++) {
            if(p.get(i).getTeam().toLowerCase().equals(t.toLowerCase())) {
                s.add(p.get(i));
            }
        }
        for(int i=0; i<s.size(); i++) {
			int min = i;
			for(int j=i+1; j<s.size(); j++) {
				if(s.get(j).getJersey() < s.get(min).getJersey()) {
					min = j;
				}
			}
			SoccerPlayer temp = s.get(i); 
			s.set(i,s.get(min));
			s.set(min,temp);
		}
        return s;
    }
}
class SoccerPlayer {
    
    private Name name;
    private int jersey;
    private String position;
    private String team;
    private int id;
    private boolean captain;

    private static int createID = 12345;

    public ArrayList<String> teams;

    public SoccerPlayer(Name n,int j,String p,String t,boolean c) {
        if(teams == null) {
            try {
                teams = teamsList();
            }
            catch(IOException e) {

            }
        }
        name=n;
        jersey=checkJersey(j);
        position=checkPosition(p);   
        team=checkTeam(t,teams);
        captain=c;
        id=createID;
        createID++;
    }
    public SoccerPlayer() {
        this(new Name(),99,"keeper","arsenal",false);
    }
    
    public ArrayList<String> teamsList() throws IOException {
        ArrayList<String> teams = new ArrayList<>();
        Scanner in = new Scanner(new File("Teams.txt"));
        while(in.hasNextLine()) {
            String team=in.next();
            teams.add(team);
        }
        return teams;
    }
    //check methods
    public int checkJersey(int j) {
        if(j<1||j>=100) {
            error("Jersey numbers must be in the interval [1,100).");
            return -1;
        }
        return j;
    }
    public String checkPosition(String p) {
        p = p.toLowerCase();
        if(!(p.equals("keeper")||p.equals("defender")||p.equals("midfielder")||p.equals("attacker"))) {
            error("Valid positions are keeper, defender, midfielder, and attacker.");
        }
        return Character.toUpperCase(p.charAt(0))+p.substring(1,p.length()).toLowerCase();
    } 
    public String checkTeam(String t,ArrayList<String> teams) {
        t = t.toLowerCase();
        for(int i=0; i<teams.size(); i++) {
            if(t.equals(teams.get(i).toLowerCase())) {
                return Character.toUpperCase(t.charAt(0))+t.substring(1,t.length()).toLowerCase();
            }
        }
        error("Refer to the list of valid team names.");
        return "";
    } 
    //error method that displays message describing what went wrong and terminates the program
    public void error(String s) {
        System.out.println("Your input is not valid. "+s);
        System.exit(0);
    }
    //get and set methods
    public Name getName() {
        return name;
    }
    public void setName(String f,String l) {
        name=new Name(f,l);
    }
    public int getJersey() {
        return jersey;
    }
    public void setJersey(int j) {
        checkJersey(j);
        jersey=j;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String p) {
        checkPosition(p);
        position=p;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String t) {
        t=checkTeam(t,teams);
        team=t;
    } 
    public boolean getCaptain() {
        return captain;
    }
    public void setCaptain(boolean c) {
        captain=c;
    }
    public int getID() {
        return id;
    }
    public String toString() {
        return "Name: "+name+"\nJersey Number: "+jersey+"\nPosition: "+position+"\nTeam: "
            +team+"\nID: "+id+"\nCaptain: "+captain+"\n";
    }
}
class Name
{
	String firstName,lastName;
	public Name()
	{
		this("Leslie","Evans");
	}
	public Name(String f,String l)
	{
		firstName=check(f);
		lastName=check(l);
	}
	public String check(String n) {
        for(int i=0; i<n.length(); i++) {
            if(Character.isDigit(n.charAt(i)) || (Character.isLetterOrDigit(n.charAt(i))==false && n.charAt(i)!=' ')) {
              System.out.println("Name is invalid.");
              System.exit(1);
            } 
        }
        return Character.toUpperCase(n.charAt(0))+n.substring(1,n.length()).toLowerCase();
    }
	public String toString() {
		return firstName+" "+lastName;
	}
}