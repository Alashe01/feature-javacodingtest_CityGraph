package com.cardmaster.citygraph.model;

import static com.cardmaster.citygraph.model.MapperEnum.DEPTH_FIRST_SEARCH;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class CityMap<T> {

	private final Apex<T>[] apexList;
	private final Set<Set<Integer>> linkedGroups;

	private final boolean requestMeterics;
	private final MapperEnum mapLogic;

	/**
	 * 
	 * @param uniqueMembers
	 * @param pairs
	 */
	public CityMap(List<T> uniqueMembers, Set<Dual<T>> duo) {
		this(uniqueMembers, duo, DEPTH_FIRST_SEARCH, false);
	}

	/**
	 * 
	 * @param uniqueMembers
	 * @param duo
	 * @param requestMeterics
	 */
	public CityMap(List<T> uniqueMembers, Set<Dual<T>> pairs, boolean requestMeterics) {
		this(uniqueMembers, pairs, DEPTH_FIRST_SEARCH, requestMeterics);
	}

	/**
	 * 
	 * @param uniqueMembers
	 * @param pairs
	 * @param mapLogic
	 */
	public CityMap(List<T> uniqueMembers, Set<Dual<T>> pairs, MapperEnum mapEnum) {
		this(uniqueMembers, pairs, mapEnum, false);
	}

	/**
	 * 
	 * @param uniqueMembers
	 * @param pairs
	 * @param mapLogic
	 */
	@SuppressWarnings("unchecked")
	public CityMap(List<T> uniqueMembers, Set<Dual<T>> duos, MapperEnum mapEnum, boolean requestMeterics) {

		this.mapLogic = mapEnum;
		this.requestMeterics = requestMeterics;
		apexList = new Apex[uniqueMembers.size()];

		// construct apex
		for (int ApexIndex = 0; ApexIndex < apexList.length; ApexIndex++) {
			apexList[ApexIndex] = new Apex<T>(uniqueMembers.get(ApexIndex), null);
		}

		// create ends
		Iterator<Dual<T>> iterator = duos.iterator();
		while (iterator.hasNext()) {
			Dual<T> pair = iterator.next();
			// read Apex names and translate to Apex numbers
			int ApexIndex1 = indexForMember(pair.getLeftDirection());
			int ApexIndex2 = indexForMember(pair.getRightDirection());

			// add ApexIndex2 to front of ApexIndex1's list and
			apexList[ApexIndex1].setAList(new End(ApexIndex2, apexList[ApexIndex1].getAList()));
			// add ApexIndex1 to front of ApexIndex2's list
			apexList[ApexIndex2].setAList(new End(ApexIndex1, apexList[ApexIndex2].getAList()));
		}
		if (requestMeterics) {
			this.linkedGroups = null;
		} else if (mapLogic == DEPTH_FIRST_SEARCH) {
			this.linkedGroups = getLinkedGroupsUsingDFS();
		} else {
			this.linkedGroups = getLinkedGroupsUsingBFS();
		}
	}

	private Set<Set<Integer>> getlinkedGroups() {
		return linkedGroups;
	}

	private int indexForMember(T memberObject) {
		for (int ApexIndex = 0; ApexIndex < apexList.length; ApexIndex++) {
			if (apexList[ApexIndex].getMember().equals(memberObject)) {
				return ApexIndex;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param city1
	 * @param city2
	 * @return boolean
	 */
	public boolean isLocatorPresent(T city1, T city2) {
		int indexApex1 = indexForMember(city1);
		int indexApex2 = indexForMember(city2);
		if (indexApex1 == -1 || indexApex2 == -1)
			return false;

		if (requestMeterics) {
			if (mapLogic == DEPTH_FIRST_SEARCH) {
				return isDFSMatch(indexApex1, indexApex2, new HashSet<Integer>());
			} else {
				return isBFSMatch(indexApex1, indexApex2);
			}
		}
		Optional<Set<Integer>> findFirst = getlinkedGroups().stream()
				.filter(set -> set.contains(indexApex1) && set.contains(indexApex2)).findFirst();
		return findFirst.isPresent();
	}

	private boolean isDFSMatch(int ApexIndexStart, int ApexIndexEnd, Set<Integer> vistedNodes) {
		vistedNodes.add(ApexIndexStart);
		if (ApexIndexStart == ApexIndexEnd)
			return true;
		for (End end = apexList[ApexIndexStart].getAList(); end != null; end = end.getNext()) {
			if (!vistedNodes.contains(end.getApexIndex())) {
				boolean matched = isDFSMatch(end.getApexIndex(), ApexIndexEnd, vistedNodes);
				if (matched)
					return true;
			}
		}
		return false;
	}

	private void getDFSCollection(int ApexIndexStart, Set<Integer> vistedNodes, Set<Integer> connectedNodes) {
		vistedNodes.add(ApexIndexStart);
		connectedNodes.add(ApexIndexStart);
		for (End end = apexList[ApexIndexStart].getAList(); end != null; end = end.getNext()) {
			if (!vistedNodes.contains(end.getApexIndex())) {
				getDFSCollection(end.getApexIndex(), vistedNodes, connectedNodes);
			}
		}
	}

	private Set<Set<Integer>> getLinkedGroupsUsingDFS() {
		Set<Integer> visitedNodes = new HashSet<>();
		Set<Set<Integer>> linkedGroups = new HashSet<>();
		for (int ApexIndex = 0; ApexIndex < apexList.length; ApexIndex++) {
			if (!visitedNodes.contains(ApexIndex)) {
				Set<Integer> connectedNodes = new HashSet<>();
				linkedGroups.add(connectedNodes);
				getDFSCollection(ApexIndex, visitedNodes, connectedNodes);
			}
		}
		return linkedGroups;
	}

	private void bfsCollect(int ApexIndex, Set<Integer> connectedNodes) {
		Set<Integer> visitedNodes = new HashSet<>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visitedNodes.add(ApexIndex);
		queue.add(ApexIndex);

		while (queue.size() != 0) {
			ApexIndex = queue.poll();
			connectedNodes.add(ApexIndex);
			for (End edge = apexList[ApexIndex].getAList(); edge != null; edge = edge.getNext()) {
				int vIndex = edge.getApexIndex();
				if (!visitedNodes.contains(vIndex)) {
					visitedNodes.add(vIndex);
					queue.add(vIndex);
				}
			}
		}
	}

	private boolean isBFSMatch(int ApexIndexStart, int ApexIndexEnd) {
		Set<Integer> visitedNodes = new HashSet<>();
		LinkedList<Integer> queue = new LinkedList<Integer>();
		visitedNodes.add(ApexIndexStart);
		queue.add(ApexIndexStart);

		while (queue.size() != 0) {
			ApexIndexStart = queue.poll();
			for (End end = apexList[ApexIndexStart].getAList(); end != null; end = end.getNext()) {
				int vIndex = end.getApexIndex();
				if (ApexIndexEnd == vIndex)
					return true;
				if (!visitedNodes.contains(vIndex)) {
					visitedNodes.add(vIndex);
					queue.add(vIndex);
				}
			}
		}
		return false;
	}

	private Set<Set<Integer>> getLinkedGroupsUsingBFS() {
		Set<Set<Integer>> linkedGroups = new HashSet<>();
		for (int ApexIndex = 0; ApexIndex < apexList.length; ApexIndex++) {
			Set<Integer> connectedNodes = new TreeSet<>();
			bfsCollect(ApexIndex, connectedNodes);
			linkedGroups.add(connectedNodes);
		}
		return linkedGroups;
	}
}
