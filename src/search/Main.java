package search;

public class Main {
  public static void main(String[] args) throws Exception {
    TextSearcherTest tests = new TextSearcherTest();
    tests.testOneHitNoContext();
    System.out.println("Test 1 passed!");
    tests.testMultipleHitsNoContext();
    System.out.println("Test 2 passed!");
    tests.testBasicSearch();
    System.out.println("Test 3 passed!");
    tests.testBasicMoreContext();
    System.out.println("Test 4 passed!");
    tests.testApostropheQuery();
    System.out.println("Test 5 passed!");
    tests.testNumericQuery();
    System.out.println("Test 6 passed!");
    tests.testMixedQuery();
    System.out.println("Test 7 passed!");
    tests.testCaseInsensitiveSearch();
    System.out.println("Test 8 passed!");
    tests.testNearBeginning();
    System.out.println("Test 9 passed!");
    tests.testNearEnd();
    System.out.println("Test 10 passed!");
    tests.testMultipleSearches();
    System.out.println("Test 11 passed!");
    tests.testOverlappingHits();
    System.out.println("Test 12 passed!");
    tests.testNoHits();
    System.out.println("Test 13 passed!");
    tests.testTokenizer();
    System.out.println("Test 14 passed!");
  }
}



/*

Hi! My main language is JavaScript, and I actually had to learn Java in order to do this problem. I just wanted to include my JavaScript solution as well since this is what I'm most comfortable coding in:


const fs = require('fs')

class TextSearcher {
  constructor(path) {
    this.textContent
    this.hash = {}

    // this.initializeTextContent will run when class is initialized
    // reads in a file from a given path and saves it as a string on this.textContent
    this.initializeTextContent = function (path) {
      try {
        const data = fs.readFileSync(path, 'utf8')
        // add a space to the beginning of the text to help with RegExp searching
        this.textContent = ' ' + data
      } catch (err) {
        console.error(err)
      }
    }

    // this.initializeHash also runs when class is initialized
    // Iterates through this.textContent and creates this.hash
    // Keys of hash are every unique word, values are an array of indexes representing the starting location of each occurrence of the word in textContent
    this.initializeHash = function () {
      let re = /([\n\r\s]+)([a-z]+)/gi
      let matches
      while ((matches = re.exec(this.textContent))) {
        let word = matches[2].toLowerCase()
        let index = matches.index + matches[1].length
        if (word in this.hash) {
          this.hash[word].push(index)
        } else {
          this.hash[word] = [index]
        }
      }
    }

    this.initializeTextContent(path)
    this.initializeHash()
  }

  // Takes in a word (string) and the number of context words (int) needed to the left and right
  // Returns an array of strings representing the words & context words found in the text file
  search(queryWord, contextWords) {
    const output = []
    if (queryWord.toLowerCase() in this.hash) {
      for (let index of this.hash[queryWord]) {
        let leftIdx = getLeftIndex(index, contextWords, this.textContent)
        let rightIdx = getRightIndex(index, contextWords, this.textContent)
        output.push(this.textContent.slice(leftIdx, rightIdx).trim())
      }
    }
    return output
  }
}

// Utility Functions:

// get the left index of the context words
getLeftIndex = function (index, contextWords, textContent) {
  let wordCounter = 0
  let prevChar = ''
  while (index > 0 && wordCounter <= contextWords) {
    if (!/[\n\r\s]/.test(prevChar) && /[\n\r\s]/.test(textContent[index])) {
      wordCounter++
    }
    prevChar = textContent[index]
    index--
  }
  return index + 1
}
// get the right index of the context words
getRightIndex = function (index, contextWords, textContent) {
  let wordCounter = 0
  let prevChar = ''
  while (index < textContent.length - 1 && wordCounter <= contextWords) {
    if (/[\n\r\s]/.test(prevChar) && !/[\n\r\s]/.test(textContent[index])) {
      wordCounter++
    }
    prevChar = textContent[index]
    index++
  }
  return index === textContent.length - 1 ? index + 1 : index - 1
}

// Create an instance and run tests:

const searcher = new TextSearcher('./files/short_excerpt.txt')
let results = searcher.search('naturalists', 3)
console.log(results)

*/
