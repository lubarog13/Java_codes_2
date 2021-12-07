package lubarog13.Entetys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Date birthday;
    private Date registrationDate;
    private String email;
    private String phone;
    private String GenderCode;
    private String PhotoPath;
    private ImageIcon image;

    public Client(int id, String firstName, String lastName, String patronymic, Date birthday, Date registrationDate, String email, String phone, String genderCode, String photoPath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.registrationDate = registrationDate;
        this.email = email;
        this.phone = phone;
        GenderCode = genderCode;
        PhotoPath = photoPath;
        try {
            this.image = new ImageIcon(ImageIO.read(Client.class.getClassLoader().getResource("book.jpg")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
