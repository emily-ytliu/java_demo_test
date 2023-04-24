package com.example.java_demo_test.vo;

import java.util.List;

import com.example.java_demo_test.entity.PersonInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddPersonInfoRequest {

	@JsonProperty("person_info_list")
	private List<PersonInfo> personInfoList;

	public List<PersonInfo> getPersonInfoList() {
		return personInfoList;
	}

	public void setPersonInfo(List<PersonInfo> personInfo) {
		this.personInfoList = personInfo;
	}
	
	
}
