package Se.Iths.alexis;

import java.util.List;

public interface UserDao {



    List<User> getByName(String firstName);


    boolean remove(String firstName);
}
