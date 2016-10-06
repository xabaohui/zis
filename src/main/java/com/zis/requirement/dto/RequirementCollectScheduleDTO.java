package com.zis.requirement.dto;

/**
 * 图书需求量收集进度DTO
 * 
 * @author yz
 * 
 */
public class RequirementCollectScheduleDTO {

	private Integer partId;
	private String college;
	private String institute;
	private String partName;
	private Integer grade;
	private Integer term;
	private String operator;
	private Integer count;

	public RequirementCollectScheduleDTO() {
	}

	// partId, college, institute, partName, grade, term, count(*)
	public RequirementCollectScheduleDTO(Integer partId, String college,
			String institute, String partName, Integer grade, Integer term,
			Long count) {
		super();
		this.partId = partId;
		this.college = college;
		this.institute = institute;
		this.partName = partName;
		this.grade = grade;
		this.term = term;
		this.count = count.intValue();
	}

	public RequirementCollectScheduleDTO(Integer partId, String college,
			String institute, String partName, Integer grade, Integer term,
			String operator, Long count) {
		super();
		this.partId = partId;
		this.college = college;
		this.institute = institute;
		this.partName = partName;
		this.grade = grade;
		this.term = term;
		this.operator = operator;
		this.count = count.intValue();
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
