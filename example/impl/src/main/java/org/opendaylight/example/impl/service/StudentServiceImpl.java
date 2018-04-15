package org.opendaylight.example.impl.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.opendaylight.example.impl.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentServiceImpl {
	private EntityManagerFactory emf;
	private static final Logger LOG = LoggerFactory.getLogger(StudentServiceImpl.class);

	public void setEntityManagerFactory(EntityManagerFactory emf) {
		this.emf = emf;
		LOG.info("StudentServiceImpl==emf :{} ", this.emf);
	}

	public void add() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Student student1 = new Student();
			student1.setName("dengdengdeng");
			Student student2 = new Student();
			student2.setName("dengdengdeng02");
			em.persist(student1);
			em.persist(student2);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("add Exception:{}", e);
		} finally {
			em.close();
		}
	}

	public void delete() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Student student = em.find(Student.class, new Integer(7));
			em.remove(student);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("delete Exception:{}", e);
		} finally {
			em.close();
		}
	}

	public void update() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Student student = em.find(Student.class, new Integer(2));
			student.setName("update");
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("update Exception:{}", e);
		} finally {
			em.close();
		}
	}

	public Student get() {
		EntityManager em = emf.createEntityManager();
		Student student = null;
		try {
			em.getTransaction().begin();
			student = em.find(Student.class, new Integer(2));
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("get Exception:{}", e);
		} finally {
			em.close();
		}
		LOG.info("get Student:{}", student);

		return student;
	}

	public List<Student> getAll() {
		EntityManager em = emf.createEntityManager();
		List<Student> pList = null;
		try {
			em.getTransaction().begin();
			pList = em.createQuery("from Student", Student.class).getResultList();
			for (Student student : pList) {
				LOG.info("getAll Student:{}", student.toString());
			}
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("getAll Exception:{}", e);
		} finally {
			em.close();
		}
		return pList;
	}

	public void rollBack() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Student student1 = new Student();
			student1.setName("example1");
			em.persist(student1);

			Student student2 = new Student();
			student2.setName("example2");
			em.persist(student2);

			Student student3 = new Student();
			student3.setId(6);
			student3.setName("example3");
			em.persist(student3);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("rollBack Exception:{}", e);
		} finally {
			em.close();
		}
	}

	public void deleteAll() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.createQuery("delete from Student").executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("deleteAll Exception:{}", e);
		} finally {
			em.close();
		}
		LOG.info("deleteAll");
	}

	private void insertUtil() {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			for (int i = 0; i <= 20; i++) {
				Student student = new Student();
				student.setName("example===" + i);
				em.persist(student);
			}
			em.getTransaction().commit();

		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("insertUtil Exception:{}", e);
		} finally {
			em.close();
		}
		LOG.info("insertUtil");

	}

	public void hqlQuery() {

		insertUtil();
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();

			String hql = "select s from Student s";
			List<Student> pList = em.createQuery(hql, Student.class).getResultList();

			hql = "select s.name from Student s";
			List query = em.createQuery(hql).getResultList();

			hql = "select p.name from Student p where p.id = 5";
			List r = em.createQuery(hql).getResultList();

			hql = "select name, id from Student p";
			List<Object[]> e = em.createQuery(hql).getResultList();

			hql = "select new map(id , name) from Student";
			List<Map> s = em.createQuery(hql).getResultList();
			em.getTransaction().commit();

			for (Student student : pList) {
				LOG.info("select s from Student s, student:{}", student);
			}

			for (int i = 0; i < query.size(); i++) {
				String name = (String) query.get(i);
				LOG.info("select s.name from Student s, name:{}}", name);
			}

			for (int i = 0; i < r.size(); i++) {
				String name = (String) r.get(i);
				LOG.info("select p.name from Student p where p.id = 5, name:{}}", name);
			}

			for (Object[] object : e) {
				String name = (String) object[0];
				int id = ((Integer) object[1]).intValue();
				LOG.info("select name, id from Student p, id{}, name{}}", id, name);
			}

			for (Map map : s) {
				int id = ((Integer) map.get("0")).intValue();
				String name = (String) map.get("1");
				LOG.info("select new map(id , name) from Student, id{}, name{}}", id, name);
			}
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("hqlQuery Exception:{}", e);
		} finally {
			em.close();
		}

	}

	public void pageQuery() {
		// 获取记录，从第5条开始，总共获取6条记录
		EntityManager em = emf.createEntityManager();
		List<Student> pList = null;
		try {
			em.getTransaction().begin();
			pList = em.createQuery("from Student", Student.class).setFirstResult(5).setMaxResults(6)
					.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			LOG.info("hqlQuery Exception:{}", e);
		} finally {
			em.close();
		}

		for (Student student : pList) {
			LOG.info("pageQuery student:{}", student);
		}
	}

}
