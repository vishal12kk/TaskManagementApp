package vishal.com.taskmanagementapp;

public class Date {
    private String main_title, description, date_view;

    public Date() {
    }

    public Date(String main_title, String description, String date_view) {
       this.main_title = main_title;
        this.description= description;
        this.date_view = date_view;
    }

    public String getMainTitle() {
        return main_title;
    }

    public String getDescription() {
       return description;
    }

    public String getDate() {
        return date_view;
    }


}