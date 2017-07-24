package com.ts.main.sys.knowledge;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;

@Entity
@Table(name = "SYS_KnowledgeType")
public class KnowledgeType extends BaseBean {
	@BeanProperty(description = "知识类型父ID")
	private Integer knowledgeTypeId;
	 
	@BeanProperty(description = "默认产品类别")
	@Column(length = 10)
	private String defaultType;
	
	@BeanProperty(description = "类型名称")
	@Column(length = 50)
	private String typeName;
	

	public Integer getKnowledgeTypeId() {
		return knowledgeTypeId;
	}

	public void setKnowledgeTypeId(Integer knowledgeTypeId) {
		this.knowledgeTypeId = knowledgeTypeId;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
}
