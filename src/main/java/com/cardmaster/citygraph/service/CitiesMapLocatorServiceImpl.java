package com.cardmaster.citygraph.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import com.cardmaster.citygraph.model.CityMap;
import com.cardmaster.citygraph.model.Dual;
import com.cardmaster.citygraph.model.MapperEnum;

public class CitiesMapLocatorServiceImpl implements CitiesMapLocatorServiceIface {

	private volatile CityMap<String> cMap;

	@Value("${citygraph.graph.mapLogic}")
	private String mapLogic;

	@Value("${citygraph.graph.file}")
	private String inputfile;

	@Value("${citygraph.graph.search.searchTime}")
	private String searchRealTime;

	/*
	 * Initialize City Map may throw an IOException for unexpected input.
	 */

	@PostConstruct
	public void init() throws IOException {

		Set<String> city = new HashSet<>();
		Set<Dual<String>> mainCity = new HashSet<>();

		ClassPathResource resource = new ClassPathResource(inputfile);
		InputStream resourceAsChainStream = resource.getInputStream();
		Scanner scanner = new Scanner(resourceAsChainStream);

		while (scanner.hasNext()) {
			String ln = scanner.nextLine();
			String[] split = ln.split(",");
			Dual<String> mainLinkedCities = new Dual<>(split[0].trim(), split[1].trim());
			mainCity.add(mainLinkedCities);
			city.add(mainLinkedCities.getLeftDirection());
			city.add(mainLinkedCities.getRightDirection());
		}
		scanner.close();
		cMap = new CityMap<>(new ArrayList<>(city), mainCity, MapperEnum.valueOf(mapLogic),
				Boolean.valueOf(searchRealTime));
	}

	@Override
	public boolean isLocatorAvailable(String city1, String city2) {
		return cMap.isLocatorPresent(city1, city2);
	}

}
