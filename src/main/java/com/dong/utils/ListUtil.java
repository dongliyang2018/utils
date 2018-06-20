package com.dong.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * List集合帮助类
 * @version 1.0 2018/06/14
 * @author dongliyang
 */
public final class ListUtil {

	private ListUtil() {}
	
	/**
	 * 拆分List集合
	 * <pre>
	 * 例如:
	 * 有List集合datas，将其拆分为多个子集合，每个子集合包含2个元素。
	 * List&lt;String&gt; datas = Arrays.asList("1","2","3","4","5");
	 * List&lt;List&lt;String&gt;&gt; splitDatas = ListUtils.split(datas,2);
	 * 
	 * 拆分后：splitDatas各子集合包含的元素
	 * index: 0 -> ["1","2"]
	 * index: 1 -> ["3","4"]
	 * index: 2 -> ["5"]
	 * </pre>
	 * @param datas List集合
	 * @param splitSize 子集合的大小
	 * @return List<List<T>>
	 */
	public static <T> List<List<T>> split(List<T> datas,int splitSize){
		if(datas == null || splitSize < 1){
			return null;
		}

		int totalSize = datas.size();
		int count = (totalSize % splitSize == 0) ? (totalSize / splitSize) : (totalSize / splitSize + 1);
		List<List<T>> rows = new ArrayList<>();

		for(int i = 0; i < count; i++){
			List<T> cols = datas.subList(i * splitSize, (i == count - 1) ? totalSize : splitSize * (i + 1));
			rows.add(cols);
		}
		return rows;
	}
	
	/**
	 * 等分List集合，将集合分割为N份
	 * <pre>
	 * 例如:
	 * 有List集合datas，将其等分为2个子集合。
	 * List&lt;String&gt; datas = Arrays.asList("1","2","3","4","5");
	 * List&lt;List&lt;String&gt;&gt; chunksDatas = ListUtils.chunks(datas,2);
	 * 
	 * 等分后：chunksDatas各子集合包含的元素
	 * index: 0 -> ["1","2","3"]
	 * index: 1 -> ["4","5"]
	 * 
	 * <b>*特别注意*</b>：如果datas.size < num，即：集合不够等分，则后面的子集合存在为空的情况。
	 * 例如，将上面的集合，等分为6个子集合。
	 * 等分后：chunksDatas各子集合包含的元素
	 * index: 0 -> ["1"]
	 * index: 1 -> ["2"]
	 * index: 2 -> ["3"]
	 * index: 3 -> ["4"]
	 * index: 4 -> ["5"]
	 * index: 5 -> []
	 * </pre>
	 * @param datas List集合
	 * @param num N份
	 * @return List<List<T>>
	 */
	public static <T> List<List<T>> chunks(List<T> datas,int num){
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = datas.size() % num;
		int number = datas.size() / num;
		int offset = 0;
		for(int i = 0; i < num; i++){
			List<T> value = null;
			if(remaider > 0){
				value = datas.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			}else{
				value = datas.subList(i * number + offset, (i + 1) * number + offset);
			}
		    result.add(value);
		}
		return result;
	}
	
	/**
	 * 求两个集合的差集
	 * @param list1 集合1
	 * @param list2 集合2
	 * @return List<String>
	 */
	public static List<String> minus(List<String> list1,List<String> list2){
		List<String> list = new ArrayList<String>();
		List<String> history = new ArrayList<String>();
		List<String> longerList = null;
		List<String> shorterList = null;
		
		if(list1.size() > list2.size()){
			longerList = list1;
			shorterList = list2;
		} else {
			longerList = list2;
			shorterList = list1;
		}
		
		for(String e : longerList){
			if(!list.contains(e)){
				list.add(e);
			}
		}
		
		for(String e : shorterList){	
			if(list.contains(e)){
				history.add(e);
				list.remove(e);
			} else {
				if(!history.contains(e)){
					list.add(e);
				}
			}
		}
		
		return list;
	}
	
	/**
	 * 求两个集合的交集
	 * @param list1 集合1
	 * @param list2 集合2
	 * @return
	 */
	public static List<String> intersect(List<String> list1,List<String> list2){
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		for(String e : list1){
			if(!map.containsKey(e)){
				map.put(e, Boolean.FALSE);
			}
		}
		
		for(String e : list2){
			if(map.containsKey(e)){
				map.put(e, Boolean.TRUE);
			}
		}
		
		List<String> list = new ArrayList<String>();
		for(Entry<String, Boolean> e : map.entrySet()){
			if(e.getValue().equals(Boolean.TRUE)){
				list.add(e.getKey());
			}
		}
		return list;
	}
}
