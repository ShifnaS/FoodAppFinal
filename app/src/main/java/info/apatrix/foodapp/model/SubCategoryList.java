package info.apatrix.foodapp.model;

/**
 * Created by pc on 22-Oct-18.
 */

public class SubCategoryList {
    private int subcat_id;
    private int parentcats_id;
    private String subcatname;
    private String subcatdescrip;
    private String subcatpic;

    public int getSubcat_id() {
        return subcat_id;
    }

    public void setSubcat_id(int subcat_id) {
        this.subcat_id = subcat_id;
    }

    public int getParentcats_id() {
        return parentcats_id;
    }

    public void setParentcats_id(int parentcats_id) {
        this.parentcats_id = parentcats_id;
    }

    public String getSubcatname() {
        return subcatname;
    }

    public void setSubcatname(String subcatname) {
        this.subcatname = subcatname;
    }

    public String getSubcatdescrip() {
        return subcatdescrip;
    }

    public void setSubcatdescrip(String subcatdescrip) {
        this.subcatdescrip = subcatdescrip;
    }

    public String getSubcatpic() {
        return subcatpic;
    }

    public void setSubcatpic(String subcatpic) {
        this.subcatpic = subcatpic;
    }
}
