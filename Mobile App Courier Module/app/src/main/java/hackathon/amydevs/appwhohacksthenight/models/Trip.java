package hackathon.amydevs.appwhohacksthenight.models;

import com.google.gson.annotations.SerializedName;

public class Trip {

    @SerializedName("trip_type")
    private String tripType;
    @SerializedName("country_code")
    private String countryCode;
    @SerializedName("arrival_time")
    private String arrivalTime;
    @SerializedName("departure_time")
    private String departureTime;
    @SerializedName("stay_duration")
    private String stayDuration;
    @SerializedName("stay_location")
    private String stayLocation;
    @SerializedName("city")
    private String city;
    @SerializedName("destination_name")
    private String destionationName;
    @SerializedName("description")
    private String description;
    @SerializedName("user_id")
    private int userId;

    public Trip() {
    }

    public String getDestionationName() {
        return destionationName;
    }

    public String getCity() {
        return city;
    }

    public String getStayLocation() {
        return stayLocation;
    }

    public String getStayDuration() {
        return stayDuration;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDestionationName(String destionationName) {
        this.destionationName = destionationName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStayLocation(String stayLocation) {
        this.stayLocation = stayLocation;
    }

    public void setStayDuration(String stayDuration) {
        this.stayDuration = stayDuration;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
