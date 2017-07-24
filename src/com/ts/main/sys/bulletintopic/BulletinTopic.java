package com.ts.main.sys.bulletintopic;
 
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.Table; 

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;  

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;  


@BeanProperty(description="公告栏栏目设置")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "SYS_BulletinTopic")
public class BulletinTopic  extends BaseBean{
	
	@BeanProperty(description="公告大类别")
    @Column(length = 20)  
	private String topicKind; 

	public String getTopicKind() {
		return topicKind;
	}

	public void setTopicKind(String topicKind) {
		this.topicKind = topicKind;
	}
    
}
