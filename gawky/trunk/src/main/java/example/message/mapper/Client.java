package example.message.mapper;


import gawky.message.part.Desc;
import gawky.message.part.DescV;
import gawky.message.part.Part;

public class Client extends Part {

	//Record definition
	public Desc[] getDesc() {
		
		return new Desc[]  {
			new DescV("client_id"),
			new DescV("clientname")
		};
	}

	String client_id;
	String clientname;
	
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
}
