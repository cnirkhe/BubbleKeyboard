import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;


public class Runner {
	
	public static LinkedList<TrigramData> words;
	public static HashMap<Integer,TrigramData> wordsindex;

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException
	{
		//Read the first file
		File f = new File("news.vfreq");
		Scanner scan = new Scanner(f);
		
		words = new LinkedList<TrigramData> ();
		wordsindex = new HashMap<Integer,TrigramData> ();
		
		while(scan.hasNextLine())
		{
			String line = scan.nextLine();
			Scanner lineScanner = new Scanner(line);
			String name = lineScanner.next();
			int value = lineScanner.nextInt();
			
			name = name.toLowerCase();
			
			TrigramData container = contains(name);
			if (container == null)
			{
				container = new TrigramData(name);
				words.add(container);
			}
			container.numericalvalues.add(new Integer(value));
			wordsindex.put(new Integer(value), container);
		}	
		
		//Read the Second File
		File f_ = new File("News.id3gram");
		Scanner scan_ = new Scanner(f_);
		
		LinkedList<Trigram> trigrams = new LinkedList<Trigram> ();
		
		while(scan_.hasNextLine())
		{
			String line_ = scan_.nextLine();
			Scanner lineScanner_ = new Scanner(line_);
			int first = lineScanner_.nextInt();
			int second = lineScanner_.nextInt();
			int third = lineScanner_.nextInt();
			int frequency = lineScanner_.nextInt();
			
			TrigramData first_ = wordsindex.get(first);
			TrigramData second_ = wordsindex.get(second);
			TrigramData third_ = wordsindex.get(third);
			
			if (first_ != null && second_ != null && third_ != null)
			{
				trigrams.add(new Trigram(first_, second_, third_, frequency));
			}
		}
		
		Collections.sort(words);
		
		for(int i = 0; i < words.size(); i++)
		{
			words.get(i).assignednumber = i;
		}
		
		Collections.sort(trigrams);
		
		//Construct New Files
		
		f = new File("file1.out");
		f_ = new File("file2.out");
		
		PrintStream p = new PrintStream(f);
		PrintStream p_ = new PrintStream(f_);
		
		for(int i = 0; i < words.size(); i++)
		{
			TrigramData t = words.get(i);
			p.println(t.name + " " + t.assignednumber);
		}
		
		for(int i = 0; i < trigrams.size(); i++)
		{
			Trigram t = trigrams.get(i);
			p_.println(t.first.assignednumber + " " + t.second.assignednumber + " " + t.third.assignednumber + " " + t.frequency);
		}
		
		
	}
	
	public static TrigramData contains(String a)
	{
		for(TrigramData t : words)
		{
			if (t.name.equalsIgnoreCase(a))
			{
				return t;
			}
			
		}
		return null;
	}

}
