package org.ncut.edu.cn.correlation.pearson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PearsonCalculation {
	
	public PearsonCalculation() {
			
	}
	
	
	public List<Double> fileToTimeSeries(String filepath){
		List<Double> list=new ArrayList<Double>();
		File file = new File(filepath);
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(Double.valueOf(line));
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public double calculatePearsonCoefficient(List<Double> timeSeries1, List<Double> timeSeries2) {
		double p=PearsonUtils.pearson(timeSeries1,timeSeries2);
		return p;
	}
	
	
	public double calculatePearsonCoefficientWithOffset(List<Double> timeSeries1, List<Double> timeSeries2) {
		PositionDouble p=PearsonUtils.pearsonWithOffsetReturnPosition(timeSeries1,timeSeries2,5);
		return p.getValue();
	}
	
	public static void main(String[] args) {
		PearsonCalculation pc = new PearsonCalculation();
		
		List<Double> x=pc.fileToTimeSeries("./resource/A492.txt");
		List<Double> y=pc.fileToTimeSeries("./resource/A477.txt");
		double normalPearsonValue = pc.calculatePearsonCoefficient(x, y);
		double offsetPearsonValue = pc.calculatePearsonCoefficientWithOffset(x, y);
		System.out.println("normal pearson value is:"+normalPearsonValue);
		System.out.println("offset pearson value is:"+offsetPearsonValue);
	}
}
