package hackathon.amydevs.appwhohacksthenight.models;


import com.google.gson.annotations.SerializedName;

public class Item {

    private double id;
    private double weight;
    @SerializedName("box_length")
    private double boxLength;

    @SerializedName("img_path")
    private String image;
    @SerializedName("box_height")
    private double boxHeight;
    @SerializedName("box_width")
    private double boxWidth;
    @SerializedName("est_value")
    private double estValue;
    private int fragile;
    private String remarks;
    @SerializedName("owner_id")
    private int ownerId;
    @SerializedName("country_code")
    private int countryCode;

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int isFragile() {
        return fragile;
    }

    public void setFragile(int fragile) {
        this.fragile = fragile;
    }

    public double getEstValue() {
        return estValue;
    }

    public void setEstValue(double estValue) {
        this.estValue = estValue;
    }

    public double getBoxWidth() {
        return boxWidth;
    }

    public void setBoxWidth(double boxWidth) {
        this.boxWidth = boxWidth;
    }

    public double getBoxLength() {
        return boxLength;
    }

    public void setBoxLength(double boxLength) {
        this.boxLength = boxLength;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getBoxHeight() {
        return boxHeight;
    }

    public void setBoxHeight(double boxHeight) {
        this.boxHeight = boxHeight;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
