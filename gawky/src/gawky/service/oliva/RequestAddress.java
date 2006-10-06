package gawky.service.oliva;

/**
 * Überschrift: OLIVAData<p>
 * Beschreibung: String generator and Parser for Oliva requests<p>
 * Copyright: Copyright (c) 2001<p>
 * Organisation: Bertelsmann Mediasystems<p>
 * @author Ingo Harbeck
 * @version 1.0
 */

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 * Implements Oliva datastore
 */

public class RequestAddress extends Part
{
	//Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("ADDR"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "character_set"),
			new Reserved(8),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "name"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "name_2"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "street"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "city"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "state"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 3,   "country"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 10,  "zip"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 16,  "po_box"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 10,  "zip_po_box"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "phone_number"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "fax_number"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 100, "email_address"),
			new DescV(Desc.FMT_A, Desc.CODE_R, 19,  "credit_card"),
			new DescV(Desc.FMT_9, Desc.CODE_R, 4,   "cc_expiry_date"),
			new DescV(Desc.FMT_9, Desc.CODE_R, 4,   "issue_date"),
			new DescV(Desc.FMT_9, Desc.CODE_R, 2,   "issue_number"),
		    new DescV(Desc.FMT_A, Desc.CODE_R, 10,  "bank_id"),
		    new DescV(Desc.FMT_A, Desc.CODE_R, 16,  "bank_account"),
		    new DescV(Desc.FMT_A, Desc.CODE_R, 2,   "RIB_code"),
		    new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "street_2"),
		    new DescV(Desc.FMT_A, Desc.CODE_R, 40,  "title")
		}; 
	}
	
	// Address part
	private String record_type    = "ADDR";   // default
	private String version_number = "00";     // default
	private String character_set  = "1";      // default is non UTF
	private String name;
	private String name_2;
	private String street;
	private String city;
	private String state;
	private String country = "";
	private String zip;
	private String po_box;
	private String zip_po_box;
	private String phone_number;
	private String fax_number;
	private String email_address;
	private String credit_card;
	private String cc_expiry_date;
	private String issue_date;
	private String issue_number;
	private String bank_id;
	private String bank_account;
	private String RIB_code;
	private String street_2;
	private String title; 

	
	public String getBank_account() {
		return bank_account;
	}
	
	public void setBank_account(String bank_account) {
		this.bank_account = bank_account;
	}
	
	public String getBank_id() {
		return bank_id;
	}
	
	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}
	
	public String getCc_expiry_date() {
		return cc_expiry_date;
	}
	
	public void setCc_expiry_date(String cc_expiry_date) {
		this.cc_expiry_date = cc_expiry_date;
	}
	
	public String getCharacter_set() {
		return character_set;
	}
	
	public void setCharacter_set(String character_set) {
		this.character_set = character_set;
	}
	
	public String getCity() {
		return city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCredit_card() {
		return credit_card;
	}
	
	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}
	
	public String getEmail_address() {
		return email_address;
	}
	
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	
	public String getFax_number() {
		return fax_number;
	}
	
	public void setFax_number(String fax_number) {
		this.fax_number = fax_number;
	}
	
	public String getIssue_date() {
		return issue_date;
	}
	
	public void setIssue_date(String issue_date) {
		this.issue_date = issue_date;
	}
	
	public String getIssue_number() {
		return issue_number;
	}
	
	public void setIssue_number(String issue_number) {
		this.issue_number = issue_number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName_2() {
		return name_2;
	}
	
	public void setName_2(String name_2) {
		this.name_2 = name_2;
	}
	
	public String getPhone_number() {
		return phone_number;
	}
	
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	
	public String getPo_box() {
		return po_box;
	}
	
	public void setPo_box(String po_box) {
		this.po_box = po_box;
	}
	
	public String getRecord_type() {
		return record_type;
	}
	
	public void setRecord_type(String record_type) {
		this.record_type = record_type;
	}
	
	public String getRIB_code() {
		return RIB_code;
	}
	
	public void setRIB_code(String rib_code) {
		RIB_code = rib_code;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getStreet_2() {
		return street_2;
	}
	
	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getVersion_number() {
		return version_number;
	}
	
	public void setVersion_number(String version_number) {
		this.version_number = version_number;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getZip_po_box() {
		return zip_po_box;
	}
	
	public void setZip_po_box(String zip_po_box) {
		this.zip_po_box = zip_po_box;
	}
}
