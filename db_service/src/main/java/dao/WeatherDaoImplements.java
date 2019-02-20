package dao;

import entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;



public class WeatherDaoImplements implements WeatherDao {
    private final Logger log = LoggerFactory.getLogger(WeatherDaoImplements.class);

    @PersistenceContext(unitName = "manager")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Weather entity) {
        em.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isForecastDuplicate(Date date, String city) {
        Query query = em.createQuery(
                "select count (*) from Weather w where w.date =:date  and w.city =:city"
        );
        query.setParameter("date", date);
        query.setParameter("city", city);
        Long count = (Long) query.getSingleResult();
        log.debug("Number of entries with duplicate values: {}" + count);
        return count == 0;
    }
}