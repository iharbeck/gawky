/*
 * RequestAddress.java
 *
 * Created on 6. August 2003, 11:35
 */

package gawky.service.crm;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;
import gawky.message.part.Reserved;

/**
 *
 * @author  harb05
 */
public class RequestAddress extends Part 
{
    public Desc[] getDesc() 
 	{
		return new Desc[] {
			new DescC("ADDR"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "character_set"),
			new DescF(Desc.FMT_BINARY, Desc.CODE_R, 15,  "score_type"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 36,  "fe_customer_id"),
	        new DescF(Desc.FMT_9, Desc.CODE_R, 3,   "address_number"),
	        new Reserved(44),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "title"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "name"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "name_2"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 8,   "date_of_birth"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "city_of_birth"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "street"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "street_number"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 16,  "po_box"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 10,  "zip"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "city"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "region"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 3,   "country"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "phone_number"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "fax_number"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 100, "email_address"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 19,  "credit_card"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 4,   "cc_expiry_date"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 4,   "issue_date"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 2,   "issue_number"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 10,  "bank_id"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 16,  "bank_account"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 2,   "rib_code"),
	        new DescV(Desc.FMT_A, Desc.CODE_O, 40,  "company_name")
		}; 
	}

	private  String character_set   = "";
	private  String score_type      = "";
	private  String fe_customer_id  = "";
    private  String address_number  = "";
    private  String title           = "";
    private  String name            = "";
    private  String name_2          = "";
    private  String date_of_birth   = "";
    private  String city_of_birth   = "";
    private  String street          = "";
    private  String street_number   = "";
    private  String po_box          = "";
    private  String zip             = "";
    private  String city            = "";
    private  String region          = "";
    private  String country         = "";
    private  String phone_number    = "";
    private  String fax_number      = "";
    private  String email_address   = "";
    private  String credit_card     = "";
    private  String cc_expiry_date  = "";
    private  String issue_date      = "";
    private  String issue_number    = "";
    private  String bank_id         = "";
    private  String bank_account    = "";
    private  String rib_code        = "";
    private  String company_name    = "";
    

	public String getAddress_number() {
		return address_number;
	}

	public void setAddress_number(String address_number) {
		this.address_number = address_number;
	}

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

	public String getCity_of_birth() {
		return city_of_birth;
	}

	public void setCity_of_birth(String city_of_birth) {
		this.city_of_birth = city_of_birth;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
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

	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
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

	public String getFe_customer_id() {
		return fe_customer_id;
	}

	public void setFe_customer_id(String fe_customer_id) {
		this.fe_customer_id = fe_customer_id;
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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getRib_code() {
		return rib_code;
	}

	public void setRib_code(String rib_code) {
		this.rib_code = rib_code;
	}

	public String getScore_type() {
		return score_type;
	}

	public void setScore_type(String score_type) {
		this.score_type = score_type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet_number() {
		return street_number;
	}

	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
