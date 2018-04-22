package webservices;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

public class EvaluationResult {

	String url;
	Map<String, String> damages = new HashMap<>();
	
	public EvaluationResult(String url, Map<String, String> damages, Map<String, String> crop_urls) {
		super();
		this.url = url;
		this.damages = damages;
		this.crop_urls = crop_urls;
	}
	Map<String, String> crop_urls;
	
	public EvaluationResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EvaluationResult(String url, Map<String, String> damages) {
		super();
		this.url = url;
		this.damages = damages;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Map<String, String> getDamages() {
		return damages;
	}
	public void setDamages(Map<String, String> damages) {
		this.damages = damages;
	}
}
