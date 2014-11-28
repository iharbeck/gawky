package gawky.processor;

public class BaseObject implements BaseObjectI
{

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

	@Override
	public String getAccountnowner()
	{
		return accountnowner;
	}

	@Override
	public void setAccountnowner(String accountnowner)
	{
		this.accountnowner = accountnowner;
	}

	@Override
	public String getAddressvalcode()
	{
		return addressvalcode;
	}

	@Override
	public void setAddressvalcode(String addressvalcode)
	{
		this.addressvalcode = addressvalcode;
	}

	@Override
	public String getAmount()
	{
		return amount;
	}

	@Override
	public void setAmount(String amount)
	{
		this.amount = amount;
	}

	@Override
	public String getAmount_org()
	{
		return amount_org;
	}

	@Override
	public void setAmount_org(String amount_org)
	{
		this.amount_org = amount_org;
	}

	@Override
	public String getAuthcode()
	{
		return authcode;
	}

	@Override
	public void setAuthcode(String authcode)
	{
		this.authcode = authcode;
	}

	@Override
	public String getAuthcodelong()
	{
		return authcodelong;
	}

	@Override
	public void setAuthcodelong(String authcodelong)
	{
		this.authcodelong = authcodelong;
	}

	@Override
	public String getAuthdate()
	{
		return authdate;
	}

	@Override
	public void setAuthdate(String authdate)
	{
		this.authdate = authdate;
	}

	@Override
	public String getAuthtime()
	{
		return authtime;
	}

	@Override
	public void setAuthtime(String authtime)
	{
		this.authtime = authtime;
	}

	@Override
	public String getBankaccount()
	{
		return bankaccount;
	}

	@Override
	public void setBankaccount(String bankaccount)
	{
		this.bankaccount = bankaccount;
	}

	@Override
	public String getBanknumber()
	{
		return banknumber;
	}

	@Override
	public void setBanknumber(String banknumber)
	{
		this.banknumber = banknumber;
	}

	@Override
	public String getBanksortcode()
	{
		return banksortcode;
	}

	@Override
	public void setBanksortcode(String banksortcode)
	{
		this.banksortcode = banksortcode;
	}

	@Override
	public String getBic()
	{
		return bic;
	}

	@Override
	public void setBic(String bic)
	{
		this.bic = bic;
	}

	@Override
	public String getBookcode()
	{
		return bookcode;
	}

	@Override
	public void setBookcode(String bookcode)
	{
		this.bookcode = bookcode;
	}

	@Override
	public String getBookdate()
	{
		return bookdate;
	}

	@Override
	public void setBookdate(String bookdate)
	{
		this.bookdate = bookdate;
	}

	@Override
	public String getBookingtext()
	{
		return bookingtext;
	}

	@Override
	public void setBookingtext(String bookingtext)
	{
		this.bookingtext = bookingtext;
	}

	@Override
	public String getBooknumber()
	{
		return booknumber;
	}

	@Override
	public void setBooknumber(String booknumber)
	{
		this.booknumber = booknumber;
	}

	@Override
	public String getBooksubcode()
	{
		return booksubcode;
	}

	@Override
	public void setBooksubcode(String booksubcode)
	{
		this.booksubcode = booksubcode;
	}

	@Override
	public String getCcexpiry()
	{
		return ccexpiry;
	}

	@Override
	public void setCcexpiry(String ccexpiry)
	{
		this.ccexpiry = ccexpiry;
	}

	@Override
	public String getCcid()
	{
		return ccid;
	}

	@Override
	public void setCcid(String ccid)
	{
		this.ccid = ccid;
	}

	@Override
	public String getCcnumber()
	{
		return ccnumber;
	}

	@Override
	public void setCcnumber(String ccnumber)
	{
		this.ccnumber = ccnumber;
	}

	@Override
	public String getCctype()
	{
		return cctype;
	}

	@Override
	public void setCctype(String cctype)
	{
		this.cctype = cctype;
	}

	@Override
	public String getCity()
	{
		return city;
	}

	@Override
	public void setCity(String city)
	{
		this.city = city;
	}

	@Override
	public String getClientid()
	{
		return clientid;
	}

	@Override
	public void setClientid(String clientid)
	{
		this.clientid = clientid;
	}

	@Override
	public String getCompanyname()
	{
		return companyname;
	}

	@Override
	public void setCompanyname(String companyname)
	{
		this.companyname = companyname;
	}

	@Override
	public String getCountrycode()
	{
		return countrycode;
	}

	@Override
	public void setCountrycode(String countrycode)
	{
		this.countrycode = countrycode;
	}

	@Override
	public String getCountryname()
	{
		return countryname;
	}

	@Override
	public void setCountryname(String countryname)
	{
		this.countryname = countryname;
	}

	@Override
	public String getCurrency()
	{
		return currency;
	}

	@Override
	public void setCurrency(String currency)
	{
		this.currency = currency;
	}

	@Override
	public String getCurrency_org()
	{
		return currency_org;
	}

	@Override
	public void setCurrency_org(String currency_org)
	{
		this.currency_org = currency_org;
	}

	@Override
	public String getDc()
	{
		return dc;
	}

	@Override
	public void setDc(String dc)
	{
		this.dc = dc;
	}

	@Override
	public String getDebtor_account()
	{
		return debtor_account;
	}

	@Override
	public void setDebtor_account(String debtor_account)
	{
		this.debtor_account = debtor_account;
	}

	@Override
	public String getFirstname()
	{
		return firstname;
	}

	@Override
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	@Override
	public String getIban()
	{
		return iban;
	}

	@Override
	public void setIban(String iban)
	{
		this.iban = iban;
	}

	@Override
	public String getLastname()
	{
		return lastname;
	}

	@Override
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	@Override
	public String getMandate_id()
	{
		return mandate_id;
	}

	@Override
	public void setMandate_id(String mandate_id)
	{
		this.mandate_id = mandate_id;
	}

	@Override
	public String getMandate_status()
	{
		return mandate_status;
	}

	@Override
	public void setMandate_status(String mandate_status)
	{
		this.mandate_status = mandate_status;
	}

	@Override
	public String getPaymenttype()
	{
		return paymenttype;
	}

	@Override
	public void setPaymenttype(String paymenttype)
	{
		this.paymenttype = paymenttype;
	}

	@Override
	public String getPaymenttype2()
	{
		return paymenttype2;
	}

	@Override
	public void setPaymenttype2(String paymenttype2)
	{
		this.paymenttype2 = paymenttype2;
	}

	@Override
	public String getRef_nr()
	{
		return ref_nr;
	}

	@Override
	public void setRef_nr(String ref_nr)
	{
		this.ref_nr = ref_nr;
	}

	@Override
	public String getReferencetext()
	{
		return referencetext;
	}

	@Override
	public void setReferencetext(String referencetext)
	{
		this.referencetext = referencetext;
	}

	@Override
	public String getRibcode()
	{
		return ribcode;
	}

	@Override
	public void setRibcode(String ribcode)
	{
		this.ribcode = ribcode;
	}

	@Override
	public String getState()
	{
		return state;
	}

	@Override
	public void setState(String state)
	{
		this.state = state;
	}

	@Override
	public String getStreet()
	{
		return street;
	}

	@Override
	public void setStreet(String street)
	{
		this.street = street;
	}

	@Override
	public String getStreet2()
	{
		return street2;
	}

	@Override
	public void setStreet2(String street2)
	{
		this.street2 = street2;
	}

	@Override
	public String getZip()
	{
		return zip;
	}

	@Override
	public void setZip(String zip)
	{
		this.zip = zip;
	}

}
