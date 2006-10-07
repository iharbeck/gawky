/*
 * Created on 24.03.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package example.database;


import gawky.database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;



/**
 * @author Ingo Harbeck
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Booking {

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
    	
	
	public static void main(String[] args) {
	}
	/**
	 * @return Returns the amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the booking_date.
	 */
	public java.util.Date getBooking_date() {
		return booking_date;
	}
	/**
	 * @param booking_date The booking_date to set.
	 */
	public void setBooking_date(java.util.Date booking_date) {
		this.booking_date = booking_date;
	}
	/**
	 * @return Returns the client_id.
	 */
	public String getClient_id() {
		return client_id;
	}
	/**
	 * @param client_id The client_id to set.
	 */
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	/**
	 * @return Returns the customer_id.
	 */
	public String getCustomer_id() {
		return customer_id;
	}
	/**
	 * @param customer_id The customer_id to set.
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return Returns the distribution_channel.
	 */
	public String getDistribution_channel() {
		return distribution_channel;
	}
	/**
	 * @param distribution_channel The distribution_channel to set.
	 */
	public void setDistribution_channel(String distribution_channel) {
		this.distribution_channel = distribution_channel;
	}
	/**
	 * @return Returns the dunning_level.
	 */
	public String getDunning_level() {
		return dunning_level;
	}
	/**
	 * @param dunning_level The dunning_level to set.
	 */
	public void setDunning_level(String dunning_level) {
		this.dunning_level = dunning_level;
	}
	/**
	 * @return Returns the invoice_number.
	 */
	public String getInvoice_number() {
		return invoice_number;
	}
    
    /**
     * @return Returns the invoice_number.
     */
    public String getExtInvoice_number() {
        return extInvoice_number;
    }
    
	/**
	 * @param invoice_number The invoice_number to set.
	 */
	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}
    
    /**
     * @param invoice_number The invoice_number to set.
     */
    public void setExtInvoice_number(String extInvoice_number) {
        this.extInvoice_number = extInvoice_number;
    }

	/**
	 * @return Returns the product_line.
	 */
	public String getProduct_line() {
		return product_line;
	}
	/**
	 * @param product_line The product_line to set.
	 */
	public void setProduct_line(String product_line) {
		this.product_line = product_line;
	}
	
	public void setDone() throws Exception
	{
        String sql = " update fx_dat_deb_bookings_t" +
                     " set status_processed = ifnull(status_processed,0) + power(2,20) " +
                     " where seq_booking_id = ? ";


		
		PreparedStatement stmt = null;
		Connection conn = DB.getConnection();
		
		try {
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, getSeq_booking_id());
		stmt.execute();
		
		} finally {
		try {
			if(stmt != null) stmt.close();
			if(conn != null) conn.close();
		} catch (Exception e) {}
		}
	}
	/**
	 * @return Returns the seq_booking_id.
	 */
	public String getSeq_booking_id() {
		return seq_booking_id;
	}
	/**
	 * @param seq_booking_id The seq_booking_id to set.
	 */
	public void setSeq_booking_id(String seq_booking_id) {
		this.seq_booking_id = seq_booking_id;
	}
}
