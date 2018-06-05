package node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import xml.WriteXMLFile;

public class NodeUtil {
	
	public static String concatNamespace(String uri) {
		String finalURI = null;
		if(IConstants.SYSTEM_DATA_LANGUAGE==null) {
			new Exception("System data language not defined");
		}else if(IConstants.SYSTEM_DATA_LANGUAGE==IConstants.BRAZILIAN_DBPEDIA) {
			finalURI = "http://pt.dbpedia.org/resource/"+uri;
		}else if(IConstants.SYSTEM_DATA_LANGUAGE==IConstants.ENGLISH_DBPEDIA) {
			finalURI = "http://dbpedia.org/resource/"+uri;
		}
		return finalURI;
	}
	
	public static String removeNamespace(String uri){
		String finalString = uri;
		if (finalString.contains("http://dbpedia.org/resource/")) {
			finalString = finalString.replace("http://dbpedia.org/resource/","").trim();
		}
		if (finalString.contains("http://pt.dbpedia.org/resource/")) {
			finalString = finalString.replace("http://pt.dbpedia.org/resource/","").trim();
		}
		return finalString;
	}	
	
	public static List<String> convertResourcesInStringSet(List<Resource> resources) {

		List<String> stringList = new ArrayList<String>();

		for (Resource resource : resources) {
			stringList.add(resource.getURI());
		}
		return stringList;
	}	

	public static List<Node> convertResourceInNodes(List<Resource> resources, int idCount, boolean checkLabel, List<Node> cnns) {

		List<Node> nodes = new LinkedList<Node>();

		int count = idCount + 1;

		if (cnns == null) {
			cnns = new ArrayList<Node>();
		}
		for (Resource resource : resources) {
			// NodeUtil.print("resource.getURI())"+resource.getURI());
			Node node = null;
			Node existing = NodeUtil.getNodeByURI(resource.getURI(), cnns);
			if (existing == null && !checkLabel) {
				node = new Node("" + count, IConstants.NO_LABEL, resource.getURI());
				cnns.add(node);
				nodes.add(node);
			} else if (checkLabel && existing != null && IConstants.getValidLabel(existing.getLabel()) != null) {
				node = existing;
				nodes.add(node);
			} else if (!checkLabel) {
				node = existing;
				nodes.add(node);
			}
			count++;
		}
		return nodes;
	}
	
	public static List<Node> convertResourceInNodes(List<Resource> resources, int idCount, String label, boolean checkLabel, List<Node> cnns) {

		List<Node> nodes = new LinkedList<Node>();

		int count = idCount + 1;

		if (cnns == null) {
			cnns = new ArrayList<Node>();
		}
		for (Resource resource : resources) {
			// NodeUtil.print("resource.getURI())"+resource.getURI());
			Node node = null;
			Node existing = NodeUtil.getNodeByURI(resource.getURI(), cnns);
			if (existing == null && !checkLabel) {
				node = new Node("" + count, label, resource.getURI());
				cnns.add(node);
				nodes.add(node);
			} else if (checkLabel && existing != null && IConstants.getValidLabel(existing.getLabel()) != null) {
				node = existing;
				nodes.add(node);
			} else if (!checkLabel) {
				node = existing;
				nodes.add(node);
			}
			count++;
		}
		return nodes;
	}	
	

	/**
	 * @param memoryPredictions
	 */
	public static void updateLabelsAfterClassification(List<NodePrediction> memoryPredictions) {
		Collections.sort(memoryPredictions);
		for (NodePrediction nodePrediction : memoryPredictions) {
			Node node = getNodeByURI(nodePrediction.getNode().getURI(), Lodica.cnns);
			node.setLabel(nodePrediction.getPredictedLabel());
			node.setRankingScore(nodePrediction.getPredictionScore());
		}
	}
	
	public static void printPredictions(List<NodePrediction> predictions) {
		for (NodePrediction prediction : predictions) {
			printPrediction(prediction);
		}
	}
	
	public static void printPredictionsWhy(List<NodePrediction> predictions) {
		for (NodePrediction prediction : predictions) {
			printPredictionWhy(prediction);
		}
	}	
	
	public static void printEvaluations(List<Evaluation> evaluations) {
		for (Evaluation evaluation : evaluations) {
			printEvaluation(evaluation);
		}
	}	
	
	private static void printPredictionNewLabelWhy(NodePrediction nodePrediction) {
		NodeUtil.print(
				"NODE:" + nodePrediction.getNode().getId() + ":"+ nodePrediction.getNode().getURI() +
				"\tSEED:"+ nodePrediction.getSeed() + 
				"\tSCORE:"+ nodePrediction.getPredictionScore() + 
				"\tPREVIOUS LABEL:"+ nodePrediction.getEvaluationLabel() + 
				"\tPREDICTED_LABEL:"+ nodePrediction.getPredictedLabel() +
				"\tUSER ID:"+ nodePrediction.getUserId()+
				"\tGRAPH_STRUCTURE:"+ nodePrediction.getGraphStructure()+
			    "\tWHY:"+ nodePrediction.getWhy());
				NodeUtil.print();
	}
	
	private static void printPredictionSameLabelWhy(NodePrediction nodePrediction) {
		NodeUtil.print(
				"NODE:" + nodePrediction.getNode().getId() + ":"+ nodePrediction.getNode().getURI()+ 
				"\tSEED:"+ nodePrediction.getSeed() + 
				"\tSCORE:"+ nodePrediction.getPredictionScore() + 
				"\tPREDICTED SAME LABEL:"+ nodePrediction.getEvaluationLabel()+
				"\tUSER ID:"+ nodePrediction.getUserId()+
				"\tGRAPH_STRUCTURE:"+ nodePrediction.getGraphStructure()+
				"\tWHY:"+ nodePrediction.getWhy());		
		NodeUtil.print();		
	}	

	private static void printPredictionNewLabel(NodePrediction nodePrediction) {
		NodeUtil.print(
				"NODE:" + nodePrediction.getNode().getId() + ":"+ nodePrediction.getNode().getURI() +
				"\tSEED:"+ nodePrediction.getSeed() + 
				"\tSCORE:"+ nodePrediction.getPredictionScore() + 
				"\tPREVIOUS LABEL:"+ nodePrediction.getEvaluationLabel() + 
				"\tPREDICTED_LABEL:"+ nodePrediction.getPredictedLabel() +
				"\tUSER ID:"+ nodePrediction.getUserId()+
				"\tGRAPH_STRUCTURE:"+ nodePrediction.getGraphStructure());
				NodeUtil.print();
	}
	
	private static void printPredictionSameLabel(NodePrediction nodePrediction) {
		NodeUtil.print(
				"NODE:" + nodePrediction.getNode().getId() + ":"+ nodePrediction.getNode().getURI()+ 
				"\tSEED:"+ nodePrediction.getSeed() + 
				"\tSCORE:"+ nodePrediction.getPredictionScore() + 
				"\tPREDICTED SAME LABEL:"+ nodePrediction.getEvaluationLabel()+
				"\tUSER ID:"+ nodePrediction.getUserId()+
				"\tGRAPH_STRUCTURE:"+ nodePrediction.getGraphStructure());
		NodeUtil.print();		
	}	

	/**
	 * @param memoryPredictions
	 */
	public static void updateLabelsAfterClassification(NodePrediction memoryPrediction) {
		List<NodePrediction> memoryPredictions = new ArrayList<NodePrediction>();
		memoryPredictions.add(memoryPrediction);
		updateLabelsAfterClassification(memoryPredictions);
	}

	public void bootstrapByRandomLabeling(List<Node> nodes) {
		for (Node node : nodes) {
			if (node.isUnlabeled()) {
				node.setLabel(IConstants.getLabels().get(new Random().nextInt(IConstants.getLabels().size())));
			}
		}
	}

	public static void bootstrapNullLabelsNodes(List<Node> nodes, String label) {
		for (Node node : nodes) {
			if (node.isUnlabeled()) {
				node.setLabel(label);
			}
		}
	}

	public static void printNodes(List<Node> nodes) {
		//Collections.sort(nodes);
		for (Node node : nodes) {
			printNode(node);
		}
	}
	
	public static void printResources(List<Resource> resources) {
		//Collections.sort(nodes);
		for (Resource resource : resources) {
			print(resource.getURI());
		}
	}	
	
	public static void printResources(Set<Resource> resources) {
		//Collections.sort(nodes);
		for (Resource resource : resources) {
			print(resource.getURI());
		}
	}	

	public static void printNodes(Set<Node> nodes) {
		Collections.sort(new ArrayList<Node>(nodes));
		for (Node node : nodes) {
			printNode(node);
		}
	}
	
	public static void printNodesWithoutID(Set<Node> nodes) {
		for (Node node : nodes) {
			printNodeWithoutID(node);
		}
	}	

	public static void print(String message) {
		if (IConstants.PRINT) {
			System.out.println(message);	
		}
		
	}

	public static void print() {
		System.out.println();
	}

	public static void print(boolean message) {
		System.out.println(message);
	}

	public static void print(int message) {
		System.out.println("" + message);
	}

	public static void print(double message) {
		System.out.println("" + message);
	}

	public void printRelationalAndObservedAttributesForAllNodes(List<Node> nodes) {
		Collections.sort(nodes);
		for (Node node : nodes) {
			this.printRelationalAndObservedAttributesForAllNodes(node);
		}
	}

	public static void printPrediction(NodePrediction nodePrediction) {
		if (!nodePrediction.getEvaluationLabel().equals(nodePrediction.getPredictedLabel())) {
			printPredictionNewLabel(nodePrediction);
		} else {
			printPredictionSameLabel(nodePrediction);
		}
	}
	
	public static void printPredictionWhy(NodePrediction nodePrediction) {
		if (!nodePrediction.getEvaluationLabel().equals(nodePrediction.getPredictedLabel())) {
			printPredictionNewLabelWhy(nodePrediction);
		} else {
			printPredictionSameLabelWhy(nodePrediction);
		}
	}	

	public void printRelationalAndObservedAttributesForAllNodes(Node node) {
		System.out.println("NodeID:" + node.getId() + "	label:" + node.getLabel() + "	Relational Attributes("
				+ node.getRelationalFeatures().size() + "):" + node.getRelationalFeatures().toString()
				+ "	Observed Attributes(" + node.getObservedAtrributes().size() + "):"
				+ node.getObservedAtrributes().toString());
		System.out.println();
	}

	public static void printRelationalAttributesForAllNodes(List<Node> nodes) {
		Collections.sort(nodes);
		for (Node node : nodes) {
			printRelationalAttributes(node);
		}
		System.out.println();
	}

	public static void printObservedAttributesForAllNodes(List<Node> nodes) {
		Collections.sort(nodes);
		for (Node node : nodes) {
			printObservedAttributes(node);
		}
	}

	public static void printNode(Node node) {
		if (IConstants.PRINT) {
			System.out.printf("" + "NodeID:" + node.getId() + "\tURI: " + new ResourceImpl(node.getURI()) + "\tlabel:"
					+ node.getLabel() + "\t direct links:" + node.getNodes().toString());
			System.out.println();
		}
	}
	
	public static void printNodeWithoutID(Node node) {
		if (IConstants.PRINT) {
			System.out.printf("" + "NodeID:NULL  \tURI: " + new ResourceImpl(node.getURI()) + "\tlabel:"
					+ node.getLabel() + "\t direct links:" + node.getNodes().toString());
			System.out.println();
		}
	}	

	public static void printRelationalAttributes(Node node) {
		System.out.printf("NodeID:" + node.getId() + "	label:" + node.getLabel() + "	Relational Attributes("
				+ node.getRelationalFeatures().size() + "):" + node.getRelationalFeatures().toString());
		System.out.println();
	}

	public static void printObservedAttributes(Node node) {
		System.out.printf("" + "NodeID:" + node.getId() + "\tURI: " + new ResourceImpl(node.getURI()) + "	label:"
				+ node.getLabel() + "	Observed Attributes(" + node.getObservedAtrributes().size() + "):"
				+ node.getObservedAtrributes().toString());
		System.out.println();

	}
	
	public static void printEvaluation(Evaluation evaluation) {
		NodeUtil.print("Node "+evaluation.getUri()+" for user "+evaluation.getUserId()+" has "+evaluation.getCorrect()+" correct prediction(s) and "+evaluation.getIncorrect()+" "
				+ "incorrect prediction(s) using similarity method "+evaluation.getSimilarityMethod() + " and score "+evaluation.getScore() + "  and position "+evaluation.getPosition() + "  and original "+evaluation.getOriginalCandidate() + " and rr "+evaluation.getRr());
	}
	
	/**
	 * @param startNode
	 */
	public void bootstrapByStartNode(Node startNode) {
		for (Node node : getUnlabeledNodes(startNode.getNodes())) {
			node.setLabel(startNode.getLabel());
		}
	}

	public static Set<Node> getUnlabeledNodes(List<Node> nodes) {
		Set<Node> unlablednodes = new HashSet<Node>();
		for (Node node : nodes) {
			if (node.getLabel() == null || node.getLabel().equals(IConstants.NO_LABEL)) {
				unlablednodes.add(node);
			}
		}
		return unlablednodes;
	}

	public static void connect2Nodes(Node nodeOut, Node nodeIn) {
		if (!nodeIn.getNodes().contains(nodeOut) && (!nodeOut.getNodes().contains(nodeIn))) {
			nodeIn.addNode(nodeOut);
			nodeOut.addNode(nodeIn);
		}

	}

	public void connect3Nodes(Node node1, Node node2, Node node3) {
		node1.addNode(node2);
		node2.addNode(node1);

		node1.addNode(node3);
		node3.addNode(node3);

		node2.addNode(node3);
		node3.addNode(node2);
	}
	
	public static Set<Node> getLabeledNodesExcept(List<Node> nodes, Node nodeExcept) {
		Set<Node> labeledNodes = new HashSet<Node>();
		for (Node localNode : nodes) {
			//if (localNode.getLabel() != null && (IConstants.getLabels().contains(localNode.getLabel()))) {
			if (IConstants.LIKE.equals(localNode.getLabel())&& !localNode.getURI().equals(nodeExcept.getURI())) {
				labeledNodes.add(localNode);
			}
		}
		return labeledNodes;
	}
	
	public static Set<Node> getLabeledNodesExcept(Set<Node> nodes, Node nodeExcept) {
		Set<Node> labeledNodes = new HashSet<Node>();
		for (Node localNode : nodes) {
			//if (localNode.getLabel() != null && (IConstants.getLabels().contains(localNode.getLabel()))) {
			if (IConstants.LIKE.equals(localNode.getLabel())&& !localNode.getURI().equals(nodeExcept.getURI())) {
				labeledNodes.add(localNode);
			}
		}
		return labeledNodes;
	}	

	/**
	 * @param node
	 * @return
	 */
	public static Set<Node> getLabeledNodes(List<Node> nodes) {
		Set<Node> labeledNodes = new HashSet<Node>();
		for (Node localNode : nodes) {
			//if (localNode.getLabel() != null && (IConstants.getLabels().contains(localNode.getLabel()))) {
			if (IConstants.LIKE.equals(localNode.getLabel())) {
				labeledNodes.add(localNode);
			}
		}
		return labeledNodes;
	}

	/**
	 * @param node
	 * @return
	 */
	public static Set<Node> getLabeledNodes(Set<Node> nodes) {
		Set<Node> labeledNodes = new HashSet<Node>();
		for (Node localNode : nodes) {
			if (localNode.getLabel() != null && (IConstants.getLabels().contains(localNode.getLabel()))) {
				labeledNodes.add(localNode);
			}
		}
		return labeledNodes;
	}

	/**
	 * @param node
	 * @return
	 */
	public static Set<Node> getLabeledNeighbourhood(Node node) {
		Set<Node> labelledNodes = new HashSet<Node>();
		Set<Node> tempNodes = new HashSet<Node>();
		tempNodes.add(node);
		for (int i = 0; i < IConstants.N; i++) {
			Set<Node> aux = null;
			for (Node nodeTemp : tempNodes) {
				aux = new HashSet<Node>();
				aux.addAll(getLabeledNodes(nodeTemp.getNodes()));
				labelledNodes.addAll(aux);
			}
			tempNodes = new HashSet<Node>(aux);
		}
		// printNodes(newNeighbours);
		return labelledNodes;
	}

	/**
	 * @param node
	 * @return
	 */
	public static void labelNodes(List<Node> nodes, String label) {
		for (Node localNode : nodes) {
			localNode.setLabel(label);
		}
	}

	public static void labelNodes(Set<Node> nodes, String label) {
		for (Node localNode : nodes) {
			localNode.setLabel(label);
		}
	}

	public static String getMaxNodeID(List<Node> cnns) {
		List<Integer> ids = new ArrayList<Integer>();
		if (cnns == null || cnns.isEmpty()) {
			return "1";
		}
		for (Node localNode : cnns) {
			ids.add(Integer.valueOf(localNode.getId()));
		}
		return "" + Collections.max(ids);
	}

	public static Node getNodeByURI(String uri, List<Node> cnns) {
		if (cnns == null) {
			return null;
		}
		for (Node node : cnns) {
			if (node.getURI().equals(uri)) {
				return node;
			}
		}
		return null;
	}

	public static Node getNodeByURI(String uri, Set<Node> nodes) {
		if (nodes == null) {
			return null;
		}

		for (Node node : nodes) {
			if (node.getURI().equals(uri)) {
				return node;
			}
		}
		return null;
	}
	
	public static void addIDsToCandidates(Set<Node> candidates) {
		for (int i = 0; i < candidates.size(); i++) {
			new ArrayList<Node>(candidates).get(i).setId((""+i+1));
		}
	}
	
	public static void addIDsToCandidates(List<Node> candidates) {
		for (int i = 0; i < candidates.size(); i++) {
			candidates.get(i).setId((""+i+1));
		}
	}	


	public static Node getNodeByID(String id) {
		for (Node node : Lodica.cnns) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	public static Node getNodeByID(String id, Set<Node> nodes) {
		for (Node node : nodes) {
			if (node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	/**
	 * Checks if the neighbours are directly or indirectly linked in the LOD
	 * 
	 * @param nodeTest
	 * @param nodes
	 * @return
	 */
	public static Set<Node> getDirectAndIndirectLabeledNodes(Node nodeTest, List<Node> nodes) {
		Set<Node> labeledNodes = new HashSet<Node>();
		for (Node nodeExternalNode : NodeUtil.getLabeledNodes(new ArrayList<Node>(nodes))) {
			if (nodeExternalNode.getLabel() != null && (IConstants.getLabels().contains(nodeExternalNode.getLabel()))) {
				if (SparqlWalk.getCountDBpediaObjecstBySubjectBetween2Resources(nodeTest.getURI(),
						nodeExternalNode.getURI()) > 0) {
					labeledNodes.add(nodeExternalNode);
				} else if (SparqlWalk.countTotalNumberOfIndirectInconmingLinksBetween2Resources(nodeTest.getURI(),
						nodeExternalNode.getURI()) > 0) {
					labeledNodes.add(nodeExternalNode);
				} else if (SparqlWalk.countTotalNumberOfIndirectOutgoingLinksBetween2Resources(nodeTest.getURI(),
						nodeExternalNode.getURI()) > 0) {
					labeledNodes.add(nodeExternalNode);
				}
			}
		}

		return labeledNodes;
	}

	/**
	 * Check if 2 nodes are different by URI
	 * 
	 * @param node1
	 * @param node2
	 * @return
	 */
	public static boolean isDistinctURI(Node node1, Node node2) {
		if (!node1.getURI().trim().equals(node2.getURI().trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param node
	 * @return
	 */
	public List<Node> getLinkedUnLabeledNodes(Node node) {

		List<Node> linkedUnLabeledNodes = new ArrayList<Node>();

		for (Node localNode : node.getNodes()) {
			if (localNode.getLabel() == null) {
				linkedUnLabeledNodes.add(node);
			}
		}

		return linkedUnLabeledNodes;
	}

	public static void labelXNodesRandomly(List<Node> nodes, int x, String label) {
		Random random = new Random();
		for (int i = 0; i < Math.min(nodes.size(), x); i++) {
			nodes.get(random.nextInt(nodes.size())).setLabel(label);
		}
	}

	public static void labelXNodesRandomly(List<Node> nodes, int x) {
		Random random = new Random();
		for (int i = 0; i < Math.min(nodes.size(), x); i++) {
			nodes.get(random.nextInt(nodes.size()))
					.setLabel(IConstants.getLabels().get(random.nextInt(IConstants.getLabels().size())));
		}
	}

	public static Set<String> getSetStringURI(Set<Node> userNodesProfile) {
		Set<String> stringUris = new HashSet<String>();
		for (Node node : userNodesProfile) {
			stringUris.add(node.getURI());
		}
		return stringUris;
	}
	
	public static Map<String,Set<Node>> getMapSetStringURI(Map<Node,Set<Node>> map) {
		Map<String,Set<Node>> mapString = new HashMap<String,Set<Node>>();
		for (Node node : map.keySet()) {
			mapString.put(node.getURI(), map.get(node));
		}
		return mapString;
	}

	public static List<Node> getNodesFromPredictionList(Collection<NodePrediction> nodePredictions) {
		List<Node> nodes = new ArrayList<Node>();
		for (NodePrediction nodePrediction : nodePredictions) {
			nodes.add(nodePrediction.getNode());
			//print(nodePrediction.getNode().getURI());
			//print(nodePrediction.getNode().getLabel());
		}
		return nodes;
	}

	public static List<String> getSetStringURIFromPrediction(Collection<NodePrediction> nodePredictions) {
		List<String> stringUris = new ArrayList<String>();
		for (NodePrediction nodePrediction : nodePredictions) {
			stringUris.add(nodePrediction.getNode().getURI().trim());
		}
		return stringUris;
	}	

	public static Set<Node> createNodesWithLabel(List<Resource> resources, String label) {
		Set<Node> labelledNodesLocal = new HashSet<Node>();
		for (Resource resource : resources) {
			labelledNodesLocal.add(new Node(label, resource.getURI()));
		}
		return labelledNodesLocal;
	}

	public static Set<Node> createNewLabeledNodesNotInNPlus(List<Resource> resources, String label,
			Set<Node> trainingSet, int newId) {
		Set<Node> labelledNodesLocal = new HashSet<Node>();
		for (Resource resource : resources) {
			Node newNode = null;
			if (newNode == null) {
				newNode = getNodeByURI(resource.getURI(), trainingSet);
			}
			if (newNode == null) {
				labelledNodesLocal.add(new Node(""+(++newId), label, resource.getURI()));
			} else {
				labelledNodesLocal.add(newNode);
			}

		}
		return labelledNodesLocal;
	}

	static public Map<Node, Set<Node>> neighboursPlus = new HashMap<Node, Set<Node>>();

	public static Set<Node> getNeighboursPlus(Map<Node, Set<Node>> neighboursPlus) {
		Set<Node> valuesNodes = new HashSet<Node>();
		for (Set<Node> values : neighboursPlus.values()) {
			valuesNodes.addAll(values);
		}
		return valuesNodes;
	}

	/**
	 * Very important method that removes items from a collection regardless the
	 * ids. Sometimes collections from database have distinct ids from items
	 * created in the Linked Data
	 * 
	 * @param nodesTobeReduced
	 * @param nodesReference
	 * @return
	 */
	public static Set<Node> removeNodesByURI(Set<Node> nodesTobeReduced, Set<Node> nodesReference) {
		Set<Node> userProfileReduced = new HashSet<Node>(nodesTobeReduced);
		for (Node nodeUserProfile : nodesTobeReduced) {
			for (Node trainingSetNode : nodesReference) {
				if (nodeUserProfile.getURI().equals(trainingSetNode.getURI())) {
					userProfileReduced.remove(nodeUserProfile);
				}
			}
		}
		return userProfileReduced;
	}
	
	
	public static Set<Node> removeNodesByURI(Set<Node> nodesTobeReduced, List<Resource> resources) {
		Set<Node> userProfileReduced = new HashSet<Node>(nodesTobeReduced);
		for (Node nodeUserProfile : nodesTobeReduced) {
			for (Resource resource : resources) {
				if (nodeUserProfile.getURI().equals(resource.getURI())) {
					userProfileReduced.remove(nodeUserProfile);
				}
			}
		}
		return userProfileReduced;
	}	
	

	/**
	 * Ensure that all nodes are of distinct URIs and Ids
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkForDistinctIdsAndURIs(List<Node> nodes) throws Exception {
		for (Node node : nodes) {
			for (Node node2 : nodes) {
				if ((!node.getId().equals(node2.getId())) && (node.getURI().equals(node2.getURI()))) {
					NodeUtil.print("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
					NodeUtil.print(node.getId());
					NodeUtil.print(node2.getId());
					NodeUtil.print(node.getURI());
					NodeUtil.print(node2.getURI());
					throw new Exception("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
				}
			}
		}
	}
	
	/**
	 * Ensure that all nodes are of distinct URIs and Ids
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkForDistinctIdsAndURIs(Set<Node> nodes) throws Exception {
		for (Node node : nodes) {
			for (Node node2 : nodes) {
				if ((!node.getId().equals(node2.getId())) && (node.getURI().equals(node2.getURI()))) {
					NodeUtil.print("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
					NodeUtil.print(node.getId());
					NodeUtil.print(node2.getId());
					NodeUtil.print(node.getURI());
					NodeUtil.print(node2.getURI());
					throw new Exception("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
				}
			}
		}
	}
	
	
	/**
	 * Ensure that all nodes are of distinct URIs
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkForDistinctURIs(List<Node> nodes, List<Node> nodes2) throws Exception {
		for (Node node : nodes) {
			for (Node node2 : nodes2) {
				if (node.getURI().equals(node2.getURI())) {
					NodeUtil.print("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
					NodeUtil.print(node.getId());
					NodeUtil.print(node2.getId());
					NodeUtil.print(node.getURI());
					NodeUtil.print(node2.getURI());
					throw new Exception("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
				}
			}
		}
	}
	
	/**
	 * Ensure that all nodes are of distinct URIs
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkForDistinctURIs(Set<Node> nodes, Set<Node> nodes2) throws Exception {
		for (Node node : nodes) {
			for (Node node2 : nodes2) {
				if (node.getURI().equals(node2.getURI())) {
					NodeUtil.print("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
					NodeUtil.print(node.getId());
					NodeUtil.print(node2.getId());
					NodeUtil.print(node.getURI());
					NodeUtil.print(node2.getURI());
					throw new Exception("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
				}
			}
		}
	}	
	
	/**
	 * Ensure that all nodes are of distinct URIs
	 * 
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkForDistinctURIs(List<Node> nodes, Set<Node> nodes2) throws Exception {
		for (Node node : nodes) {
			for (Node node2 : nodes2) {
				if (node.getURI().equals(node2.getURI())) {
					NodeUtil.print("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
					NodeUtil.print(node.getId());
					NodeUtil.print(node2.getId());
					NodeUtil.print(node.getURI());
					NodeUtil.print(node2.getURI());
					throw new Exception("NODES WITH SAME URI IN THE DATASET - SOMETHING WRONG");
				}
			}
		}
	}	

	/**
	 * @param nodes
	 * @throws Exception
	 */
	public static void checkTrainingTestSet(Set<Node> trainingSet, Set<Node> testSet, List<Node> cnns)
			throws Exception {
		if (trainingSet.isEmpty()) {
			throw new Exception("Training Set is Empty. LODICA say good bye :(");
		}
		if (testSet.isEmpty()) {
			throw new Exception("Test Set is Empty. LODICA say good bye :(");
		}
		if (trainingSet.contains(testSet) || testSet.contains(testSet)) {
			throw new Exception("Trainset intersects TestSet");
		}
		if (cnns.size() != (trainingSet.size() + testSet.size())) {
			throw new Exception("allnodes size different from Trainset+TestSet Size");
		}
	}

	/**
	 * @param nodes
	 */
	public void makeAllNodesLinked(List<Node> nodes) {
		for (Node node : nodes) {
			for (Node node2 : nodes) {
				if (!node.getId().equals(node2.getId()) && !node.getNodes().contains(node2)
						&& !node2.getNodes().contains(node)) {
					NodeUtil.connect2Nodes(node, node2);
				}
			}
		}
	}

	/**
	 * @param nodes
	 */
	public void linkNodesFromLOD(List<Node> nodes) {
		for (Node node : nodes) {
			for (Node node2 : nodes) {
				if (!node.getId().equals(node2.getId()) && !node.getNodes().contains(node2)
						&& !node2.getNodes().contains(node)) {
					if (SparqlWalk.countDirectLinksBetween2Resources(node.getURI(), node2.getURI()) > 0) {
						NodeUtil.connect2Nodes(node, node2);
						continue;
					} else if (SparqlWalk.countDirectLinksBetween2Resources(node2.getURI(), node.getURI()) > 0) {
						NodeUtil.connect2Nodes(node2, node);
					}
				}
			}
		}
	}

	/**
	 * Plot a graph for a set of nodes
	 * 
	 * @param showGraph
	 * @param nodes
	 */
	public static void showGraph(boolean showGraph, List<Node> nodes) {
		if (showGraph) {
			WriteXMLFile.createXML(nodes);
			GraphView.start();
			// System.exit(0);
		}
	}

	/**
	 * @param uri
	 * @param label
	 */
	public static void updateLabelFromWeb(String uri, String label, List<Node> nodes) {
		Node node = getNodeByURI(uri, nodes);
		if (node == null) {
			NodeUtil.print("No label updates - nodes are null");
		} else {
			node.setLabel(label);
			// printNode(node);
		}

		LodicaOldVersion.externalClassifiedNodesForTrainingSet.add(node);
	}

	static public Map<Integer, String> stabMap = new HashMap<Integer, String>();

	public static boolean isStabel(int interation, Set<Node> cnns) {
		boolean stable = true;
		StringBuilder stabel = new StringBuilder();
		for (Node node : cnns) {
			stabel.append(node.getId() + ":" + node.getLabel());
		}
		stabMap.put(interation, stabel.toString());
		NodeUtil.print("stabMap " + stabMap);

		if (stabMap.size() <= IConstants.AMOUNT_OF_ITERATIONS_TO_STABALIZE) {
			return false;
		}

		int indexToCheck = stabMap.size() - IConstants.AMOUNT_OF_ITERATIONS_TO_STABALIZE;

		for (int i = indexToCheck; i < stabMap.size(); i++) {
			if (new ArrayList<String>(stabMap.values()).get(i).equals(stabMap)) {
				stable = stable && true;
			}
		}
		return stable;
	}

	public static void describeNode(Node node) {
		NodeUtil.print("Node:");
		printNode(node);
		NodeUtil.print();
		NodeUtil.print("Direct Nodes");
		printNodes(node.getNodes());
		NodeUtil.print();
		NodeUtil.print("Neighborhood");
	}

	private void labelXNodesRandomly(Integer max, Set<Node> cnns) {
		if (max != null) {
			// printNodes(allnodes);
			NodeUtil.print();
			int total = cnns.size() / max;
			NodeUtil.print(total);
			NodeUtil.labelXNodesRandomly(new ArrayList<Node>(cnns), total);
			NodeUtil.print();
			NodeUtil.printNodes(cnns);

		}
	}

	/**
	 * @param node
	 * @return
	 */
	public List<Node> getLinkedLabeledNodes(Node node) {

		List<Node> linkedLabeledNodes = new ArrayList<Node>();

		for (Node localNode : node.getNodes()) {
			if (localNode.getLabel() != null) {
				linkedLabeledNodes.add(node);
			}
		}

		return linkedLabeledNodes;
	}

	/**
	 * @param node
	 * @return
	 */
	public static List<String> getListByAttribute(String uriProperty, List<Node> cnns) {

		List<String> listByAttribute = new ArrayList<String>();

		for (Node localNode : cnns) {
			// NodeUtil.print("ss"+localNode.getObservedAtrributes().get(uriProperty));
			listByAttribute.add(localNode.getObservedAtrributes().get(uriProperty));
		}

		return listByAttribute;
	}

	public static List<Node> getRelevantItems(Set<Node> originalUnlabeledNodesToClassify) {
		Random random = new Random();
		int bound = random.nextInt(originalUnlabeledNodesToClassify.size());
		List<Node> finalList = new LinkedList<Node>(originalUnlabeledNodesToClassify).subList(0, bound);
		Collections.sort(finalList);
		return finalList;
	}
	
	public static List<NodePrediction> getRandomPredictions(List<NodePrediction> predictions) {
		Random random = new Random();
		int bound = random.nextInt(predictions.size());
		List<NodePrediction> finalList = new LinkedList<NodePrediction>(predictions).subList(0, bound);
		Collections.sort(finalList);
		return finalList;
	}
	
	public static List<NodePrediction> getPredictionsByLabel(List<NodePrediction> predictions, String label) {
		List<NodePrediction> finalList = new LinkedList<NodePrediction>();
		for (NodePrediction nodePrediction : predictions) {
			if (nodePrediction.getPredictedLabel().equals(label)) {
				finalList.add(nodePrediction);
			}
		}
		return finalList;
	}
	
	public static int countItersectionPredictionByURIAndLabel(List<NodePrediction> reference, List<NodePrediction> test, String label) {
		int countIntersec = 0;
		for (NodePrediction nodePredictionReference : reference) {
			for (NodePrediction nodePredictionTest : test) {
				//printPrediction(nodePredictionReference);
				//printPrediction(nodePredictionTest);
				if (nodePredictionTest.getPredictedLabel().equals(label) && nodePredictionReference.getPredictedLabel().equals(label) &&  nodePredictionReference.getNode().getURI().equals(nodePredictionTest.getNode().getURI())) {
					countIntersec++;
				}
			}
		}
		return countIntersec;
	}
	
	
	
	/**
	 * @param nodes
	 * @return
	 */
	public static List<Integer> getIds(Set<Node> nodes) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Node node : nodes) {
			ids.add(Integer.valueOf(node.getId()));
		}
		return ids;
	}
	
	public static List<Integer> getIdsByList(List<Node> nodes) {
		List<Integer> ids = new ArrayList<Integer>();
		for (Node node : nodes) {
			ids.add(Integer.valueOf(node.getId()));
		}
		return ids;
	}	
	
	public static Set<Integer> getIds(List<Node> nodes) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Node node : nodes) {
			ids.add(Integer.valueOf(node.getId()));
		}
		return ids;
	}
	
	public static Set<Integer> getIdsBySet(Set<Node> nodes) {
		Set<Integer> ids = new HashSet<Integer>();
		for (Node node : nodes) {
			ids.add(Integer.valueOf(node.getId()));
		}
		return ids;
	}
	
	public static List<String> getUris(Set<Node> nodes) {
		List<String> uris = new ArrayList<String>();
		for (Node node : nodes) {
			uris.add(node.getURI());
		}
		return uris;
	}
	
	public static List<String> getUrisByList(List<Node> nodes) {
		List<String> uris = new ArrayList<String>();
		for (Node node : nodes) {
			uris.add(node.getURI());
		}
		return uris;
	}	
	
	public static Set<String> getUris(List<Node> nodes) {
		Set<String> uris = new HashSet<String>();
		for (Node node : nodes) {
			uris.add(node.getURI());
		}
		return uris;
	}
	
	public static Set<String> getUrisBySet(Set<Node> nodes) {
		Set<String> uris = new HashSet<String>();
		for (Node node : nodes) {
			uris.add(node.getURI());
		}
		return uris;
	}	
	
	public static void writeExcel(Integer userId, String uri, int n, int nPrime,int cnnsize, int tu, int classificationSize, String time, double ms) {
		
		try {
		
			WritableWorkbook wworkbook = null;
			
			Workbook workbook = null;
			
			WritableSheet	wsheet =  null;
			
			File exlFile = new File("statistics.xls");
			
			Label labelUserId = new Label(0, 0, "USERID");
			Label labelURI = new Label(1, 0, "URI");
			Label labelN = new Label(2, 0, "N");
			Label labelNPrime = new Label(3, 0, "N_PRIME");
			Label labelCNN = new Label(4, 0, "CNN");
			Label labelTu = new Label(5, 0, "TU");
			Label labelClassification = new Label(6, 0, "CLASSIFIED");
			Label labelTime = new Label(7, 0, "TIME");
			Label labelMs = new Label(8, 0, "MS");			

			int total=0;
			if (!exlFile.exists()) {
				total = total+1;
				exlFile.createNewFile();
				wworkbook = Workbook.createWorkbook(exlFile);
				wsheet = wworkbook.createSheet("statistics", 0);
				wsheet = wworkbook.getSheet(0);	

			}else{
				workbook = Workbook.getWorkbook(exlFile);
			    wworkbook = Workbook.createWorkbook(exlFile,workbook);

			    wsheet = wworkbook.getSheet(0);	
				total = wsheet.getRows();
			}
			
			Cell userIdCell = wsheet.getCell(labelUserId.getColumn(),labelUserId.getRow());
			Cell labelURICell = wsheet.getCell(labelURI.getColumn(), labelURI.getRow());
			Cell labelNCell = wsheet.getCell(labelN.getColumn(), labelN.getRow());
			Cell labelNPrimeCell = wsheet.getCell(labelNPrime.getColumn(), labelNPrime.getRow());
			Cell labelCNNCell = wsheet.getCell(labelCNN.getColumn(), labelCNN.getRow());
			Cell labelTuCell = wsheet.getCell(labelTu.getColumn(), labelTu.getRow());
			Cell labelClassificationCell = wsheet.getCell(labelClassification.getColumn(), labelClassification.getRow());
			Cell labelTimeCell = wsheet.getCell(labelTime.getColumn(), labelTime.getRow());
			Cell labelMsCell = wsheet.getCell(labelMs.getColumn(), labelMs.getRow());

			
			jxl.write.Number userIdValue = new jxl.write.Number(
					userIdCell.getColumn(), total, userId);
			jxl.write.Label labelUriValue = new jxl.write.Label(
					labelURICell.getColumn(), total, uri);
			jxl.write.Number labelNValue = new jxl.write.Number(
					labelNCell.getColumn(), total, n);
			jxl.write.Number labelNPrimeValue = new jxl.write.Number(
					labelNPrimeCell.getColumn(), total, nPrime);
			jxl.write.Number labelCNNValue = new jxl.write.Number(
					labelCNNCell.getColumn(), total, cnnsize);
			jxl.write.Number labelTuValue = new jxl.write.Number(
					labelTuCell.getColumn(), total, tu);
			jxl.write.Number labelClassificationValue = new jxl.write.Number(
					labelClassificationCell.getColumn(),total,
					classificationSize);
			jxl.write.Label labelTimeValue = new jxl.write.Label(
					labelTimeCell.getColumn(), total, time);
			jxl.write.Number labelMsValue = new jxl.write.Number(
					labelMsCell.getColumn(), total, ms);
			

	
			wsheet.addCell(labelUserId);
			wsheet.addCell(labelURI);
			wsheet.addCell(labelN);
			wsheet.addCell(labelNPrime);
			wsheet.addCell(labelCNN);
			wsheet.addCell(labelTu);
			wsheet.addCell(labelClassification);
			wsheet.addCell(labelTime);
			wsheet.addCell(labelMs);
			
			
			wsheet.addCell(userIdValue);
			wsheet.addCell(labelUriValue);
			wsheet.addCell(labelNValue);
			wsheet.addCell(labelNPrimeValue);
			wsheet.addCell(labelCNNValue);
			wsheet.addCell(labelTuValue);
			wsheet.addCell(labelClassificationValue);
			wsheet.addCell(labelTimeValue);
			wsheet.addCell(labelMsValue);
			
			wworkbook.write();
			wworkbook.close();
			
		

	

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}	
	
	
	

}
