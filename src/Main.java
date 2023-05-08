import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
	private static List<Integer> Red_nums = new ArrayList<Integer>();
	private static List<Integer> Black_nums = new ArrayList<Integer>();
	private static List<Integer> New_Red_nums = new ArrayList<Integer>();
	private static List<Integer> New_Black_nums = new ArrayList<Integer>();
	public static void main(String[] args) throws IOException {
		BufferedReader Console = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Mennyi uj szam kell szinenkent?");
		int need = 0;
		
		boolean go = false;
		String in = Console.readLine();
		while(!go) {
			boolean nn = false;
			while(!nn) {
				try {
					need = Integer.parseInt(in);
					nn=true;
				}catch(NumberFormatException e) {
					System.out.println("Hibas szamformatum! Probalja ujra!");
				}
			}
			if(need%6==0)
				System.out.println(need+" szam lesz generalva a start parancs beirasa utan.");
			else
				System.out.println("A szam nem oszthato hattal! Irjon be uj szamot, ha modosiatani szeretne a mennyiseget vagy a start parancsot az "+need+" szam generalasahoz.");
			in = Console.readLine();
			if(in.equalsIgnoreCase("start")) 
				go = true;	
		}
		//start generating numbers
		FillUsed();
		int generated = 0;
		Random r = new Random();
		while(generated<2*need){
			int uj = r.nextInt()%10000;
			if(NumberCorrect(uj)&&(!Used(uj))) {
				if(generated%2==0)
					New_Red_nums.add(uj);
				else
					New_Black_nums.add(uj);
				generated++;
			}
		}
		System.out.println("Az uj piros szamok:");
		for(Integer i : New_Red_nums)
			System.out.println(i);
		System.out.println("Az uj fekete szamok:");
		for(Integer i : New_Black_nums)
			System.out.println(i);
		
		go = false;
		System.out.println("A mentes parancs beirasaval a szamok bekerulnek a hasznaltak koze es elmentodnek kulon fajlokba.");
		in = Console.readLine();
		while(!go) {
			if(in.equalsIgnoreCase("mentes")) 
				go = true;
			else
				System.out.println("Hibas parancs!");
		}
		for(Integer i : New_Red_nums)
			Red_nums.add(i);
		for(Integer i : New_Black_nums)
			Black_nums.add(i);
		
		BufferedWriter f0 = new BufferedWriter(new FileWriter("Used_Red.txt"));
		for(Integer i : Red_nums) {
			f0.write(i.toString());
			f0.newLine();
		}
		f0.close();
		
		BufferedWriter f1 = new BufferedWriter(new FileWriter("Used_Black.txt"));
		for(Integer i : Black_nums) {
			f1.write(i.toString());
			f1.newLine();
		}
		f1.close();
		
		
		LocalDateTime tm = LocalDateTime.now();
		String ido = tm.getYear()+"_"+tm.getMonth()+"_"+tm.getDayOfMonth()+"_"+tm.getHour()+"_"+tm.getMinute()+"_"+tm.getSecond()+"_";
		
		BufferedWriter f2 = new BufferedWriter(new FileWriter(ido+"New_Red.txt"));
		f2.write("M1\tM2\tM3");
		f2.newLine();
		f2.write(New_Red_nums.get(0).toString()+"\t"+New_Red_nums.get(1).toString()+"\t"+New_Red_nums.get(2).toString());
		for(int i=3;i+2<New_Red_nums.size();i+=3) {
			f2.newLine();
			f2.write(New_Red_nums.get(i).toString()+"\t"+New_Red_nums.get(i+1).toString()+"\t"+New_Red_nums.get(i+2).toString());
		}
		f2.close();
		
		BufferedWriter f3 = new BufferedWriter(new FileWriter(ido+"New_Black.txt"));
		f3.write("M1\tM2\tM3");
		f3.newLine();
		f3.write(New_Black_nums.get(0).toString()+"\t"+New_Black_nums.get(1).toString()+"\t"+New_Black_nums.get(2).toString());
		for(int i=3;i+2<New_Black_nums.size();i+=3) {
			f3.newLine();
			f3.write(New_Black_nums.get(i).toString()+"\t"+New_Black_nums.get(i+1).toString()+"\t"+New_Black_nums.get(i+2).toString());
			
		}
		f3.close();
		
		System.out.println("Kesz.");
	}
	public static int[] FillUsed() throws IOException {
		int readed[] = {0,0};
		BufferedReader br0 = new BufferedReader(new FileReader("Used_Black.txt"));
		String line = br0.readLine();
		while(line!=null) {
			Black_nums.add(Integer.parseInt(line));
			readed[0]++;
			line = br0.readLine();
		}
		br0.close();
		BufferedReader br1 = new BufferedReader(new FileReader("Used_Red.txt"));
		line = br1.readLine();
		while(line!=null) {
			Black_nums.add(Integer.parseInt(line));
			readed[1]++;
			line = br1.readLine();
		}
		br1.close();
		return readed;
	}
	public static boolean NumberCorrect(int n) {
		//Range check
		if(n>9999) return false;
		if(n<1000) return false;
		int d0 = n%10;
		n/=10;
		int d1 = n%10;
		n/=10;
		int d2 = n%10;
		n/=10;
		int d3 = n%10;
		n/=10;
		//No common digit
		int na[] = {d3, d2, d1, d0};
		for(int i=0;i<3;i++) {
			for(int j=i+1;j<4;j++) {
				if(na[i]==na[j])
					return false;
			}
		}
		return true;
	}
	
	public static boolean Used(int n) {
		for(Integer i : Red_nums)
			if(i.equals(n))
				return true;
		for(Integer i : New_Red_nums)
			if(i.equals(n))
				return true;
		for(Integer i : Black_nums)
			if(i.equals(n))
				return true;
		for(Integer i : New_Black_nums)
			if(i.equals(n))
				return true;
		return false;
	}
}
