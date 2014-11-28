package example.global;

import java.util.ArrayList;

import example.message.parser.RequestHead;
import example.message.parser.RequestPos;

public class Session
{

	private String response = "";
	private String details = "";

	private RequestHead head = new RequestHead();
	private ArrayList positions = new ArrayList();

	public RequestPos getPos(int i)
	{
		return (RequestPos)positions.get(0);
	}

	public RequestHead getHead()
	{
		return head;
	}

	public void setHead(RequestHead head)
	{
		this.head = head;
	}

	public ArrayList getPositions()
	{
		return positions;
	}

	public String getDetails()
	{
		return details;
	}

	public void setDetails(String details)
	{
		this.details = details;
	}

	public String getResponse()
	{
		return response;
	}

	public void setResponse(String response)
	{
		this.response = response;
	}

}
