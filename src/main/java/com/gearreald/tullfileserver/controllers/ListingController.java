package com.gearreald.tullfileserver.controllers;

import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;

import com.gearreald.tullfileserver.files.TullFileSystem;
import com.gearreald.tullfileserver.files.TullFolder;

import spark.Request;
import spark.Response;
import spark.Route;

public class ListingController {
	public static Route listFiles = (Request request, Response response) -> {
		JSONObject responseJSON = new JSONObject();
		try{
			response.type("application/json");
			JSONObject requestJSON = new JSONObject(request.body());
			String directory = requestJSON.getString("directory");
			TullFileSystem tfs = TullFileSystem.getTFS();
			if(directory.equals("/"))
				responseJSON.put("response", tfs.getRootFolder().toJSON());
			else{
				TullFolder folder = tfs.getTullFolderAtPath(directory);
				responseJSON.put("response", folder.toJSON());
			}
			return responseJSON.toString();
		}catch(FileNotFoundException e){
			responseJSON.put("error", "The file was not found.");
		}catch(JSONException e){
			responseJSON.put("error", "The request was invalid.");
		}
		return responseJSON.toString();
	};
}