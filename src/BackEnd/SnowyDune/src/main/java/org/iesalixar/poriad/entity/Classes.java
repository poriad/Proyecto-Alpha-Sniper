package org.iesalixar.poriad.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.iesalixar.poriad.security.entity.UserSnowy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Classes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double priceHour;

	@Column(columnDefinition = "varchar(1400)")
	private String description;

	@Column(unique = true)
	private String name;

	private String phone;

	private String email;

	private String location;

	private String country;

	private String urlImages;

	private Integer activated;

	// quitar el ignore
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = true)
	private UserSnowy user;

	// quitar el ignore
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;

	@JsonIgnore
	@OneToMany(mappedBy = "classes")
	private Set<Comment> comments;

}
