package com.ts.main.sys.knowledge;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ts.core.common.bean.BaseFileBean;


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_KnowledgeFile")
public class KnowledgeFile extends BaseFileBean {
	@ManyToOne(cascade={CascadeType.PERSIST}  ) 
	@JoinColumn(name="knowledge") 
	private Knowledge knowledge;

	public Knowledge getKnowledge() {
		return knowledge;
	}

	public void setKnowledge(Knowledge knowledge) {
		this.knowledge = knowledge;
	}
	
}
