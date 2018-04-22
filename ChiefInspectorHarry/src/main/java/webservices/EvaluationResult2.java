package webservices;

import java.util.List;
import java.util.Map;

public class EvaluationResult2 {

	String url;
	List<Damage> damages;
	
	public EvaluationResult2() {
		super();
		// TODO Auto-generated constructor stub
	}
	public EvaluationResult2(String url, List<Damage> damages) {
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
	public List<Damage>  getDamages() {
		return damages;
	}
	public void setDamages(List<Damage>  damages) {
		this.damages = damages;
	}
}
