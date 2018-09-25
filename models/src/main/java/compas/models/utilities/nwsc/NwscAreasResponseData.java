package compas.models.utilities.nwsc;

/**
 * Created by CLLSDJACKT013 on 9/20/2018.
 */
public class NwscAreasResponseData {
    private String description;
    private String id;
    private String longLabel;
    private String shortLabel;

    //default constructor
    public NwscAreasResponseData(){}

    //getters and setters here

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLongLabel() {
        return longLabel;
    }

    public void setLongLabel(String longLabel) {
        this.longLabel = longLabel;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }
}
