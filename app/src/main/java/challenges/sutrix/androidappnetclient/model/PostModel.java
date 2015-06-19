package challenges.sutrix.androidappnetclient.model;

/**
 * Created by root on 19/05/2015.
 */
public class PostModel {
    //User information
    private User user;

    //Post in html format
    private String html;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * User information
     */
    public class User {
        private String username;

        //Avatar image information
        private Avatar avatar_image;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Avatar getAvatar_image() {
            return avatar_image;
        }

        public void setAvatar_image(Avatar avatar_image) {
            this.avatar_image = avatar_image;
        }
    }

    /**
     * Avatar image information
     */
    public class Avatar {
        private String url;
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
