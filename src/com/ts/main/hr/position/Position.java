package com.ts.main.hr.position;
 
import javax.persistence.Entity; 
import javax.persistence.Table; 

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;  

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;  


@BeanProperty(description="职位")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "HR_Position")
public class Position  extends BaseBean {
	  
    
}
