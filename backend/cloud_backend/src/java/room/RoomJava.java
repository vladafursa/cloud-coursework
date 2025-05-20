package room;

import com.google.gson.annotations.SerializedName;
import java.time.LocalDate;

public class RoomJava {
    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private Location location;

    @SerializedName("details")
    private Details details;

    @SerializedName("price_per_month_gbp")
    private long price_per_month_gbp;

    @SerializedName("availability_date")
    private LocalDate availability_date;

    @SerializedName("spoken_languages")
    private String[] spoken_languages;
    
    private String weather;
    private String lastCrime;

   public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    public long getPricePerMonthGbp() {
        return price_per_month_gbp;
    }

    public void setPricePerMonthGbp(long pricePerMonthGbp) {
        this.price_per_month_gbp = pricePerMonthGbp;
    }

    public LocalDate getAvailabilityDate() {
        return availability_date;
    }

    public void setAvailabilityDate(LocalDate availabilityDate) {
        this.availability_date = availabilityDate;
    }

    public String[] getSpokenLanguages() {
        return spoken_languages;
    }

    public void setSpokenLanguages(String[] spokenLanguages) {
        this.spoken_languages = spokenLanguages;
    }
    
    public String getWeather(){
    return weather;
    }
    
    public void setWeather(String weather){
    this.weather=weather;
    }
    
    public String getCrime(){
    return lastCrime;
    }
    
    public void setCrime(String lastCrime){
    this.lastCrime=lastCrime;
    }

    // Inner classes for Location and Details
    public static class Location {
        @SerializedName("city")
        private String city;

        @SerializedName("county")
        private String county;

        @SerializedName("postcode")
        private String postcode;

        // Getters and setters
        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }
    }

    public static class Details {
        @SerializedName("furnished")
        private boolean furnished;

        @SerializedName("amenities")
        private String[] amenities;

        @SerializedName("live_in_landlord")
        private boolean live_in_landlord;

        @SerializedName("shared_with")
        private long shared_with;

        @SerializedName("bills_included")
        private boolean bills_included;

        @SerializedName("bathroom_shared")
        private boolean bathroom_shared;

        // Getters and setters
        public boolean isFurnished() {
            return furnished;
        }

        public void setFurnished(boolean furnished) {
            this.furnished = furnished;
        }

        public String[] getAmenities() {
            return amenities;
        }

        public void setAmenities(String[] amenities) {
            this.amenities = amenities;
        }

        public boolean isLiveInLandlord() {
            return live_in_landlord;
        }

        public void setLiveInLandlord(boolean liveInLandlord) {
            this.live_in_landlord = liveInLandlord;
        }

        public long getSharedWith() {
            return shared_with;
        }

        public void setSharedWith(long sharedWith) {
            this.shared_with = sharedWith;
        }

        public boolean isBillsIncluded() {
            return bills_included;
        }

        public void setBillsIncluded(boolean billsIncluded) {
            this.bills_included = billsIncluded;
        }

        public boolean isBathroomShared() {
            return bathroom_shared;
        }

        public void setBathroomShared(boolean bathroomShared) {
            this.bathroom_shared = bathroomShared;
        }
    }
}