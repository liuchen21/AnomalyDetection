package org.ncut.edu.cn.correlation.pearson;

/**
 * 对应带偏移相关系数的返回结果
 * 
 * @author wxb
 */
public class PositionDouble {
	private double value;
	private int position;

	/**
	 * @param position
	 *            偏移位置
	 * @param value
	 *            相关系数值
	 */
	public PositionDouble(int position, double value) {
		this.position = position;
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + position;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PositionDouble other = (PositionDouble) obj;
		if (position != other.position)
			return false;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value))
			return false;
		return true;
	}

	public String toString() {
		return this.position + "#" + Double.valueOf(String.format("%.4f", new Object[] { Double.valueOf(this.value) }));
	}

	/**
	 * 返回相关系数
	 * 
	 * @author wangxb
	 * @date 2016年9月6日 下午1:42:34
	 * @return double
	 */
	public double getValue() {
		return this.value;
	}

	/**
	 * 设置相关系数
	 * 
	 * @author wangxb
	 * @date 2016年9月6日 下午1:42:34
	 * @return void
	 */
	public void setValue(double value) {
		this.value = value;
	}

	/**
	 * 返回偏移位置
	 * 
	 * @author wangxb
	 * @date 2016年9月6日 下午1:42:34
	 * @return int
	 */
	public int getPosition() {
		return this.position;
	}

	/**
	 * 设置偏移位置
	 * 
	 * @author wangxb
	 * @date 2016年9月6日 下午1:42:34
	 * @return void
	 */
	public void setPosition(int position) {
		this.position = position;
	}
}
