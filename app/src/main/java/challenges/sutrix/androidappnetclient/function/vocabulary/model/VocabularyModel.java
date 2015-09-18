package challenges.sutrix.androidappnetclient.function.vocabulary.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**reated by root on 16/07/2015.
 */
@Table(name = "word", id = "id")
public class VocabularyModel extends Model{
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "word")
    private  String word;

    @Column(name = "category_id")
    private long categoryId;

    @Column(name = "type")
    private  String type;

    @Column(name = "phonetic")
    private String phonetic;

    @Column(name = "sortmean")
    private  String shortMeanVietnamese;

    @Column(name = "mean")
    private  String meanVietnamese;

    @Column(name = "synonyms")
    private  String synonyms;

    @Column(name = "sentence")
    private  String example;

    @Column(name = "sentencemean")
    private  String exampleMeaning;

    @Column(name = "adequatemean")
    private  String paragraph;

    @Column(name = "remember")
    private int remember;

    public boolean isRemember() {
        boolean isRemember;
        if(remember >0){
            isRemember = true;
        }else{
            isRemember = false;
        }
        return isRemember;
    }

    public void setRemember(boolean isRemember) {
        if(isRemember  == true){
            remember = 1;
        }else{
            remember = 0;
        }
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

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getShortMeanVietnamese() {
        return shortMeanVietnamese;
    }

    public void setShortMeanVietnamese(String shortMeanVietnamese) {
        this.shortMeanVietnamese = shortMeanVietnamese;
    }

    public String getMeanVietnamese() {
        return meanVietnamese;
    }

    public void setMeanVietnamese(String meanVietnamese) {
        this.meanVietnamese = meanVietnamese;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
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

    public static List<VocabularyModel> getVocabularies(long id){
        return new Select()
                .from(VocabularyModel.class)
                .where("category_id = ?",id)
                .orderBy("id ASC")
                .execute();
    }
    public static List<VocabularyModel> getAll() {
        // This is how you execute a query
        return new Select()
                .from(VocabularyModel.class)
                .orderBy("id ASC")
                .execute();
    }
}
