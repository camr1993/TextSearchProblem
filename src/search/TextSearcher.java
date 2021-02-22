package search;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap; // import the HashMap class
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String[] output = new String[0];

		// basic idea is for each index saved in the hash, iterate indexes left and right until you get to the right amount of context words
		if (hash.containsKey(queryWord.toLowerCase())) {
			List<Integer> listOfIdx = hash.get(queryWord.toLowerCase());
			output = new String[listOfIdx.size()];
			for (int i = 0; i < listOfIdx.size(); i++) {
				int index = listOfIdx.get(i);
				LeftIndex leftIndexInstance = new LeftIndex(index, contextWords, textContent);
				int leftIdx = leftIndexInstance.getLeftIndex();
				RightIndex rightIndexInstance = new RightIndex(index, contextWords, textContent);
				int rightIdx = rightIndexInstance.getRightIndex();
				output[i] = textContent.substring(leftIdx, rightIdx).trim();
			}
		} else {
			output = new String[0];
		}

		return output;
	}
}

// Any needed utility classes can just go in this file

// get the left index of the context words
class LeftIndex {
  int index;
	int contextWords;
	String textContent;

	public LeftIndex(int index, int contextWords, String textContent) {
		this.index = index;
		this.contextWords = contextWords;
		this.textContent = textContent;
	}

	public int getLeftIndex() {
		int wordCounter = 0;
		String prevChar = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9']");
		while (index > 0 && wordCounter <= contextWords) {
			Matcher matcherPrev = pattern.matcher(prevChar);
    	boolean matchFoundPrev = matcherPrev.find();
			Matcher matcherCurr = pattern.matcher(textContent.substring(index, index + 1));
    	boolean matchFoundCurr = matcherCurr.find();

			// iterating left and checking if the previous char was a letter and current char is not a letter (meaning we just ended going through a word)
			if (matchFoundPrev == true && matchFoundCurr == false) {
				wordCounter++;
			}

			prevChar = textContent.substring(index, index + 1);
    	index--;
		}
		if (index == 0) {
			return index;
		} else {
			return index + 1;
		}
	}
}

// get the left index of the context words
class RightIndex {
  int index;
	int contextWords;
	String textContent;

	public RightIndex(int index, int contextWords, String textContent) {
		this.index = index;
		this.contextWords = contextWords;
		this.textContent = textContent;
	}

	public int getRightIndex() {
		int wordCounter = 0;
		String prevChar = "";
		Pattern pattern = Pattern.compile("[a-zA-Z0-9']");
		while (index < textContent.length() - 1 && wordCounter <= contextWords) {
			Matcher matcherPrev = pattern.matcher(prevChar);
    	boolean matchFoundPrev = matcherPrev.find();
			Matcher matcherCurr = pattern.matcher(textContent.substring(index, index + 1));
    	boolean matchFoundCurr = matcherCurr.find();

			// iterating right and checking if the previous char was letter and current char is a not a letter (meaning we just ended going through a word)
			if (matchFoundPrev == true && matchFoundCurr == false) {
				wordCounter++;
			}

			prevChar = textContent.substring(index, index + 1);
    	index++;
		}
		if (index == textContent.length() - 1) {
			return index + 1;
		} else {
			return index - 1;
		}
	}
}
