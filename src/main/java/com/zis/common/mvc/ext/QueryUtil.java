package com.zis.common.mvc.ext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class QueryUtil<T> {

	List<T> Classlist = new ArrayList<T>();
	List<String> conditionList = new ArrayList<String>();
	Class<T> entityClass;

	public QueryUtil(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public QueryUtil<T> eq(String name, Object value) {
		return savePList(name, value, "eq");
	}

	public QueryUtil<T> like(String name, Object value) {
		return savePList(name, value, "like");
	}

	public QueryUtil<T> ne(String name, Object value) {
		return savePList(name, value, "ne");
	}

	/**
	 * 获取Specification 的对象
	 * 
	 * @return
	 */
	public Specification<T> getSpecification() {
		return new Specification<T>() {

			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				final Integer size = Classlist.size();
				Predicate[] ps = new Predicate[size];
				for (int i = 0; i < size; i++) {
					if ("eq".equals(conditionList.get(i))) {
						Path<String> p = root.get(getName(i));
						ps[i] = cb.equal(p, getValue(i));
					}
					if ("like".equals(conditionList.get(i))) {
						String likeName = getValue(i).toString();
						Path<String> p = root.get(getName(i));
						ps[i] = cb.like(p, likeName);
					}
					if ("ne".equals(conditionList.get(i))) {
						Path<String> p = root.get(getName(i));
						ps[i] = cb.notEqual(p, getValue(i));
					}
				}
				return cb.and(ps);
			}
		};
	}

	/**
	 * 获取集合中属性不为空的值
	 * 
	 * @param i
	 * @return
	 */
	private Object getValue(Integer i) {
		Object value = null;
		Method[] mode = Classlist.get(i).getClass().getMethods();
		for (Method method : mode) {
			if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
				try {
					if (method.invoke(Classlist.get(i)) != null) {
						value = method.invoke(Classlist.get(i));
						return value;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * 获取类中属性不为空的名称
	 * 
	 * @param i
	 * @return
	 */
	private String getName(Integer i) {
		String fieldName = null;
		Method[] mode = Classlist.get(i).getClass().getMethods();
		for (Method method : mode) {
			if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
				try {
					if (method.invoke(Classlist.get(i)) != null) {
						String methodName = method.getName().substring(3);
						fieldName = new StringBuilder().append(Character.toLowerCase(methodName.charAt(0)))
								.append(methodName.substring(1)).toString();
						return fieldName;
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return fieldName;
	}

	/**
	 * 存储集合的方式通过反射获取 对应的对象进入
	 * 
	 * @param name
	 * @param value
	 * @param condition
	 * @return
	 */
	private QueryUtil<T> savePList(String name, Object value, String condition) {
		try {
			T newEntity = entityClass.newInstance();
			Method[] entityMethod = newEntity.getClass().getMethods();
			for (Method method : entityMethod) {
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					String methodSetName = method.getName().substring(3);
					if (methodSetName.equalsIgnoreCase(name)) {
						method.invoke(newEntity, value);
					}
				}
			}
			conditionList.add(condition);
			Classlist.add(newEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
}
