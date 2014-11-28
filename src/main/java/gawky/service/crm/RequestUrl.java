package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.Part;

/**
 *
 * @author  Ingo Harbeck
 */
public class RequestUrl extends Part
{
	private String client_redirect_url = "";

	@Override
	public Desc[] getDesc()
	{
		return new Desc[] {
		        new DescC("URL "),
		        new DescC("00"),
		        new DescF(Desc.FMT_A, Desc.CODE_R, 100, "client_redirect_url"),
		        new DescC(Desc.END02)
		};
	}

	public String getClient_redirect_url()
	{
		return client_redirect_url;
	}

	public void setClient_redirect_url(String client_redirect_url)
	{
		this.client_redirect_url = client_redirect_url;
	}
}
