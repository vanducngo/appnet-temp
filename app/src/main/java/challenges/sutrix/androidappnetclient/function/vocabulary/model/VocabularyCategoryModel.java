package challenges.sutrix.androidappnetclient.function.vocabulary.model;

/**
 * Created by root on 31/08/2015.
 */
public class VocabularyCategoryModel {
    private long id;
    private String name;
    private String meaning;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
