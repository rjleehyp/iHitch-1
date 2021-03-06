package model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.bean.Car;
import model.dao.superInterface.CarDAO;

@Repository
public class CarDAOHibernate implements CarDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Car select(int id) {
		return this.getSession().get(Car.class, id);
	}

	@Override
	public List<Car> select() {
		return this.getSession().createQuery("FROM Car", Car.class).list();
	}

	@Override
	public Car select(String carNo) {
		return this.getSession().createQuery("FROM Car WHERE carNo= :carNo", Car.class).setParameter("carNo", carNo)
				.uniqueResult();
	}

	@Override
	public Integer insert(Car bean) {
		if (bean != null) {
			Car temp = this.getSession().createQuery("FROM Car WHERE carNo= :carNo", Car.class)
					.setParameter("carNo", bean.getCarNo()).uniqueResult();
			if (temp == null) {
				return (Integer) this.getSession().save(bean);
			}
		}
		return null;
	}

	@Override
	public Car update(Car bean) {
		if (bean != null) {
			Car result = this.getSession().get(Car.class, bean.getCarId());
			if (result != null) {
				result.setCarNo(bean.getCarNo());
				result.setCarBrand(bean.getCarBrand());
				result.setCarType(bean.getCarType());
				result.setColor(bean.getColor());
				result.setCarDocPhoto(bean.getCarDocPhoto());
				result.setPcFriendly(bean.getPcFriendly());
				result.setMid(bean.getMid());
			}
			return result;
		}
		return null;
	}

	@Override
	public boolean delete(int id) {
		Car temp = this.getSession().get(Car.class, id);
		if (temp != null) {
			this.getSession().delete(temp);
			return true;
		}
		return false;
	}

}