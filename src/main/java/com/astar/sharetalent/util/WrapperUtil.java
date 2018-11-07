package com.astar.sharetalent.util;

import com.astar.sharetalent.common.annotation.Query;
import com.baomidou.mybatisplus.enums.SqlLike;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xiaoleilu.hutool.util.StrUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WrapperUtil {
	
	private static SimpleDateFormat sdfyMd = new SimpleDateFormat("yyyy-MM-dd");

	public static Wrapper getWrapperByVO(Object vo){
		EntityWrapper ew=new EntityWrapper();
		Class clazz = vo.getClass();
        try {
			ew.setEntity(clazz.newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field:fields){
			String property = field.getName();
			String getMethodName = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
			try {
				if (!"serialVersionUID".equals(property)){
					Method getMethod = clazz.getDeclaredMethod(getMethodName, new Class[]{ });
					Object o = getMethod.invoke(vo, null);
					boolean isAnno = field.isAnnotationPresent(Query.class);
					if (isAnno){
						Query query = field.getAnnotation(Query.class);
						if (StrUtil.isBlank(query.sqlColumn())){
							createSqlAndParam(StrUtil.toUnderlineCase(property),query,o,ew);
						}else {
							createSqlAndParam(query.sqlColumn(),query,o,ew);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ew;
	}
	

	
	private static void createSqlAndParam(String property,Query query,Object o,EntityWrapper ew){
		
		try {
			if(o!=null){
				if(o instanceof String && "".equals(o.toString())){
					return;
				}else{
					if(null == query){
						 ew.eq(property,o.toString());
						return;
					}
					if(query.type().equals(Query.LIKE)){
						ew.like(property,o.toString(), SqlLike.DEFAULT);
					}else if(query.type().equals(Query.LEFTLIKE)){
						ew.like(property,o.toString(), SqlLike.LEFT);
					}else if(query.type().equals(Query.RIGHTLIKE)){
						ew.like(property,o.toString(), SqlLike.RIGHT);
					}else if(query.type().equals(Query.LESS)){
						ew.lt(property, o.toString());
					}else if(query.type().equals(Query.LESSEQUAL)){
						
						if(o instanceof Date){
							Date end = (Date)o;
							String dateStr = sdfyMd.format(end);
							ew.le(property, dateStr + " 23:59:59");
						}else{
							ew.le(property, o.toString());
						}
					}else if(query.type().equals(Query.MORE)){
						ew.gt(property, o.toString());
					}else if(query.type().equals(Query.MOREEQUAL)){
						if(o instanceof Date){
							Date end = (Date)o;
							String dateStr = sdfyMd.format(end);
							ew.ge(property, dateStr + " 00:00:00");
						}else{
							ew.ge(property, o.toString());
						}
					}else{
						ew.eq(property, o.toString());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} 
		
	}
	
	
}
