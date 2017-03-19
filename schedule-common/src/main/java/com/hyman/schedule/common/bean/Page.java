package com.hyman.schedule.common.bean;

import java.util.ArrayList;
import java.util.List;



public class Page<T>{
	public static int PAGESIZE = 10;
	private int pageSize = PAGESIZE;
	private List<T> items;
	private int totalCount;
	private int[] indexes = new int[0];
	private int startIndex = 0;
	private int lastStartIndex;
	
	public Page(){
	}

	public Page(List<T> items, int totalCount) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(0);
	}

	public Page(List<T> items, int totalCount, int startIndex) {
		setPageSize(PAGESIZE);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public Page(List<T> items, int totalCount, int pageSize,
			int startIndex) {
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setItems(items);
		setStartIndex(startIndex);
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
			int count = totalCount / pageSize;
			if (totalCount % pageSize > 0)
				count++;
			indexes = new int[count];
			for (int i = 0; i < count; i++) {
				indexes[i] = pageSize * i;
			}
		} else {
			this.totalCount = 0;
		}
	}

	public int[] getIndexes() {
		return indexes;
	}

	public void setIndexes(int[] indexes) {
		this.indexes = indexes;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		if (totalCount <= 0)
			this.startIndex = 0;
		else if (startIndex >= totalCount)
			this.startIndex = indexes[indexes.length - 1];
		else if (startIndex < 0)
			this.startIndex = 0;
		else {
			this.startIndex = indexes[startIndex / pageSize];
		}
	}

	public int getNextIndex() {
		int nextIndex = getStartIndex() + pageSize;
		if (nextIndex >= totalCount)
			return 0;
		else
			return nextIndex;
	}

	public int getPreviousIndex() {
		int previousIndex = getStartIndex() - pageSize;
		if (previousIndex < 0)
			return 0;
		else
			return previousIndex;
	}
	
	public int getLastIndex(){
		int[] indexes = this.getIndexes();
		if(indexes != null && indexes.length >0){
			lastStartIndex = indexes[indexes.length-1];
		}
		return lastStartIndex;
		
	}
	
	public int getPages(){
		if(getTotalCount()%pageSize==0){
			return getTotalCount()/pageSize;
		}
		return (getTotalCount()/pageSize)+1;
	}
	public int getCurrentPage(){
		return (getStartIndex()/pageSize)+1;
	}
	
	/**
	 * 记录显示的页码组
	 * @param totalPages
	 * @param currentPage
	 * @return
	 */
	public ArrayList<Integer> page(int totalPages, int currentPage) {  
		int adjacents = 3;//这个参数可以调ArrayList的长度，试试就知道了
		ArrayList<Integer> result = new ArrayList<Integer>();  
		if (totalPages < (5 + (adjacents * 2))){ // not enough links to make it worth breaking up  
		    writeNumberedLinks(1, totalPages, result);  
		    }else{
			  if ((totalPages - (adjacents * 2) > currentPage) && (currentPage > (adjacents * 2))){ // in the middle  
				  writeNumberedLinks(1, 1, result);  
				  writeElipsis(result);  
				  writeNumberedLinks(currentPage - adjacents - 1, currentPage + adjacents, result);  
				  writeElipsis(result);  
				  writeNumberedLinks(totalPages, totalPages, result);  
			  }else if (currentPage < (totalPages / 2)){
				  writeNumberedLinks(1, 3 + (adjacents * 2), result);  
				  writeElipsis(result);  
				  writeNumberedLinks(totalPages, totalPages, result);  
			  }else{ // at the end  
		            writeNumberedLinks(1, 1, result);  
		            writeElipsis(result);  
		            writeNumberedLinks(totalPages - (2 + (adjacents * 2)), totalPages, result);  
			      }  
			  }  
		 return result;  
	}  
		  
	  /** 
	  * @param result  
	  *  
	  */  
	  private void writeElipsis(ArrayList<Integer> result) {  
		  result.add(-1);//-1是用来打点的
	  }
		   
	  /** 
	  * @param i 
	  * @param lastIndex 
	  * @param result  
	  */  
	private void writeNumberedLinks(int i, int lastIndex, ArrayList<Integer> result) {  
		for (int d=i; d <= lastIndex; d++) {  
		  result.add(d);  
		}
	}    

}