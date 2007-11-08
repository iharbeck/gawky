package gawky.processor;


public class BaseObject implements BaseObjectI {

	private String clientid;
	private String debtor_account;
	private String ref_nr;
	private String bookdate;
	private String bookcode;
	private String booksubcode;
	private String booknumber;
	private String currency;
	private String amount;
	private String currency_org;
	private String amount_org;
	private String dc;
	private String ccnumber;
	private String ccexpiry;
	private String addressvalcode;
	private String authcode;
	private String authdate;
	private String ccid;
	private String banksortcode;
	private String bankaccount;
	private String lastname;
	private String firstname;
	private String street;
	private String zip;
	private String city;
	private String countrycode;
	private String countryname;
	private String bookingtext;
	private String cctype;
	private String paymenttype;
	private String banknumber;
	private String authcodelong;
	private String ribcode;
	private String referencetext;
	private String iban;
	private String bic;
	private String mandate_status;
	private String state;
	private String mandate_id;
	private String street2;
	private String paymenttype2;
	private String companyname;
	private String accountnowner;
	private String authtime;
	
	
	public String getAccountnowner() {
		return accountnowner;
	}
	public void setAccountnowner(String accountnowner) {
		this.accountnowner = accountnowner;
	}
	public String getAddressvalcode() {
		return addressvalcode;
	}
	public void setAddressvalcode(String addressvalcode) {
		this.addressvalcode = addressvalcode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAmount_org() {
		return amount_org;
	}
	public void setAmount_org(String amount_org) {
		this.amount_org = amount_org;
	}
	public String getAuthcode() {
		return authcode;
	}
	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}
	public String getAuthcodelong() {
		return authcodelong;
	}
	public void setAuthcodelong(String authcodelong) {
		this.authcodelong = authcodelong;
	}
	public String getAuthdate() {
		return authdate;
	}
	public void setAuthdate(String authdate) {
		this.authdate = authdate;
	}
	public String getAuthtime() {
		return authtime;
	}
	public void setAuthtime(String authtime) {
		this.authtime = authtime;
	}
	public String getBankaccount() {
		return bankaccount;
	}
	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}
	public String getBanknumber() {
		return banknumber;
	}
	public void setBanknumber(String banknumber) {
		this.banknumber = banknumber;
	}
	public String getBanksortcode() {
		return banksortcode;
	}
	public void setBanksortcode(String banksortcode) {
		this.banksortcode = banksortcode;
	}
	public String getBic() {
		return bic;
	}
	public void setBic(String bic) {
		this.bic = bic;
	}
	public String getBookcode() {
		return bookcode;
	}
	public void setBookcode(String bookcode) {
		this.bookcode = bookcode;
	}
	public String getBookdate() {
		return bookdate;
	}
	public void setBookdate(String bookdate) {
		this.bookdate = bookdate;
	}
	public String getBookingtext() {
		return bookingtext;
	}
	public void setBookingtext(String bookingtext) {
		this.bookingtext = bookingtext;
	}
	public String getBooknumber() {
		return booknumber;
	}
	public void setBooknumber(String booknumber) {
		this.booknumber = booknumber;
	}
	public String getBooksubcode() {
		return booksubcode;
	}
	public void setBooksubcode(String booksubcode) {
		this.booksubcode = booksubcode;
	}
	public String getCcexpiry() {
		return ccexpiry;
	}
	public void setCcexpiry(String ccexpiry) {
		this.ccexpiry = ccexpiry;
	}
	public String getCcid() {
		return ccid;
	}
	public void setCcid(String ccid) {
		this.ccid = ccid;
	}
	public String getCcnumber() {
		return ccnumber;
	}
	public void setCcnumber(String ccnumber) {
		this.ccnumber = ccnumber;
	}
	public String getCctype() {
		return cctype;
	}
	public void setCctype(String cctype) {
		this.cctype = cctype;
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
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}
	public String getCountryname() {
		return countryname;
	}
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrency_org() {
		return currency_org;
	}
	public void setCurrency_org(String currency_org) {
		this.currency_org = currency_org;
	}
	public String getDc() {
		return dc;
	}
	public void setDc(String dc) {
		this.dc = dc;
	}
	public String getDebtor_account() {
		return debtor_account;
	}
	public void setDebtor_account(String debtor_account) {
		this.debtor_account = debtor_account;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getMandate_id() {
		return mandate_id;
	}
	public void setMandate_id(String mandate_id) {
		this.mandate_id = mandate_id;
	}
	public String getMandate_status() {
		return mandate_status;
	}
	public void setMandate_status(String mandate_status) {
		this.mandate_status = mandate_status;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getPaymenttype2() {
		return paymenttype2;
	}
	public void setPaymenttype2(String paymenttype2) {
		this.paymenttype2 = paymenttype2;
	}
	public String getRef_nr() {
		return ref_nr;
	}
	public void setRef_nr(String ref_nr) {
		this.ref_nr = ref_nr;
	}
	public String getReferencetext() {
		return referencetext;
	}
	public void setReferencetext(String referencetext) {
		this.referencetext = referencetext;
	}
	public String getRibcode() {
		return ribcode;
	}
	public void setRibcode(String ribcode) {
		this.ribcode = ribcode;
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
	public String getStreet2() {
		return street2;
	}
	public void setStreet2(String street2) {
		this.street2 = street2;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
			

	
	
}
