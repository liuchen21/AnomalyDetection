package org.ncut.edu.cn.data.clean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import de.lmu.ifi.dbs.elki.data.IntegerVector;
import de.lmu.ifi.dbs.elki.data.NumberVector;
import de.lmu.ifi.dbs.elki.distance.distancefunction.CosineDistanceFunction;

/**
 * Computing similarity of two given lines of Chinese text.
 * 
 * @author liuchen
 *
 */
public class TextLineSimilarity {

	private List<String> splitChineseWords(String str) {
		Set<String> expectedNature = new HashSet<String>() {
			{
				add("n");
				add("v");
				add("vd");
				add("vn");
				add("vf");
				add("vx");
				add("vi");
				add("vl");
				add("vg");
				add("nt");
				add("nz");
				add("nw");
				add("nl");
				add("ng");
				add("userDefine");
				add("wh");
			}
		};
		
		List<String> words = new ArrayList();
		Result result = ToAnalysis.parse(str); // 分词结果的一个封装，主要是一个List<Term>的terms
		Iterator<Term> it = result.getTerms().iterator();
		while (it.hasNext()) {
			Term term = (Term) it.next();
			String word = term.getName();

			String natureStr = term.getNatureStr(); // 拿到词性
			if (expectedNature.contains(natureStr)) {
				words.add(word);
			}

		}
		
		return words;

	}

	
	private NumberVector mapToNumberVector(String[] keySet, String[] strSet) {
		int size = keySet.length;
		//数组所有元素需要初始化为0 ？
		int[] values = new int[size];
		
		for(int i=0;i<keySet.length;i++) {
			String key = keySet[i];
			
			for(int j=0;j<strSet.length;j++) {
				String str = strSet[j];
				
				if(key.equalsIgnoreCase(str)) values[i]++;
			}
		}
		
		NumberVector nv = new IntegerVector(values) ;
		return nv;
	}
	
	public double getCosineSimilarity(String str1, String str2) {
		//分词
		List<String> words1 =this.splitChineseWords(str1);
		List<String> words2 =this.splitChineseWords(str2);
		
		double  sim =0;
		
		//生成所有不重复单词集合，作为两个向量的键
		HashSet<String> keyset = new HashSet<String>();
		keyset.addAll(words1);
		keyset.addAll(words2);
		int size = keyset.size();
		System.out.println("keyset =="+keyset);
		System.out.println("keyset size=="+size);
		
		String[] keys = keyset.toArray(new String[size]);
		
		
		//生成数字向量
		NumberVector nv1 = this.mapToNumberVector(keys, (String[])words1.toArray(new String[words1.size()]));
		NumberVector nv2 = this.mapToNumberVector(keys, (String[])words2.toArray(new String[words1.size()]));
		
		System.out.println("nv1=="+nv1);
		System.out.println("nv2=="+nv2);
		
		CosineDistanceFunction cos_dinstance_func = CosineDistanceFunction.STATIC;
		sim = cos_dinstance_func.distance(nv1, nv2);
		
		return sim;
	}
	
	
	public static void main(String[] args) {
		String str1 = "16833074,除灰#3炉1A电场副三灰斗旁输灰管道伸缩节漏灰,2016/4/20 20:07,#3炉电除尘一电场#1输灰管道伸缩节";
		String str2 = "16833075,除灰#4炉二电场副二灰斗圆顶阀气缸排气阀漏气,2016/4/20 20:11,#4炉电除尘二电场灰斗#3园顶阀汽缸";
		
	
		TextLineSimilarity tls = new TextLineSimilarity();
		System.out.println(tls.getCosineSimilarity(str1, str2));

	}
}
