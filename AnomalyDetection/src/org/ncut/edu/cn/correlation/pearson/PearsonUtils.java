package org.ncut.edu.cn.correlation.pearson;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * 带偏移的相关系数的工具类
 * @author wxb
 */
public class PearsonUtils
{
  private static PearsonsCorrelation pc = new PearsonsCorrelation();
  
  /**
   * 返回皮尔逊相关系数，要求同一个List中至少出现两个唯一的数字
   * @author wangxb
   * @date 2016年9月6日 下午1:42:34
   * @param x	输入List
   * @param y	输入List
   * @return double  返回相关系数
   */
  public static double pearson(List<Double> x, List<Double> y)
  {
    double[] xx = new double[x.size()];
    int count = 0;
    for (Double ss : x) {
      xx[(count++)] = ss.doubleValue();
    }
    double[] yy = new double[y.size()];
    count = 0;
    for (Double ss : y) {
      yy[(count++)] = ss.doubleValue();
    }
    return pearsonMeanByCommonMath(xx, yy);
  }
  
  /**
   * 返回皮尔逊相关系数，要求同一个数组中至少出现两个唯一的数字
   * @author wangxb
   * @date 2016年9月6日 下午1:42:34
   * @param x	输入数组
   * @param y	输入数组
   * @return double  返回相关系数
   */
  public static double pearsonMeanByCommonMath(double[] x, double[] y)
  {
	  double max = Double.valueOf(String.format("%.4f", new Object[] { Double.valueOf(pc.correlation(x, y)) })).doubleValue();
	  return max;
  }
  
  /**
   * 返回皮尔逊相关系数，要求同一个数组中至少出现两个唯一的数字
   * @author wangxb
   * @date 2016年9月6日 下午1:42:34
   * @param x	输入List
   * @param y	输入List
   * @param position	相对于数组x的第0个数字，y的最大偏移距离{-position,position}
   * @return PositionDouble  返回相关系数最大的偏移位置，以及该位置的相关系数
   */
  public static PositionDouble pearsonWithOffsetReturnPosition(List<Double> x, List<Double> y, int position)
  {
    double[] xx = new double[x.size()];
    int count = 0;
    for (Double ss : x) {
      xx[(count++)] = ss.doubleValue();
    }
    double[] yy = new double[y.size()];
    count = 0;
    for (Double ss : y) {
      yy[(count++)] = ss.doubleValue();
    }
    return pearsonWithOffsetReturnPosition(xx, yy, position);
  }
  
  /**
   * 返回皮尔逊相关系数，要求同一个数组中至少出现两个唯一的数字
   * @author wangxb
   * @date 2016年9月6日 下午1:42:34
   * @param x	输入数组
   * @param y	输入数组
   * @param position	相对于数组x的第0个数字，y的最大偏移距离{-position,position}
   * @return PositionDouble  返回相关系数最大的偏移位置，以及该位置的相关系数
   */
  public static PositionDouble pearsonWithOffsetReturnPosition(double[] x, double[] y, int position)
  {
    double max = 0.0D;
    int add = 0;
    int len = Math.min(x.length, y.length) - (2 * position + 1);
    if (len > 0) {
      for (int from1 = 0; from1 <= 2 * position; from1++)
      {
        double pearson_rs = pc.correlation(Arrays.copyOfRange(x, from1, from1 + len + 1), 
          Arrays.copyOfRange(y, position, position + len + 1));
        if (Math.abs(pearson_rs) > Math.abs(max))
        {
          add = from1;
          max = pearson_rs;
        }
      }
    }
    max = Double.valueOf(String.format("%.4f", new Object[] { Double.valueOf(max) })).doubleValue();
    return new PositionDouble(add - position, max);
  }
  
}
