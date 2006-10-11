/*
 * Response.java
 *
 * Created on 6. August 2003, 10:32
 */

package gawky.service.crm;


import gawky.message.parser.ParserException;
import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author  Ingo Harbeck
 */
public class Response extends Part {
  
//	 Record definition
	public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("CMPR"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 5,  "record_length"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 5,  "checksum"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "return_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "reason_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 12, "details"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "build_number"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "subsystem_reason_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "transaction_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "sequence_number"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10, "customer_id"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 3,  "currency_code_of_credit_limit"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 14, "amount_of_credit_limit"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "address_validated"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 6,  "address_return_code"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 2,  "address_reference"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 4,  "cra_grade"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,  "negative_entry_list"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "return_field_1"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16, "return_field_2"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "title"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "name"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "name_2"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "street"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "street_number"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 16, "po_box"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 10, "zip"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "city"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "region"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 3,  "country"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 40, "company_name"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 4,  "identity_score"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 4,  "micro_geographic_score"),
			new DescV(Desc.FMT_A, Desc.CODE_O, 100,"redirect_url")
		}; 
	}
	
	public Response(String str) throws ParserException
    {
		parse(str);		
    }
	
    private String record_length                    = "";
    private String checksum                         = "";
    private String return_code                      = "";
    private String reason_code                      = "";
    private String details                          = "";
    private Date   details_date;
    private String build_number                     = "";
    private String subsystem_reason_code            = "";
    private String transaction_id                   = "";
    private String sequence_number                  = "";
    private String customer_id                      = "";
    private String currency_code_of_credit_limit    = "";
    private String amount_of_credit_limit           = "";
    private String address_validated                = "";
    private String address_return_code              = "";
    private String address_reference                = "";
    private String cra_grade                        = "";
    private String negative_entry_list              = "";
    private String return_field_1                   = "";
    private String return_field_2                   = "";
    private String title                            = "";
    private String name                             = "";
    private String name_2                           = "";
    private String street                           = "";
    private String street_number                    = "";
    private String po_box                           = "";
    private String zip                              = "";
    private String city                             = "";
    private String region                           = "";
    private String country                          = "";
    private String company_name                     = "";
    private String identity_score                   = "";
    private String micro_geographic_score           = "";
    private String redirect_url                     = "";
        

	public String getAddress_reference() {
		return address_reference;
	}

	public void setAddress_reference(String address_reference) {
		this.address_reference = address_reference;
	}

	public String getAddress_return_code() {
		return address_return_code;
	}

	public void setAddress_return_code(String address_return_code) {
		this.address_return_code = address_return_code;
	}

	public String getAddress_validated() {
		return address_validated;
	}

	public void setAddress_validated(String address_validated) {
		this.address_validated = address_validated;
	}

	public String getAmount_of_credit_limit() {
		return amount_of_credit_limit;
	}

	public void setAmount_of_credit_limit(String amount_of_credit_limit) {
		this.amount_of_credit_limit = amount_of_credit_limit;
	}

	public String getBuild_number() {
		return build_number;
	}

	public void setBuild_number(String build_number) {
		this.build_number = build_number;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCra_grade() {
		return cra_grade;
	}

	public void setCra_grade(String cra_grade) {
		this.cra_grade = cra_grade;
	}

	public String getCurrency_code_of_credit_limit() {
		return currency_code_of_credit_limit;
	}

	public void setCurrency_code_of_credit_limit(
			String currency_code_of_credit_limit) {
		this.currency_code_of_credit_limit = currency_code_of_credit_limit;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
		
		try {
          SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddhhmm");
          details_date = formatter.parse(details);
        }
        catch (Exception e) {
        }
	}

	public Date getDetails_date() {
		return details_date;
	}

	public void setDetails_date(Date details_date) {
		this.details_date = details_date;
	}

	public String getIdentity_score() {
		return identity_score;
	}

	public void setIdentity_score(String identity_score) {
		this.identity_score = identity_score;
	}

	public String getMicro_geographic_score() {
		return micro_geographic_score;
	}

	public void setMicro_geographic_score(String micro_geographic_score) {
		this.micro_geographic_score = micro_geographic_score;
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

	public String getNegative_entry_list() {
		return negative_entry_list;
	}

	public void setNegative_entry_list(String negative_entry_list) {
		this.negative_entry_list = negative_entry_list;
	}

	public String getPo_box() {
		return po_box;
	}

	public void setPo_box(String po_box) {
		this.po_box = po_box;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getRecord_length() {
		return record_length;
	}

	public void setRecord_length(String record_length) {
		this.record_length = record_length;
	}

	public String getRedirect_url() {
		return redirect_url;
	}

	public void setRedirect_url(String redirect_url) {
		this.redirect_url = redirect_url;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReturn_code() {
		return return_code;
	}

	public void setReturn_code(String return_code) {
		this.return_code = return_code;
	}

	public String getReturn_field_1() {
		return return_field_1;
	}

	public void setReturn_field_1(String return_field_1) {
		this.return_field_1 = return_field_1;
	}

	public String getReturn_field_2() {
		return return_field_2;
	}

	public void setReturn_field_2(String return_field_2) {
		this.return_field_2 = return_field_2;
	}

	public String getSequence_number() {
		return sequence_number;
	}

	public void setSequence_number(String sequence_number) {
		this.sequence_number = sequence_number;
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

	public String getSubsystem_reason_code() {
		return subsystem_reason_code;
	}

	public void setSubsystem_reason_code(String subsystem_reason_code) {
		this.subsystem_reason_code = subsystem_reason_code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
}
