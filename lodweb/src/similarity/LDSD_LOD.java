package similarity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;

import node.IConstants;
import node.Lodica;
import node.Node;
import node.NodeUtil;
import node.SparqlWalk;
import util.StringUtilsNode;

public class LDSD_LOD extends LDSD  {
	
	public static String resourceBRA = "http://dbpedia.org/resource/Brazil";
	public static String resourceIRE = "http://dbpedia.org/resource/Republic_of_Ireland";
	//static String resourceA = "http://dbpedia.org/resource/Finding_Nemo";
	public static String resourceA = "http://dbpedia.org/resource/Brazil";
	//static String resourceA = "http://dbpedia.org/resource/!!!";
	//static String resourceB = "http://dbpedia.org/resource/Finding_Dory";
	public static String resourceB = "http://dbpedia.org/resource/Republic_of_Ireland";
	
	
	//static String resourceC = "http://dbpedia.org/resource/AMC_Concord";
	public static String resourceC = "http://dbpedia.org/resource/Pixar";
	//static String resourceC = "http://dbpedia.org/resource/25_Years_On";
	
	
	public static String resourceD = "http://dbpedia.org/resource/Finding_Nemo";
	public static String resourceE = "http://dbpedia.org/resource/Finding_Dory";
	
	static String resourceJC = "http://dbpedia.org/resource/Johnny_Cash";
	static String resourceJCC = "http://dbpedia.org/resource/June_Carter_Cash";
	static String resourceKK = "http://dbpedia.org/resource/Al_Green";
	static String resourceEP = "http://dbpedia.org/resource/Elvis_Presley";
	
	
	public static void main(String[] args) {	
/*		//String resources[] = {resourceA,resourceB,resourceC,resourceD,resourceE};
		//String resources[] = {resourceJC,resourceEP,resourceKK};
		//simVSMbyProperty(resources);
		
		//System.out.println(LDSDweighted(resourceJC,resourceJC));
		//System.out.println(LDSDweighted("http://dbpedia.org/resource/Johnny_Cash","http://dbpedia.org/resource/June_Carter_Cash"));
		System.out.println(LDSDweighted("http://dbpedia.org/resource/Johnny_Cash","http://dbpedia.org/resource/Bob_Dylan"));
		System.out.println(LDSD.LDSDweighted("http://dbpedia.org/resource/Johnny_Cash","http://dbpedia.org/resource/Bob_Dylan"));
		//System.out.println(LDSDweighted("http://dbpedia.org/resource/Johnny_Cash","http://dbpedia.org/resource/Al_Green"));
		//System.out.println(LDSDweighted("http://dbpedia.org/resource/Johnny_Cash","http://dbpedia.org/resource/ElWWvis_Presley"));
		//System.out.println(LDSDweighted("http://dbpedia.org/resource/Diary","http://dbpedia.org/resource/Fight_Club"));
		//System.out.println(LDSDweighted(resourceJC,resourceJCC));
		//System.out.println(LDSDweighted(resourceKK,resourceJC));
		//System.out.println(LDSDweighted(resourceJC,resourceEP));
*/		
		
		
	/*	System.out.println("LDSDdirect	"+LDSDdirect("http://learningsparql.com/ns/expenses#r1","http://learningsparql.com/ns/expenses#r2"));
		System.out.println("LDSDDirectweighted "+LDSDDirectweighted("http://learningsparql.com/ns/expenses#r1","http://learningsparql.com/ns/expenses#r2"));
		
		System.out.println("LDSDIndirect	"+LDSDIndirect("http://learningsparql.com/ns/expenses#r1","http://learningsparql.com/ns/expenses#r2"));
		System.out.println("LDSDIndirectweighted "+LDSDIndirectweighted("http://learningsparql.com/ns/expenses#r1","http://learningsparql.com/ns/expenses#r2"));
		
		System.out.println("LDSDweighted	"+LDSDweighted("http://learningsparql.com/ns/expenses#r1","http://learningsparql.com/ns/expenses#r2"));	*/	
		
		
	      long init = System.currentTimeMillis();
			System.out.println("LDSD_LOD.LDSDweighted	"+LDSD_LOD.LDSDweighted("http://dbpedia.org/resource/Argentina","http://dbpedia.org/resource/Brazil"));
			//System.out.println("LDSD_LOD.LDSDweighted	"+LDSD_LOD.LDSDweighted("http://dbpedia.org/resource/Brazil","http://dbpedia.org/resource/Argentina"));
			//System.out.println("LDSD.LDSDweighted	"+LDSD.LDSDweighted("http://dbpedia.org/resource/Argentina","http://dbpedia.org/resource/Brazil"));

	      NodeUtil.print("Time elapse: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));		
		
		
	}
	public static List<VSMSimilarity> simVSMbyProperty(String[] resources){
		
		List<VSMSimilarity> vSMSimilaritys = new ArrayList<VSMSimilarity>();
		
		List<String> control = new ArrayList<String>();

		for (String r1 : resources) {
			for (String r2 : resources) {
				if (r1!=r2  && (!(control.contains(r2+r1)||control.contains(r1+r2)))) {
					VSMSimilarity vsmSimilarity = new VSMSimilarity(new ResourceImpl(r1), new ResourceImpl(r2));
					vsmSimilarity.setSimScore(LDSDweighted(r1,r2));
					vSMSimilaritys.add(vsmSimilarity);
				}
				control.add(r1+r2);
				control.add(r2+r1);
			}
		}
		
		for (VSMSimilarity vsmSimilarity : vSMSimilaritys) {
			System.out.println("VSM:"+vsmSimilarity.getResource1().getLocalName()+ " X " + vsmSimilarity.getResource2().getLocalName()+ " has score == "+String.format( "%.2f", (vsmSimilarity.getSimScore())));				
		}

		return vSMSimilaritys;
	}	

	public static double LDSDweighted(String resourceA, String resourceB){
		if (resourceA.trim().equals(resourceB.trim())) {
			return 0d;
		}	
		double ldsd = 1d / ((double)(1d + (double)nDDLR(resourceA,resourceB) + (double)nDDLR(resourceB,resourceA) + (double)nIDLI_LOD(resourceA,resourceB) + (double)nIDLO(resourceB,resourceA)));
		//System.out.println("LDSDweighted:"+ldsd);
		return ldsd;
	}

	/**
	 * DONE
	 * Function that compute the number of Indirect and Distinct Income Links
	 * @param resourceA
	 * @param resourceB
	 * @param linkInstance
	 * @return
	 */
	public static double nIDLI_LOD(String resourceA, String resourceB){
		double sum = 0d;
		List<Resource> resources = new ArrayList<Resource>();
		resources.addAll(SparqlWalk.getIndirectDistinctInconmingLinksBetween2Resources(resourceA,resourceB));
		
		Node nodeA = NodeUtil.getNodeByURI(resourceA,Lodica.cnns);
		Node nodeB = NodeUtil.getNodeByURI(resourceB,Lodica.cnns);
		if (nodeA!=null && nodeB!=null && nodeA.getLabel().equals(IConstants.LIKE) && nodeB.getLabel().equals(IConstants.LIKE)) {
			resources.add(new ResourceImpl(IConstants.LIKE));
		}

		for (Resource resource : resources) {
			double totalIndireictIncoming = (double)SparqlWalk.getCountIndirectIncomingLinkFrom2ResourcesAndLink(resourceA, resourceB,resource.getURI());
			double totalIndireictUnobserved = getTotalOfIncomingLinksFromUnobservedRelations(resourceA,resourceB,IConstants.LIKE);
			double indirectIncomingLinkFrom2ResourcesAndLink = totalIndireictIncoming + totalIndireictUnobserved;
			sum = sum + ( indirectIncomingLinkFrom2ResourcesAndLink / (1 + Math.log(calculateTotalNumberIndirectInconmingLinksFromResourceAndLink_LOD(resourceA,resource.getURI()))));			
		}
		
		//System.out.println("nIDLI_LOD sim "+sum);System.out.println();
		return sum; 
	}
	
	/**
	 * @param resourceA
	 * @param resourceB
	 * @return
	 */
	public static double LDSD_LODIndirect(String resourceA, String resourceB){
		
		double totalIndireictIncoming =(double)SparqlWalk.countTotalNumberOfIndirectInconmingLinksBetween2Resources(resourceA, resourceB);
		double totalIndireictUnobserved = getTotalOfIncomingLinksFromUnobservedRelations(resourceA,resourceB,IConstants.LIKE);
		double totalIndireictIncomingICA = totalIndireictIncoming + totalIndireictUnobserved;
		
		double ldsd = 1d / ((double)(1d + totalIndireictIncomingICA + (double)SparqlWalk.countTotalNumberOfIndirectOutgoingLinksBetween2Resources(resourceA, resourceB)));
		
		//System.out.println("LDSDindirect:"+ldsd);
		return ldsd;
	}	
	
	/**
	 * @param resourceA
	 * @param resourceB
	 * @return
	 */
	public static double LDSDIndirectweighted(String resourceA, String resourceB){
		double ldsd = 1d / ((double)(1d + (double)nIDLI_LOD(resourceA,resourceB) + (double)nIDLO(resourceB,resourceA)));
		//System.out.println("LDSDweighted:"+ldsd);
		return ldsd;
	}

	/**
	 * @param resourceA
	 * @param resourceB
	 * @return
	 */
	private static double getTotalOfIncomingLinksFromUnobservedRelations(String resourceA, String resourceB, String link) {
		double total = 0d;
		Node nodeA = null;
		Node nodeB = null;	
		if (Lodica.cnns!=null) {
			nodeA = NodeUtil.getNodeByURI(resourceA,Lodica.cnns);
			nodeB = NodeUtil.getNodeByURI(resourceB,Lodica.cnns);
			if (nodeA!=null && nodeB!=null && nodeA.getLabel().equals(link) &&  nodeB.getLabel().equals(nodeA.getLabel())) {
				total = 1d;
			}
		}		
		//System.out.println("totalDisikeB"+sum);System.out.println();
		return total;
	}	
		

	/**
	 * @param resource
	 * @param link
	 * @return
	 */
	public static int calculateTotalNumberIndirectInconmingLinksFromResourceAndLink_LOD(String resource, String link){
		int numberResourcesIndirectLinkByResourceAndLink = SparqlWalk.countIndirectIncomingLinksFromResourceAndLink(resource,link);
		int numberResourcesIndirectLinkByResourceAndLinkUnobserved = countIndirectIncomingLinksFromResourceAndLinkUnobserved(resource,link);
		numberResourcesIndirectLinkByResourceAndLink = numberResourcesIndirectLinkByResourceAndLink + numberResourcesIndirectLinkByResourceAndLinkUnobserved;
		return numberResourcesIndirectLinkByResourceAndLink;
	}	
	

	/**
	 * Calculates the total of unobserved links from the resource resource to other resources via the link link
	 * @param resource
	 * @param link
	 * @return
	 */
	public static int countIndirectIncomingLinksFromResourceAndLinkUnobserved(String resource, String link){
		Node node = null;
		int numberResourcesIndirectLinkByResourceAndLink = 0;
		node = NodeUtil.getNodeByURI(resource,Lodica.cnns);
		if (node!=null && Lodica.neighboursPlus!=null && Lodica.neighboursPlus.get(node)!=null) {
			numberResourcesIndirectLinkByResourceAndLink = Lodica.neighboursPlus.get(node).size() + Lodica.currentCNNLabeledNodes.size();
/*			NodeUtil.showGraph(false, Lodica.cnns);
			NodeUtil.print("For CNN size:"+Lodica.cnns.size()+ " and URI:" +node.getURI()+ " and link: " +link+" exists "+numberResourcesIndirectLinkByResourceAndLink+" new unobserved links");
			NodeUtil.print("Current Labeled:"+Lodica.currentCNNLabeledNodes.size());
			NodeUtil.print("TU:"+Lodica.neighboursPlus.get(node).size());
			NodeUtil.print("Candidate:"+Lodica.nodeUnderEvaluation.getURI());
			NodeUtil.print("UserID:"+Lodica.userId);
			NodeUtil.print();
			NodeUtil.printNodes(Lodica.cnns);
			NodeUtil.print();
			NodeUtil.printNodes(Lodica.currentCNNLabeledNodes);*/
		}
		return numberResourcesIndirectLinkByResourceAndLink;
	}	
}
