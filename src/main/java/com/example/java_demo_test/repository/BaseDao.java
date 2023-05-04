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

	@PersistenceContext  //JPA�M��������
	private EntityManager entityManager;  //�ޤJEntityManager
	/*
	 * �v����protected��public���i�H�A�]������n�~��(extends)�A�̧C�v����protected�~�i�H
	 * <EntityType> List<EntityType> �x����List (EntityType�O�۩w�q���W�r)
	 * Map��� �ѼơAkey�O�r��(String)�Avalue�OObject(�]���i��OList�BString...)
	 * Class<EntityType>��� �^�ǫ��A
	 * Query �nimport persistence��
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz) {
		Query query = entityManager.createQuery(sql, clazz);  //�z�LEntityManager�ӫإ�Query
		return doQuery(sql, params, clazz, -1);  //�u�Ƽg�k: �I�s�a���|�ӰѼƪ�doQuery�A�ĥ|�ӰѼ����L����
		
//		//�p�GMap��paarams ���Onull�B���O��
//		if (!CollectionUtils.isEmpty(params)) {
//			//�g�k�@
//			//Map��foreach
//			for (Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());  //��J�Ѽƪ�key�ȩMvalue��
//			}
//			//�g�k�G
////			for (Parameter p : query.getParameters()) {
////				query.setParameter(p, params.get(p.getName()));
////			}
//		}
//		return query.getResultList();
	}
	
	/*
	 * limitSize: ����^�ǵ���
	 * (�a���|�ӰѼƪ�doQuery)
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize) {
		Query query = entityManager.createQuery(sql, clazz);  //�z�LEntityManager�ӫإ�Query
		return doQuery(sql, params, clazz, limitSize, -1);  //�u�Ƽg�k: �I�s�a�����ӰѼƪ�doQuery�A�Ĥ��ӰѼ����L����
		
//		//�p�GMap��paarams ���Onull�B���O��
//		if (!CollectionUtils.isEmpty(params)) {
//			//�g�k�@
//			//Map��foreach
//			for (Entry<String, Object> item : params.entrySet()) {
//				query.setParameter(item.getKey(), item.getValue());  //��J�Ѽƪ�key�ȩMvalue��
//			}
//		}
//		if (limitSize > 0) {
//			query.setMaxResults(limitSize);
//		}
//		return query.getResultList();
	}
	
	/*
	 * limitSize: ����^�ǵ���
	 * startPosition: �C�����_�l��m
	 * (�a�����ӰѼƪ�doQuery)
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize, int startPosition) {
		Query query = entityManager.createQuery(sql, clazz);  //�z�LEntityManager�ӫإ�Query
//		Query query;
//		if (clazz == null) {
//			query = entityManager.createQuery(sql);  //�z�LEntityManager�ӫإ�Query
//		} else {
//			query = entityManager.createQuery(sql, clazz);  //�z�LEntityManager�ӫإ�Query
//		}
		//Map��params ���Onull�B���O��
		if (!CollectionUtils.isEmpty(params)) {
			//�g�k�@
			//Map��foreach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //��J�Ѽƪ�key�ȩMvalue��
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
	 * ��s
	 */
	protected int doUpdate(String sql, Map<String, Object> params) {
		Query query = entityManager.createQuery(sql);  //�z�LEntityManager�ӫإ�Query
		if (!CollectionUtils.isEmpty(params)) {
			//�g�k�@
			//Map��foreach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //��J�Ѽƪ�key�ȩMvalue��
			}
		}
		return query.executeUpdate();
	}
	
	/*
	 * createNativeQuery: ������DB���ާ@
	 */
	@SuppressWarnings("unchecked")
	protected <EntityType> List<EntityType> doNativeQuery(String sql, Map<String, Object> params, Class<EntityType> clazz, 
			int limitSize, int startPosition) {
		Query query = entityManager.createNativeQuery(sql, clazz);  //�z�LEntityManager�ӫإ�Query
		//Map��params ���Onull�B���O��
		if (!CollectionUtils.isEmpty(params)) {
			//�g�k�@
			//Map��foreach
			for (Entry<String, Object> item : params.entrySet()) {
				query.setParameter(item.getKey(), item.getValue());  //��J�Ѽƪ�key�ȩMvalue��
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
