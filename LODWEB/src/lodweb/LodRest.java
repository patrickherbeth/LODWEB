package lodweb;
 
/**
 * @author Crunchify.com
 * 
 */
 
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import database.DBFunctions;
import model.OnlineEvaluation;
import model.User;
import node.Classifier;
import node.IConstants;
import node.Item;
import node.Lodica;
import node.Node;
import node.NodePrediction;
import node.NodeUtil;
import node.SimpleTriple;
import node.SparqlWalk;
import node.ThreadLod;
import node.ThreadPredictions;
 
@Path("/")
public class LodRest {
	
	
	@POST
	@Path("/lodrest")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response lodREST(InputStream incomingData) {
		StringBuilder crunchifyBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				crunchifyBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Data Received: " + crunchifyBuilder.toString());
 
		// return HTTP response 200 in case of success
		return Response.status(200).entity(crunchifyBuilder.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	@GET
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON)
	public Response verifyRESTService(InputStream incomingData) {
		//System.out.println("/verify");
		//String result = "{\"edges\":[{\"source\":\"a\", \"target\":\"b\"},{\"source\":\"b\", \"target\":\"d\"},{\"source\":\"a\", \"target\":\"c\"},{\"source\":\"c\",\"target\":\"d\"},{\"source\":\"d\",\"target\":\"e\"},{\"source\":\"e\",\"target\":\"a\"},{\"source\":\"f\",\"target\":\"c\"}],\"nodes\":[{\"id\":\"a\", \"label\":\"a\"},{\"id\":\"b\", \"label\":\"b\"},{\"id\":\"c\", \"label\":\"c\"},{\"id\":\"d\", \"label\":\"d\"},{\"id\":\"e\", \"label\":\"e\"},{\"id\":\"f\", \"label\":\"f\"}]}";
		String result = "It works";		
		// return HTTP response 200 in case of success
		return Response.status(200).entity(result).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/profile/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response profile(@PathParam("userid") int userid) {
		
		StringBuilder jasonNodeString = new StringBuilder();
		Set<String> likeItems = Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(userid);
		if (likeItems.isEmpty()) {
			return Response.status(200).entity("<label class='instruction-light'>Your profile is empty.</label>").header("Access-Control-Allow-Origin", "*").build();
		}
		jasonNodeString.append("<ul>");
		for (String string : likeItems) {
			String uri = NodeUtil.removeNamespace(string);
			//jasonNodeString.append("<li><a id="+uri+" target='_blank' href="+string+" onclick='setUri(id);' >"+uri+"</a>&nbsp;<img id="+uri+" onclick='removeprofile(id);' class='userProfileRest' src='../dist/img/close.png' alt='Remove' width='12' height='12'/></li>");
			jasonNodeString.append("<li><a id="+uri+"  href='#' onclick='setUri(id);' >"+uri+"</a>&nbsp;<img id="+uri+" onclick='removeprofile(id);' class='userProfileRest' src='../dist/img/close.png' alt='Remove' width='12' height='12'/></li>");
		}
		jasonNodeString.append("</ul>");
    	//System.out.println(jasonNodeString.toString()); onclick='setUri("+string+");'
		// return HTTP response 200 in case of success
		return Response.status(200).entity(jasonNodeString.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	

	
	
	@POST
	@Path("/updateimageurlpath/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response updateimageurlpath(@FormParam("uri") String uri , @FormParam("imageurlpath") String imageurlpath ) {
		
		JSONObject json = new JSONObject();
		try {
			Lodica.getDatabaseConnection().updateItemUrlPath(NodeUtil.concatNamespace(uri), imageurlpath);
			json.put("uri", uri);
			json.put("message","sucess");
			json.put("path",imageurlpath);
			return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();			
		} catch (Exception e) {
			json.put("uri", uri);
			json.put("message","fail");			
			return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
		}
	}	

	@GET
	@Path("/elicitation/{category}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response elicitation(@PathParam("category") String category) {

		StringBuilder jasonNodeString = new StringBuilder();
		
		List<Item> items = new ArrayList<Item>();
				
		items.addAll(new ArrayList<Item>(Lodica.getDatabaseConnection().getItems(category,100)));

		if (items.isEmpty()) {
			return Response.status(200).entity("Sorry, no items to show").header("Access-Control-Allow-Origin", "*").build();
		}

		for (Item item : items) {
			String itemURI =  NodeUtil.removeNamespace(item.getUri());
			String itemURILabel = itemURI;
			
			if (item.getImg()==null || item.getImg().isEmpty()){
				item.setImg(IConstants.SORRY_NO_IMAGE);
				DBFunctions.insertItem(item);
			}
			
			if ( item.getImg().equals(IConstants.SORRY_NO_IMAGE) && itemURI.length()>20 ) {
				itemURILabel = itemURI.substring(0,20).concat("...");
			}else if (!item.getImg().equals(IConstants.SORRY_NO_IMAGE) && itemURI.length()>25) {
				itemURILabel = itemURI.substring(0,25).concat("...");
			}

		if (!item.getImg().equals(IConstants.SORRY_NO_IMAGE)) {
			  
			  jasonNodeString.append("<div class='responsive'><div class='gallery'>"
						+ "<img id='imgid_"+itemURI+"' src='"+item.getImg()+"' onclick='elicitPreference(id);' alt='"+itemURI+"'/><div class='desc'><a target='_blank' href="+item.getUri()+">"+itemURILabel+"</a>"
						+ "</div></div></div>");
		}else if (item.getImg().equals(IConstants.SORRY_NO_IMAGE)) {
			  
			String updateUrlImage = 
					"<div id='updateid_"+itemURI+"'class='modal' style='display:none;'><div class='modal-content'>"
				  + "<table>"
				  + "<tr bgcolor='black'><td><label style='color:white' for='imageTitle' class='instruction-light'>Update Image URL</label></td></tr>"							
				  + "<tr><td><label for='imageurl' class='instruction-light'>Image Url:&nbsp;</label><input id='imageurl_"+itemURI+"' name='imageurl_"+itemURI+"' type='text' size='100'/></td></tr>"
				  + "<tr><td><button id='submitid_"+itemURI+"' onclick='updateUrlPath(id)' class='myButton'>Update</button>"
  		  		  + "&nbsp;<button id='dismissid_"+itemURI+"' onclick='div_hide(id)' class='myButton'>Close</button></td></tr>"
		  		  + "</table></div></div>";		
				
			  jasonNodeString.append("<div class='responsive'><div class='gallery'>"
					+ "<img id='imgid_"+itemURI+"' src='"+item.getImg()+"' onclick='elicitPreference(id);' alt='"+itemURI+"'/><div class='desc'><a target='_blank' href="+item.getUri()+">"+itemURILabel+"</a>"
					+ "&nbsp;<a id='hrefid_"+itemURI+"' onclick='div_show(id);' href='#'>(*)</a>"
                    //+ "<img id='hrefid_"+itemURI+"' onclick='div_show(id);' src='../dist/img/edit.png' alt='Update Image'  width='1' height='1'/>"					
					+ "</div>"+updateUrlImage+"</div></div>");			
		  }
		}

    	//System.out.println(jasonNodeString.toString());
		// return HTTP response 200 in case of success
		return Response.status(200).entity(jasonNodeString.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/searchitem/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchitem(@PathParam("query") String query) {

		StringBuilder jasonNodeString = new StringBuilder();
		
		List<Item> items = new ArrayList<Item>(Lodica.getDatabaseConnection().searchItemsByTitle(query));

		if (items.isEmpty()) {
			return Response.status(200).entity("Sorry, no items to show").header("Access-Control-Allow-Origin", "*").build();
		}

		for (Item item : items) {
			String itemURI = NodeUtil.removeNamespace(item.getUri());
			String itemURILabel = itemURI;
			
			if ( item.getImg().equals(IConstants.SORRY_NO_IMAGE) && itemURI.length()>20 ) {
				itemURILabel = itemURI.substring(0,20).concat("...");
			}else if (!item.getImg().equals(IConstants.SORRY_NO_IMAGE) && itemURI.length()>25) {
				itemURILabel = itemURI.substring(0,25).concat("...");
			}

		if (!item.getImg().equals(IConstants.SORRY_NO_IMAGE)) {
			  
			  jasonNodeString.append("<div class='responsive'><div class='gallery'>"
						+ "<img id='imgid_"+itemURI+"' src='"+item.getImg()+"' onclick='elicitPreference(id);' alt='"+itemURI+"'/><div class='desc'><a target='_blank' href="+item.getUri()+">"+itemURILabel+"</a>"
						+ "</div></div></div>");
		}else if (item.getImg().equals(IConstants.SORRY_NO_IMAGE)) {
			  
			String updateUrlImage = 
					"<div id='updateid_"+itemURI+"'class='modal' style='display:none;'><div class='modal-content'>"
				  + "<table>"
				  + "<tr bgcolor='black'><td><label style='color:white' for='imageTitle' class='instruction-light'>Update Image URL</label></td></tr>"							
				  + "<tr><td><label for='imageurl' class='instruction-light'>Image Url:&nbsp;</label><input id='imageurl_"+itemURI+"' name='imageurl_"+itemURI+"' type='text' size='100'/></td></tr>"
				  + "<tr><td><button id='submitid_"+itemURI+"' onclick='updateUrlPath(id)' class='myButton'>Update</button>"
  		  		  + "&nbsp;<button id='dismissid_"+itemURI+"' onclick='div_hide(id)' class='myButton'>Close</button></td></tr>"
		  		  + "</table></div></div>";		
				
			  jasonNodeString.append("<div class='responsive'><div class='gallery'>"
					+ "<img id='imgid_"+itemURI+"' src='"+item.getImg()+"' onclick='elicitPreference(id);' alt='"+itemURI+"'/><div class='desc'><a target='_blank' href="+item.getUri()+">"+itemURILabel+"</a>"
					+ "&nbsp;<a id='hrefid_"+itemURI+"' onclick='div_show(id);' href='#'>(*)</a>"
					+ "</div>"+updateUrlImage+"</div></div>");			
		  }
		}

    	//System.out.println(jasonNodeString.toString());
		// return HTTP response 200 in case of success
		return Response.status(200).entity(jasonNodeString.toString()).header("Access-Control-Allow-Origin", "*").build();
	}		
	
	@GET
	@Path("/infobox/{uri}/{tss}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response infobox(@PathParam("uri") String uri,@PathParam("tss") String tss) {
		
		StringBuilder jasonNodeString = new StringBuilder();
		try {
			uri = NodeUtil.concatNamespace(uri);
			jasonNodeString = new StringBuilder();
			Set<String> literals = new HashSet<String>();
			SparqlWalk.setService(tss);
			List<SimpleTriple> triples = SparqlWalk.getLiteralByUri(uri);
			if (triples.isEmpty()) {
				return Response.status(200).entity("<table id='infoboxTable'><tr><th>Sorry</th><td>No description available for this resource.</td></tr></table>").header("Access-Control-Allow-Origin", "*").build();
			}
			//jasonNodeString.append("<table id='infoboxTable'><tr>");
			jasonNodeString.append("<table id='infoboxTable'>");
			for (SimpleTriple simpleTriple : triples) {
				
				jasonNodeString.append("<tr>");
				if (simpleTriple.getPredicate().contains("has abstract") && simpleTriple.getObject().length()>200) {
					jasonNodeString.append("<th>Abstract</th>");
					jasonNodeString.append("<td>"+StringUtils.capitalize(simpleTriple.getObject().substring(0, 200))+"<a target='_blank' href="+uri+">(+)</a></td>");	
				}else{
					if (simpleTriple.getPredicate().contains("has abstract") && simpleTriple.getObject().length()>200) {
						jasonNodeString.append("<th>Abstract</th>");
						jasonNodeString.append("<td>"+StringUtils.capitalize(simpleTriple.getObject())+"</td>");
					}else{
						String literalValue = StringUtils.capitalize(simpleTriple.getPredicate());
						if (!literals.contains(literalValue)) {
							jasonNodeString.append("<td>"+StringUtils.capitalize(simpleTriple.getPredicate())+"</td>");
							literals.add(literalValue);
							jasonNodeString.append("<td>"+StringUtils.capitalize(simpleTriple.getObject())+"</td>");
						}
							
					}
				}
				jasonNodeString.append("</tr>");
			}
			jasonNodeString.append("</table>");
	    	//System.out.println(jasonNodeString.toString());
		} catch (Exception e) {
			return Response.status(200).entity("Exception: "+e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
		
		return Response.status(200).entity(jasonNodeString.toString()).header("Access-Control-Allow-Origin", "*").build();
		
	}	

	@GET
	@Path("/updateprofile/{userid}/{uri}/{rating}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response profile(@PathParam("userid") int userid,@PathParam("uri") String uri, @PathParam("rating") String rating) {
		String uriLocal = "http://dbpedia.org/resource/"+uri;
		if (rating.equals(IConstants.LIKE)) {
			Lodica.getDatabaseConnection().insertUserProfile(userid,uriLocal);
			new ThreadLod(uriLocal).start();
		}else if (rating.equals(IConstants.DISLIKE)) {
			Lodica.getDatabaseConnection().deleteBookLikeByUriAndUserID(uriLocal,userid);
			if (Lodica.getDatabaseConnection().getUrisFromOnlineEvaluationByUserAndSeedWithSKIP(userid, uriLocal).isEmpty()) {
				Lodica.getDatabaseConnection().updateOnlineEvaluationByUserAndRecommendationUriWithAddProfile(userid, uriLocal, "false");	
			}else{
				Lodica.getDatabaseConnection().deleteOnlineEvaluation(userid, uriLocal);
			}
		}
		
		//When the user profile is updated, the his cache needs to be recalculated and the old values are useless therefore they are erased.
		Lodica.getDatabaseConnection().deleteSimilarityByUserIdAndMethod(userid, IConstants.LDSD_LOD);
		Lodica.getDatabaseConnection().deletePredictionByUserIdAndSimilarityMethod(userid, IConstants.LDSD_LOD);
		NodeUtil.print("Deleted similarities and predictions for USER ID: "+userid);

		StringBuilder jasonNodeString = new StringBuilder();
		
		Set<String> likeItems = Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(userid);
		if (likeItems.isEmpty()) {
			return Response.status(200).entity("User "+userid+" likes nothing").build();
		}
		jasonNodeString.append("<ul>");
		for (String string : likeItems) {
			String uriId = NodeUtil.removeNamespace(string);
			//jasonNodeString.append("<li><a id="+uriId+" onmouseover='setUri(id);' target='_blank' href="+string+">"+uriId+"</a>&nbsp;<img id="+uriId+" onclick='removeprofile(id);' class='userProfileRest' src='../dist/img/close.png' alt='Remove' width='12' height='12'/></li>");
			jasonNodeString.append("<li><a id="+uriId+" href='#' onclick='setUri(id);' >"+uriId+"</a>&nbsp;<img id="+uriId+" onclick='removeprofile(id);' class='userProfileRest' src='../dist/img/close.png' alt='Remove' width='12' height='12'/></li>");						
		}
		jasonNodeString.append("</ul>");
    	//System.out.println(jasonNodeString.toString());
		// return HTTP response 200 in case of success
		return Response.status(200).entity(jasonNodeString.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@GET
	@Path("/register/{first}/{second}/{email}/{password}/{sex}/{agerange}/{country}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@PathParam("first") String first,@PathParam("second") String second,
			@PathParam("email") String email,@PathParam("password") String password,@PathParam("sex") String sex,@PathParam("agerange") String agerange,@PathParam("country") String country) {
		try {
			User user = Lodica.getDatabaseConnection().getUser(first,second,email,password);
			if (user!=null) {
				JSONObject json = new JSONObject();
				json.put("userid", user.getUserid());
				json.put("first", user.getFirst());
				json.put("second", user.getSecond());
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
			}else{
				Lodica.getDatabaseConnection().insertOrUpdateUser(first, second, email, password,sex,agerange,country);
				user = Lodica.getDatabaseConnection().getUser(email, password);
				JSONObject json = new JSONObject();
				json.put("userid", user.getUserid());
				json.put("first", user.getFirst());
				json.put("second", user.getSecond());
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();				
			}
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}	
	
	@GET
	@Path("/login/{emaillogin}/{passlogin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@PathParam("emaillogin") String emaillogin,@PathParam("passlogin") String passlogin) {
		try {
			User user = Lodica.getDatabaseConnection().getUser(emaillogin, passlogin);
			if (user!=null) {
				JSONObject json = new JSONObject();
				json.put("userid", user.getUserid());
				json.put("first", user.getFirst());
				json.put("second", user.getSecond());
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
			}else{

				return Response.status(200).entity("login.failed").header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}		

	@GET
	@Path("/eval/{userid}/{uri}/{seed}/{similaritymethod}/{strategy}/{star}/{accuracy}/{understand}/{satisfaction}/{novelty}/{addprofile}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response evaluation(@PathParam("userid") int userid,@PathParam("uri") String uri,
			@PathParam("seed") String seed,@PathParam("similaritymethod") String similaritymethod,
			@PathParam("strategy") String strategy,
			@PathParam("star") int star,
			@PathParam("accuracy") String accuracy,
			@PathParam("understand") String understand,
			@PathParam("satisfaction") String satisfaction,
			@PathParam("novelty") String novelty,
			@PathParam("addprofile") String addprofile) {
		try {
			uri = NodeUtil.concatNamespace(uri);
			seed = NodeUtil.concatNamespace(seed);
			
			if (Lodica.getDatabaseConnection().getOnlineEvaluationsByUserAndUriAndSeed(userid, seed, uri, similaritymethod)!=null) {
				Lodica.getDatabaseConnection().insertOrUpdateOnlineEvaluation(userid,uri,seed,similaritymethod,strategy,star,accuracy,understand,satisfaction,novelty,addprofile);
				return Response.status(200).entity("Evaluation updated").header("Access-Control-Allow-Origin", "*").build();
			}else if (Lodica.getDatabaseConnection().getOnlineEvaluationsByUserAndSeed(userid,seed,similaritymethod).size()<IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED) {
				Lodica.getDatabaseConnection().insertOrUpdateOnlineEvaluation(userid,uri,seed,similaritymethod,strategy,star,accuracy,understand,satisfaction,novelty,addprofile);
				return Response.status(200).entity("Evaluation succeed ").header("Access-Control-Allow-Origin", "*").build();
			}else{
				return Response.status(200).entity("Only "+IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED+" evaluations are permitted per seed").header("Access-Control-Allow-Origin", "*").build();				
			}
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/removeevaluation/{userid}/{uri}/{seed}/{similaritymethod}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeevaluation(@PathParam("userid") int userid,@PathParam("uri") String uri,@PathParam("seed") String seed,@PathParam("similaritymethod") String similaritymethod) {
		try {
			uri = NodeUtil.concatNamespace(uri);
			seed = NodeUtil.concatNamespace(seed);
			if (Lodica.getDatabaseConnection().getUrisFromOnlineEvaluationByUserAndSeedWithSKIP(userid, seed, similaritymethod).size()>0) {
				Lodica.getDatabaseConnection().deleteOnlineEvaluationBySeed(userid,seed,similaritymethod);	
			}else{
				Lodica.getDatabaseConnection().deleteOnlineEvaluation(userid,uri,seed,similaritymethod);	
			}
			Lodica.getDatabaseConnection().deleteBookLikeByUriAndUserID(uri,userid);
			return Response.status(200).entity("Evaluation succeed").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(404).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/clearcache/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response clearcache(@PathParam("userid") int userid) {
		try {
			Lodica.getDatabaseConnection().deletePredictionByUserId(userid);
			return Response.status(200).entity("Cache Removed").header("Access-Control-Allow-Origin", "*").build();
		} catch (Exception e) {
			return Response.status(200).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}	
	
	@GET
	@Path("/retrieveevalulation/{userid}/{uri}/{seed}/{similaritymethod}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEvaluation(@PathParam("userid") int userid,@PathParam("uri") String uri,@PathParam("seed") String seed,@PathParam("similaritymethod") String similaritymethod) {
		try {
			uri = NodeUtil.concatNamespace(uri);
			seed = NodeUtil.concatNamespace(seed);
			
			OnlineEvaluation onlineEvaluation = Lodica.getDatabaseConnection().getOnlineEvaluation(userid,uri,seed,similaritymethod);

			if (onlineEvaluation!=null) {
				JSONObject json = new JSONObject();
				json.put("userid", onlineEvaluation.getUserid());
				json.put("uri", NodeUtil.removeNamespace(onlineEvaluation.getUri()));
				json.put("seed", NodeUtil.removeNamespace(onlineEvaluation.getSeed()));
				json.put("similarityMethod", onlineEvaluation.getSimilarityMethod());
				json.put("star", onlineEvaluation.getStar());
				json.put("strategy", onlineEvaluation.getStrategy());
				json.put("accuracy", onlineEvaluation.getAccuracy());
				json.put("understand", onlineEvaluation.getUnderstand());
				json.put("satisfaction", onlineEvaluation.getSatisfaction());
				json.put("novelty", onlineEvaluation.getNovelty());
				json.put("addprofile", onlineEvaluation.getAddprofile());
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
			}else{
				return Response.status(200).entity("null").header("Access-Control-Allow-Origin", "*").build();
			}
		} catch (Exception e) {
			return Response.status(200).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
	}
	
	@GET
	@Path("/evaluationlog/{userid}/{similaritymethod}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response evaluationlog(@PathParam("userid") int userid,@PathParam("similaritymethod") String similaritymethod) {
		JSONObject json = new JSONObject();
		List<String> seedsOnlineEvaluations = new ArrayList<String>();
		StringBuilder jasonNodeString = new StringBuilder();
		StringBuilder evaluationLogMessage = new StringBuilder();
		Integer missingEvaluationsPerUser = 0;
		int seedsWithSkip = 0;
		
		try {
			
			Set<String> likeItems = Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(userid);
			if (likeItems.size()<IConstants.MIN_PROFILE_SIZE) {
				json.put("evaluationLogTable","");
				json.put("evaluationLogMessage","<table id='evaluationLog'><td class='instruction-light'>Please fill your profile with at least "+IConstants.MIN_PROFILE_SIZE+" items. Then pick one item and click on the <b>Recommend</b> button. </td></tr></table>");
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();				
			}			
			
			
			jasonNodeString = new StringBuilder();
			seedsOnlineEvaluations = Lodica.getDatabaseConnection().getSeedsFromOnlineEvaluationByUser(userid, similaritymethod);
			seedsWithSkip = Lodica.getDatabaseConnection().getSeedsFromOnlineEvaluationByUserWithSKIP(userid, similaritymethod).size();
			if (seedsOnlineEvaluations==null || seedsOnlineEvaluations.isEmpty()) {
				json.put("evaluationLogTable","");
				json.put("evaluationLogMessage","<table id='evaluationLog'><td class='instruction-light'>Lets go ! You still have "+IConstants.MAX_ONLINE_EVALUATIONS_PER_USER+" missing recommendations to evaluate. Please, click on one item from your profile, and click on the <b>Recommend</b> button.</td></tr></table>");
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();				
			}

			
			//header
			jasonNodeString.append("<table border='1' id='evaluationLog'><tr bgcolor='white'><th>SEED</th>");
			for (int i = 0; i < IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED; i++) {
				jasonNodeString.append("<th>EVALUATION "+(i+1)+"</th>");	
			}
			jasonNodeString.append("</tr>");
			
			Collections.sort(seedsOnlineEvaluations);
			for (String seedForEvaluation : seedsOnlineEvaluations) {
				int count = 0;
				String seedUri = NodeUtil.removeNamespace(seedForEvaluation);
				jasonNodeString.append("<tr>");
				jasonNodeString.append("<td bgcolor='#CCFFCC' align='center' ><a id="+seedUri+" class='seedEvaluation' onclick='setUri(id);'>"+seedUri+"</a></td>");
				List<String> evaluatedRecommendations = Lodica.getDatabaseConnection().getUrisFromOnlineEvaluationByUserAndSeed(userid,seedForEvaluation,similaritymethod);
				int missing = Math.max(IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED-evaluatedRecommendations.size(),0);
				missingEvaluationsPerUser=missingEvaluationsPerUser+missing;
				for (int i = 0; i <missing; i++) {
					evaluatedRecommendations.add(IConstants.MISSING);
				}
				//Collections.sort(evaluatedRecommendations);
				for (String urisOnlineEvaluation : evaluatedRecommendations) {
					if (urisOnlineEvaluation.equals(IConstants.MISSING)) {
						jasonNodeString.append("<td align='center' id='missing' name="+seedUri+"  bgcolor='#f2dede'>"+urisOnlineEvaluation+"</td>");
						//count++;
					}else if (urisOnlineEvaluation.startsWith(IConstants.PICK_ANOTHER_SEED)) {
						//String message = IConstants.PICK_ANOTHER_SEED.replace("http://dbpedia.org/resource/","");
						String message = IConstants.NO_RECOMMENDATION;
						jasonNodeString.append("<td align='center' id='pick' name="+seedUri+"  bgcolor='#f2dede'>"+message+"</td>");
						//count++; 
					
					}else{
						String evaluatedRecommendation = NodeUtil.removeNamespace(urisOnlineEvaluation);
						//style='text-decoration:underline;color:blue;display:inline-block;'
						jasonNodeString.append("<td align='center'  bgcolor='#ffd8b7'>"
								+ "<a class='recommendationEvaluatedClass' id="+evaluatedRecommendation+""
								+ " onclick=showinfoboxFromEvaluation(id,'"+seedUri+"');>"+evaluatedRecommendation+"</a>&nbsp;<img  id="+evaluatedRecommendation+" name="+seedUri+" onclick='removeEvaluationItem(id,name);evaluationLog();' class='removeEvaluationItem' src='../dist/img/close.png' alt='Remove' width='12' height='12'/></td>");							
						count++;
					}
					if (count==IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED) {
						break;
					}
				}
				jasonNodeString.append("</tr>");
			}			
			jasonNodeString.append("</table>");
	    	//System.out.println(jasonNodeString.toString());
		} catch (Exception e) {
			json.put("evaluationLogTable","");
			json.put("evaluationLogMessage", "Exception: "+e.getMessage());
			return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
		}

		json.put("evaluationLogTable", jasonNodeString.toString());
		int totalSeedToEvaluate = (int) IConstants.MAX_ONLINE_EVALUATIONS_PER_USER / IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED;
		int totalMissingSeeds = IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED *(totalSeedToEvaluate-(seedsOnlineEvaluations.size()-seedsWithSkip));
		int totalMissing = missingEvaluationsPerUser+totalMissingSeeds;
		if (((totalMissing%IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED)==0) && (totalMissing!=0) && (totalMissing!=IConstants.MAX_ONLINE_EVALUATIONS_PER_USER)) {
			json.put("evaluationLogMessage", evaluationLogMessage.append("You still have <b>"+(totalMissing)+"</b> missing recommendations to evaluate. Please, pick a <b>seed</b> from your profile or from recommendations that you liked, and click on the <b>Recommend</b> button."));
		}else if (totalMissing==IConstants.MAX_ONLINE_EVALUATIONS_PER_USER) {
			json.put("evaluationLogMessage", evaluationLogMessage.append("You still have <b>"+(totalMissing)+"</b> missing recommendations to evaluate. Please, pick another <b>seed</b> from your profile or from recommendations that you liked, and click on the <b>Recommend</b> button."));
		}else if (((totalMissing%IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED)!=0) && (totalMissing!=IConstants.MAX_ONLINE_EVALUATIONS_PER_USER)) {
			json.put("evaluationLogMessage", evaluationLogMessage.append("You still have <b>"+(totalMissing)+"</b> missing recommendations to evaluate. If there is no recommendations, please click on the <b>first seed</b> with missing evaluations to generate new ones."));
		}else if (totalMissing==0) {
			json.put("evaluationLogMessage", evaluationLogMessage.append("Congratulations, you have concluded the experiment. Thanks for your participation !"));
		}
		
		return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
	}	
	

	public static void main(String[] args) {
		//LodRest l = new LodRest();
		//l.infobox("The_Lost_Boys", "DBPEDIA");	
		//l.newNodes(4,"Finding_Nemo",-1d,true,false,100,true,false,"DBPEDIA",30);
		
		//l.elicitation(IConstants.CATEGORY_BOOK);
	}
	
	
	public void filterByType(){
/*		
		List<NodePrediction> nodePredictionsAux = nodePredictions;
		List<NodePrediction> nodePredictionsAux2 = new ArrayList<NodePrediction>();
		for (NodePrediction nodePredictionAux : nodePredictionsAux) {
			String type = SparqlWalk.getMostSpecificSubclasseOfDbpediaResource(nodePredictionAux.getNode().getURI()).getURI().replace("http://dbpedia.org/resource/","");
			
			List<String> list = NodeUtil.convertResourcesInStringSet(SparqlWalk.getCategoryByResource(nodePredictionAux.getNode().getURI()));

			//if (type.equals("Film") && (list.contains("http://dbpedia.org/resource/Category:American_films"))){
			if ((list.contains("http://dbpedia.org/resource/Category:American_films"))){
				nodePredictionsAux2.add(nodePredictionAux);
			}
			
			if (!list.contains("http://dbpedia.org/resource/Category:American_films")){
				nodePredictionsAux2.add(nodePredictionAux);
			}				
		}			
		nodePredictions = nodePredictionsAux2;*/
	}

	@GET
	@Path("/new/{userid}/{uri}/{scorefilter}/{removeCategory}/{showTU}/{topN}/{similaritymethod}/{showAll}/{tss}/{hybrid}/{maxhybrid}/{nodeChildIds}/{maxNode}/{enablecache}/{isevaluation}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response newNodes(@PathParam("userid") int userid,@PathParam("uri") String uri,@PathParam("scorefilter") double scorefilter,@PathParam("removeCategory") boolean removeCategory,@PathParam("showTU") boolean showTU,
			@PathParam("topN") int topN,@PathParam("similaritymethod") String similaritymethod,@PathParam("showAll") boolean showAll,@PathParam("tss") String tss,@PathParam("hybrid") int hybrid,
			@PathParam("maxhybrid") int maxhybrid,@PathParam("nodeChildIds") String nodeChildIds,@PathParam("maxNode") int maxNode,@PathParam("enablecache") boolean enablecache,@PathParam("isevaluation") boolean isevaluation) {
		
		if(isevaluation){
			List<String> onlineEvaluationURIs = Lodica.getDatabaseConnection().getUrisFromOnlineEvaluationByUserAndSeedWithoutSKIP(userid, similaritymethod);
			if(onlineEvaluationURIs.size()>=IConstants.MAX_ONLINE_EVALUATIONS_PER_USER) {
				JSONObject json = new JSONObject();
				json.put("recommendationMessage","Congratulations, you have concluded the experiment. Thanks for your participation !");
				return Response.status(200).entity(json.toString()).header("Access-Control-Allow-Origin", "*").build();
			}

		}	
		
		
		
		IConstants.SYSTEM_DATA_LANGUAGE=IConstants.ENGLISH_DBPEDIA;
		
		// The seed neighbors are needed to ensure that new recommendations differ from items already shown on the screen.
/*		List<String> seedNeighbours = null;
		if (nodeChildIds!="none") {
			seedNeighbours = StringUtilsNode.getListToken(nodeChildIds,",");	
		}*/
		
		long init = System.currentTimeMillis();
		
		StringBuilder jasonNodeString = new StringBuilder();
		
		StringBuilder jasonEdgeString = new StringBuilder();
		
		//NodeUtil.print("Time elapse 1: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
		
		try {

			if (uri.equals("null")) {
				uri = "http://dbpedia.org/resource/Cork";
				jasonNodeString.append("{\"id\":\""+uri+"\",\"label\":\""+uri+"\",\"color\":\"rgb(229,124,190)\"},");
			}else{
				uri = NodeUtil.concatNamespace(uri);
				
			}
			//jasonNodeString.append("{\"id\":\""+uri+"\",\"label\":\""+uri+"\",\"color\":\"rgb(229,124,190)\"},");
	
			List<NodePrediction> nodePredictions = new ArrayList<NodePrediction>();
			
			nodePredictions = Lodica.getDatabaseConnection().getPredictions(uri, userid, similaritymethod);

			if (nodePredictions.isEmpty()) {
				try {
					Lodica lodica = getIcaInstance();
					SparqlWalk.USE_SPARQL_CACHE = enablecache;
					SparqlWalk.setService(tss);
					IConstants.FILTER_CATEGORY = removeCategory;
					IConstants.MAX_CANDIDATES = maxNode;
					Lodica.RUNNING_CNN_REDUCTION_STRATEGY = IConstants.CNN_REDUCTION_STRATEGY_TU;
					
					//System.out.println(IConstants.FILTER_CATEGORY);
					nodePredictions.addAll(lodica.startWeb(userid,uri,similaritymethod));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			//NodeUtil.print("Time elapse 2: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));

			//NodeUtil.printPredictions(nodePredictions);
			
			//NodeUtil.printPredictionsWhy(nodePredictions);	
			
			if (!showAll && (hybrid>0||maxhybrid>0)) {
				//NodeUtil.print(hybrid);
				
				//List<NodePrediction> nodePredictionsHybrid = new ArrayList<NodePrediction>();
				double topX = 0;
				
				if (maxhybrid>0) {
					topX = maxhybrid;	
				}else{
					topX = (double)nodePredictions.size() * (hybrid/100d);
				}

				//String descURI = SparqlWalk.getDescription(uri);
				
				int count = 0;
				
				//Map<String,Double> mapps = new HashMap<String,Double>();
				
				double treshold=0.00;
				
				Set<String> likeItems = Lodica.getDatabaseConnection().getUnionLikedMoviesMusicsBooksByUserIdAndConvertToString(userid);
				Collections.sort(nodePredictions);				
				//NodeUtil.printPredictions(nodePredictions);
				//NodeUtil.print();
				
				
				if(isevaluation){

					
					
					//From that point on, only items not evaluated can be recommended.
					List<String> onlineEvaluationURIs = Lodica.getDatabaseConnection().getOnlineEvaluationURIsByUserAndSeed(userid,uri,similaritymethod);
					List<NodePrediction> nodePredictionsFinal = null;
					//Set<String> onlineEvaluationUris = new HashSet<String>();

					if(!onlineEvaluationURIs.isEmpty()){
						nodePredictionsFinal = new ArrayList<NodePrediction>();
						for (NodePrediction nodePrediction : nodePredictions) {
							if (!onlineEvaluationURIs.contains(nodePrediction.getNode().getURI())) {
								nodePredictionsFinal.add(nodePrediction);
							}
						}
						if (!nodePredictionsFinal.isEmpty()) {
							nodePredictions = nodePredictionsFinal;
						}
					}
				}				
				
				//NodeUtil.print("Time elapse 3: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
				
				for (NodePrediction nodePrediction : nodePredictions) {
					//NodeUtil.printPrediction(nodePrediction);
					if (!(count < (int)topX)){
						break;						
					}
					//to enforce 5 new from a seed
					if (nodePrediction.getNode().getURI().equals(uri)){
						continue;						
					}
					
					//avoid items that are in the user profile
					if (likeItems.contains(nodePrediction.getNode().getURI())){
						continue;						
					}					

					//to guarantee the existing neighbors are not counted as new recommendations
/*					if (seedNeighbours!=null && seedNeighbours.contains(nodePrediction.getNode().getURI().replace("http://dbpedia.org/resource/",""))){
						continue;						
					}*/
					
					if (!nodePrediction.getPredictedLabel().equals(IConstants.LIKE) ) {
						//String
						//String predDescURI = SparqlWalk.getDescription(nodePrediction.getNode().getURI()); 
						//double sim = LuceneCosineSimilarity.getCosineSimilarity(descURI+profileLikes, predDescURI);
						double sim = Classifier.calculateSemanticDistance(uri, nodePrediction.getNode().getURI(),IConstants.LDSD,userid);
	/*					System.out.println(sim);
						System.out.println();*/
						//mapps.put(nodePrediction.getNode().getURI(), sim);
						if (sim>=treshold) {
							//nodePredictionsHybrid.add(nodePrediction);
							nodePrediction.setPredictedLabel(IConstants.LIKE);
							nodePrediction.setPredictionScore(sim);
							//nodePrediction.setWhy("The description of <b>"+nodePrediction.getNode().getURI().replace("http://dbpedia.org/resource/","").trim()+"</b> is similar ("+String.format( "%.2f", (sim))+") to the description of your query <b>"+nodePrediction.getSeed().replace("http://dbpedia.org/resource/","").trim()+"</b> and the items in your profile.");
							//nodePrediction.setWhy("The description of <b>"+nodePrediction.getNode().getURI().replace("http://dbpedia.org/resource/","").trim()+"</b> is similar to the description of your query <b>"+nodePrediction.getSeed().replace("http://dbpedia.org/resource/","").trim()+"</b> and the items in your profile.");
							nodePrediction.setWhy("The resource <b>"+NodeUtil.removeNamespace(nodePrediction.getNode().getURI())+"</b> is "+String.format("%.2f", sim)+" close to <b>"+ NodeUtil.removeNamespace(nodePrediction.getSeed())+"</b>.");							
							count++;
						}
					}else{
						count++;
					}
					
				}

			//	NodeUtil.print("Time elapse 4: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));				
				
				//this code removes additional recommendations needeless
				if(!isevaluation){
					nodePredictions = nodePredictions.subList(0,(int)Math.min(topX+1d,nodePredictions.size()));	
				}else{
					//int evaluatedItemsAddedToProfile = Lodica.getDatabaseConnection().getOnlineEvaluationsAddedToProfileByUserAndSeed(userid,uri,similaritymethod,strategy).size();
					int evaluatedItems = Lodica.getDatabaseConnection().getOnlineEvaluationsByUserAndSeed(userid,uri,similaritymethod).size();
					int missingEvaluations = IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED-evaluatedItems;
					if (nodePredictions.size()-1<missingEvaluations) {
							for (int i = 0; i < missingEvaluations; i++) {
								Lodica.getDatabaseConnection().insertOrUpdateOnlineEvaluation(userid,IConstants.PICK_ANOTHER_SEED+i,uri,similaritymethod,"none",0,"0","0","0","0","0");
							}
							return Response.status(404).entity("Please pick another seed as there is/are no more recommendation(s) to evaluate.").header("Access-Control-Allow-Origin", "*").build();
					}
					nodePredictions = nodePredictions.subList(0,Math.min((IConstants.MAX_ONLINE_EVALUATIONS_PER_SEED-evaluatedItems),nodePredictions.size()));
				}				
			}
			
			//NodeUtil.print("Time elapse 5: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));

	
			if (topN<=20) {
				List<NodePrediction> nodePredictionsTOP = new ArrayList<NodePrediction>();
				int count = 1;
				double topScore=Double.MAX_VALUE;
				for (NodePrediction nodePrediction : nodePredictions) {
					if (count < topN) {
						if (nodePrediction.getPredictionScore() <= topScore) {
							topScore = nodePrediction.getPredictionScore();
							nodePredictionsTOP.add(nodePrediction);
							count++;	
						}
					}else{
						break;	
					}
				}
				nodePredictions = nodePredictionsTOP;
			}

			
			
			
			for (NodePrediction nodePrediction : nodePredictions) {
				String relation = SparqlWalk.getSemanticRelation(uri, nodePrediction.getNode().getURI());
				if (showAll) {
					if (nodePrediction.getPredictionScore()<scorefilter || (removeCategory && nodePrediction.getNode().getURI().contains("http://dbpedia.org/resource/Category:"))) {
						continue;
					}
				}else{
					if (nodePrediction.getPredictedLabel().equals(IConstants.NO_LABEL) || nodePrediction.getPredictionScore()<scorefilter || (removeCategory && nodePrediction.getNode().getURI().contains("http://dbpedia.org/resource/Category:"))) {
						continue;
					}
				}
	
				Map<String,Set<Node>> mapStringPlusSet = NodeUtil.getMapSetStringURI(Lodica.neighboursPlus);
				//if (!nodePrediction.getSeed().equals(IConstants.SEED_EVALUATION)) {
	    			if (showTU && mapStringPlusSet.keySet().contains(nodePrediction.getNode().getURI())) {
	    				for (Node nodeLocal : mapStringPlusSet.get(nodePrediction.getNode().getURI())) {
	    					String localRelation = SparqlWalk.getSemanticRelation(nodePrediction.getNode().getURI(), nodeLocal.getURI());
	        				jasonNodeString.append("{\"id\":\""+nodeLocal.getURI()+"\",\"label\":\""+nodeLocal.getURI()+"\",\"color\":\"rgb(153,153,255)\"},");
	        				//jasonEdgeString.append("{\"source\":\""+nodePrediction.getNode().getURI()+"\",\"target\":\""+nodeLocal.getURI()+"\"},");
	        				jasonEdgeString.append("{\"source\":\""+nodePrediction.getNode().getURI()+"\",\"target\":\""+nodeLocal.getURI()+"\",\"label\":\""+localRelation+"\"},");
	    				}
	    			}
	    			//jasonNodeString.append("{\"id\":\""+nodePrediction.getNode().getURI()+"\",\"label\":\""+nodePrediction.getNode().getURI()+"\",\"color\":\"rgb(204, 242, 255)\"},");
	    			jasonNodeString.append("{\"id\":\""+nodePrediction.getNode().getURI()+"\",\"label\":\""+nodePrediction.getNode().getURI()+"\",\"color\":\"rgb(204, 242, 255)\",\"attributes\":{\"why\":\""+nodePrediction.getWhy()+"\"}},");
	    			
	    			if (!uri.equals(nodePrediction.getNode().getURI())) {
	    				jasonEdgeString.append("{\"source\":\""+uri+"\",\"target\":\""+nodePrediction.getNode().getURI()+"\",\"label\":\""+relation+"\"},");	
					}
	    			
				//}
			} 
	
			if (!jasonNodeString.toString().contains("\"id\":\""+uri+"\"")) {
				jasonNodeString.append("{\"id\":\""+uri+"\",\"label\":\""+uri+"\",\"color\":\"rgb(204, 242, 255)\",\"attributes\":{\"why\":\""+""+"\"}},");			
			}
			
			//jasonNodeString.append("{\"id\":\""+uri+"\",\"label\":\""+uri+"\",\"color\":\"rgb(229,124,190)\"},\"attributes\":{\"why\":\""+nodePrediction.getWhy()+"\"}},");
			
			//System.out.println("/new");
			//System.out.println(id);
			String result = "{\"edges\":["+jasonEdgeString.toString()+"],\"nodes\":["+jasonNodeString.toString()+"]}";
			result = result.replace("},]", "}]");
			result = NodeUtil.removeNamespace(result);
			//System.out.println(result);
			
			ThreadPredictions t = new ThreadPredictions(nodePredictions);
			t.start();
			//NodeUtil.print("Time elapse 6: "+(StringUtilsNode.getDuration(System.currentTimeMillis() - init)));
			return Response.status(200).entity(result).header("Access-Control-Allow-Origin", "*").build();
		
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(200).entity(e.getMessage()).header("Access-Control-Allow-Origin", "*").build();
		}
		//System.out.println(result);
		// return HTTP response 200 in case of success
		
	}


    Lodica lodica;
	
	public  Lodica getIcaInstance() {
		if (lodica == null) {
			lodica = new Lodica();
		}
		return lodica;

	}
	
	/*	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@FormParam("first") String first,@FormParam("second") String second,@FormParam("email") String email,@FormParam("password") String password) {
	}*/	
 
}