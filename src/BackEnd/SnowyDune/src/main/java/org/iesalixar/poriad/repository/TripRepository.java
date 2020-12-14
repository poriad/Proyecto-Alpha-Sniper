package org.iesalixar.poriad.repository;

import java.util.List;
import org.iesalixar.poriad.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(collectionResourceRel = "trip", path = "trip")
@CrossOrigin
public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query(value = "SELECT * FROM trip where entry_date > SYSDATE() and user_id = :userId ORDER BY entry_date ASC Limit 0, 5", nativeQuery = true)
	List<Trip> getTripsSysdate(@Param("userId") Integer userId);

	@Query(value = "SELECT * FROM trip where user_id = :userId and (entry_date BETWEEN '2020-01-01' AND SYSDATE()) ORDER BY entry_date ASC Limit 0, 3", nativeQuery = true)
	List<Trip> getTripsDoneThisYear(@Param("userId") Integer userId);

	@Query(value = "SELECT * FROM trip where user_id = :userId and entry_date BETWEEN DATE_SUB(NOW(), INTERVAL 30 DAY) AND NOW() ORDER BY entry_date", nativeQuery = true)
	List<Trip> getTripsDoneLastMonth(@Param("userId") Integer userId);

	@Query(value = "SELECT * FROM trip where entry_date BETWEEN '2019-01-30' AND '2020-01-01' AND user_id = :userId ORDER BY entry_date ASC Limit 0, 3", nativeQuery = true)
	List<Trip> getTripsDoneLastYear(@Param("userId") Integer userId);

	@Query(value = "SELECT t FROM Trip t WHERE t.cart.id is not null AND t.user.id = :id and (t.checkout = 0 or t.checkout = 1) and t.payment.id is null")
	List<Trip> getTripsInCart(@Param("id") Long id);

	@Query(value = "SELECT t FROM Trip t WHERE t.cart.id is not null AND t.user.id = :id and t.checkout = 1 and t.payment.id is null")
	List<Trip> getTripsInCartForCheckout(@Param("id") Long id);

	@Modifying
	@Query(value = "UPDATE FROM Trip t SET t.checkout = 1 WHERE t.id = :id")
	void updateTripToCheckout(@Param("id") Long tripId);

	@Modifying
	@Query(value = "UPDATE FROM Trip t SET t.payment.id = :paymentId WHERE t.id = :tripId")
	void updateTripPaymentDone(@Param("paymentId") Long paymentId, @Param("tripId") Long tripId);

	@Modifying
	@Query(value = "DELETE FROM Trip t WHERE t.id = :id")
	void deleteTrip(@Param("id") Long id);

	@Modifying
	@Query(value = "UPDATE Trip t SET t.user.id = :userId, t.station.id = :stationId, t.hotel.id = :hotelId, t.classes.id = :classesId, t.skiMaterial.id = :skiMaterialId, t.carRental.id = :carRentalId, t.cart.id = :cartId  WHERE t.id = :id")
	void updateTrip(@Param("id") Long id, @Param("userId") Long userid, @Param("stationId") Long stationId,
			@Param("hotelId") Long hotelId, @Param("classesId") Long classesId,
			@Param("skiMaterialId") Long skiMaterialId, @Param("carRentalId") Long carRentalId,
			@Param("cartId") Long cartId);

}
