
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.cardmaster.citygraph.model.CityMap;
import com.cardmaster.citygraph.model.Dual;
import com.cardmaster.citygraph.model.MapperEnum;

public class CityMapTest {

	@Test
	public void testLinkedGroupsUsingDFS() {

		Set<Dual<String>> duos = new HashSet<>();
		duos.add(new Dual<String>("Boston", "New York"));
		duos.add(new Dual<String>("Philadelphia", "Newark"));
		duos.add(new Dual<String>("Newark", "Boston"));
		duos.add(new Dual<String>("Trenton", "Albany"));

		Set<String> uniqueMembers = new HashSet<>();
		duos.stream().forEach(duo -> {
			uniqueMembers.add(duo.getLeftDirection());
			uniqueMembers.add(duo.getRightDirection());
		});
		CityMap<String> cMap = new CityMap<String>(new ArrayList<>(uniqueMembers), duos, MapperEnum.DEPTH_FIRST_SEARCH);

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("New York", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "New York"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "New York"));
		Assert.assertTrue(cMap.isLocatorPresent("New York", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Newark"));

		Assert.assertFalse(cMap.isLocatorPresent("Philadelphia", "Albany"));
		Assert.assertFalse(cMap.isLocatorPresent("Albany", "Philadelphia"));
	}

	@Test
	public void testLinkedGroupsUsingBFS() {

		Set<Dual<String>> duos = new HashSet<>();
		duos.add(new Dual<String>("Boston", "New York"));
		duos.add(new Dual<String>("Philadelphia", "Newark"));
		duos.add(new Dual<String>("Newark", "Boston"));
		duos.add(new Dual<String>("Trenton", "Albany"));

		Set<String> uniqueMembers = new HashSet<>();
		duos.stream().forEach(duo -> {
			uniqueMembers.add(duo.getLeftDirection());
			uniqueMembers.add(duo.getRightDirection());
		});
		CityMap<String> cMap = new CityMap<String>(new ArrayList<>(uniqueMembers), duos,
				MapperEnum.BREADTH_FIRST_SEARCH);

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("New York", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "New York"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "New York"));
		Assert.assertTrue(cMap.isLocatorPresent("New York", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Newark"));

		Assert.assertFalse(cMap.isLocatorPresent("Philadelphia", "Albany"));
		Assert.assertFalse(cMap.isLocatorPresent("Albany", "Philadelphia"));
	}

	@Test
	public void testUsingDFSLazySearch() {

		Set<Dual<String>> duos = new HashSet<>();
		duos.add(new Dual<String>("Boston", "New York"));
		duos.add(new Dual<String>("Philadelphia", "Newark"));
		duos.add(new Dual<String>("Newark", "Boston"));
		duos.add(new Dual<String>("Trenton", "Albany"));

		Set<String> uniqueMembers = new HashSet<>();
		duos.stream().forEach(duo -> {
			uniqueMembers.add(duo.getLeftDirection());
			uniqueMembers.add(duo.getRightDirection());
		});
		CityMap<String> cMap = new CityMap<String>(new ArrayList<>(uniqueMembers), duos, MapperEnum.DEPTH_FIRST_SEARCH,
				true);

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("New York", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "New York"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "New York"));
		Assert.assertTrue(cMap.isLocatorPresent("New York", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Newark"));

		Assert.assertFalse(cMap.isLocatorPresent("Philadelphia", "Albany"));
		Assert.assertFalse(cMap.isLocatorPresent("Albany", "Philadelphia"));
	}

	@Test
	public void testUsingBFSLazySearch() {

		Set<Dual<String>> duos = new HashSet<>();
		duos.add(new Dual<String>("Boston", "New York"));
		duos.add(new Dual<String>("Philadelphia", "Newark"));
		duos.add(new Dual<String>("Newark", "Boston"));
		duos.add(new Dual<String>("Trenton", "Albany"));

		Set<String> uniqueMembers = new HashSet<>();
		duos.stream().forEach(duo -> {
			uniqueMembers.add(duo.getLeftDirection());
			uniqueMembers.add(duo.getRightDirection());
		});
		CityMap<String> cMap = new CityMap<String>(new ArrayList<>(uniqueMembers), duos,
				MapperEnum.BREADTH_FIRST_SEARCH, true);

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("New York", "Newark"));
		Assert.assertTrue(cMap.isLocatorPresent("Newark", "New York"));

		Assert.assertTrue(cMap.isLocatorPresent("Boston", "New York"));
		Assert.assertTrue(cMap.isLocatorPresent("New York", "Boston"));

		Assert.assertTrue(cMap.isLocatorPresent("Newark", "Philadelphia"));
		Assert.assertTrue(cMap.isLocatorPresent("Philadelphia", "Newark"));

		Assert.assertFalse(cMap.isLocatorPresent("Philadelphia", "Albany"));
		Assert.assertFalse(cMap.isLocatorPresent("Albany", "Philadelphia"));
	}
}
