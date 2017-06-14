package dao;

import javax.persistence.*;

public class EMFService {
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("gmsk.odb");
	private EMFService() {
	}
	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
