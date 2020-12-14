package org.iesalixar.poriad.service;

import java.util.List;

import org.iesalixar.poriad.entity.Station;
import org.iesalixar.poriad.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StationService {

	@Autowired
	StationRepository stationRepository;

	public List<Station> listStation() {
		return stationRepository.findAll();
	}

	public List<Station> listStationStatus(Integer status) {
		return stationRepository.listStationStatus(status);
	}

	public void updateStationStatus(Long id, Integer status) {
		stationRepository.updateStationStatus(id, status);
	}

	public void saveStation(Station station) {
		stationRepository.save(station);
	}

	public void deleteStation(Long id) {
		stationRepository.deleteById(id);
	}

	public boolean existById(Long id) {
		return stationRepository.existsById(id);

	}

	public boolean existByName(String name) {
		return stationRepository.existsByName(name);

	}

	public Station findById(Long id) {
		return stationRepository.getOne(id);
	}

}
