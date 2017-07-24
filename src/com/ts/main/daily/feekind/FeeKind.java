package com.ts.main.daily.feekind;
 
import javax.persistence.Entity; 
import javax.persistence.Table; 

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;  

import com.ts.core.annotation.BeanProperty;
import com.ts.core.common.bean.BaseBean;  


@BeanProperty(description="费用类别")
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "DAILY_FeeKind")
public class FeeKind  extends BaseBean{
	  
    
}
