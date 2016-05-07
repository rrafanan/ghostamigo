package preferences.beans;

import java.util.ArrayList;
import java.util.List;


public class UserPreferenceValueObject {
    private int recordSetStart = 0;
    private int recordSetEnd = 0;
    private int pageSize = 0;
    private int currentPage = 0;
    private int totalPages = 0;
    private int totalRecords = 0;
    
    private List <UserPreferenceBean>preferenceList = new ArrayList<UserPreferenceBean>();
    
	public List <UserPreferenceBean> getPreferenceList() {
		return preferenceList;
	}
	public int getRecordSetStart() {
		return recordSetStart;
	}
	public void setRecordSetStart(int recordSetStart) {
		this.recordSetStart = recordSetStart;
	}
	public int getRecordSetEnd() {
		return recordSetEnd;
	}
	public void setRecordSetEnd(int recordSetEnd) {
		this.recordSetEnd = recordSetEnd;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public void setPreferenceList(List<UserPreferenceBean> preferenceList) {
		this.preferenceList = preferenceList;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
    

}
