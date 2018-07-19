package com.cardmaster.citygraph.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.cardmaster.citygraph.service.CitiesMapLocatorServiceIface;

@RestController
public class CitiesMapController {
	@Autowired
	private CitiesMapLocatorServiceIface citiesMapLocatorService;

	@RequestMapping(value = "/connected")
	public String areCitiesConnected(@RequestParam("origin") String origin,
			@RequestParam("destination") String destination) {
		boolean isLocatorAvailable = citiesMapLocatorService.isLocatorAvailable(origin, destination);
		if (isLocatorAvailable)
			return "yes";
		return "no";
	}

}
