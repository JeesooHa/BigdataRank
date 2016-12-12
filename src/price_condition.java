
public class price_condition {
	public static String condition(int price){	

		String s = "";
		
 		//0.2958050379900349
		int set = 30000;
		int term = 42455;
		while(price>set){
			set+=term;
		}
		
		s = (set-term/2)+"";
		
		
		return s;
	}
}
