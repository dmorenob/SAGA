package examples;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import gate.Corpus;
import gate.Factory;
import gate.Gate;
import gate.gui.MainFrame;
import gateModules.DictionaryBasedSentimentAnalyzer;

public class DictionaryBasedEmoticonAndFinancialSentimentAnalysis{
	
	/**
	 * Execute "DictionaryBasedSentimentAnalyzer" module in GATE graphic/local mode.
	 * 
	 * @param args not used
	 * @throws Exception
	 */
	
	public static void main(String[] args) throws Exception{
		Gate.init(); // Prepare the library
		MainFrame.getInstance().setVisible(true); //Set GATE app visible
		// For using ANNIE PR's
		// Get the root plugins dir
		File pluginsDir = Gate.getPluginsHome();
		// Load the Annie plugin
		File aPluginDir = new File(pluginsDir, "ANNIE");
		// Load the plugin
		Gate.getCreoleRegister().registerDirectories(aPluginDir.toURI().toURL());
		//Create DictionaryBasedSentimentAnalyzer and set the gazetteer that we are going to use in this example, which is about Spanish finances.
		ArrayList<URL> dictionaries = new ArrayList<URL>();
		dictionaries.add((new DictionaryBasedEmoticonAndFinancialSentimentAnalysis()).getClass().getResource("/resources/gazetteer/emoticon/lists.def"));
		dictionaries.add((new DictionaryBasedFinancialSentimentAnalysis()).getClass().getResource("/resources/gazetteer/finances/spanish/paradigma/lists.def"));
		DictionaryBasedSentimentAnalyzer module = new DictionaryBasedSentimentAnalyzer("SAGA - Emoticon Sentiment Analyzer", dictionaries);
		//Register our own plugins to use our own PRs located in the package processingResources.
		module.registerPrPlugin();
		//Create the corpus and populate it.
	    //Corpus corpus = module.createCorpusAndPupulateItExample();
		Corpus corpus = Factory.newCorpus("Tweets");
		corpus.add(Factory.newDocument("El valor de BBVA sube en bolsa. :)"));
		corpus.add(Factory.newDocument("El valor de BBVA cae en bolsa. :("));
		corpus.add(Factory.newDocument("El valor de BBVA ni sube ni cae en bolsa. -.-"));
	    module.setCorpus(corpus); // Set corpus into the controller. 
		module.execute();
	}
}
