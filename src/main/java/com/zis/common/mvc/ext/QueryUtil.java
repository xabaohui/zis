package com.zis.common.mvc.ext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

public class QueryUtil<T> {

	private final Logger logger = LoggerFactory.getLogger(QueryUtil.class);

	private final String EQ = "eq";
	private final String LIKE = "like";
	private final String NE = "ne";
	private final String IN = "in";
	private final String LT = "lt";
	private final String GE = "ge";
	private final String LE = "le";
	private final String BETWEEN = "between";
	private final String GROUP = "group";

	private List<Condition> conditions = new ArrayList<QueryUtil.Condition>();
	private List<Order> orderList = new ArrayList<QueryUtil.Order>();

	public QueryUtil<T> eq(String name, Object value) {
		conditions.add(new Condition(EQ, name, value));
		return this;
	}

	public QueryUtil<T> like(String name, Object value) {
		conditions.add(new Condition(LIKE, name, value));
		return this;
	}

	public QueryUtil<T> ne(String name, Object value) {
		conditions.add(new Condition(NE, name, value));
		return this;
	}

	public QueryUtil<T> in(String name, Object value) {
		conditions.add(new Condition(IN, name, value));
		return this;
	}

	public QueryUtil<T> lt(String name, Object value) {
		conditions.add(new Condition(LT, name, value));
		return this;
	}

	public QueryUtil<T> ge(String name, Object value) {
		conditions.add(new Condition(GE, name, value));
		return this;
	}

	public QueryUtil<T> le(String name, Object value) {
		conditions.add(new Condition(LE, name, value));
		return this;
	}

	public QueryUtil<T> between(String name, Object max, Object min) {
		conditions.add(new Condition(BETWEEN, name, max, min));
		return this;
	}

	public QueryUtil<T> asc(String name) {
		orderList.add(new Order(name, true));
		return this;
	}

	public QueryUtil<T> desc(String name) {
		orderList.add(new Order(name, false));
		return this;
	}

	public QueryUtil<T> groupBy(String name) {
		conditions.add(new Condition(GROUP, name));
		return this;
	}

	/**
	 * 获取Specification 的对象
	 * 
	 * @return
	 */
	public Specification<T> getSpecification() {
		return new Specification<T>() {

			@SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
			public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<String> groupNames = null;
				List<Predicate> psList = new ArrayList<Predicate>();
				for (Condition c : conditions) {
					if (EQ.equals(c.condition)) {
						Path<String> p = root.get(c.name);
						psList.add(cb.equal(p, c.vals[0]));
					}

					if (LIKE.equals(c.condition)) {
						String likeName = c.vals[0].toString();
						Path<String> p = root.get(c.name);
						psList.add(cb.like(p, likeName));
					}

					if (NE.equals(c.condition)) {
						Path<String> p = root.get(c.name);
						psList.add(cb.notEqual(p, c.vals[0]));
					}

					if (LT.equals(c.condition)) {
						Object value = c.vals[0];
						Path<String> p = root.get(c.name);
						if (value instanceof Date) {
							psList.add(cb.lessThan(p.as(Date.class), (Date) value));
						} else if (value instanceof String) {
							psList.add(cb.lessThan(p.as(String.class), value.toString()));
						} else {
							psList.add(cb.lt(p.as(Number.class), (Number) value));
						}
					}

					if (IN.equals(c.condition)) {
						psList.add(root.get(c.name).in(c.vals));
					}

					if (BETWEEN.equals(c.condition)) {
						Path p = root.get(c.name);
						Object max = c.vals[0];
						Object min = c.vals[1];
						psList.add(cb.between(p.as(Comparable.class), (Comparable) max, (Comparable) min));
					}

					if (GE.equals(c.condition)) {
						Object value = c.vals[0];
						Path<String> p = root.get(c.name);
						if (value instanceof Date) {
							psList.add(cb.greaterThanOrEqualTo(p.as(Date.class), (Date) value));
						} else if (value instanceof String) {
							psList.add(cb.greaterThanOrEqualTo(p.as(String.class), value.toString()));
						} else {
							psList.add(cb.ge(p.as(Number.class), (Number) value));
						}
					}

					if (LE.equals(c.condition)) {
						Object value = c.vals[0];
						Path<String> p = root.get(c.name);
						psList.add(cb.le(p.as(Number.class), (Number) value));
					}

					if (GROUP.equals(c.condition)) {
						String name = c.name;
						if (groupNames == null) {
							groupNames = new ArrayList<String>();
						}
						groupNames.add(name);
					}

				}
				Predicate[] ps = new Predicate[psList.size()];
				query.where(psList.toArray(ps));

				for (Order order : orderList) {
					String orderName = order.name;
					if (order.asc) {
						query.orderBy(cb.asc(root.get(orderName)));
					} else {
						query.orderBy(cb.desc(root.get(orderName)));
					}
				}

				// 判断集合groupNames是否为空如果不为空则添加group条件
				if (groupNames != null && !groupNames.isEmpty()) {
					for (String s : groupNames) {
						query.groupBy(root.get(s));
					}
				}
				return query.getRestriction();
			}
		};
	}

	public static class Condition {

		private String name;
		private Object[] vals;
		private String condition;

		public Condition(String condition, String name, Object... vals) {
			this.name = name;
			this.vals = vals;
			this.condition = condition;
		}
	}

	public static class Order {

		private String name;
		private boolean asc;

		public Order(String name, boolean asc) {
			super();
			this.name = name;
			this.asc = asc;
		}
	}
}
