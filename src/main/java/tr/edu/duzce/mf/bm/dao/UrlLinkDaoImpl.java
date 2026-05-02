package tr.edu.duzce.mf.bm.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tr.edu.duzce.mf.bm.entity.UrlLink;

import java.util.List;

@Repository // Spring'e bu sınıfın bir veritabanı erişim sınıfı olduğunu söyleriz
public class UrlLinkDaoImpl implements UrlLinkDao {

    @Autowired
    private SessionFactory sessionFactory; // HibernateConfig'de oluşturduğumuz havuz

    // O anki aktif veritabanı oturumunu getirir
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(UrlLink urlLink) {
        getCurrentSession().persist(urlLink); // Veritabanına INSERT yapar
    }

    @Override
    public UrlLink findByShortCode(String shortCode) {
        // Kısa koda göre veritabanından sorgulama (SELECT) yapar
        Query<UrlLink> query = getCurrentSession().createQuery("FROM UrlLink WHERE shortCode = :code", UrlLink.class);
        query.setParameter("code", shortCode);
        return query.uniqueResult();
    }

    @Override
    public void update(UrlLink urlLink) {
        getCurrentSession().merge(urlLink); // Veritabanında UPDATE yapar
    }

    @Override
    public List<UrlLink> findAll() {
        return getCurrentSession().createQuery("FROM UrlLink", UrlLink.class).getResultList();
    }
}