/*
 * Created on 30.03.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package example.database;


import gawky.database.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;


/**
 * @author Ingo Harbeck
 * Objectlayer for Database access.
 */

public class Account 
{	
	ArrayList bookings = new ArrayList();
	
	
	String client_id;
	String customer_id;
	
	String first_name;
	String last_name;
	String street;
	String street_2;
	String city;
	String zip;
	String state;
	String country;
	String email;
	String phone;
	String seq_account_id;	
	
	public Account(String ext_invoice) throws Exception
	{
		/*
		String sql = " select ifnull(mandant, '') mandant, ifnull(debitoren_konto,'') debitoren_konto, ifnull(name_1,'') name_1, ifnull(name_2,'') name_2, ifnull(strasse,'') strasse, ifnull(strasse_2,'') strasse_2, ifnull(ort,'') ort, ifnull(plz,'') plz, ifnull(state,'') state, ifnull(country,'') country, ifnull(email,'') email, ifnull(telefon,'') telefon" +
 		 			 " from fx_dat_deb_accounts_t " +
					 " where debitoren_konto = ? ";
		*/
			
		String sql = 
		" select ifnull(ac.mandant, '') mandant, ifnull(ac.debitoren_konto,'') debitoren_konto, ifnull(name_1,'') name_1, ifnull(name_2,'') name_2, ifnull(strasse,'') strasse, ifnull(strasse_2,'') strasse_2, ifnull(ort,'') ort, ifnull(plz,'') plz, ifnull(ac.state,'') state, ifnull(country,'') country, ifnull(email,'') email, ifnull(telefon,'') telefon, ac.seq_account_id " +
		" from fx_dat_deb_accounts_t ac,  fx_dat_deb_bookings_t " +
		" where ac.seq_account_id = fx_dat_deb_bookings_t.seq_account_id " +
        " and  invoice_ext = ? ";

		
		
		PreparedStatement stmt = null;
		Connection conn = DB.getConnection();
		ResultSet rset = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, ext_invoice);
			rset = stmt.executeQuery();
			
			if(rset.next())
			{
				setClient_id   (rset.getString("mandant"));
				setCustomer_id (rset.getString("debitoren_konto"));
				
				setFirst_name  (rset.getString("name_2"));
				setLast_name   (rset.getString("name_1"));
				setStreet      (rset.getString("strasse"));
				setStreet_2    (rset.getString("strasse_2"));
				setCity        (rset.getString("ort"));
				setZip         (rset.getString("plz"));
				setState       (rset.getString("state"));
				setCountry     (rset.getString("country"));
				setEmail       (rset.getString("email"));
				setPhone       (rset.getString("telefon"));
				setSeq_account_id(rset.getString("seq_account_id"));
			
				findBookings();
			}
			else
				throw new Exception("Account (" + ext_invoice + ") is not available");
		} finally {
			try {
				if(rset != null) rset.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {}
		}
	}
	
	public boolean isCanceled() throws Exception
	{
		/*
		  
		String sql = " select book.*, addinfo.feld_1 " +
					 " from fx_dat_deb_bookings_t book, fx_dat_deb_accounts_t account, fias_dat_deb_add_info addinfo " +
					 " where book.seq_account_id = account.seq_account_id  " +
					 " and account.seq_account_id = addinfo.seq_booking_id " +
					 " and book.debitoren_konto = ? " +
					 " and (ifnull(status_processed,0) & power(2,21)) = power(2,21) ";
		*/
		
		String sql = " select book.*, addinfo.feld_1 " +
						 " from fx_dat_deb_bookings_t book, fx_dat_deb_accounts_t account, fias_dat_deb_add_info addinfo " +
						 " where book.seq_account_id = account.seq_account_id  " +
						 " and account.seq_account_id = addinfo.seq_booking_id " +
						 " and book.seq_account_id = ? " +
						 " and (ifnull(status_processed,0) & power(2,21)) = power(2,21) ";
			
		
			
		PreparedStatement stmt = null;
		Connection conn = DB.getConnection();
		ResultSet rset = null;

		try {
			stmt = conn.prepareStatement(sql);
			//stmt.setString(1, getCustomer_id());
			stmt.setString(1, getSeq_account_id());
			rset = stmt.executeQuery();
	 
			if(rset.next())
			{
				return true;
        	}
		} finally {
			try {
				if(rset != null) rset.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {}
		}
		
		return false;
	}
	
	private void findBookings() throws Exception
	{
		/*
		String sql = " select book.*, addinfo.feld_1 " +
					 " from fx_dat_deb_bookings_t book, fx_dat_deb_accounts_t account, fias_dat_deb_add_info addinfo " +
					 " where book.seq_account_id = account.seq_account_id  " +
					 " and account.seq_account_id = addinfo.seq_booking_id " +
					 " and book.debitoren_konto = ? " +
					 " and ((ifnull(status_processed,0) & power(2,20))=0) " +
					 " and ((ifnull(status_processed,0) & power(2,21))=0) ";
		*/
		String sql = " select book.*, addinfo.feld_1 " +
					 " from fx_dat_deb_bookings_t book, fx_dat_deb_accounts_t account, fias_dat_deb_add_info addinfo " +
					 " where book.seq_account_id = account.seq_account_id  " +
					 " and account.seq_account_id = addinfo.seq_booking_id " +
					 " and book.seq_account_id = ? " +
					 " and ((ifnull(status_processed,0) & power(2,20))=0) " +
					 " and ((ifnull(status_processed,0) & power(2,21))=0) ";
				
		PreparedStatement stmt = null;
		Connection conn = DB.getConnection();
		ResultSet rset = null;
		
		try {
			stmt = conn.prepareStatement(sql);
			//stmt.setString(1, getCustomer_id());
			stmt.setString(1, getSeq_account_id());
			
			rset = stmt.executeQuery();
			 
			while(rset.next())
			{
				Booking book = new Booking();
				                 
				book.setClient_id            (rset.getString("mandant"));
				book.setCustomer_id          (rset.getString("debitoren_konto"));
				book.setInvoice_number       (rset.getString("beleg_nr"));
				book.setAmount               (rset.getDouble("wert"));
				book.setBooking_date         (new Date( rset.getTimestamp("datum_buchung").getTime()));
				book.setDistribution_channel (rset.getString("schl_vertriebskanal_versandweg"));
				book.setProduct_line         (rset.getString("feld_1"));
				book.setDunning_level        (rset.getString("mahnstufe"));
				book.setSeq_booking_id       (rset.getString("seq_booking_id"));
                book.setExtInvoice_number    (rset.getString("invoice_ext"));
				bookings.add(book);
			}
		} finally {
			try {
				if(rset != null) rset.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			} catch (Exception e) {}
		}
	}
	/**
	 * @return Returns the bookings.
	 */
	public ArrayList getBookings() {
		return bookings;
	}
	/**
	 * @param bookings The bookings to set.
	 */
	public void setBookings(ArrayList bookings) {
		this.bookings = bookings;
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
	public String getCustomer_id__() {
		return customer_id;
	}
	/**
	 * @param customer_id The customer_id to set.
	 */
	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}
	/**
	 * @return Returns the city.
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city The city to set.
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return Returns the country.
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country The country to set.
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return Returns the first_name.
	 */
	public String getFirst_name() {
		return first_name;
	}
	/**
	 * @param first_name The first_name to set.
	 */
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	/**
	 * @return Returns the last_name.
	 */
	public String getLast_name() {
		return last_name;
	}
	/**
	 * @param last_name The last_name to set.
	 */
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	/**
	 * @return Returns the phone.
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone The phone to set.
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return Returns the state.
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state The state to set.
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return Returns the street.
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street The street to set.
	 */
	public void setStreet(String street) {
		this.street = street;
	}
	/**
	 * @return Returns the street_2.
	 */
	public String getStreet_2() {
		return street_2;
	}
	/**
	 * @param street_2 The street_2 to set.
	 */
	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}
	/**
	 * @return Returns the zip.
	 */
	public String getZip() {
		return zip;
	}
	/**
	 * @param zip The zip to set.
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}
	/**
	 * @return
	 */
	public String getSeq_account_id() {
		return seq_account_id;
	}

	/**
	 * @param string
	 */
	public void setSeq_account_id(String string) {
		seq_account_id = string;
	}

}
