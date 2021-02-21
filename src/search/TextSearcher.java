package search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap; // import the HashMap class
import java.util.List;
import java.util.ArrayList;

public class TextSearcher {

	// initialize a HashMap
	// Keys of hash are every unique word, values are an array of indexes representing the starting location of each occurrence of the word in textContent
	HashMap<String, List<Integer>> hash = new HashMap<String, List<Integer>>();
	String textContent;

	/**
	 * Initializes the text searcher with the contents of a text file.
	 * The current implementation just reads the contents into a string
	 * and passes them to #init().  You may modify this implementation if you need to.
	 *
	 * @param f Input file.
	 * @throws IOException
	 */
	public TextSearcher(File f) throws IOException {
		FileReader r = new FileReader(f);
		StringWriter w = new StringWriter();
		char[] buf = new char[4096];
		int readCount;

		while ((readCount = r.read(buf)) > 0) {
			w.write(buf,0,readCount);
		}

		this.textContent = w.toString();
		init(w.toString());
	}

	/**
	 *  Initializes any internal data structures that are needed for
	 *  this class to implement search efficiently.
	 */
	protected void init(String fileContents) {
		// TODO -- fill in implementation

		// regex to search for words
		String regex = "([a-zA-Z0-9']+)";
		TextTokenizer tokenizer = new TextTokenizer(fileContents, regex);

		// Iterate through file looking for words
		// When you find a word, add it to the hash along with its starting index (if it's already in the hash, just push its starting index to the value array)
		while (tokenizer.hasNext()) {
			String nextMatch = tokenizer.next().toLowerCase();
			if (tokenizer.isWord(nextMatch)) {
				if (hash.containsKey(nextMatch)) {
					hash.get(nextMatch).add(tokenizer.getIndex());
				} else {
					hash.put(nextMatch, new ArrayList<Integer>());
					hash.get(nextMatch).add(tokenizer.getIndex());
				}
			}
		}
	}

	/**
	 *
	 * @param queryWord The word to search for in the file contents.
	 * @param contextWords The number of words of context to provide on
	 *                     each side of the query word.
	 * @return One context string for each time the query word appears in the file.
	 */
	public String[] search(String queryWord,int contextWords) {
		// TODO -- fill in implementation
		System.out.println(textContent);
		System.out.println(hash);
		return new String[0];
	}
}

// Any needed utility classes can just go in this file
