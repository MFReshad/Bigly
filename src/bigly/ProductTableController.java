package bigly;

public class ProductTableController {

    String id, name, category, main_Price, offer_price, imageLoc, short_desc, desc, status;

    public ProductTableController(String id, String name, String category, String main_Price, String offer_price, String imageLoc, String short_desc, String desc, String status) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.main_Price = main_Price;
        this.offer_price = offer_price;
        this.imageLoc = imageLoc;
        this.short_desc = short_desc;
        this.desc = desc;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMain_Price() {
        return main_Price;
    }

    public void setMain_Price(String main_Price) {
        this.main_Price = main_Price;
    }

    public String getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(String offer_price) {
        this.offer_price = offer_price;
    }

    public String getImageLoc() {
        return imageLoc;
    }

    public void setImageLoc(String imageLoc) {
        this.imageLoc = imageLoc;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

    }
