package network;

public class HelpFunc {
	public static int[] ToolsToDead(Player p){
		//make the Tool array of a player into an array of types 
		int[] ans=new int[11];
		Tool[] t=p.getTools();
		for(int i=1; i<40;i++){
			if(t[i].isDead()){
				ans[t[i].getType()]++;
			}
		}
		for(int i=0;i<11;i++){
			if(ans[i]!= 0)break;
			if(i==10)return null;
		}
		return ans;
	}
	public static int FirstDead(Player p, int type) {
		//finds the first dead tool of the type
		for(int i=0; i<40; i++) {
			Tool t=p.getTools()[i];
			if(t.getType()== type && t.isDead()) {
				return i;
			}
		}
		return -1;
	}
	public static int DeadOfType(Player p, int type) {
		//find out how many dead of this type
		int c=0;
		for(int i=0; i<40; i++) {
			Tool t=p.getTools()[i];
			if(t.getType()== type && t.isDead()) {
				c++;
			}
		}
		return c;
	}
	public static int ToolByLoc(int x, int y, Player p) {
		//finds the tool on that location and returns it's number. If there isn't, returns -1
		Tool[] t= p.getTools();
		for(int i=0;i<t.length;i++) {
			if(t[i].getPlace().getX()==x&&t[i].getPlace().getY()==y) {
				return i;
			}
		}
		return -1;
	}
	public static String turnAllToText(Player p) {
		String ans="";
		for(int i=0;i<40;i++) {
			int x= (int) p.getTools()[i].getPlace().getX(),y= (int) p.getTools()[i].getPlace().getY();
			ans+= x+"##"+y+"##"+i+"data";
		}
		return ans;
	}
	public static String turnOneToText(Player p, int i) {
		String ans="";
			Point loc= p.getTools()[i].getPlace();
			ans+= loc.getX()+"##"+loc.getY()+"##"+i;
		return ans;
	}
}