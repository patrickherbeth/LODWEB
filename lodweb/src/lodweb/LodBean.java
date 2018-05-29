package lodweb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.mindmap.DefaultMindmapNode;
import org.primefaces.model.mindmap.MindmapNode;

import node.IConstants;
import node.Lodica;
import node.Node;
import node.NodePrediction;
import node.SimpleTriple;
import node.SparqlWalk;
import util.StringUtilsNode;

@ManagedBean(name = "lodBean")
@ViewScoped
public class LodBean implements Serializable {

	public LodBean() {
		
		List<NodePrediction> nodePredictions = null;
		try {
			nodePredictions = getIcaInstance().startWeb(1,null,IConstants.LDSD_LOD);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (NodePrediction nodePrediction : nodePredictions) {
			if (nodePrediction.getSeed().equals(IConstants.SEED_EVALUATION)) {
				root = new DefaultMindmapNode(nodePrediction.getNode().getURI(), nodePrediction.getNode().getURI(), "6e9ebf", false);
			}
			
			if (Lodica.neighboursPlus.keySet().contains(nodePrediction.getNode().getURI())) {
				MindmapNode cnnNeighbour = new DefaultMindmapNode(nodePrediction.getNode().getURI(), nodePrediction.getNode().getURI(), "FFCC00", true);
				for (Node node : Lodica.neighboursPlus.get(nodePrediction.getNode().getURI())) {
					MindmapNode NPlus = new DefaultMindmapNode(node.getURI(), node.getURI(), "6e9ebf", false);
					cnnNeighbour.addNode(NPlus);
				}
				root.addNode(cnnNeighbour);
			}else{
				MindmapNode cnnNeighbour = new DefaultMindmapNode(nodePrediction.getNode().getURI(), nodePrediction.getNode().getURI(), "FFCC00", true);
				root.addNode(cnnNeighbour);
				
			}
		}
	}
	
	
	
/*	public LodBean() {
		
        root = new DefaultMindmapNode("google.com", "Google WebSite", "FFCC00", false);
        
        for (int i = 0; i < 10; i++) {
        	MindmapNode ips = new DefaultMindmapNode("Filho"+i, "Filho"+i, "6e9ebf", true);
            for (int j = 0; j < 2; j++) {
            	MindmapNode ips2 = new DefaultMindmapNode("Neto"+j, "Neto"+j, "FFEE10", true);
            	ips.addNode(ips2);
            }
        	root.addNode(ips);
        }
        
	}*/
	
	
	private MindmapNode root;
    
    private MindmapNode selectedNode;
 
    public MindmapNode getRoot() {
        return root;
    }
 
    public MindmapNode getSelectedNode() {
        return selectedNode;
    }
    public void setSelectedNode(MindmapNode selectedNode) {
        this.selectedNode = selectedNode;
    }
 
    public void onNodeSelect(SelectEvent event) {
        MindmapNode node = (MindmapNode) event.getObject();
         
        //populate if not already loaded
        if(node.getChildren().isEmpty()) {
            Object label = node.getLabel();
            
            	
    		List<NodePrediction> nodePredictions = null;
    		try {
    			nodePredictions = getIcaInstance().startWeb(1,(String)label,IConstants.LDSD_LOD);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		
    		String color1 = StringUtilsNode.getHexRandomColorWithNoHashTag();
    		for (NodePrediction nodePrediction : nodePredictions) {
    			if (!nodePrediction.getSeed().equals(IConstants.SEED_EVALUATION)) {
	    			if (Lodica.neighboursPlus.keySet().contains(nodePrediction.getNode().getURI())) {
	    				MindmapNode cnnNeighbour = new DefaultMindmapNode(nodePrediction.getNode().getURI(), nodePrediction.getNode().getURI(), color1.replace("#","").trim(), true);
	    				String color2 = StringUtilsNode.getHexRandomColorWithNoHashTag();
	    				for (Node nodeLocal : Lodica.neighboursPlus.get(nodePrediction.getNode().getURI())) {
	    					MindmapNode NPlus = new DefaultMindmapNode(nodeLocal.getURI(), nodeLocal.getURI(), color2.replace("#","").trim(), false);
	    					cnnNeighbour.addNode(NPlus);
	    				}
	    				node.addNode(cnnNeighbour);
	    			}else{
	    				MindmapNode cnnNeighbour = new DefaultMindmapNode(nodePrediction.getNode().getURI(), nodePrediction.getNode().getURI(),color1, true);
	    				node.addNode(cnnNeighbour);
	    				
	    			}
    			}
    		}            
            
            
 
/*            if(label.equals("NS(s)")) {
                for(int i = 0; i < 25; i++) {
                    node.addNode(new DefaultMindmapNode("ns" + i + ".google.com", "Namespace " + i + " of Google", "82c542", false));
                }
            }
            else if(label.equals("IPs")) {
                for(int i = 0; i < 18; i++) {
                    node.addNode(new DefaultMindmapNode("1.1.1."  + i, "IP Number: 1.1.1." + i, "fce24f", false));
                } 
            }
            else if(label.equals("Malware")) {
                for(int i = 0; i < 18; i++) {
                    String random = UUID.randomUUID().toString();
                    node.addNode(new DefaultMindmapNode("Malware-"  + random, "Malicious Software: " + random, "3399ff", false));
                }
            }*/
        }   
    }
     
    public void onNodeDblselect(SelectEvent event) {
        this.selectedNode = (MindmapNode) event.getObject();        
    }	
	
	
	
	
	

	private boolean isEvaluation = false;

	public boolean isEvaluation() {
		return isEvaluation;
	}

	public boolean getIsEvaluation() {
		return isEvaluation;
	}

	public void setEvaluation(boolean isEvaluation) {
		this.isEvaluation = isEvaluation;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<SelectItem> getDomains() {
		return domains;
	}

	public void setDomains(List<SelectItem> domains) {
		this.domains = domains;
	}

	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	private List<SelectItem> domains;

	private String text;

	private String subject = "";

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	private String link;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@PostConstruct
	public void init() {
		domains = new ArrayList<SelectItem>();
		for (Resource resource : SparqlWalk.getDbpediaDomains(30)) {
			domains.add(new SelectItem(resource.getLocalName(), resource
					.getLocalName()));
		}
	}

	/**
	 * DROPDOWN MENU
	 * 
	 * @param event
	 */
	public void fireSelection(ValueChangeEvent event) {
		// System.out.println("Domain chosen : "+event.getNewValue()+", Old: "+event.getOldValue());
		System.out.println("Domain chosen : " + event.getNewValue());
		setEvaluation(false);

		lodica = null;

		populateTriples(SparqlWalk.searchDbpediaResourcesTriplesByDomain(
				"http://dbpedia.org/ontology/" + event.getNewValue(), 50));

	}

	public void searchLod(String query) {
		System.out.println("A query eh " + query);
		this.setSubject(query);
		populateTriples(SparqlWalk.searchDbpediaResourcesTriples(query));
	}

	Lodica lodica;

	public Lodica getIcaInstance() {
		if (lodica == null) {
			lodica = new Lodica();
		}
		return lodica;

	}

	/**
	 * @throws Exception
	 */
	public void getRelatedResources() throws Exception {
		
		Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String resourceURI = params.get("resourceUri");
		Resource resource = new ResourceImpl(resourceURI);
		this.subject = resource.getLocalName();
		// this.subject = new
		// ResourceImpl(resourceURI).getLocalName().toString();
		// populateTriples(SparqlWalk.getDbpediaObjecstTriplesBySubjectURI(resourceURI));
		// Lodica.useExternalClassifiedNodesForTrainingSet = true;

		Set<Node> candidates = new HashSet<Node>();
		
		candidates.add(new Node(null, IConstants.NO_LABEL, resourceURI));
		
		List<NodePrediction> nodePredictions = null;//getIcaInstance().startWeb();

		List<SimpleTriple> simpleTriples = new LinkedList<SimpleTriple>();

		for (NodePrediction nodePrediction : nodePredictions) {
			simpleTriples.add(new SimpleTriple(resourceURI, "("
					+ nodePrediction.getNode().getId() + ") :"
					+ nodePrediction.getPredictedLabel() + ":"
					+ nodePrediction.getPredictionScore(), nodePrediction
					.getNode().getURI()));
		}

		this.setEvaluation(true);
		populateTriples(simpleTriples);

	}

	public void updateLabelLIKE() throws Exception {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String resourceLIKE = params.get("resourceLIKE");
		System.out.println("resourceLIKE " + resourceLIKE);
		throw new Exception("fix this with new code");
		// Lodica.updateLabelFromWeb(resourceLIKE,IConstants.LIKE);
	}

	public void updateLabelDISLIKE() throws Exception {
		Map<String, String> params = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestParameterMap();
		String resourceDISLIKE = params.get("resourceDISLIKE");
		System.out.println("resourceDISLIKE " + resourceDISLIKE);
		throw new Exception("fix this with new code");
		// Lodica.updateLabelFromWeb(resourceDISLIKE,IConstants.DISLIKE);
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private List<ResourceView> resourceViews = new ArrayList<ResourceView>();

	public List<ResourceView> getResourceViews() {
		return resourceViews;
	}

	public void setResourceViews(List<ResourceView> resourceViews) {
		this.resourceViews = resourceViews;
	}

	public void populateTriples(List<SimpleTriple> triples) {

		// System.out.println(triples.size());

		resourceViews = new ArrayList<ResourceView>();

		for (SimpleTriple triple : triples) {
			ResourceView resourceView = new ResourceView();
			resourceView.setSubject(triple.getSubject());
			resourceView.setProperty(triple.getPredicate());
			resourceView.setObject(triple.getObject());
			this.resourceViews.add(resourceView);
		}

	}

}
