package challenges.sutrix.androidappnetclient.function.vocabulary.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**reated by root on 16/07/2015.
 */
@Table(name = "Vocabularies", id = "_id")
public class VocabularyModel extends Model{
    @Column(name = "id", unique = true)
    private long id;
    @Column(name = "word")
    private  String word;
    @Column(name = "categoryId")
    private long categoryId;
    @Column(name = "phonetic")
    private String phonetic;
    @Column(name = "ShortMeanVietnamese")
    private  String ShortMeanVietnamese;
    @Column(name = "meanVietnamese")
    private  String meanVietnamese;
    @Column(name = "meanEnglish")
    private  String meanEnglish;
    @Column(name = "type")
    private  String type;
    @Column(name = "example")
    private  String example;
    @Column(name = "exampleMeaning")
    private  String exampleMeaning;
    @Column(name = "paragraph")
    private  String paragraph;
    @Column(name = "image")
    private  byte[] image;
    @Column(name = "isRemember")
    private  boolean isRemember;

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }

    public void setVocabularyId(long id) {
        this.id = id;
    }

    public long getVocabularyId(){
        return id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public long getCategory() {
        return categoryId;
    }

    public void setCategory(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getShortMeanVietnamese() {
        return ShortMeanVietnamese;
    }

    public void setShortMeanVietnamese(String shortMeanVietnamese) {
        ShortMeanVietnamese = shortMeanVietnamese;
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

    public String getExampleMeaning() {
        return exampleMeaning;
    }

    public void setExampleMeaning(String exampleMeaning) {
        this.exampleMeaning = exampleMeaning;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
