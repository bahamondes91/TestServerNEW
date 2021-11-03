package Se.Iths.alexis;

import javax.persistence.*;


@Entity
    @Table(name="Users")
    public class User {
        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private String id;
        private String firstName;
        private String lastName;

    public User(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {

        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

    @Override
    public String toString() {
        return "From Tabell User: " +
                "id = " + id +
                ", firstName = " + firstName + '\'' +
                ", lastName = " + lastName;
    }
}
