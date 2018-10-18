package com.dong.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数字区间算法工具类
 * <p>
 * 使用本工具类需要明确两个概念
 * <ul>
 * <li>数字区间Interval {@link Interval}</li>
 * <li>桶Bucket {@link Bucket}</li>
 * </ul>
 * @version 1.0 2018/10/17
 * @author dongliyang
 * @see Interval
 * @see Bucket
 */
public final class IntervalUtil {
	
	//注意:该类的作用单一纯粹，千万不要在本类中添加无关的方法!!!
	
	private IntervalUtil() {}
		
	/**
	 * 拆分区间，每个桶内区间最多包含splitSize个数字
	 * <p>
	 * 比如有三个区间
	 * <ul>
	 * <li>区间一   [1,6]</li>
	 * <li>区间二   [8,10]</li>
	 * <li>区间三   [15,18]</li>
	 * </ul>
	 * 现在需要拆分区间，每个区间最多包含splitSize个数字。
	 * <p>
	 * 返回的结果
	 * <ul>
	 * <li>桶一   [1,4]</li>
	 * <li>桶二   [5,6]、[8,9]</li>
	 * <li>桶三   [10,10]、[15,17]</li>
	 * <li>桶四   [18,18]</li>
	 * </ul>
	 * @param intervalList
	 * @param splitSize
	 * @return
	 */
	public static List<Bucket> split(List<Interval> intervalList,int splitSize) {
		List<Bucket> bucketResultList = new ArrayList<>();
		
		/*
		 * 算法总体思路：
		 * 判断当前桶内是否包含元素，以及(当前区间的个数 + 桶内元素的个数)是否比SPLIT_SIZE大。
		 * 1.IF 当前桶内有元素:
		 *		IF (当前桶内元素的个数 + 当前区间元素个数) > SPLIT_SIZE:
		 *			> 从当前元素中拆(SPLIT_SIZE - 当前桶内元素个数)个元素，加入桶内。
		 *			> 加入后，桶就满了。需要换一个新桶。
		 *			> 由于当前元素后面还有数据，所以，需要把剩下的元素，作为新的当前元素来处理。
		 *      ELIF (当前桶内元素的个数 + 当前区间元素个数) == SPLIT_SIZE:
		 *      	> 把当前元素加入后，桶正好满了。
		 *      	> 换一个新桶。
		 *      	> 开始处理下一个区间。
		 *      ELIF (当前桶内元素的个数 + 当前区间元素个数) < SPLIT_SIZE:
		 *      	> 把当前元素加入后，桶还没满。
		 *      	> 开始处理下一个区间。
		 * 2.IF 当前桶内无元素：
		 * 		IF 前区间元素个数 > SPLIT_SIZE:
		 * 			> 判断是否能够拆分成整数份
		 * 			> 计算拆分
		 * 		ELIF 前区间元素个数 = SPLIT_SIZE:
		 * 			> 把当前元素加入后，桶正好满了。
		 * 			> 换一个新桶。
		 * 			> 开始处理下一个区间。
		 * 		ELIF 前区间元素个数 < SPLIT_SIZE:
		 * 			> 把当前元素加入后，桶还没满。
		 * 			> 开始处理下一个区间。
		 */
		final int SPLIT_SIZE = splitSize;
		int i = 0;
		final int INERVAL_LEGNTH = intervalList.size();
		Interval current = intervalList.get(0);
		//首先给出一个桶
		Bucket bucket = new Bucket();
		while(current != null) {
			if(bucket.count() > 0) {
				if(bucket.count() + current.getCount() > SPLIT_SIZE) {
					int needCount = SPLIT_SIZE - bucket.count();
					int end = current.getStart() + needCount - 1;
					bucket.add(new Interval(current.getStart(), end));
					bucketResultList.add(bucket);
					
					bucket = new Bucket();
					current = new Interval(end + 1, current.getEnd());
				} else if(bucket.count() + current.getCount() == SPLIT_SIZE){
					bucket.add(current);
					bucketResultList.add(bucket);
					
					bucket = new Bucket();
					i = i + 1;
					current = (i < INERVAL_LEGNTH ? intervalList.get(i) : null);
				} else {
					bucket.add(current);
					
					i = i + 1;
					current = (i < INERVAL_LEGNTH ? intervalList.get(i) : null);
				}
			} else {
				if(current.getCount() > SPLIT_SIZE) {
					
					//是否能够整除
					boolean divideExactly = current.getCount() % SPLIT_SIZE == 0;
					int stepSize = current.getCount() / SPLIT_SIZE;
					for(int stepIndex = 0; stepIndex < stepSize; stepIndex++) {
						int start = current.getStart() + stepIndex * SPLIT_SIZE;
						int end = start + SPLIT_SIZE - 1;
						Interval newInterval = new Interval(start, end);
						bucket.add(newInterval);
						bucketResultList.add(bucket);
						
						//上面的步骤已经使用了桶，并且已经满了，需要新建一个桶
						bucket = new Bucket();
					}
					if(!divideExactly) {
						//把剩余的添加进桶内
						Interval cacheInterval = new Interval(current.getStart() + stepSize * SPLIT_SIZE,current.getEnd());
						bucket.add(cacheInterval);
					}
					
					//处理完后，移到下一个元素
					i = i + 1;
					current = (i < INERVAL_LEGNTH ? intervalList.get(i) : null);	
				} else if(current.getCount() == SPLIT_SIZE){
					bucket.add(current);
					bucketResultList.add(bucket);
					
					bucket = new Bucket();
					
					i = i + 1;
					current = (i < INERVAL_LEGNTH ? intervalList.get(i) : null);
				} else {
					bucket.add(current);
					
					i = i + 1;
					current = (i < INERVAL_LEGNTH ? intervalList.get(i) : null);
				}
			}
		}
		//处理完最后一个区间，桶还没满的情况
		if(bucket.count() > 0) {
			bucketResultList.add(bucket);
		}
		
		return bucketResultList;
	}
	
	/**
	 * 合并有重叠的区间
	 * <p>
	 * 比如
	 * <p>
	 * 输入： [1,3],[2,6],[8,10],[15,18]
	 * <p>
	 * 输出：[1,6],[8,10],[15,18]
	 * @param intervalList 区间列表
	 * @return
	 */
	public static List<Interval> merge(List<Interval> intervalList) {
		List<Interval> result = new ArrayList<>();
		if(intervalList == null || intervalList.size() == 0) {
			return result;
		}
		
		//先对区间进行排序，按照start升序
		Collections.sort(intervalList, (o1,o2) -> o1.getStart() - o2.getStart());
		
		/*
		 * 排序后，后一个元素(记为next)的start一定是不小于前一个(记为prev)start的，
		 * 对于新添加的区间，如果next.start大于prev.end就说明这两个区间时分开的，
		 * 要添加一个新的区间，否则说明next.start在[prev.start,prev.end]内，则只要
		 * 看next.end是否大于prev.end，如果大于就要合并区间(扩大)
		 */
		Interval prev = null;
		for(Interval item : intervalList) {
			if(prev == null || prev.getEnd() < item.getStart()) {
				result.add(item);
				prev = item;
			} else if(prev.getEnd() < item.getEnd()) {
				prev.setEnd(item.getEnd());
			}
		}
		
		return result;
	}
	
	/**
	 * 抽象概念，数据桶。用来容纳数字区间。
	 * <p>
	 * 比如一个数据桶包含三个数字区间，
	 * <ul>
	 * <li>区间一 [1,6]    共6个数字</li>
	 * <li>区间二 [10,12]  共3个数字</li>
	 * <li>区间三 [15,18]  共4个数字</li>
	 * </ul>
	 * 该桶内总共包含了13个数字，通过count()方法获取。区间列表，通过intervalList()方法获取。
	 * @version 1.0 2018/10/18
	 * @author dongliyang
	 */
	public static class Bucket implements Serializable {
		
		/** SerialVersionUID */
		private static final long serialVersionUID = 3595217834910037303L;
		
		private int count;
		private List<Interval> intervalList;
		
		public Bucket() {
			this.count = 0;
			this.intervalList = new ArrayList<>();
		}
		public void add(Interval interval) {
			this.intervalList.add(interval);
			this.count += interval.getCount();
		}
		public int count() {
			return this.count;
		}
		public List<Interval> intervalList(){
			return this.intervalList;
		}
	}
	
	/**
	 * 数字区间，包含开始和结束。
	 * <p>
	 * 比如[1,6]表示[1,2,3,4,5,6]的数字区间。
	 * <p>
	 * 开始结束可以相同，比如[7,7]表示单个数字7。
	 * @version 1.0 2018/10/18
	 * @author dongliyang
	 */
	public static class Interval implements Serializable {
		
		/** SerialVersionUID */
		private static final long serialVersionUID = -1202067691909442909L;
		
		/** 开始 */
		private int start;
		/** 结束 */
		private int end;
		
		public Interval(int start,int end) {
			this.start = start;
			this.end = end;
		}
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}
		public int getCount() {
			return (end - start) + 1;
		}
	}
}