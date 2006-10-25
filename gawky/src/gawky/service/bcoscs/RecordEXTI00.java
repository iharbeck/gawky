/*
 * RequestHead.java
 *
 * Created on 6. August 2003, 11:27
 */

package gawky.service.bcoscs;

import gawky.message.part.Desc;
import gawky.message.part.DescC;
import gawky.message.part.DescF;
import gawky.message.part.DescV;
import gawky.message.part.Part;

/**
 *
 * @author  Ingo Harbeck
 */
public class RecordEXTI00 extends Part 
{
    public Desc[] getDesc() 
	{
		return new Desc[] {
			new DescC("EXTI"),	
			new DescC("00"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "encoding"),          
			new DescF(Desc.FMT_A, Desc.CODE_R, 9,   "clientid"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 16,  "recordid"),
			new DescF(Desc.FMT_9, Desc.CODE_R, 1,   "accounttype"),
			new DescF(Desc.FMT_A, Desc.CODE_R, 10,  "accountid"),
			new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "transactionid"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "transactioncode"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "transactionsubcode"),
		    new DescF(Desc.FMT_D, Desc.CODE_R, 8,   "transactiondate"),
		    new DescF(Desc.FMT_D, Desc.CODE_R, 8,   "duedate"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 14,  "amountacc"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "accountcurrency"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 14,  "amountbac"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "basecurrency"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,   "exchangedate"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 20,  "exchangerate"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "dc"),
		    new DescF(Desc.FMT_9, Desc.CODE_O, 10,  "referencenumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 32,  "referencenumberadd"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 1,   "encrypt"),
		    
		    new DescF(Desc.FMT_A, Desc.CODE_O, 48,  "creditcardnumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "creditcardexpiry"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 4,   "issuedate"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "cardid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 12,  "bankid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 80,  "bankaccount"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 80,  "iban"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 11,  "bic"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "ribcode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "accountowner"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 6,   "authcode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 8,   "authdate"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 6,   "authtime"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "avscode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "fiascctype"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 2,   "paymenttype"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 3,   "banknumber"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 12,  "authorizationcode"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 2,   "mandatestatus"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 20,  "mandateid"),
		    new DescF(Desc.FMT_A, Desc.CODE_O, 10,  "orderid"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "bookingtext"),
		    new DescF(Desc.FMT_9, Desc.CODE_R, 6,   "addressnumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "title"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "company"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "name"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "name2"),
		    
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "street"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "street2"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 10,  "zipcode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "city"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 16,  "pobox"),
		    new DescF(Desc.FMT_A, Desc.CODE_R, 3,   "country"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "region"),
		    
		    new DescV(Desc.FMT_9, Desc.CODE_O, 9,   "geocode"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "phonenumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "faxnumber"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 255, "email"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline1"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline2"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline3"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline4"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline5"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline6"),
		    new DescV(Desc.FMT_A, Desc.CODE_O, 64,  "addressline7")
		}; 
	}

    private String encoding;
    private String clientid;
    private String recordid;
    private String accounttype;
    private String accountid;
    private String transactionid;
    private String transactioncode;
    private String transactionsubcode;
    private String transactiondate;
    private String duedate;
    private String amountacc;
    private String accountcurrency;
    private String amountbac;
    private String basecurrency;
    private String exchangedate;
    private String exchangerate;
    private String dc;
    private String referencenumber;
    private String referencenumberadd;
    private String encrypt;
    private String creditcardnumber;
    private String creditcardexpiry;
    private String issuedate;
    private String cardid;
    private String bankid;
    private String bankaccount;
    private String iban;
    private String bic;
    private String ribcode;
    private String accountowner;
    private String authcode;
    private String authdate;
    private String authtime;
    private String avscode;
    private String fiascctype;
    private String paymenttype;
    private String banknumber;
    private String authorizationcode;
    private String mandatestatus;
    private String mandateid;
    private String orderid;
    private String bookingtext;
    private String addressnumber;
    private String title;
    private String company;
    private String name;
    private String name2;
    private String street;
    private String street2;
    private String zipcode;
    private String city;
    private String pobox;
    private String country;
    private String region;
    private String geocode;
    private String phonenumber;
    private String faxnumber;
    private String email;
    private String addressline1;
    private String addressline2;
    private String addressline3;
    private String addressline4;
    private String addressline5;
    private String addressline6;
    private String addressline7;
    
	public String getAccountcurrency() {
		return accountcurrency;
	}
	public void setAccountcurrency(String accountcurrency) {
		this.accountcurrency = accountcurrency;
	}
	public String getAccountid() {
		return accountid;
	}
	public void setAccountid(String accountid) {
		this.accountid = accountid;
	}
	public String getAccountowner() {
		return accountowner;
	}
	public void setAccountowner(String accountowner) {
		this.accountowner = accountowner;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}
	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}
	public String getAddressline3() {
		return addressline3;
	}
	public void setAddressline3(String addressline3) {
		this.addressline3 = addressline3;
	}
	public String getAddressline4() {
		return addressline4;
	}
	public void setAddressline4(String addressline4) {
		this.addressline4 = addressline4;
	}
	public String getAddressline5() {
		return addressline5;
	}
	public void setAddressline5(String addressline5) {
		this.addressline5 = addressline5;
	}
	public String getAddressline6() {
		return addressline6;
	}
	public void setAddressline6(String addressline6) {
		this.addressline6 = addressline6;
	}
	public String getAddressline7() {
		return addressline7;
	}
	public void setAddressline7(String addressline7) {
		this.addressline7 = addressline7;
	}
	public String getAddressnumber() {
		return addressnumber;
	}
	public void setAddressnumber(String addressnumber) {
		this.addressnumber = addressnumber;
	}
	public String getAmountacc() {
		return amountacc;
	}
	public void setAmountacc(String amountacc) {
		this.amountacc = amountacc;
	}
	public String getAmountbac() {
		return amountbac;
	}
	public void setAmountbac(String amountbac) {
		this.amountbac = amountbac;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getAuthdate() {
		return authdate;
	}
	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}
	public String getAuthorizationcode() {
		return authorizationcode;
	}
	public void setAuthorizationcode(String authorizationcode) {
		this.authorizationcode = authorizationcode;
	}
	public String getAuthtime() {
		return authtime;
	}
	public void setAuthtime(String authtime) {
		this.authtime = authtime;
	}
	public String getAvscode() {
		return avscode;
	}
	public void setAvscode(String avscode) {
		this.avscode = avscode;
	}
	public String getBankaccount() {
		return bankaccount;
	}
	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getBanknumber() {
		return banknumber;
	}
	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}
	public String getBasecurrency() {
		return basecurrency;
	}
	public void setBasecurrency(String basecurrency) {
		this.basecurrency = basecurrency;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getBookingtext() {
		return bookingtext;
	}
	public void setBookingtext(String bookingtext) {
		this.bookingtext = bookingtext;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCreditcardexpiry() {
		return creditcardexpiry;
	}
	public void setCreditcardexpiry(String creditcardexpiry) {
		this.creditcardexpiry = creditcardexpiry;
	}
	public String getCreditcardnumber() {
		return creditcardnumber;
	}
	public void setCreditcardnumber(String creditcardnumber) {
		this.creditcardnumber = creditcardnumber;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getEncrypt() {
		return encrypt;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public String getExchangedate() {
		return exchangedate;
	}
	public void setExchangedate(String exchangedate) {
		this.exchangedate = exchangedate;
	}
	public String getExchangerate() {
		return exchangerate;
	}
	public void setExchangerate(String exchangerate) {
		this.exchangerate = exchangerate;
	}
	public String getFaxnumber() {
		return faxnumber;
	}
	public void setFaxnumber(String faxnumber) {
		this.faxnumber = faxnumber;
	}
	public String getFiascctype() {
		return fiascctype;
	}
	public void setFiascctype(String fiascctype) {
		this.fiascctype = fiascctype;
	}
	public String getGeocode() {
		return geocode;
	}
	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getIssuedate() {
		return issuedate;
	}
	public void setIssuedate(String issuedate) {
		this.issuedate = issuedate;
	}
	public String getMandateid() {
		return mandateid;
	}
	public void setMandateid(String mandateid) {
		this.mandateid = mandateid;
	}
	public String getMandatestatus() {
		return mandatestatus;
	}
	public void setMandatestatus(String mandatestatus) {
		this.mandatestatus = mandatestatus;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getPobox() {
		return pobox;
	}
	public void setPobox(String pobox) {
		this.pobox = pobox;
	}
	public String getRecordid() {
		return recordid;
	}
	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	public String getReferencenumber() {
		return referencenumber;
	}
	public void setReferencenumber(String referencenumber) {
		this.referencenumber = referencenumber;
	}
	public String getReferencenumberadd() {
		return referencenumberadd;
	}
	public void setReferencenumberadd(String referencenumberadd) {
		this.referencenumberadd = referencenumberadd;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRibcode() {
		return ribcode;
	}
	public void setRibcode(String ribcode) {
		this.ribcode = ribcode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTransactioncode() {
		return transactioncode;
	}
	public void setTransactioncode(String transactioncode) {
		this.transactioncode = transactioncode;
	}
	public String getTransactiondate() {
		return transactiondate;
	}
	public void setTransactiondate(String transactiondate) {
		this.transactiondate = transactiondate;
	}
	public String getTransactionid() {
		return transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getTransactionsubcode() {
		return transactionsubcode;
	}
	public void setTransactionsubcode(String transactionsubcode) {
		this.transactionsubcode = transactionsubcode;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

}
