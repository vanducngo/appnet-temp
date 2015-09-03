package challenges.sutrix.androidappnetclient.function.vocabulary.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by root on 31/08/2015.
 */
@Table(name="VocabularyCategories", id = "_id")
public class VocabularyCategoryModel extends Model{
    @Column(name = "id",unique = true)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "meaning")
    private String meaning;

    public VocabularyCategoryModel(){
        super();
    }

    public VocabularyCategoryModel(String name, String meaning){
        super();
        this.name = name;
        this.meaning = meaning;
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
