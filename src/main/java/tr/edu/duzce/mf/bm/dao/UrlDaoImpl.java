package tr.edu.duzce.mf.bm.dao;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tr.edu.duzce.mf.bm.entity.UrlLink;

@Repository
public class UrlDaoImpl implements UrlDao {
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void save(UrlLink urlLink) {
        sessionFactory.getCurrentSession().persist(urlLink);
    }
    
    @Override
    public UrlLink findByShortCode(String shortCode) {
        String hql = "FROM UrlLink WHERE shortCode = :shortCode";
        Query<UrlLink> query = sessionFactory.getCurrentSession()
                .createQuery(hql, UrlLink.class);
        query.setParameter("shortCode", shortCode);
        
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public boolean existsByShortCode(String shortCode) {
        String hql = "SELECT COUNT(u) FROM UrlLink WHERE shortCode = :shortCode";
        Query<Long> query = sessionFactory.getCurrentSession()
                .createQuery(hql, Long.class);
        query.setParameter("shortCode", shortCode);
        
        Long count = query.getSingleResult();
        return count != null && count > 0;
    }
}
