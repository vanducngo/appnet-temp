package challenges.sutrix.androidappnetclient.function.grammar.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * reated by root on 16/07/2015.
 */
@Table(name = "grammar", id = "id")
public class GrammarModel extends Model {
    @Column(name = "id", unique = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "description")
    private String description;

    public GrammarModel() {
        super();
    }

    public GrammarModel(String name, String meaning, String description) {
        super();
        this.name = name;
        this.meaning = meaning;
        this.description = description;
    }

    public void setCategoryId(long id) {
        this.id = id;
    }

    public long getCategoryId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<GrammarModel> getAll() {
        // This is how you execute a query
        return new Select()
                .from(GrammarModel.class)
                .orderBy("id ASC")
                .execute();
    }
}
