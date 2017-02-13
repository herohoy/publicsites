package com.op.clazz;

/**
 * 复利（利滚利）计算相关
 * @author Administrator
 *
 */
public class Usuries {
	
	/**
	 * 复利计算器
	 * @param origin 原数字（本金）
	 * @param inc 增长率（利息）
	 * @param timeLen 时长（单位：秒）
	 * @return 复利计算结果
	 */
	public static double usuryCompute(double origin,double inc,long timeLen){
		double result = 0;
		timeLen = timeLen / (365 * 24 * 3600);
		result = origin * Math.pow((1+inc),timeLen);
		return result;
	}

	public static void main(String[] args) {
		long l = 365*24*3600 * 30;
//		System.out.println(Usuries.usuryCompute(100, 0.1, l));
		System.out.println(Usuries.usuryCompute(100, 0.03, l));
	}

}
