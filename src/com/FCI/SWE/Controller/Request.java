package com.FCI.SWE.Controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.mvc.Viewable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.FCI.SWE.Models.Post;
import com.FCI.SWE.Models.PostType;

class Request  {
	public Request(){

	}

	public static String sendRequest(String url,String urlParams,String type){
		String retJson = Connection.connect(url, urlParams, type,
				"application/x-www-form-urlencoded;charset=UTF-8");

		return retJson;
	}
}