package challenges.sutrix.androidappnetclient.function.vocabulary.model;

/**
 * Created by root on 16/07/2015.
 */
public class VocabularyModel {
    private long id;
    private String word;
    private String category;
    private String meanVietnamese;
    private String meanEnglish;
    private String type;
    private String example;
    private byte[] image;
    private boolean isRemember;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMeanVietnamese() {
        return meanVietnamese;
    }

    public void setMeanVietnamese(String meanVietnamese) {
        this.meanVietnamese = meanVietnamese;
    }

    public String getMeanEnglish() {
        return meanEnglish;
    }

    public void setMeanEnglish(String meanEnglish) {
        this.meanEnglish = meanEnglish;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }
}
