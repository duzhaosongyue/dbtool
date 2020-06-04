package com.csf.dbtool.model;

import java.util.List;

public class Page<E> implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * 默认条数
	 */
	public  final static  Integer PAGE_SIZE_DEFAULT=10;
	
	/**
	 * 当前页
	 */
	private Integer currentPage=1;
	
	/**
	 * 上一页
	 */
	private Integer pre=0;
	
	/**
	 * 下一页
	 */
	private Integer next=0;
	
	private int pageNum=1;
	
	/**
	 * 每页显示条数
	 */
	private Integer numPerPage=PAGE_SIZE_DEFAULT;
	
	/**
	 * 页数
	 */
	private Integer totalPage=0;
	
	/**
	 * 页数
	 */
	private Integer wideCount;
	
	/**
	 * 页数
	 */
	private Integer totalNWCount;
	
	/**
	 * 条数
	 */
	private Integer totalCount=0;
	
	/**
	 * 根据那列排序显示
	 */
	private String orderBy;
	
	/**
	 * 排序
	 */
	private String order;
	
	/**
	 * 结果集
	 */
	private List<E> result;

	private String flag;

	private Integer count;

	public Integer getCurrentPage() {
		currentPage=pageNum;
		//当页面传来的 当前页大于 总页数，显示最后一页
		 if (currentPage>totalPage) {
			 currentPage=totalPage;
		 }
		
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPre() {
		return pre;
	}

	public void setPre(Integer pre) {
		this.pre = pre;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	
	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalNWCount() {
		return totalNWCount;
	}

	public void setTotalNWCount(Integer totalNWCount) {
		this.totalNWCount = totalNWCount;
	}

	public Integer getTotalPage() {
		//在总条数里求总页数 
		if(totalCount%numPerPage>0){
			totalPage= (totalCount/numPerPage+1);
		}else{
			totalPage= totalCount/numPerPage;
		}
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Integer getTotalCount() {
		
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<E> getResult() {
		return result;
	}

	public void setResult(List<E> result) {
		this.result = result;
	}
	
	 public int getPageNum() {
		
	   return pageNum;
	 }
	 
	 public void setPageNum(int pageNum) {
	   this.pageNum = pageNum;
	 }
	public static Integer getPageSizeDefault() {
		return PAGE_SIZE_DEFAULT;
	}
	
	
	 public int getFirstOfPage()
	  {
	    return ((this.pageNum - 1) * this.numPerPage + 1);
	  }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getWideCount() {
		return wideCount;
	}

	public void setWideCount(Integer wideCount) {
		this.wideCount = wideCount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Page [currentPage=" + currentPage + ", pre=" + pre + ", next=" + next + ", pageNum=" + pageNum
				+ ", numPerPage=" + numPerPage + ", totalPage=" + totalPage + ", totalCount=" + totalCount
				+ ", orderBy=" + orderBy + ", order=" + order + ", result=" + result + ", flag=" + flag + "]";
	}

	
	

}
