package org.iesalixar.poriad.dto;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import org.iesalixar.poriad.entity.Forfait;
import org.iesalixar.poriad.security.dto.NewUser;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

	private String name;

	private String location;

	private String country;

	private Date openingDate;

	private Date closingDate;

	private String description;

	private String urlImages;

	private Integer activated;

	@JsonProperty("forfait")
	private Forfait forfait;

}
