package ua.edu.sumdu.lab3.group11.obj;

import org.apache.log4j.Logger;

/**
 *
 * @author Yulia Lukianenko
 */
public class Country {
    
    private final static Logger log = Logger.getLogger(Country.class.getName());
    
    private int countryId;
    private int countryParentID = 0;
    private String countryName;
    private CountryType countryType; 
    public enum CountryType {
        country,
        partOfWorld
    };

    public void setCountryType(CountryType countryType){
        this.countryType = countryType;
    }
    
    public CountryType getCountryType(){
        return countryType;
    }
    
    public int getCountryParentID() {
        return countryParentID;
    }

    public void setCountryParentID(int countryParentID) {
        this.countryParentID = countryParentID;
    }
    
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    
    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder();
        if(countryParentID == 0){
            builder.append("Country \"")
                    .append(getCountryName())
                    .append("\" with country_ID = ")
                    .append(getCountryId())
                    .append(" and type of country : ")
                    .append(getCountryType())
                    ;
        }
        else {
            builder.append(" and parentID = ")
                    .append(getCountryParentID())
                    .append("\n");
        }
        return builder.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
    
        if (obj == this)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Country country = (Country) obj;
        return countryId == country.countryId &&
                countryParentID == country.countryParentID &&
                countryName.equals(country.countryName) ;
    }
    
    @Override
    public int hashCode(){
        return 3 * countryId + 5 * countryParentID + 7 * countryName.hashCode();
    }
}
