package weather_service.dao;

import db.entity.WeatherEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import weather_service.view.WeatherFilter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

@Repository
public class WeatherDaoImplements implements WeatherDao {
    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public WeatherEntity getByCityAndDate(WeatherFilter filter) {
        if (filter.date == null || StringUtils.isBlank(filter.city)) {
            throw new RuntimeException("date or city is empty");
        }
        Date date = filter.date;
        String city = filter.city;
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<WeatherEntity> criteriaQuery = builder.createQuery(WeatherEntity.class);
        Root<WeatherEntity> root = criteriaQuery.from(WeatherEntity.class);
        criteriaQuery.select(root);
        Predicate criteria = builder.conjunction();
        Predicate datePredicate = builder.equal(root.get("date"), date);
        criteria = builder.and(criteria, datePredicate);
        Predicate cityPredicate = builder.equal(root.get("city"), city);
        criteria = builder.and(criteria, cityPredicate);
        criteriaQuery.where(criteria);

        return em.createQuery(criteriaQuery).getSingleResult();
    }
}
