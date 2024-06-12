package storages.userStorage;

import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserStorage implements IUserStorage{
    private static UserStorage instance;

    private UserStorage() {}

    public static UserStorage getInstance() {
        if(instance == null){
            synchronized (UserStorage.class){
                if(instance == null){
                    instance = new UserStorage();
                }
            }
        }
        return instance;
    }


    private static final File file = new File("user.csv");

    @Override
    public void writeFile(List<User> userList) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder userString = new StringBuilder();
            for (User user : userList) {
                userString.append(user.getUserName()).append(",")
                        .append(user.getPassword()).append(",")
                        .append(user.getFirstName()).append(",")
                        .append(user.getLastName()).append(",")
                        .append(user.getPhoneNumber()).append(",")
                        .append(user.getRole()).append(",")
                        .append(user.getWallet()).append("\n");
            }
            bufferedWriter.write(userString.toString());
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readFile() {
        List<User> userList = new ArrayList<>();
        String line;
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                String[] userString = line.split(",");
                String username = userString[0];
                String password = userString[1];
                String firstName = userString[2];
                String lastName = userString[3];
                String phoneNumber = userString[4];
                String role = userString[5];
                double wallet =  Double.parseDouble(userString[6]);
                User user = new User(username, password, firstName, lastName, phoneNumber, role, wallet);
                userList.add(user);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
}
