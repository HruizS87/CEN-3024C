import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TextAnalyzerTest {
	TextAnalyzer analyzer;

	@BeforeEach
	void setUp() throws Exception {
		 analyzer = new TextAnalyzer();
	}
	
	/**
	 * This test checks if the taPath - file path JTextArea is correctly initialized 
	 */
	@Test
	void testTaPath() {
		assertNotNull(analyzer.taPath, "taPath - file path should be initialized");
	}
	
	/**
	 * This test checks if the ta - JTextArea is correctly initialized
	 */
	@Test
	void testTa() {
		assertNotNull(analyzer.ta, "ta - text area field should be initialized");
	}
	/**
	 * This test checks if the btnOpenFile is correctly initialized
	 */
	@Test
	void testBtnOpenFile() {
		assertNotNull(analyzer.btnOpenFile, "btnOpenFile button should be initialized");
	}
	
	/**
	 * This test checks if the btnViewdAllWords is correctly initialized
	 */
	@Test
	void testBtnViewdAllWords() {
		assertNotNull(analyzer.btnViewdAllWords, "btnViewdAllWords button should be initialized");
	}
	
	/**
	 * This test checks if the btnViewdAllWords is correctly initialized
	 */
	@Test
	void testBtnViewTop20Words() {
		assertNotNull(analyzer.btnViewTop20Words,"btnViewTop20Words button should be initialized");
	}
	
	/**
	 * This test checks if the btnOpenFile can correctly open the file
	 */
	@Test
	void testBtnOpenFileClick() {
		analyzer.btnOpenFile.doClick();
		assertTrue(analyzer.taPath.getText().length() > 0,"Path variable length is expected to be greater than zero");
	}

	/**
	 * This test checks if the btnViewdAllWords can correctly display all the words
	 */
	@Test
	void testBtnViewdAllWordsClick() {
		//calls the testBtnOpenFileClick() to prompt the tester for the file to be opened
		testBtnOpenFileClick();
		//get the content of the text area before button click
		String str = analyzer.ta.getText();
		analyzer.btnViewdAllWords.doClick();
		assertTrue(analyzer.ta.getText().length() > str.length(), "Content of the text area is expected to be greater than initial text area content");
	}
	
	/**
	 * This test checks if the btnViewTop20Words can correctly display the top 20 words
	 */
	@Test
	void testBtnViewTop20WordsClick() {
		//calls the testBtnOpenFileClick() to prompt the tester for the file to be opened
		testBtnOpenFileClick();
		//get the content of the text area before button click
		String str = analyzer.ta.getText();
		analyzer.btnViewTop20Words.doClick();
		assertTrue(analyzer.ta.getText().length() < str.length(),"Current text area content should be be less than the initial text area content");
	}
}
