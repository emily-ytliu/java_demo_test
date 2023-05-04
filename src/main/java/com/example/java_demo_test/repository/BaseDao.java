package com.example.java_demo_test.repository;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

public class BaseDao {

	@PersistenceContext  //JPA盡Τ爹睦
	private EntityManager entityManager;  //まEntityManager
	/*
	 * 舦ノprotected┪public常ぇ璶膥┯(extends)程舦ノprotected
	 * <EntityType> List<EntityType> 獂List (EntityType琌﹚竡)
	 * Mapボ 把计key琌﹃(String)value琌Object(琌ListString...)
	 * Class<EntityType>ボ 肚篈
	 * Query 璶import persistence
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz) {
		Query query = entityManager.createQuery(sql, clazz);  //硓筁EntityManagerㄓミQuery
		return doQuery(sql, params, clazz, -1);  //纔て糶猭: ㊣盿Τ把计doQuery材把计琵ア
		
//		//狦Mappaarams ぃ琌nullぃ琌
//		if (!CollectionUtils.isEmpty(params)) {
//			//糶猭
//			//Mapforeach
//			for (Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());  //把计key㎝value
//			}
//			//糶猭
////			for (Parameter p : query.getParameters()) {
////				query.setParameter(p, params.get(p.getName()));
////			}
//		}
//		return query.getResultList();
	}
	
	/*
	 * limitSize: 肚掸计
	 * (盿Τ把计doQuery)
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize) {
		Query query = entityManager.createQuery(sql, clazz);  //硓筁EntityManagerㄓミQuery
		return doQuery(sql, params, clazz, limitSize, -1);  //纔て糶猭: ㊣盿Τき把计doQuery材き把计琵ア
		
//		//狦Mappaarams ぃ琌nullぃ琌
//		if (!CollectionUtils.isEmpty(params)) {
//			//糶猭
//			//Mapforeach
//			for (Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());  //把计key㎝value
//			}
//		}
//		if (limitSize > 0) {
//			query.setMaxResults(limitSize);
//		}
//		return query.getResultList();
	}
	
	/*
	 * limitSize: 肚掸计
	 * startPosition: –癬﹍竚
	 * (盿Τき把计doQuery)
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize, int startPosition) {
		Query query = entityManager.createQuery(sql, clazz);  //硓筁EntityManagerㄓミQuery
//		Query query;
//		if (clazz == null) {
//			query = entityManager.createQuery(sql);  //硓筁EntityManagerㄓミQuery
//		} else {
//			query = entityManager.createQuery(sql, clazz);  //硓筁EntityManagerㄓミQuery
//		}
		//Mapparams ぃ琌nullぃ琌
		if (!CollectionUtils.isEmpty(params)) {
			//糶猭
			//Mapforeach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //把计key㎝value
			}
		}
		if (limitSize > 0) {
			query.setMaxResults(limitSize);
		}
		if (limitSize >= 0) {
			query.setFirstResult(startPosition);
		}
		return query.getResultList();
	}
	
	
	/*
	 * 穝
	 */
	protected int doUpdate(String sql, Map<String, Object> params) {
		Query query = entityManager.createQuery(sql);  //硓筁EntityManagerㄓミQuery
		if (!CollectionUtils.isEmpty(params)) {
			//糶猭
			//Mapforeach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //把计key㎝value
			}
		}
		return query.executeUpdate();
	}
	
	/*
	 * createNativeQuery: 钡癸DB暗巨
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doNativeQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize, int startPosition) {
		Query query = entityManager.createNativeQuery(sql, clazz);  //硓筁EntityManagerㄓミQuery
		//Mapparams ぃ琌nullぃ琌
		if (!CollectionUtils.isEmpty(params)) {
			//糶猭
			//Mapforeach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //把计key㎝value
			}
		}
		if (limitSize > 0) {
			query.setMaxResults(limitSize);
		}
		if (limitSize >= 0) {
			query.setFirstResult(startPosition);
		}
		return query.getResultList();
	}
}
