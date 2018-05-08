package capstone.com.doctorfinder;

/**
 * Created by amr on 5/7/18.
 */

public class DoctorPreview
{
    public String Name,Image,Address,DUID;
    public double rating;

    public DoctorPreview(String name, String image, String address, String DUID, double rating) {
        Name = name;
        this.Image = image;
        Address = address;
        this.DUID = DUID;
        this.rating = rating;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDUID() {
        return DUID;
    }

    public void setDUID(String DUID) {
        this.DUID = DUID;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}

