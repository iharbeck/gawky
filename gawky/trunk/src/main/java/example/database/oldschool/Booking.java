package example.database.oldschool;

import gawky.database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class Booking
{

	String client_id;
	String customer_id;
	String invoice_number;
	String extInvoice_number;
	double amount;
	java.util.Date booking_date;
	String distribution_channel;
	String product_line;
	String dunning_level;
	String seq_booking_id;

	public static void main(String[] args)
	{
	}

	public double getAmount()
	{
		return amount;
	}

	public void setAmount(double amount)
	{
		this.amount = amount;
	}

	public java.util.Date getBooking_date()
	{
		return booking_date;
	}

	public void setBooking_date(java.util.Date booking_date)
	{
		this.booking_date = booking_date;
	}

	public String getClient_id()
	{
		return client_id;
	}

	public void setClient_id(String client_id)
	{
		this.client_id = client_id;
	}

	public String getCustomer_id()
	{
		return customer_id;
	}

	public void setCustomer_id(String customer_id)
	{
		this.customer_id = customer_id;
	}

	public String getDistribution_channel()
	{
		return distribution_channel;
	}

	public void setDistribution_channel(String distribution_channel)
	{
		this.distribution_channel = distribution_channel;
	}

	public String getDunning_level()
	{
		return dunning_level;
	}

	public void setDunning_level(String dunning_level)
	{
		this.dunning_level = dunning_level;
	}

	public String getInvoice_number()
	{
		return invoice_number;
	}

	public String getExtInvoice_number()
	{
		return extInvoice_number;
	}

	public void setInvoice_number(String invoice_number)
	{
		this.invoice_number = invoice_number;
	}

	public void setExtInvoice_number(String extInvoice_number)
	{
		this.extInvoice_number = extInvoice_number;
	}

	public String getProduct_line()
	{
		return product_line;
	}

	public void setProduct_line(String product_line)
	{
		this.product_line = product_line;
	}

	public void setDone() throws Exception
	{
		String sql = " update fx_dat_deb_bookings_t" +
		        " set status_processed = ifnull(status_processed,0) + power(2,20) " +
		        " where seq_booking_id = ? ";

		PreparedStatement stmt = null;
		Connection conn = DB.getConnection();

		try
		{
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, getSeq_booking_id());
			stmt.execute();

		}
		finally
		{
			try
			{
				DB.doClose(stmt);
				DB.doClose(conn);
			}
			catch(Exception e)
			{
			}
		}
	}

	public String getSeq_booking_id()
	{
		return seq_booking_id;
	}

	public void setSeq_booking_id(String seq_booking_id)
	{
		this.seq_booking_id = seq_booking_id;
	}
}
