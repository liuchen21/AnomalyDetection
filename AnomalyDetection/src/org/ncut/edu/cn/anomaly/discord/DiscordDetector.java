package org.ncut.edu.cn.anomaly.discord;

import java.io.IOException;

import net.seninp.jmotif.sax.NumerosityReductionStrategy;
import net.seninp.jmotif.sax.SAXException;
import net.seninp.jmotif.sax.TSProcessor;
import net.seninp.jmotif.sax.discord.DiscordRecord;
import net.seninp.jmotif.sax.discord.DiscordRecords;
import net.seninp.jmotif.sax.discord.HOTSAXImplementation;

/**
 * Detect discords from time series data with HotSAX algorithm
 * @author liuchen
 *
 */
public class DiscordDetector {

	private String TEST_DATA_FNAME = "";

	private int WIN_SIZE = 100;
	private int PAA_SIZE = 3;
	private int ALPHABET_SIZE = 3;

	private double NORM_THRESHOLD = 0.01;

	private int DISCORDS_TO_TEST = 5;

	private NumerosityReductionStrategy STRATEGY = NumerosityReductionStrategy.EXACT;

	/**
	 * 
	 * @param data_file
	 */
	public DiscordDetector(String data_file) {
		this.TEST_DATA_FNAME = data_file;
	}
	
	/**
	 * 
	 * @param data_file: file contains time series data
	 * @param win_size: window size
	 * @param paa_size: 
	 * @param alphabet_size
	 */
	public DiscordDetector(String data_file, int win_size, int paa_size, int alphabet_size) {
		this.TEST_DATA_FNAME = data_file;
		this.WIN_SIZE = win_size;
		this.PAA_SIZE = paa_size;
		this.ALPHABET_SIZE =  alphabet_size;
	}
	
	public DiscordRecords getDiscordRecords() {
		DiscordRecords discords = null;
		
		try {
			double[] series = TSProcessor.readFileColumn(TEST_DATA_FNAME, 0, 0);
			discords = HOTSAXImplementation.series2Discords(series, DISCORDS_TO_TEST, WIN_SIZE, PAA_SIZE, ALPHABET_SIZE,
					STRATEGY, NORM_THRESHOLD);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return discords;
	}
	
	public static void main(String[] args) {
		String file = "./resource/ecg0606_1.csv";
		DiscordDetector detector = new DiscordDetector(file);
		DiscordRecords records = detector.getDiscordRecords();
		for (DiscordRecord d : records) {
			System.out.println(d.toString());
		}
		
	}
	
}
